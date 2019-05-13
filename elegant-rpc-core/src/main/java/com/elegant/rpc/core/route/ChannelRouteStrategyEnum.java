package com.elegant.rpc.core.route;

import com.elegant.rpc.core.route.strategy.ChannelRouterFirst;
import com.elegant.rpc.core.route.strategy.ChannelRouterLast;
import com.elegant.rpc.core.route.strategy.ChannelRouterRandom;
import com.elegant.rpc.core.route.strategy.ChannelRouterRound;
import lombok.Getter;

/**
 * @description: 路由策略
 * @author: xiaoxu.nie
 * @date: 2019-01-18 9:55
 */
@Getter
public enum ChannelRouteStrategyEnum {

    RANDOM("RANDOM", new ChannelRouterRandom()),
    ROUND("ROUND", new ChannelRouterRound()),
    FIRST("FIRST", new ChannelRouterFirst()),
    LAST("LAST", new ChannelRouterLast());

    ChannelRouteStrategyEnum(String strategyName, ChannelRouter channelRouter) {
        this.strategyName = strategyName;
        this.channelRouter = channelRouter;
    }

    private String strategyName;

    private ChannelRouter channelRouter;

    public static ChannelRouter getChannelByStrategy(String strategyName) {
        for (ChannelRouteStrategyEnum routeStrategyEnum : ChannelRouteStrategyEnum.values()) {
            if (routeStrategyEnum.strategyName.equals(strategyName)) {
                return routeStrategyEnum.channelRouter;
            }
        }
        // 默认采用随机路由
        return ChannelRouteStrategyEnum.ROUND.channelRouter;
    }


}
