package com.example.Fstock.ultis;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Component
public class CloudinaryUltis {
    @Autowired
    private Cloudinary cloudinary;
    public String getImageUrlAfterUpload(MultipartFile multipartFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("folder", "fstock"));
        return (String) uploadResult.get("secure_url");
    }
    public String getPublicIdImageCloudinary(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String imageName = parts[parts.length - 1].split("\\.")[0];
        String folder = parts[parts.length - 2];
        String publicId = folder + "/" + imageName;
        return publicId;
    }
}
