package com.bb.web.framework.helper;

import com.bb.web.framework.util.ClassUtil;

/**
 * @ClassName HelperLoader
 * @Description: 加载相应的 Helper 类
 * @Author zzy
 * @Date 2020/10/28
 **/
public class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
