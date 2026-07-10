package com.commercehub.user.dto.response;

import com.commercehub.user.enums.Role;

public record RegisterResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String mobileNumber,
    Role role
) {
}