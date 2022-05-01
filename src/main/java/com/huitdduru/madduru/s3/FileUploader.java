package com.huitdduru.madduru.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploader {

    String uploadFile(MultipartFile file) throws IOException;

    File getFile(String imageName) throws IOException;

    String getUrl(String imageName);
}
