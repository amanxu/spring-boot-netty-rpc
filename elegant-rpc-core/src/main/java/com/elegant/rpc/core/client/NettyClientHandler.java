package com.elegant.rpc.core.client;

import com.alibaba.fastjson.JSON;
import com.elegant.rpc.core.common.Constant;
import com.elegant.rpc.core.config.NettyClientConfigProperties;
import com.elegant.rpc.core.config.NettyClientStarter;
import com.elegant.rpc.core.enums.ErrorCodeEnum;
import com.elegant.rpc.core.enums.MsgTypeEnum;
import com.elegant.rpc.core.exception.BusinessException;
import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.MessageContent;
import com.elegant.rpc.core.utils.RpcClientChannelHolder;
import com.elegant.rpc.core.utils.SpringContextUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static com.elegant.rpc.core.enums.MsgTypeEnum.MSG_TRANSPORT;

/**
 * @author xiaoxu.nie
 * @date 2019-01-05
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 捕捉异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("[异常，释放资源] {}", cause.getMessage());
        try {
            // 移除缓存通道
            RpcClientChannelHolder.removeChannel();
            ctx.close();
        } catch (Exception e) {
            log.error("exceptionCaught:{}", e);
        }
        // 重启Channel通道
        NettyClientStarter cl = SpringContextUtil.getBean(NettyClientStarter.class);
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[客户端和服务端通道建立成功，准备注册通道...]");
        // 客户端缓存通道
        RpcClientChannelHolder.setChannel(ctx.channel());
        // 注册客户端到服务端
        MessageContent messageContent = new MessageContent();
        messageContent.setMsgType(MsgTypeEnum.CHANNEL_REG.getMsgType());
        NettyClientConfigProperties configProperty = SpringContextUtil.getBean(NettyClientConfigProperties.class);
        if (configProperty.getRegisterName() == null) {
            throw new BusinessException(ErrorCodeEnum.CLIENT_SERVICE_NAME_ERR.getMsg());
        }
        //获取本地的IP地址
        messageContent.setData(configProperty.getRegisterName());
        ctx.writeAndFlush(new ChannelMessage(messageContent));
    }

    /**
     * 服务端返回应答消息时，调用此方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("[客户端接收消息]: {}", JSON.toJSONString(msg));
        if (msg instanceof ChannelMessage) {
            ChannelMessage channelMessage = (ChannelMessage) msg;
            if (ErrorCodeEnum.CHANNEL_MSG_SUC.getCode().equals(channelMessage.getCode())) {
                parseChannelMsg(ctx, channelMessage.getMessageContent());
            }
        }
    }

    /**
     * 解析通道消息
     *
     * @param ctx
     * @param message
     */
    private void parseChannelMsg(ChannelHandlerContext ctx, MessageContent message) {

        try {
            if (MSG_TRANSPORT.getMsgType().equals(message.getMsgType())) {
                // 设置返回消息的类型
                message.setMsgType(MsgTypeEnum.MSG_TRANSPORT.getMsgType());

                ctx.channel().writeAndFlush(ChannelMessage.success(message));
            }
        } catch (Exception e) {
            // JOB触发失败
            log.error("JOB Executor Err:{}", e);
            ctx.channel().writeAndFlush(ChannelMessage.success(message));
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("{} -> [客户端心跳监测发送] 通道编号：{}", this.getClass().getName(), ctx.channel().id().toString());
        if (evt instanceof IdleStateEvent) {
            MessageContent messageContent = new MessageContent();
            messageContent.setMsgType(MsgTypeEnum.HEART_CHECK.getMsgType());
            messageContent.setData(Constant.HEART_CHECK);
            ctx.writeAndFlush(new ChannelMessage(messageContent));
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
