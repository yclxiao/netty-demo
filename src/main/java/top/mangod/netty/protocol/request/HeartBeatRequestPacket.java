package top.mangod.netty.protocol.request;


import top.mangod.netty.protocol.Command;
import top.mangod.netty.protocol.Packet;


public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
