package com.commercehub.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.commercehub.common.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Validation Exceptions
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
                        MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                }

                return ResponseEntity.badRequest()
                                .body(ApiResponse.failure(
                                                "Validation failed",
                                                errors));
        }

        /**
         * Duplicate Email / Business Exceptions
         */
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(
                        IllegalArgumentException ex) {

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(ApiResponse.failure(
                                                ex.getMessage(),
                                                null));
        }

        /**
         * Login Failure
         */
        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(
                        BadCredentialsException ex) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(ApiResponse.failure(
                                                "Invalid email or password",
                                                null));
        }

        /**
         * Any Unhandled Exception
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<String>> handleException(
                        Exception ex) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ApiResponse.failure(
                                                ex.getMessage(),
                                                null));
        }

        /**
         * Access Denied
         */
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(
                        AccessDeniedException ex) {

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(ApiResponse.failure(
                                                "Access Denied",
                                                null));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(
                        ResourceNotFoundException ex) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.failure(
                                                ex.getMessage(),
                                                null));
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ApiResponse<String>> handleDuplicateResourceException(
                        DuplicateResourceException ex) {

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(ApiResponse.failure(
                                                ex.getMessage(),
                                                null));
        }
}