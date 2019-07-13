package com.wise.netty.unpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author lingyuwang
 * @date 2019-06-29 23:07
 */
public class CustomProtocolDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("custom protocol decoder invoked");

        int length = in.readInt();

        byte[] content = new byte[length];
        in.readBytes(content);

        CustomProtocol customProtocol = new CustomProtocol();
        customProtocol.setLength(length);
        customProtocol.setContent(content);

        out.add(customProtocol);
    }

}
