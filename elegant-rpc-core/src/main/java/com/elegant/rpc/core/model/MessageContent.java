package com.elegant.rpc.core.model;

import com.elegant.rpc.core.enums.MsgTypeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-10 17:30
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageContent implements Serializable {

    /**
     * 消息类型
     */
    private Integer msgType = MsgTypeEnum.MSG_TRANSPORT.getMsgType();

    /**
     * 服务中的消息处理器
     */
    private String handlerName;

    /**
     * 消息内容
     */
    private Object data;
}
