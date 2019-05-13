package com.elegant.rpc.example.server.controller;

import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.model.Result;
import com.elegant.rpc.core.service.IRpcClientService;
import com.elegant.rpc.core.service.IRpcServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 11:49
 */
@RestController
@RequestMapping(value = "rpcServer")
public class RpcServerController {

    @Autowired
    private IRpcServerService rpcServerService;

    @RequestMapping("/sendMsg")
    public Result clientSendMsg() {
        MessageContent messageContent = new MessageContent();
        messageContent.setData("我是Netty 服务端.....");
        rpcServerService.sendMsg("elegant-rpc-example-client",ChannelMessage.success(messageContent));
        return Result.success();
    }
}
