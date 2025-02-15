package com.example.identify_services.controller;

import com.example.identify_services.dto.request.ApiResponse;
import com.example.identify_services.dto.request.UserCreationRequest;
import com.example.identify_services.dto.request.UserUpdateRequest;
import com.example.identify_services.entity.User;
import com.example.identify_services.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse=new ApiResponse<>();

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
    ApiResponse<User> getUserById(@PathVariable("userId") String userId){
        ApiResponse<User> apiResponse=new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));

        return apiResponse;
    }

    @PutMapping("/{userId}")
    ApiResponse<User> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        ApiResponse<User> apiResponse=new ApiResponse<>();
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
