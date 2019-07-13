package com.wise.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lingyuwang
 * @date 2019-06-29 16:56
 */
public class CustomMessageToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked");

        System.out.println("msg: " + msg);

        out.writeLong(msg);
    }

}
