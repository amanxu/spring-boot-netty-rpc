package com.elegant.rpc.core.utils;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 10:43
 */
@Slf4j
public class RpcClientChannelHolder {

    /**
     * RPC客户端 缓存的通道
     */
    public static Channel clientChannel;

    /**
     * 通道加入Map
     *
     * @param channel
     */
    public static void setChannel(Channel channel) {
        clientChannel = channel;
    }

    /**
     * 获取通道列表
     *
     * @return
     */
    public static Channel getChannel() {
        return clientChannel;
    }

    /**
     * 移除主机通道
     */
    public static void removeChannel() {
        clientChannel = null;
    }

}
