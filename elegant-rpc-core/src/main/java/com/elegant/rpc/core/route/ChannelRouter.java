package com.elegant.rpc.core.route;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 10:10
 */
public abstract class ChannelRouter {


    /**
     * 通道路由
     *
     * @param channelList
     * @return
     */
    public abstract Channel routeChannel(List<Channel> channelList);
}
