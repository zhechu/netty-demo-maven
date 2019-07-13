package com.wise.netty.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:22
 */
public class DemoServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast("httpServerCodec", new HttpServerCodec());
        channelPipeline.addLast("demoHttpServerHandler", new DemoHttpServerHandler());
    }

}
