package com.wise.netty.protobuf.multiprotocol.client;

import com.wise.netty.protobuf.multiprotocol.TaskProtobufWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lingyuwang
 * @date 2019-06-07 17:30
 */
public class ProtobufClientHandler extends SimpleChannelInboundHandler<TaskProtobufWrapper.TaskProtocol>{

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TaskProtobufWrapper.TaskProtocol taskProtocol) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        executor.scheduleAtFixedRate(() -> {
            // 产生的pack类型
            int packType = new Random().nextInt(3);
            switch (TaskProtobufWrapper.PackType.forNumber(packType)) {
                case LOGIN:
                    TaskProtobufWrapper.LoginPack loginPack = TaskProtobufWrapper.LoginPack.newBuilder().setUsername("张三[" + atomicInteger.getAndIncrement() + "]").setPassword("123456").build();
                    ctx.writeAndFlush(TaskProtobufWrapper.TaskProtocol.newBuilder().setPackType(TaskProtobufWrapper.PackType.LOGIN).setLoginPack(loginPack).build());
                    break;
                case CREATE_TASK:
                    TaskProtobufWrapper.CreateTaskPack createTaskPack = TaskProtobufWrapper.CreateTaskPack.newBuilder().setCreateTime(System.currentTimeMillis()).setTaskId("100" + atomicInteger.get()).setTaskName("任务编号" + atomicInteger.get()).build();
                    ctx.writeAndFlush(TaskProtobufWrapper.TaskProtocol.newBuilder().setPackType(TaskProtobufWrapper.PackType.CREATE_TASK).setCreateTaskPack(createTaskPack).build());
                    break;
                case DELETE_TASK:
                    TaskProtobufWrapper.DeleteTaskPack deleteTaskPack = TaskProtobufWrapper.DeleteTaskPack.newBuilder().addTaskId("1001").addTaskId("1002").build();
                    ctx.writeAndFlush(TaskProtobufWrapper.TaskProtocol.newBuilder().setPackType(TaskProtobufWrapper.PackType.DELETE_TASK).setDeleteTaskPack(deleteTaskPack).build());
                    break;
                default:
                    System.out.println("产生一个未知的包类型:[{" + packType + "}]");
                    break;
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}