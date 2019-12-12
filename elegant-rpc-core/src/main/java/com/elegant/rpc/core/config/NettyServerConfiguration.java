package com.elegant.rpc.core.config;

import com.elegant.rpc.core.service.IRpcServerService;
import com.elegant.rpc.core.service.impl.RpcServerServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019/12/12 17:12
 */
@Configuration
public class NettyServerConfiguration {

    @Bean
    public NettyServerConfigProperties nettyServerConfigProperties() {
        return new NettyServerConfigProperties();
    }

    @Bean
    @ConditionalOnBean(NettyServerConfigProperties.class)
    public NettyServerStarter nettyServerStarter() {
        return new NettyServerStarter();
    }

    @Bean
    public IRpcServerService rpcServerService() {
        return new RpcServerServiceImpl();
    }
}
