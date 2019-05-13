package com.elegant.rpc.core.config;

import com.elegant.rpc.core.route.ChannelRouteStrategyEnum;
import lombok.Getter;
import lombok.Setter;
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
@ConfigurationProperties(prefix = "netty.server")
public class NettyServerConfigProperties {

    private Integer bindPort;

    /***路由策略*/
    private String routeStrategy = ChannelRouteStrategyEnum.ROUND.getStrategyName();

}
