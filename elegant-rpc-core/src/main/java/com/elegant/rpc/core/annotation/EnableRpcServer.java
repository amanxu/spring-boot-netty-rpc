package com.elegant.rpc.core.annotation;

import com.elegant.rpc.core.config.NettyServerConfigProperties;
import com.elegant.rpc.core.config.NettyServerStarter;
import com.elegant.rpc.core.service.impl.RpcServerServiceImpl;
import org.springframework.context.annotation.Import;

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
@Import({NettyServerStarter.class, NettyServerConfigProperties.class, RpcServerServiceImpl.class})
public @interface EnableRpcServer {
}
