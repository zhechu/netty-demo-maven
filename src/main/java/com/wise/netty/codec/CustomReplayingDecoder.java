package com.wise.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author lingyuwang
 * @date 2019-06-29 17:59
 */
public class CustomReplayingDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("replaying decode invoked");

        System.out.println("msg length: " + in.readableBytes());

        out.add(in.readLong());
    }

}
