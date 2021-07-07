package com.lzos.steels.admin.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileSystemService {

    String storeFile(MultipartFile file) throws Exception;

    String downloadFile(HttpServletResponse response, String file) throws Exception;

}
