package top.mangod.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import top.mangod.netty.actioncommand.LoginActionCommand;
import top.mangod.netty.actioncommand.SendToUserActionCommand;
import top.mangod.netty.client.handler.HeartBeatTimerHandler;
import top.mangod.netty.client.handler.LoginResponseHandler;
import top.mangod.netty.client.handler.MessageResponseHandler;
import top.mangod.netty.codec.PacketDecoder;
import top.mangod.netty.codec.PacketEncoder;
import top.mangod.netty.codec.Spliter;
import top.mangod.netty.commonhandler.ImIdleStateHandler;
import top.mangod.netty.util.SessionUtil;

import java.util.Scanner;

/**
 * @author baily
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new ImIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });

        bootstrap.connect("127.0.0.1", 9000)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接服务端成功");
                        Channel channel = ((ChannelFuture) future).channel();
                        // 连接之后，假设再这里发起各种操作指令，采用异步线程开始发送各种指令，发送数据用到的的channel是必不可少的
                        sendActionCommand(channel);
                    } else {
                        System.err.println("连接服务端失败");
                    }
                });
    }

    private static void sendActionCommand(Channel channel) {
        // 直接采用控制台输入的方式，模拟操作指令
        Scanner scanner = new Scanner(System.in);
        LoginActionCommand loginActionCommand = new LoginActionCommand();
        SendToUserActionCommand sendToUserActionCommand = new SendToUserActionCommand();
        new Thread(() -> {
            // 此处循环等待客户端输入
            while (!Thread.interrupted()) {
                // 如果没登录，则直接登录
                if (!SessionUtil.hasLogin(channel)) {
                    loginActionCommand.exec(scanner, channel);
                } else {
                    //  登录后，后去获取指令
                    String command = scanner.next();
                    if (!SessionUtil.hasLogin(channel)) {
                        return;
                    }

                    // 如果是点对点发送消息
                    if ("sendToUser".equals(command)) {
                        sendToUserActionCommand.exec(scanner, channel);
                    } else {
                        System.err.println("无法识别[" + command + "]指令，请重新输入!");
                    }
                }
            }
        }).start();
    }
}
