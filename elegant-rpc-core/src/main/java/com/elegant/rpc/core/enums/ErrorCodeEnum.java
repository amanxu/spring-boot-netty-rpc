package com.elegant.rpc.core.enums;

import lombok.Getter;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-08-29 11:10
 */
@Getter
public enum ErrorCodeEnum implements Serializable {

    CHANNEL_MSG_SUC(0, "成功"),
    CHANNEL_MSG_FAIL(-1, "失败"),
    NETTY_PORT_CONF_ERR(100001, "Netty 端口配置错误"),
    CLIENT_SERVICE_NAME_ERR(100002, "客户端名称配置错误"),
    RPC_HANDLER_EXIST_ERR(100003, "接口服务不存在"),
    CLIENT_HANDLER_NOT_EXIST(100004, "JOB实例不存在");

    private Integer code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if (errorCodeEnum.code.equals(code)) {
                return errorCodeEnum.getMsg();
            }
        }
        return null;
    }
}
