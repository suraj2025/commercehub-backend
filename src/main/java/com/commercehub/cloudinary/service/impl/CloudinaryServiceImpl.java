package com.commercehub.cloudinary.service.impl;


import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.commercehub.cloudinary.dto.CloudinaryResponse;
import com.commercehub.cloudinary.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryResponse uploadImage(MultipartFile file)
            throws IOException {

        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap());

        String url = result.get("secure_url").toString();

        String publicId = result.get("public_id").toString();

        return new CloudinaryResponse(
                url,
                publicId
        );
    }

    @Override
    public void deleteImage(String publicId)
            throws IOException {

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.emptyMap());
    }
}
