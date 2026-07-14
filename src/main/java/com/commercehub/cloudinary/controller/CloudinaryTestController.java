package com.commercehub.cloudinary.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.commercehub.cloudinary.dto.CloudinaryResponse;
import com.commercehub.cloudinary.service.CloudinaryService;

@RestController
@RequestMapping("/api/v1/cloudinary")
public class CloudinaryTestController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryTestController(
            CloudinaryService cloudinaryService) {

        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public CloudinaryResponse upload(
            @RequestParam MultipartFile file)
            throws IOException {

        return cloudinaryService.uploadImage(file);
    }
}