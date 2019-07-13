package com.wise.netty.codec.server;

import com.wise.netty.codec.CustomByteToMessageDecoder;
import com.wise.netty.codec.CustomLongToStringDecoder;
import com.wise.netty.codec.CustomMessageToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:22
 */
public class CodecServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new CustomByteToMessageDecoder());
        channelPipeline.addLast(new CustomLongToStringDecoder());
        channelPipeline.addLast(new CustomMessageToByteEncoder());
        channelPipeline.addLast(new CodecServerHandler());
    }

}
