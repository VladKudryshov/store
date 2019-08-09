package com.example.demo.service.impl;

import com.example.demo.service.IFileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements IFileService {

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = RandomStringUtils.randomAlphanumeric(5) + "_" + file.getOriginalFilename();
            Path path = Paths.get("/opt/server/temp" + fileName);
            Files.copy(file.getInputStream(), path);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
