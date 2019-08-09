package com.example.demo.controllers;

import com.example.demo.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/file")
public class FileControllerController {

    @Autowired
    private IFileService fileService;

    @PostMapping(value = "upload")
    public String uploadFiles(@RequestParam("file") MultipartFile uploadFile) {
        String fileName = fileService.uploadFile(uploadFile);
        return String.format("http://165.22.89.115/files/%s", fileName);
    }
}
