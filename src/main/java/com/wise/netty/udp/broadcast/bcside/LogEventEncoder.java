package com.wise.netty.udp.broadcast.bcside;

import com.wise.netty.udp.broadcast.LogMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 编码，将实际的日志实体类编码为DatagramPacket
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogMsg> {

    private final InetSocketAddress remoteAddress;

    /**
     * LogEventEncoder 创建了即将被发送到指定的 InetSocketAddress 的 DatagramPacket 消息
     * @param remoteAddress
     */
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(
            ChannelHandlerContext channelHandlerContext,
            LogMsg logMsg,
            List<Object> out) throws Exception {
        byte[] msg = logMsg.getMsg().getBytes(CharsetUtil.UTF_8);
        // 容量的计算：两个long型+消息的内容+分割符
        ByteBuf buf = channelHandlerContext.alloc().buffer(8*2 + msg.length + 1);
        // 将发送时间写入到 ByteBuf中
        buf.writeLong(logMsg.getTime());
        // 将消息id写入到 ByteBuf中
        buf.writeLong(logMsg.getMsgId());
        // 添加一个 SEPARATOR
        buf.writeByte(LogMsg.SEPARATOR);
        // 将日志消息写入 ByteBuf中
        buf.writeBytes(msg);
        // 将一个拥有数据和目的地地址的新 DatagramPacket 添加到出栈的消息列表中
        out.add(new DatagramPacket(buf, remoteAddress));
    }

}
