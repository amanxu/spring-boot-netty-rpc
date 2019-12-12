package com.elegant.rpc.core.config;

import com.elegant.rpc.core.client.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-06 9:58
 */
public class NettyClientStarter implements CommandLineRunner {

    @Autowired
    private NettyClientConfigProperties nettyClientConfigProperties;

    @Override
    public void run(String... args) {
        startClient();
    }

    public void startClient() {
        NettyClient nettyClient = new NettyClient();
        String serverAddress = nettyClientConfigProperties.getServerAddress();
        String[] hostInfo = serverAddress.split(":");
        nettyClient.setHost(hostInfo[0]);
        nettyClient.setPort(Integer.parseInt(hostInfo[1]));
        nettyClient.connect();
    }
}
