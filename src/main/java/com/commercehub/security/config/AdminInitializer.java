package com.commercehub.security.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.commercehub.user.entity.User;
import com.commercehub.user.enums.Role;
import com.commercehub.user.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        
    public AdminInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@commercehub.com")) {

            User admin = new User();

            admin.setFirstName("Admin");
            admin.setLastName("CommerceHub");
            admin.setEmail("admin@commercehub.com");
            admin.setMobileNumber("9999999999");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }

}
