package com.commercehub.user.mapper;

import org.springframework.stereotype.Component;

import com.commercehub.user.dto.request.RegisterRequest;
import com.commercehub.user.dto.response.RegisterResponse;
import com.commercehub.user.entity.User;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request) {
        // Convert DTO -> Entity
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setMobileNumber(request.mobileNumber());
        return user;
    }

    public RegisterResponse toResponse(User user) {
        // Convert Entity -> Response
        return new RegisterResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getMobileNumber(),
            user.getRole()
        );
    }
}
