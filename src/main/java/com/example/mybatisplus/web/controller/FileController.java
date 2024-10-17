package com.example.mybatisplus.web.controller;


import com.example.mybatisplus.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {


    protected FileService fileService;
    protected ResourceLoader resourceLoader;

    public FileController(FileService fileService, ResourceLoader resourceLoader) {
        this.fileService = fileService;
        this.resourceLoader = resourceLoader;
    }

    @ApiOperation(value = "图片上传", notes = "文件上传")
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> uploadImage(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> map;
        map = fileService.uploadImage(file);
        return ResponseEntity.ok().body(map);
    }

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @RequestMapping(value = "/uploadWord", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> uploadWord(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> map;
        map = fileService.uploadWord(file);
        return ResponseEntity.ok().body(map);
    }

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @RequestMapping(value = "/uploadFeedbackAttachment", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> uploadFeedbackAttachment(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> map;
        map = fileService.uploadFeedbackAttachment(file);
        return ResponseEntity.ok().body(map);
    }

    private static String suffix(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i == -1 ? "" : fileName.substring(i + 1);
    }

}
