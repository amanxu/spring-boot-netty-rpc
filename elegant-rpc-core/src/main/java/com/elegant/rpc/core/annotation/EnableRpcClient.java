package com.elegant.rpc.core.annotation;

import com.elegant.rpc.core.config.NettyClientConfigProperties;
import com.elegant.rpc.core.config.NettyClientStarter;
import com.elegant.rpc.core.service.impl.RpcClientServiceImpl;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-09 18:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableAsync
@Import({NettyClientConfigProperties.class, NettyClientStarter.class, RpcClientServiceImpl.class})
public @interface EnableRpcClient {
}
