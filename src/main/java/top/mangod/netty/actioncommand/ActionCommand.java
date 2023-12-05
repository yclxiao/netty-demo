package top.mangod.netty.actioncommand;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 操作指令
 */
public interface ActionCommand {
    void exec(Scanner scanner, Channel channel);
}
