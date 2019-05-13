package com.elegant.rpc.core.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description: 使用动态代理模式自动匹配Bean实例
 * @author: xiaoxu.nie
 * @date: 2019-01-09 13:53
 */
@Slf4j
public class RpcDynamicProxyHandler implements InvocationHandler {

    private Object proxyInstance;

    public Object createInstance(Object target) {
        this.proxyInstance = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //before ：doSomething(); 动态代理类为目标类额外做的前置方法可以是简单的sysout，也可以是你具体的方法

        Object invoke = method.invoke(proxyInstance, args);

        // after : doSomething(); 动态代理类为目标类额外做的后置方法,可以是简单的sysout，也可以是你具体的方法

        return invoke;
    }
}
