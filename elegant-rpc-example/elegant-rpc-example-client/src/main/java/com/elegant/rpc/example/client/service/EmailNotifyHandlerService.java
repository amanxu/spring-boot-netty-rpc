package com.elegant.rpc.example.client.service;

import com.elegant.rpc.core.annotation.RpcHandler;
import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.service.IRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019/12/12 13:38
 */
@Slf4j
@Service
@RpcHandler(value = "emailNotifyService")
public class EmailNotifyHandlerService implements IRpcService {

    @Override
    public ChannelMessage execute(String param) {
        log.info("EmailNotifyHandlerService execute...");
        MessageContent messageContent = new MessageContent();
        messageContent.setData("client process");
        messageContent.setHandlerName("emailNotifyService");
        return ChannelMessage.success(messageContent);
    }
}
