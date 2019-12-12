package com.elegant.rpc.core.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019/8/12 20:23
 */
public class RpcCglibProxyHandler implements MethodInterceptor {

    /**
     * 维护目标对象
     */
    private Object target;

    public RpcCglibProxyHandler(Object target) {
        this.target = target;
    }

    /**
     * 给目标对象创建一个代理对象
     */
    public Object getProxyInstance() {
        //1.工具类
        Enhancer enhancer = new Enhancer();
        //2.设置父类
        enhancer.setSuperclass(target.getClass());
        //3.设置回调函数
        enhancer.setCallback(this);
        //4.创建子类(代理对象)
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("开始事务...");
        //执行目标对象的方法
        Object returnValue = method.invoke(target, objects);
        System.out.println("提交事务...");

        return returnValue;
    }
}
