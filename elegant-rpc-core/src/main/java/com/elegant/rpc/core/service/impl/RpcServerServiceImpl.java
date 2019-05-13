package com.elegant.rpc.core.service.impl;

import com.elegant.rpc.core.config.NettyServerConfigProperties;
import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.route.ChannelRouteStrategyEnum;
import com.elegant.rpc.core.route.ChannelRouter;
import com.elegant.rpc.core.service.IRpcServerService;
import com.elegant.rpc.core.utils.RpcServerChannelHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 11:37
 */
@Service("rpcServerService ")
public class RpcServerServiceImpl implements IRpcServerService {

    @Autowired
    private NettyServerConfigProperties nettyServerConfigProperties;

    @Override
    public void sendMsg(String hostName, ChannelMessage channelMessage) {
        ChannelRouter channelRouter = ChannelRouteStrategyEnum.getChannelByStrategy(nettyServerConfigProperties.getRouteStrategy());
        Channel channel = channelRouter.routeChannel(RpcServerChannelHolder.getChannelByHost(hostName));
        if (channel.isActive()) {
            ChannelFuture future = channel.writeAndFlush(channelMessage);
        } else {
            // 提升代码健壮性
            RpcServerChannelHolder.removeChannel(channel);
            sendMsg(hostName, channelMessage);
        }
    }
}
