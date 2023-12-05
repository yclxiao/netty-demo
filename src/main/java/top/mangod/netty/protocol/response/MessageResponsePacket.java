package top.mangod.netty.protocol.response;

import lombok.Data;
import top.mangod.netty.protocol.Packet;

import static top.mangod.netty.protocol.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
