package com.bb.web.framework.helper;

import com.bb.web.framework.annotation.Inject;
import com.bb.web.framework.util.ArrayUtil;
import com.bb.web.framework.util.CollectionUtil;
import com.bb.web.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @ClassName IocHelper
 * @Description: 依赖注入助手类
 * @Author zzy
 * @Date 2020/10/28
 **/
public class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历bean map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从beanMap中获取bean类与bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                //获取bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历bean field
                    for (Field beanField : beanFields) {
                        //如果当前字段含有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            //获取值
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            //通过反射设置值
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
