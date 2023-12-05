package top.mangod.netty.protocol;

/**
 * 指令
 */
public interface Command {

    /**
     * 登录请求
     */
    Byte LOGIN_REQUEST = 11;

    /**
     * 登录响应
     */
    Byte LOGIN_RESPONSE = 12;

    /**
     * 发送消息请求
     */
    Byte MESSAGE_REQUEST = 21;

    /**
     * 发送消息响应
     */
    Byte MESSAGE_RESPONSE = 22;

    /**
     * 心跳请求
     */
    Byte HEARTBEAT_REQUEST = 31;

    /**
     * 心跳响应
     */
    Byte HEARTBEAT_RESPONSE = 32;
}
