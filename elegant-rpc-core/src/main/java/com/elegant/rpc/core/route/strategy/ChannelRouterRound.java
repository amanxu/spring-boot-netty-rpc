package com.elegant.rpc.core.route.strategy;

import com.elegant.rpc.core.route.ChannelRouter;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 轮询
 * @author: xiaoxu.nie
 * @date: 2019-01-18 10:18
 */
@Slf4j
public class ChannelRouterRound extends ChannelRouter {

    /*** 轮询边界值*/
    private static int beyondInt = 2 << 10;

    /***轮询计数,初始为-1,AtomicInteger是线程安全的*/
    private AtomicInteger counter = new AtomicInteger(-1);

    @Override
    public Channel routeChannel(List<Channel> channelList) {
        if (CollectionUtils.isEmpty(channelList)) {
            return null;
        }
        int channelIndex = countChannelIndex(channelList.size());
        return channelList.get(channelIndex);
    }

    /**
     * 生成轮询值
     *
     * @param channelCount 通道数量
     * @return
     */
    public Integer countChannelIndex(int channelCount) {
        // 轮询服务对应的IP, 得到的下标为：0、1、2...
        Integer index = counter.incrementAndGet() % channelCount;
        // 以免超出Integer范围
        if (counter.get() > beyondInt) {
            counter.set(-1);
        }
        return index;
    }

}
