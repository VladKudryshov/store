package com.example.demo.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPUtils {
    private static FTPClient client = new FTPClient();

    public static void share(File file) {

        try {
            client.connect("84.201.155.169", 21);
            client.login("reports", "1");
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);


            try (InputStream inputStream = new FileInputStream(file)) {
                boolean done = client.storeFile(file.getName(), inputStream);
                if (!done) {
                    System.out.println("Can't share: " + client.getReplyString());
                }
            }
        } catch (IOException e) {
            System.out.println("Can't share: " + e.getMessage());
        }
    }
}
