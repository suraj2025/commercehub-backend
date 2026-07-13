package com.commercehub.customer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String profile(Authentication authentication) {

        return "Welcome " + authentication.getName();
    }
}