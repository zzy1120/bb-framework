package com.bb.web.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName ServletHelper
 * @Description: TODO
 * @Author zzy
 * @Date 2020/10/28
 **/
public class ServletHelper {
    private static Logger logger = LoggerFactory.getLogger(ServletHelper.class);

    private static ThreadLocal<ServletHelper> SERVLET_HELPER_HODLER = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_HODLER.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destroy() {
        SERVLET_HELPER_HODLER.remove();
    }

    /**
     * 获取Request对象
     */
    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HODLER.get().request;
    }

    /**
     * 获取Response对象
     */
    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HODLER.get().response;
    }

    /**
     * 获取 Session 对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 ServletContext 对象
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入 Request 中
     */
    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 从 Request 中获取属性
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从 Request 中移出属性
     */
    public static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     */
    public static void sendRedirect(String location) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            logger.error("redirect failure", e);
        }
    }

    /**
     * 将属性放入 Session 中
     */
    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从 Session 中获取属性
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String key) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * 从 Session 中移出属性
     */
    public static void removeSessionAttribute(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 使 Session 失效
     */
    public static void invalidateSession() {
        getRequest().getSession().invalidate();
    }
}
