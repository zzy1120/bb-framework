package com.bb.web.framework.helper;

import com.bb.web.framework.annotation.Aspect;
import com.bb.web.framework.annotation.Service;
import com.bb.web.framework.proxy.AspectProxy;
import com.bb.web.framework.proxy.Proxy;
import com.bb.web.framework.proxy.ProxyManager;
import com.bb.web.framework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @ClassName AopHelper
 * @Description: TODO
 * @Author zzy
 * @Date 2020/10/28
 **/
public class AopHelper {
    private static Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetProxy = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetProxy.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            logger.error("aop failure", e);
        }
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        //key-代理类，value-被代理的类集合
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        //获取实现了AspectProxy的所有类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            //如果实现类含有Aspect注解
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null & !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    /**
     * 获取被代理目标的所有代理类
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        /* key - 目标类, value - 代理类 */
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();

        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }


}
