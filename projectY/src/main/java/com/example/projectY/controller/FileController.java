package com.example.projectY.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.projectY.service.impliment.FileServiceImpl;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.projectY.response.ApiResponseDTO;
import com.example.projectY.response.ResCreateFileDTO;
import com.example.projectY.response.ResponseStatusDTO;
import com.example.projectY.service.FileService;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    private final FileServiceImpl fileServiceImpl;
    @Value("${projectY.upload.base-URI}")
    private String baseURI;

    @Autowired FileService fileService;

    FileController(FileServiceImpl fileServiceImpl) {
        this.fileServiceImpl = fileServiceImpl;
    }

    @PostMapping("/files")
    public ResponseEntity<ApiResponseDTO<?>> uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException {
        if (file.isEmpty()) {
            throw new FileUploadException("File not found");
        }

        List<String> allowExtension = Arrays.asList("pdf","png","jpg","jpeg","docx");
        Boolean isValidExtension = allowExtension.stream().anyMatch(item -> file.getOriginalFilename().toLowerCase().endsWith(item));
        if (!isValidExtension) {
            throw new FileUploadException("Invalid content");
        }

        // Create folder
        if (folder != null) {
            this.fileService.createFolder(folder);
        }
        String createdFile = this.fileService.createFile(folder, file);
        ResCreateFileDTO resCreateFileDTO = new ResCreateFileDTO();
        resCreateFileDTO.setFileName(createdFile);
        resCreateFileDTO.setCreatedAt(Instant.now());

        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(HttpStatus.OK, "Upload file success");
        return ResponseEntity.ok().body(new ApiResponseDTO<>(responseStatusDTO, resCreateFileDTO, LocalDateTime.now()));
    }
}
