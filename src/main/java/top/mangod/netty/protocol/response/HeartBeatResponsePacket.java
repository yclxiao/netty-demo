package top.mangod.netty.protocol.response;


import top.mangod.netty.protocol.Command;
import top.mangod.netty.protocol.Packet;


public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
