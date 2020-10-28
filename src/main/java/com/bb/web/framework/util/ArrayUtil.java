package com.bb.web.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @ClassName ArrayUtil
 * @Description: 数组工具类
 * @Author zzy
 * @Date 2020/10/28
 **/
public class ArrayUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    /**
     * 判断字符串是否为非空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }
}
