package com.spring.starter.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.starter.config.FileStorageProperties;
import com.spring.starter.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  private final Path fileStorageLocation;

  public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
    .toAbsolutePath()
    .normalize();
  }

  @Override
  public String storeFile(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    String[] splitName = fileName.split("\\.");

    fileName = UUID.randomUUID().toString() + "." + splitName[splitName.length - 1];

    Path targetLocation = this.fileStorageLocation.resolve(fileName);
    System.out.println(targetLocation);

    try {
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (IOException e) {
      throw new RuntimeException("Could not store the file. Please try again!", e);
    }
  }
  
}
