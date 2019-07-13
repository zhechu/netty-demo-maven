package com.wise.netty.unpack.client;

import com.wise.netty.unpack.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class UnpackClientHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("client receive msg length: " + length);
        System.out.println("client receive msg content: " + new String(content, Charset.forName("UTF-8")));
        System.out.println("client receive msg count: " + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "client msg body";
            byte[] requestContent = message.getBytes("UTF-8");

            CustomProtocol customProtocol = new CustomProtocol();
            customProtocol.setLength(requestContent.length);
            customProtocol.setContent(requestContent);

            ctx.writeAndFlush(customProtocol);
        }
    }
}
