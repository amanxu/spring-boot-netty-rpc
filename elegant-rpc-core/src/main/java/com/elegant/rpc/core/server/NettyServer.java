package com.elegant.rpc.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu.nie
 */
@Slf4j
@Setter
public class NettyServer {

    /***netty服务端口*/
    private int port;

    /**
     * 绑定端口，启动服务
     * NioEventLoopGroup本质是一个线程组，包含一组NIO线程专门用于网络事件的处理，
     * 一个用于服务端接受客户端的连接，一个用于进行SocketChannel的网络读写
     *
     * @throws Exception 异常
     */
    public void bind() {
        log.info("[启动NIO] 绑定端口:{}...", port);
        // 声明Boss线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 声明Worker线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Netty用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 将两个NIO线程组传递到ServerBootStrap中
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 配置NioServerSocketChannel的TCP参数 backlog设置为1024
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 绑定I/O事件处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加心跳支持
                            ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            // 解码编码
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024,
                                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            // 绑定端口，同步等待成功
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Netty Server InterruptedException:{}", e);
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /*public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.bind(8090);
    }*/
}
