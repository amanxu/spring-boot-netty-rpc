package com.elegant.rpc.core.server;

import com.alibaba.fastjson.JSON;
import com.elegant.rpc.core.enums.MsgTypeEnum;
import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.utils.RpcServerChannelHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaoxu.nie
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private int lossConnectCount = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 移除通道
        RpcServerChannelHolder.removeChannel(ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 移除通道
        RpcServerChannelHolder.removeChannel(ctx.channel());
    }

    /**
     * 读事件
     *
     * @param ctx ChannelHandlerContext
     * @param msg 消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ChannelMessage) {
            ChannelMessage channelMessage = (ChannelMessage) msg;
            parseChannelMsg(ctx, channelMessage);
        }
    }

    /**
     * 解析channel的消息
     *
     * @param ctx
     * @param channelMessage
     */
    private void parseChannelMsg(ChannelHandlerContext ctx, ChannelMessage channelMessage) {
        MessageContent message = channelMessage.getMessageContent();
        // 心跳消息
        if (MsgTypeEnum.HEART_CHECK.getMsgType().equals(message.getMsgType())) {
            log.info("{} -> [心跳监测] {}：通道活跃", this.getClass().getName(), ctx.channel().id());
            lossConnectCount = 0;
            // 通道注册
        } else if (MsgTypeEnum.CHANNEL_REG.getMsgType().equals(message.getMsgType())) {
            String hostName = (String) message.getData();
            RpcServerChannelHolder.putChannel(hostName, ctx.channel());
            log.info("执行器通道注册:{}", hostName);
        } else if (MsgTypeEnum.MSG_TRANSPORT.getMsgType().equals(message.getMsgType())) {
            log.info("消息传输；客户端触发成功, 触发结果:\n {}", JSON.toJSONString(channelMessage));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 触发器
     *
     * @param channelHandlerContext channelHandlerContext
     * @param evt
     * @throws Exception exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 2) {
                    log.info("{} -> [心跳检测超时，释放不活跃通道] {}", this.getClass().getName(), channelHandlerContext.channel().id());
                    channelHandlerContext.channel().close();
                    // 移除通道
                    RpcServerChannelHolder.removeChannel(channelHandlerContext.channel());
                }
            }
        } else {
            super.userEventTriggered(channelHandlerContext, evt);
        }
    }
}
