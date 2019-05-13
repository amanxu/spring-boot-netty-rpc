package com.elegant.rpc.core.service.impl;

import com.elegant.rpc.core.config.NettyClientConfigProperties;
import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.route.ChannelRouteStrategyEnum;
import com.elegant.rpc.core.route.ChannelRouter;
import com.elegant.rpc.core.service.IRpcClientService;
import com.elegant.rpc.core.utils.RpcClientChannelHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-17 20:02
 */
@Service("rpcClientService ")
public class RpcClientServiceImpl implements IRpcClientService {

    @Override
    public MessageContent sendMsg(ChannelMessage channelMessage) {
        Channel channel = RpcClientChannelHolder.getChannel();
        ChannelFuture future = channel.writeAndFlush(channelMessage);
        return null;
    }
}
