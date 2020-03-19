package com.netty.demo.common;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Description: NettyConfig
 * Netty 是一个利用 Java 的高级网络的能力，隐藏其背后的复杂性而提供一个易于使用的 API 的客户端/服务器框架。
 * Netty 已经实现了的协议就有 FTP, SMTP, HTTP, WebSocket 和 SPDY 以及其他二进制和基于文本的协议。
 */
public class NettyConfig {

    /**
     * ChannelGroup的EventExecutor主要是用来异步通知使用的。
     * ChannelGroup分组进行广播时，如果有多个组的话
     * GlobalEventExecutor.INSTANCE让所有ChannelGroup公用同一个, 只创建一个，然后直接向所有连接广播
     */
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * host name 和监听的端口号，需要配置到配置文件中
     * 这个前端客户端设置websocket
     */
    public static String WS_HOST = "127.0.0.1";
    public static int WS_PORT = 9090;
}
