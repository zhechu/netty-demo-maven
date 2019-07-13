package com.wise.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author lingyuwang
 * @date 2019-06-29 19:43
 */
public class CustomLongToStringDecoder extends MessageToMessageDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("messageToMessage decode invoked");

        out.add(String.valueOf(msg));
    }

}
