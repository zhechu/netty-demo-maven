package com.wise.netty.protobuf.multiprotocol.server;

import com.wise.netty.protobuf.multiprotocol.TaskProtobufWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class ProtobufServerHandler extends SimpleChannelInboundHandler<TaskProtobufWrapper.TaskProtocol>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TaskProtobufWrapper.TaskProtocol taskProtocol) throws Exception {
        switch (taskProtocol.getPackType()) {
            case LOGIN:
                System.out.println("接收到一个登录类型的pack:[{" + taskProtocol.getLoginPack().getUsername() + " : " + taskProtocol.getLoginPack().getPassword() + "}]");
                break;
            case CREATE_TASK:
                System.out.println("接收到一个创建任务类型的pack:[{" + taskProtocol.getCreateTaskPack().getTaskId() + " : " + taskProtocol.getCreateTaskPack().getTaskName() + "}]");
                break;
            case DELETE_TASK:
                System.out.println("接收到一个删除任务类型的pack:[{" + Arrays.toString(taskProtocol.getDeleteTaskPack().getTaskIdList().toArray()) + "}]");
                break;
            default:
                System.out.println("接收到一个未知类型的pack:[{" + taskProtocol.getPackType() + "}]");
                break;
        }
    }

}