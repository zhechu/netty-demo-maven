package com.wise.netty.unpack.client;

import com.wise.netty.unpack.CustomProtocolDecoder;
import com.wise.netty.unpack.CustomProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:22
 */
public class UnpackClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new CustomProtocolDecoder());
        channelPipeline.addLast(new CustomProtocolEncoder());
        channelPipeline.addLast(new UnpackClientHandler());
    }

}
