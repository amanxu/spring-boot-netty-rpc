package com.elegant.rpc.core.config;

import com.elegant.rpc.core.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-06 9:58
 */
@Configuration
public class NettyServerStarter implements CommandLineRunner {

    @Autowired
    private NettyServerConfigProperties nettyServerConfigProperties;

    @Override
    public void run(String... args) throws Exception {
        NettyServer nettyServer = new NettyServer();
        nettyServer.setPort(nettyServerConfigProperties.getBindPort());
        nettyServer.bind();
    }
}
