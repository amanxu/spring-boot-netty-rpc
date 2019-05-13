package com.elegant.rpc.core.route.strategy;

import com.elegant.rpc.core.route.ChannelRouter;
import io.netty.channel.Channel;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @description: 最后一个
 * @author: xiaoxu.nie
 * @date: 2019-01-18 10:18
 */
public class ChannelRouterLast extends ChannelRouter {

    @Override
    public Channel routeChannel(List<Channel> channelList) {
        if (CollectionUtils.isEmpty(channelList)) {
            return null;
        }
        return channelList.get(channelList.size() - 1);
    }
}
