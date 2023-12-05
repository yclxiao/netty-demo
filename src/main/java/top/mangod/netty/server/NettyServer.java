package top.mangod.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import top.mangod.netty.codec.PacketCodecHandler;
import top.mangod.netty.codec.PacketDecoder;
import top.mangod.netty.codec.PacketEncoder;
import top.mangod.netty.codec.Spliter;
import top.mangod.netty.commonhandler.ImIdleStateHandler;
import top.mangod.netty.server.handler.HeartBeatRequestHandler;
import top.mangod.netty.server.handler.LoginRequestHandler;
import top.mangod.netty.server.handler.MessageRequestHandler;

/**
 * @author baily
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new ImIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
//                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        serverBootstrap.bind(9000)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("端口9000绑定成功");
                    } else {
                        System.err.println("端口9000绑定失败");
                    }
                });
    }
}

