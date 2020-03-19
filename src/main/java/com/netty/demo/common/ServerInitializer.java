package com.netty.demo.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description: ServerInitializer
 * @Author: shizhimin
 * @Date: 2020/3/18
 * @Version: 1.0
 */
public class ServerInitializer extends ChannelInitializer<Channel> {

    /**
     * ChannelPipeline可以动态添加、删除、替换其中的ChannelHandler，这样的机制可以提高灵活性。
     * ChannelPipeline类是ChannelHandler实例对象的链表用于处理或截获通道的接收和发送数据。
     * 它提供了一种高级的截取过滤模式（类似serverlet中的filter功能），
     * 让用户可以在ChannelPipeline中完全控制一个事件以及如何处理ChannelHandler与ChannelPipeline的交互。
     * 对于每个新的通道Channel，都会创建一个新的ChannelPipeline，并将器pipeline附加到channel中。
     */
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("handler", new WebSocketHandler());
    }

}
