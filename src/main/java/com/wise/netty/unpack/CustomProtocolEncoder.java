package com.wise.netty.unpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lingyuwang
 * @date 2019-06-29 23:10
 */
public class CustomProtocolEncoder extends MessageToByteEncoder<CustomProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        System.out.println("custom protocol encoder invoked");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }

}
