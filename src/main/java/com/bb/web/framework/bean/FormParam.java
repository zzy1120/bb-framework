package com.bb.web.framework.bean;

/**
 * @ClassName FormParam
 * @Description: 封装上传文件参数
 * @Author zzy
 * @Date 2020/10/28
 **/
public class FormParam {
    private String fieldName;
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Object getFieldValue() {
        return this.fieldValue;
    }
}
