package top.mangod.netty.protocol.response;

import lombok.Data;
import top.mangod.netty.protocol.Command;
import top.mangod.netty.protocol.Packet;
import top.mangod.netty.serialize.SerializerAlgorithm;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
