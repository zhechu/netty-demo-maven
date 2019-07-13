package com.wise.netty.codec.client;

import com.wise.netty.codec.CustomMessageToByteEncoder;
import com.wise.netty.codec.CustomReplayingDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:22
 */
public class CodecClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new CustomReplayingDecoder());
        channelPipeline.addLast(new CustomMessageToByteEncoder());
        channelPipeline.addLast(new CodecClientHandler());
    }

}
