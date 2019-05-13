package com.elegant.rpc.core.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-09 12:14
 */
@Slf4j
public class RpcServerChannelHolder {

    /**
     * 主机对应的通道
     */
    private static final ConcurrentMap<String, List<Channel>> hostChannelMap = Maps.newConcurrentMap();
    protected static ConcurrentMap<String, String> channelMap = Maps.newConcurrentMap();

    /**
     * 通道加入Map
     *
     * @param host
     * @param channel
     */
    public static void putChannel(String host, Channel channel) {
        List<Channel> channels = hostChannelMap.get(host);
        if (CollectionUtils.isEmpty(channels)) {
            channels = Lists.newArrayList(channel);
            hostChannelMap.put(host, channels);
        } else {
            channels.add(channel);
            hostChannelMap.put(host, channels);
        }
        channelMap.put(channel.id().toString(), host);
    }

    /**
     * 根据主机获取Channel
     *
     * @param host
     * @return
     */
    public static List<Channel> getChannelByHost(String host) {
        if (StringUtils.isBlank(host)) {
            return null;
        }
        return hostChannelMap.get(host);
    }

    /**
     * 移除主机通道
     *
     * @param channel
     */
    public static void removeChannel(String hostName, Channel channel) {
        List<Channel> channelList = hostChannelMap.get(hostName);
        if (CollectionUtils.isEmpty(channelList)) {
            hostChannelMap.remove(hostName);
        } else if (channelList.size() == 1) {
            hostChannelMap.remove(hostName);
        } else {
            channelList.stream().forEach(ch -> {
                if (ch == channel) {
                    channelList.remove(ch);
                }
            });
        }
    }

    /**
     * 移除通道
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        String hostName = channelMap.get(channel.id().toString());
        if (StringUtils.isBlank(hostName)) {
            return;
        }
        removeChannel(hostName, channel);
        channelMap.remove(channel.id().toString());
    }

}
