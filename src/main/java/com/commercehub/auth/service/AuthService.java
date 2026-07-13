package com.commercehub.auth.service;

import com.commercehub.auth.dto.LoginRequest;
import com.commercehub.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}