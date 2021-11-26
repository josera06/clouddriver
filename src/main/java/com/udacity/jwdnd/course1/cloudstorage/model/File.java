package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Blob;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;
    
    public File(){
    }

    public File(Integer fileId) {
        this.fileId = fileId;
    }

    public File(Integer fileId, String fileName, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("File{fileId=").append(fileId);
        sb.append(", fileName=").append(fileName);
        sb.append(", contentType=").append(contentType);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", userId=").append(userId);
        sb.append(", fileData=").append(fileData);
        sb.append('}');
        return sb.toString();
    }

    
}
