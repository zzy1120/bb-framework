package com.bb.web.framework.bean;

import java.lang.reflect.Method;

/**
 * @ClassName Handler
 * @Description:
 * @Author zzy
 * @Date 2020/10/28
 **/
public class Handler {

    /**
     * Controller类
     */
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

}
