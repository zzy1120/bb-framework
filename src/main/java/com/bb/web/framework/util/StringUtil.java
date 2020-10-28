package com.bb.web.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName StringUtil
 * @Description: 字符串工具类
 * @Author zzy
 * @Date 2020/10/28
 **/
public class StringUtil {

    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

}
