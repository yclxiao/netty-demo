package top.mangod.netty.codec;

import io.netty.buffer.ByteBuf;
import top.mangod.netty.protocol.Packet;
import top.mangod.netty.protocol.request.HeartBeatRequestPacket;
import top.mangod.netty.protocol.request.LoginRequestPacket;
import top.mangod.netty.protocol.request.MessageRequestPacket;
import top.mangod.netty.protocol.response.HeartBeatResponsePacket;
import top.mangod.netty.protocol.response.LoginResponsePacket;
import top.mangod.netty.protocol.response.MessageResponsePacket;
import top.mangod.netty.serialize.JSONSerializer;
import top.mangod.netty.serialize.Serializer;

import java.util.HashMap;
import java.util.Map;

import static top.mangod.netty.protocol.Command.*;

public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x11111111;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        // 指令 和 对应的数据包类 映射
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        // 序列化协议ID 和 协议实现类 映射
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        Serializer serializer = getSerializer(packet.getSerializeAlgorithm());

        // 1. 序列化 java 对象
        byte[] bytes = serializer.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(packet.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 读取序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 读取指令
        byte command = byteBuf.readByte();

        // 读取数据包长度
        int length = byteBuf.readInt();

        // 读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
