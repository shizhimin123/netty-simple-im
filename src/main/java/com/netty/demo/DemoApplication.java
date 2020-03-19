package com.netty.demo;

import com.netty.demo.common.NettyConfig;
import com.netty.demo.common.ServerBootStrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

/**
 * 平常开发中有可能需要实现在项目启动后执行的功能，
 * SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，
 * 实现功能的代码放在实现的run方法中
 * SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，
 * 如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private ServerBootStrap ws;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Netty's ws server is listen: " + NettyConfig.WS_PORT);
        /**
         * InetAddress是Java对IP地址的封装，代表互联网协议（IP）地址；
         * InetSocketAddress是SocketAddress的实现子类。
         * ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求。
         */
        InetSocketAddress address = new InetSocketAddress(NettyConfig.WS_HOST, NettyConfig.WS_PORT);
        ChannelFuture future = ws.start(address);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                ws.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
