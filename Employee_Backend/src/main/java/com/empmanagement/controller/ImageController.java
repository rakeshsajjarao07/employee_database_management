package com.empmanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/uploads")
public class ImageController {

    @Value("${file.upload-dir}")
    private String employeeUploadDir;

    @Value("${file.user-upload-dir}")
    private String userUploadDir;

    /**
     * GET /uploads/{filename}
     * Will search both employeeUploadDir and userUploadDir and return the first match.
     */
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path empPath = Paths.get(employeeUploadDir).resolve(filename).normalize();
            Path userPath = Paths.get(userUploadDir).resolve(filename).normalize();

            Path fileToServe = null;
            if (Files.exists(empPath) && !Files.isDirectory(empPath)) {
                fileToServe = empPath;
            } else if (Files.exists(userPath) && !Files.isDirectory(userPath)) {
                fileToServe = userPath;
            }

            if (fileToServe == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource resource = new UrlResource(fileToServe.toUri());
            String contentType = Files.probeContentType(fileToServe);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
