package top.mangod.netty.protocol.request;

import lombok.Data;
import top.mangod.netty.protocol.Command;
import top.mangod.netty.protocol.Packet;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
