package com.example.identify_services.controller;

import com.example.identify_services.dto.request.ApiResponse;
import com.example.identify_services.dto.request.UserCreationRequest;
import com.example.identify_services.dto.request.UserUpdateRequest;
import com.example.identify_services.dto.response.UserResponse;
import com.example.identify_services.entity.User;
import com.example.identify_services.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> parent of 3239293 (authorization)
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse=new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<User>> getUsers(){
        ApiResponse<List<User>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.getUsers());

        return apiResponse;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId){
        ApiResponse<UserResponse> apiResponse=new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));

        return apiResponse;
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> apiResponse=new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(userId,request));

        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    ApiResponse deleteUser(@PathVariable String userId){
        ApiResponse<User> apiResponse=new ApiResponse<>();
        userService.deleteUser(userId);
        apiResponse.setMessage("User has been deleted");

        return apiResponse;
    }
}
