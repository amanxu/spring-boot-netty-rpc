package com.elegant.rpc.core.service;

import com.elegant.rpc.core.model.ChannelMessage;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 11:34
 */
public interface IRpcServerService {

    /**
     * 发送消息
     *
     * @param channelKey
     * @param channelMessage
     */
    void sendMsg(String channelKey, ChannelMessage channelMessage);
}
