package com.oguzhan.food_delivery.service.imageUpload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    String uploadImage(MultipartFile file) throws IOException;
}
