package com.elegant.rpc.core.service;

import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-17 19:53
 */
public interface IRpcClientService {

    /**
     * 发送消息
     *
     * @param channelMessage
     */
    MessageContent sendMsg(ChannelMessage channelMessage);
}
