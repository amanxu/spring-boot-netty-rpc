package com.elegant.rpc.core.config;

import com.elegant.rpc.core.client.ContextRefreshedListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019/12/12 17:12
 */
@Configuration
public class NettyClientConfiguration {

    @Bean
    public NettyClientConfigProperties nettyClientConfigProperties() {
        return new NettyClientConfigProperties();
    }

    @Bean
    @ConditionalOnBean(NettyClientConfigProperties.class)
    public NettyClientStarter nettyClientStarter() {
        return new NettyClientStarter();
    }

    @Bean
    public ContextRefreshedListener contextRefreshedListener() {
        return new ContextRefreshedListener();
    }
}
