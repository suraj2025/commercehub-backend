package com.commercehub.cloudinary.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.commercehub.cloudinary.dto.CloudinaryResponse;

public interface CloudinaryService {

    CloudinaryResponse uploadImage(MultipartFile file)
            throws IOException;

    void deleteImage(String publicId)
            throws IOException;
}