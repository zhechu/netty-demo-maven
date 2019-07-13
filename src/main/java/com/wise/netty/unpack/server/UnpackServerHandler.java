package com.wise.netty.unpack.server;

import com.wise.netty.unpack.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class UnpackServerHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("server receive msg length: " + length);
        System.out.println("server receive msg content: " + new String(content, Charset.forName("UTF-8")));
        System.out.println("server receive msg count: " + (++this.count));

        String responseMessage = UUID.randomUUID().toString();
        byte[] responseContent = responseMessage.getBytes("UTF-8");

        CustomProtocol customProtocol = new CustomProtocol();
        customProtocol.setLength(responseContent.length);
        customProtocol.setContent(responseContent);

        ctx.writeAndFlush(customProtocol);
    }

}
