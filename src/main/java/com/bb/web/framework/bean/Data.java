package com.bb.web.framework.bean;

/**
 * @ClassName Data
 * @Description: 返回数据对象
 * @Author zzy
 * @Date 2020/10/28
 **/
public class Data {
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
