package com.lzos.steels.admin.service.impl;

import com.lzos.steels.admin.exception.BizException;
import com.lzos.steels.admin.service.FileSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileSystemServiceImpl implements FileSystemService {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemServiceImpl.class);

    @Value("${file.upload.dir}")
    private String originDir;

    @Override
    public String storeFile(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new BizException("1002", "上传文件为空,请选择上传文件");
        }

        String fileName = file.getOriginalFilename();
        File dest = new File(originDir +'/'+ fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        file.transferTo(dest);
        return fileName;

    }

    @Override
    public String downloadFile(HttpServletResponse response, String file) throws Exception {

        File filePath = new File(originDir +'/'+ file);

        if(!filePath.exists()){
            return "下载文件不存在";
        }

        if (filePath.isDirectory()) {
            File[] files = filePath.listFiles();
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) filePath.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file);

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(filePath);
            bis = new BufferedInputStream(fis);

            byte[] buff = new byte[1024];
            os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (Exception e) {
            logger.error("下载失败： {}", e);
            return "下载失败";
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return "下载成功";
    }


}
