package com.example.projectY.service.impliment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.projectY.service.FileService;

@Service
public class FileServiceImpl implements FileService {
    @Value("${projectY.upload.base-URI}")
    private String baseURI;

    public void createFolder(String folder) throws URISyntaxException{
        URI uri = new URI(baseURI+folder);
        Path path = Paths.get(uri);
        File newFolder = new File(path.toString());

        try {
            Files.createDirectories(newFolder.toPath());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Boolean isExistFolder(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File newFolder = new File(path.toString());
        return newFolder.isDirectory();
    }

    public String createFile(String folder, MultipartFile file) throws URISyntaxException, IOException {
        String finalName = System.currentTimeMillis()+"-"+file.getOriginalFilename();
        URI uri = new URI(baseURI+folder + "/" + finalName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path,StandardCopyOption.REPLACE_EXISTING);
        }
        return folder + "/" + finalName;
    }
}
