package com.elegant.rpc.core.config;

import com.elegant.rpc.core.route.ChannelRouteStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-09 17:36
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "netty.client")
public class NettyClientConfigProperties {

    private String serverAddress;

    private String registerName;

    /***路由策略,默认随机*/
    private String routeStrategy = ChannelRouteStrategyEnum.RANDOM.getStrategyName();
}
