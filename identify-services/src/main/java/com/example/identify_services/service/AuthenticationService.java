package com.example.identify_services.service;

import com.example.identify_services.dto.request.AuthenticationRequest;
import com.example.identify_services.dto.request.IntroSpectRequest;
import com.example.identify_services.dto.response.AuthenticationResponse;
import com.example.identify_services.dto.response.IntrospectResponse;
import com.example.identify_services.entity.User;
import com.example.identify_services.exception.AppException;
import com.example.identify_services.exception.ErrorCode;
import com.example.identify_services.repository.UserRepository;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.secret}")
    protected  String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws KeyLengthException {
        var user=userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(),user.getPassword());

        if (!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public IntrospectResponse introspect (IntroSpectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier=new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT=SignedJWT.parse(token);

        var verified= signedJWT.verify(verifier);

        Date expiryTime= signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }

    public String generateToken(User user) throws KeyLengthException {
        JWSHeader header= new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet =new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ltt.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope",buildScope(user)) // custom value
                .build();

        Payload payload=new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject=new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
           log.error("Can not create token",e);
            throw new RuntimeException(e);
        }
    }

    private  String buildScope(User user){
        StringJoiner stringJoiner= new StringJoiner(" ");

        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role ->{
                stringJoiner.add("ROLE_"+ role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions()
                            .forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }

        return stringJoiner.toString();
    }
}
