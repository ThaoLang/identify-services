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
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId,request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
