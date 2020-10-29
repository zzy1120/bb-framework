package com.bb.web.framework.proxy;

/**
 * @ClassName Proxy
 * @Description:
 * @Author zzy
 * @Date 2020/10/28
 **/
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
