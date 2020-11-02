package com.bb.web.framework.bean;

import java.io.InputStream;

/**
 * @ClassName FileParam
 * @Description: 封装上传文件参数
 * @Author zzy
 * @Date 2020/10/28
 **/
public class FileParam {

    //文件表单的字段名
    private String fieldName;
    //上传文件的文件名
    private String fileName;
    //上传文件的大小
    private long fileSize;
    //文件类型
    private String contentType;
    //字节输入流
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
