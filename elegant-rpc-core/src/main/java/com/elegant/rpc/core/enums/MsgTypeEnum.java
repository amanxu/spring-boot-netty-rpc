package com.elegant.rpc.core.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-05 18:11
 */
@Getter
public enum MsgTypeEnum {

    CHANNEL_REG(100001, "客户端通道注册"),
    HEART_CHECK(100002, "心跳检测"),
    MSG_TRANSPORT(100003, "消息传输");

    private Integer msgType;

    private String desc;

    MsgTypeEnum(Integer msgType, String desc) {
        this.msgType = msgType;
        this.desc = desc;
    }

    public static String getDescByType(Integer msgType) {
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            if (msgTypeEnum.msgType.equals(msgType)) {
                return msgTypeEnum.desc;
            }
        }
        return null;
    }
}
