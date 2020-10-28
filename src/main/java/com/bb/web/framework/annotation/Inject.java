package com.bb.web.framework.annotation;

/**
 * @ClassName Inject
 * @Description: TODO
 * @Author zzy
 * @Date 2020/10/28
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
}
