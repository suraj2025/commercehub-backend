package com.commercehub.user.service.impl;

import com.commercehub.user.dto.request.RegisterRequest;
import com.commercehub.user.dto.response.RegisterResponse;
import com.commercehub.user.entity.User;
import com.commercehub.user.enums.Role;
import com.commercehub.user.exception.EmailAlreadyExistsException;
import com.commercehub.user.exception.MobileAlreadyExistsException;
import com.commercehub.user.mapper.UserMapper;
import com.commercehub.user.repository.UserRepository;
import com.commercehub.user.service.UserService;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
   

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = userMapper.toEntity(request);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(
        "Email already exists");
        }
        if (userRepository.existsByMobileNumber(user.getMobileNumber())) {
            throw new MobileAlreadyExistsException(
        "Mobile number already exists");
        }

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.CUSTOMER);
        // user.setEnabled(true);
        // user.setCreatedAt(LocalDateTime.now());
        // user.setUpdatedAt(LocalDateTime.now());
        User saved=userRepository.save(user);
        return userMapper.toResponse(saved);

    }
}