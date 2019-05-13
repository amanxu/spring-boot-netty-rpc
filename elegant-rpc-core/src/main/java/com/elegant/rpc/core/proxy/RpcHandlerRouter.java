package com.elegant.rpc.core.proxy;

import com.alibaba.fastjson.JSON;
import com.elegant.rpc.core.enums.ErrorCodeEnum;
import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.service.IRpcService;
import com.elegant.rpc.core.utils.RpcHandlerBeanUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 18:11
 */
@Slf4j
public class RpcHandlerRouter {

    /**
     * 根据调用的服务名称通过代理模式获取对应的服务
     *
     * @param message
     */
    public ChannelMessage handlerRoute(MessageContent message) {
        String handlerName = message.getHandlerName();
        // 1.获取job服务的bean
        Object bean = RpcHandlerBeanUtil.getBeanByJobName(handlerName);
        if (bean == null) {
            return ChannelMessage.error(ErrorCodeEnum.RPC_HANDLER_EXIST_ERR.getCode(), ErrorCodeEnum.RPC_HANDLER_EXIST_ERR.getMsg());
        }
        // 2.获取动态代理类
        RpcDynamicProxyHandler rpcDynamicProxyHandler = new RpcDynamicProxyHandler();
        IRpcService rpcService = (IRpcService) rpcDynamicProxyHandler.createInstance(bean);
        ChannelMessage channelMessage = rpcService.execute(JSON.toJSONString(message.getData()));
        return channelMessage;
    }
}
