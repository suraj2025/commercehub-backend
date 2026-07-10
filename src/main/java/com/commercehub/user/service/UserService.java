package com.commercehub.user.service;

import com.commercehub.user.dto.request.RegisterRequest;
import com.commercehub.user.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

}