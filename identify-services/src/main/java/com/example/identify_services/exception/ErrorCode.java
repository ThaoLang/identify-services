package com.example.identify_services.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999,"Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR), // error code 500
    USER_EXISTED(1001,"User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002,"Username must be at least 4 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005,"User not existed",HttpStatus.NOT_FOUND), //error code 404
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED), //error code 401
    UNAUTHORIZED(1007,"You do not have permission", HttpStatus.FORBIDDEN ),
    ;

    ErrorCode(int code,String message, HttpStatusCode statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode=statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
