package com.lzos.steels.admin.controller;

import com.lzos.steels.admin.common.CommonEnum;
import com.lzos.steels.admin.common.ResultBody;
import com.lzos.steels.admin.entity.vo.FileVo;
import com.lzos.steels.admin.exception.BizException;
import com.lzos.steels.admin.service.FileSystemService;
import com.lzos.steels.admin.utils.FileUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileSystemController {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemController.class);

    @Value("${file.upload.dir}")
    private String originDir;

    @Autowired
    private FileSystemService fileSystemService;

    /**
     * 上传文件
     * @param files
     * @return ResultBody
     * @throws Exception
     */
    @PostMapping("/uploadFile.json")
    public ResultBody uploadFile(@RequestParam("files") MultipartFile[] files) throws Exception {

        if (files.length == 0 || files == null) {
            throw new BizException("1002", "上传文件为空,请选择上传文件");
        }
        ArrayList<FileVo> fileVos = new ArrayList<>();

        for (MultipartFile file : files) {

            String fileName = fileSystemService.storeFile(file);

            String downloadUri = getDownloadUri(fileName);

            FileVo fileVo = new FileVo(fileName, downloadUri, file.getContentType(), file.getSize());

            fileVos.add(fileVo);

        }

        return ResultBody.success("1001", "上传文件成功", fileVos);

    }

    /**
     * 下载文件
     * @param response
     * @param file
     * @return String
     * @throws Exception
     */
    @GetMapping("/download.json")
    public String downloadFile(HttpServletResponse response, @Param("file") String file) throws Exception {
        return fileSystemService.downloadFile(response, file);
    }

    @PostMapping("/delete.json")
    public ResultBody deleteFiles(HttpServletRequest request, @RequestParam("test") String test) {

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = String.valueOf(headerNames.nextElement());
            String value = request.getHeader(key);
            System.out.println(key + ": " + value);
        }

        System.out.println(test);

        return null;
    }

    /**
     * 获取文件列表
     * @param src
     * @return ResultBody
     */
    @GetMapping("/list.json")
    public ResultBody listFiles(String src) {

        File file = new File(src);
        ArrayList<Map<String, Object>> files = FileUtil.listFile(file);
        return ResultBody.success(CommonEnum.SUCCESS.getResultCode(), "获取文件列表成功", files);

    }

    public static void main(String[] args) {
        File file = new File("/home/ssss");
        File[] files = file.listFiles();
        System.out.println(files);
    }

    private String getDownloadUri(String fileName) {

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/static/image/")
                .path(fileName)
                .toUriString();

        return downloadUri;
    }



}
