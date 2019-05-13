package com.elegant.rpc.core.service;

import com.elegant.rpc.core.model.ChannelMessage;
import com.elegant.rpc.core.model.Result;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-18 17:55
 */
public interface IRpcService {

    /**
     * RpcHandler服务
     *
     * @param param
     * @return
     */
    ChannelMessage execute(String param);
}
