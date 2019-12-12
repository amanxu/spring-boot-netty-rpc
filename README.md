# spring-boot-netty-rpc 轻量级RPC框架
1.在项目中直接引用注解自定义EnableRpcClient 和 EnableRpcServer分别启用客户端和注册中心
2.在客户端定义实例：
````java
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
````
客户端NettyClientHandler解析服务端消息，代码实现如下：
````java
/**
     * 服务端读取客户端的消息
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

        ChannelMessage channelMessage = new ChannelMessage();
        channelMessage.setCode(ErrorCodeEnum.CHANNEL_MSG_SUC.getCode());
        channelMessage.setMsg(ErrorCodeEnum.CHANNEL_MSG_SUC.getMsg());
        MessageContent messageContent = new MessageContent();
        // 设置返回消息的类型
        messageContent.setMsgType(MsgTypeEnum.MSG_TRANSPORT.getMsgType());
        try {
            if (MSG_TRANSPORT.getMsgType().equals(message.getMsgType())) {

                // 1.根据名称获取Bean实例
                ContextRefreshedListener contextRefreshedListener = SpringContextUtil.getBean(ContextRefreshedListener.class);
                Object handlerBean = contextRefreshedListener.getBeanByName(message.getHandlerName());
                if (handlerBean == null) {
                    String errMsg = new StringBuilder(CLIENT_HANDLER_NOT_EXIST.getMsg()).append(":").append(message.getHandlerName()).toString();
                    messageContent.setData(errMsg);
                    channelMessage.setMessageContent(messageContent);
                    ctx.channel().writeAndFlush(channelMessage);
                    return;
                }

                // 2.获取动态代理类
                RpcDynamicProxyHandler rpcDynamicProxyHandler = new RpcDynamicProxyHandler();
                IRpcService rpcService = (IRpcService) rpcDynamicProxyHandler.createInstance(handlerBean);
                channelMessage = rpcService.execute(JSON.toJSONString(message.getData()));

                ctx.channel().writeAndFlush(channelMessage);
            }
        } catch (Exception e) {
            // 调度出发成功，JOB执行失败
            log.error("JOB Executor Err:{}", e);
            messageContent.setData(e);
            ctx.channel().writeAndFlush(channelMessage);
        }
    }
````
3.服务端根据自定义策略调routeStrategy用指定服务名称hostname的服务实例：
````java
@Slf4j
@Service("rpcServerService ")
public class RpcServerServiceImpl implements IRpcServerService {

    @Autowired
    private NettyServerConfigProperties nettyServerConfigProperties;

    @Override
    public void sendMsg(String hostName, ChannelMessage channelMessage) {
        ChannelRouter channelRouter = ChannelRouteStrategyEnum.getChannelByStrategy(nettyServerConfigProperties.getRouteStrategy());
        Channel channel = channelRouter.routeChannel(RpcServerChannelHolder.getChannelByHost(hostName));
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(channelMessage);
        } else {
            RpcServerChannelHolder.removeChannel(channel);
        }
    }
}

````