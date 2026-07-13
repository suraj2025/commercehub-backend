package com.commercehub.test.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v1/test")
    public Map<String, Object> test(Authentication authentication) {

        return Map.of(
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities(),
                "authenticated", authentication.isAuthenticated()
        );
    }
}