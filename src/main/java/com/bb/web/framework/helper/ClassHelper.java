package com.bb.web.framework.helper;

import com.bb.web.framework.annotation.Service;
import com.bb.web.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName ClassHelper
 * @Description: 类操作助手
 * @Author zzy
 * @Date 2020/10/28
 **/
public class ClassHelper {

    /**
     * 定义类集合(用于存放所加载的类)
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet=new HashSet<>();
        for(Class<?> cls:classSet){
            if(cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

}
