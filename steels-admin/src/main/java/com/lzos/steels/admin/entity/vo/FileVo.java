package com.lzos.steels.admin.entity.vo;

public class FileVo {

    private String fileName;

    private String downloadUri;

    private String contentType;

    private Long fileSize;

    public FileVo(String fileName, String downloadUri, String contentType, Long fileSize) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

}
