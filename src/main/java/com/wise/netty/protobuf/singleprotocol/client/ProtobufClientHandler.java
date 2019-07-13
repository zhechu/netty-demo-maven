package com.wise.netty.protobuf.singleprotocol.client;

import com.wise.netty.protobuf.singleprotocol.StudentProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class ProtobufClientHandler extends SimpleChannelInboundHandler<StudentProto.Student>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentProto.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentProto.Student student = StudentProto.Student.newBuilder().setName("张三").setAge(20).setAddress("广州").build();

        ctx.channel().writeAndFlush(student);
    }
}