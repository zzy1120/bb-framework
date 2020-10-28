package com.bb.web.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName JsonUtil
 * @Description: JSON 工具类
 * @Author zzy
 * @Date 2020/10/28
 **/
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 将 POJO 转为 JSON
     */
    public static <T> String toJson(T obj) {
        String json;

        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }

        return json;
    }

    /**
     * 将 JSON 转换为 POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;

        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }

        return pojo;
    }
}
