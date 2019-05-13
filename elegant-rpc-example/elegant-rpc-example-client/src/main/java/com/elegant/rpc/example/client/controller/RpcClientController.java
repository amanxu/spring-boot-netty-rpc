package com.elegant.rpc.example.client.controller;

import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.model.Result;
import com.elegant.rpc.core.service.IRpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 11:49
 */
@RestController
@RequestMapping(value = "rpcClient")
public class RpcClientController {

    @Autowired
    private IRpcClientService rpcClientService;

    @RequestMapping("/sendMsg")
    public Result clientSendMsg() {
        ChannelMessage channelMessage = new ChannelMessage();
        MessageContent messageContent = new MessageContent();
        messageContent.setData("amanxu.......");
        messageContent.setHandlerName("888888888");
        channelMessage.setMessageContent(messageContent);
        rpcClientService.sendMsg(channelMessage);
        return Result.success();
    }
}
