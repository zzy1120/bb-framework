package com.bb.web.framework.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Aspect
 * @Description: TODO
 * @Author zzy
 * @Date 2020/10/28
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
