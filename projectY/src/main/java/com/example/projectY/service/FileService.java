package com.example.projectY.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public void createFolder(String folder) throws URISyntaxException;
    public Boolean isExistFolder(String folder) throws URISyntaxException;
    public String createFile(String folder, MultipartFile file) throws URISyntaxException, IOException;
}
