package com.netty.demo.common;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Description: ServerBootStrap
 * 初始化用于Acceptor的主"线程池"以及用于I/O工作的从"线程池"； NioEventLoopGroup
 * 初始化ServerBootstrap实例， 此实例是netty服务端应用开发的入口， ServerBootstrap
 * 通过ServerBootstrap的group方法，设置bossGroup中初始化的主从"线程池"；
 * 指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
 * 设置ServerSocketChannel的处理器
 * 配置ServerSocketChannel的选项
 * 配置子通道也就是SocketChannel的选项
 * 绑定并侦听某个端口
 */
@Component
public class ServerBootStrap {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = bootstrap.bind(address).syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    public void destroy() {
        if(channel != null) {
            channel.close();
        }
        NettyConfig.group.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

}
