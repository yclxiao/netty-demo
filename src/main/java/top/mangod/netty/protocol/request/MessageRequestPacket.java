package top.mangod.netty.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.mangod.netty.protocol.Packet;
import top.mangod.netty.serialize.SerializerAlgorithm;

import static top.mangod.netty.protocol.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
