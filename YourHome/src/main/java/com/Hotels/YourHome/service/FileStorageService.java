package com.Hotels.YourHome.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;


public interface FileStorageService {

    String saveImg(MultipartFile file) throws IOException;

    Resource loadImg(String fileName) throws MalformedURLException;
}
