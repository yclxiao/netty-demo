package top.mangod.netty.actioncommand;

import io.netty.channel.Channel;
import top.mangod.netty.protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserActionCommand implements ActionCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个用户：");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
