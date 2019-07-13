package com.wise.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author lingyuwang
 * @date 2019-06-29 17:05
 */
public class CustomByteToMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked");

        System.out.println("msg length: " + in.readableBytes());

        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }

}
