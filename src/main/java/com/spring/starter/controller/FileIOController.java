package com.spring.starter.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.starter.model.dto.res.CommonResponseDto;
import com.spring.starter.model.dto.res.FileResponseDto;
import com.spring.starter.service.FileStorageService;
import com.spring.starter.utils.constant.ApiPathConstant;

@RestController
@RequestMapping(
  ApiPathConstant.API +
  ApiPathConstant.VERSION +
  ApiPathConstant.FILE
)
public class FileIOController {

  @Autowired
  private FileStorageService fileStorageService;
  
  @PostMapping("upload")
  public ResponseEntity<CommonResponseDto<FileResponseDto>> uploadFileHandler(
    @RequestParam("file") MultipartFile file
  ) {
    String fileName = this.fileStorageService.storeFile(file);
    return ResponseEntity
    .status(HttpStatus.CREATED)
    .body(
      new CommonResponseDto<>(
        HttpStatus.CREATED.value(),
        "SuccessFully Uploaded File",
        new FileResponseDto(
          fileName,
          "get/" + fileName
        )
      )
    );
  }

  @GetMapping("get/{fileName}")
  public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
    try {
      String extension = fileName.split("\\.")[1];
      MediaType mediaType = null;

      if (extension.equals("png")) {
        mediaType = MediaType.IMAGE_PNG;
      } else if (extension.equals("jpg")) {
        mediaType = MediaType.IMAGE_JPEG;
      } else if (extension.equals("pdf")) {
        mediaType = MediaType.APPLICATION_PDF;
      }

      Path path = Paths.get("assets/" + fileName);
      byte[] file = Files.readAllBytes(path);

      return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(mediaType)
      .body(file);
    } catch (Exception e) {
      throw new RuntimeException("File not found");
    }
  }

}
