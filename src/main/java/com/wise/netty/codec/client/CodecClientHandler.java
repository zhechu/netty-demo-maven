package com.wise.netty.codec.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class CodecClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output: " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(123456L);

//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello world", Charset.forName("UTF-8")));
    }
}
