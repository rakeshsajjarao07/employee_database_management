package com.empmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.*;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String employeeUploadFolder;   // uploads/employee-images

    @Value("${file.user-upload-dir}")
    private String userUploadFolder;       // uploads/users

    /* ==========================================================
       SAVE BASE64 IMAGE (User Signup DP)
    ========================================================== */
    public String saveBase64Image(String base64) throws IOException {

        if (base64.contains(",")) base64 = base64.split(",")[1];

        byte[] data = Base64.getDecoder().decode(base64);

        Path folder = Paths.get(userUploadFolder);
        if (!Files.exists(folder)) Files.createDirectories(folder);

        String fileName = UUID.randomUUID() + ".png";
        Path target = folder.resolve(fileName);

        try (FileOutputStream fos = new FileOutputStream(target.toFile())) {
            fos.write(data);
        }

        return fileName;
    }

    /* ==========================================================
       SAVE MULTIPART IMAGE (Employee Image)
       Auto converts WEBP → PNG
    ========================================================== */
    public String saveMultipart(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) return null;

        String original = file.getOriginalFilename().toLowerCase();
        String extension = "";

        if (original.contains(".")) {
            extension = original.substring(original.lastIndexOf("."));
        }

        // ensure folder exists
        Path folder = Paths.get(employeeUploadFolder);
        if (!Files.exists(folder)) Files.createDirectories(folder);

        // Always save final file as PNG
        String finalName = UUID.randomUUID() + ".png";
        Path target = folder.resolve(finalName);

        /* ---------------------------------------------------------
           WEBP → PNG CONVERSION
        --------------------------------------------------------- */
        if (extension.equals(".webp")) {

            BufferedImage webpImage = convertWebPToPNG(file.getInputStream());

            if (webpImage == null) {
                throw new IOException("Failed to decode WEBP image");
            }

            ImageIO.write(webpImage, "png", target.toFile());
            return finalName;
        }

        /* ---------------------------------------------------------
           NORMAL IMAGE (png/jpg/jpeg)
        --------------------------------------------------------- */
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return finalName;
    }

    /* ==========================================================
       WEBP → PNG CONVERSION
    ========================================================== */
    private BufferedImage convertWebPToPNG(InputStream webpStream) throws IOException {
        return ImageIO.read(webpStream);   // Works because TwelveMonkeys handles WebP codec
    }
}
