package com.wise.netty.protobuf.singleprotocol.server;

import com.wise.netty.protobuf.singleprotocol.StudentProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class ProtobufServerHandler extends SimpleChannelInboundHandler<StudentProto.Student>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentProto.Student student) throws Exception {
        System.out.println(student.getName()); // 张三
        System.out.println(student.getAge()); // 20
        System.out.println(student.getAddress()); // 广州
    }

}