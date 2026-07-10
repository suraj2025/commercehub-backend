package com.commercehub.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercehub.common.dto.ApiResponse;
import com.commercehub.user.dto.request.RegisterRequest;
import com.commercehub.user.dto.response.RegisterResponse;
import com.commercehub.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(

            @Valid
            @RequestBody
            RegisterRequest request){

        RegisterResponse response =
                userService.register(request);

        ApiResponse<RegisterResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        "User registered successfully",
                        response
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);

    }

}
