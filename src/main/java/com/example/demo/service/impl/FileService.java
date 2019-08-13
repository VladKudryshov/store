package com.example.demo.service.impl;

import com.example.demo.service.IFileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService implements IFileService {
    private FTPClient ftpClient;

    @Override
    public String uploadFile(MultipartFile file) {

        try {
            ftpClient = new FTPClient();
            ftpClient.connect("165.22.89.115");
            ftpClient.login("reports", "1");
            String fileName = RandomStringUtils.randomAlphanumeric(5) + "_" + file.getOriginalFilename();
            shareFileToFTP(file, fileName);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void shareFileToFTP(MultipartFile file, String fileName) throws IOException {

        try (InputStream inputStream = file.getInputStream()) {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            boolean done = ftpClient.storeFile(fileName, inputStream);


            if (!done) {
                String error = ftpClient.getReplyString();
                System.out.println(error);
            }
        } finally {
            closeFtpConnection();
        }
    }

    private void closeFtpConnection() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
