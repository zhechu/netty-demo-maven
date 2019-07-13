package com.wise.netty.udp.unicast.question;

import com.wise.netty.udp.unicast.answer.UdpAnswerSide;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * 提问端的Handler，读取服务器的应答
 */
public class QuestoinHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg)
            throws Exception {
        // 获得应答，DatagramPacket提供了content()方法取得报文的实际内容
        String response = msg.content().toString(CharsetUtil.UTF_8);
        if (response.startsWith(UdpAnswerSide.ANSWER)) {
            System.out.println(response);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
