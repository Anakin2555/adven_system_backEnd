package com.example.mybatisplus.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileService {

    Map uploadImage(MultipartFile file) throws IOException;

    Map uploadWord(MultipartFile file) throws IOException;

    Map uploadFeedbackAttachment(MultipartFile file) throws IOException;
}
