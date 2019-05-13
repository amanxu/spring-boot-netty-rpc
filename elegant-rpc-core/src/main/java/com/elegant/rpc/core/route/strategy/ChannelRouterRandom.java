package com.elegant.rpc.core.route.strategy;

import com.elegant.rpc.core.route.ChannelRouter;
import io.netty.channel.Channel;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @description: 随机
 * @author: xiaoxu.nie
 * @date: 2019-01-18 10:18
 */
public class ChannelRouterRandom extends ChannelRouter {

    private static Random localRandom = new Random();

    @Override
    public Channel routeChannel(List<Channel> channelList) {

        if (CollectionUtils.isEmpty(channelList)) {
            return null;
        }
        int boundSize = channelList.size();
        return channelList.get(localRandom.nextInt(boundSize));
    }
}
