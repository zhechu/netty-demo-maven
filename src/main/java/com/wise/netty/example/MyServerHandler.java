package com.wise.netty.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("from client address: " + ctx.channel().remoteAddress());
        System.out.println("server output: " + msg);

        Thread.sleep(30000);

        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
//        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

}
