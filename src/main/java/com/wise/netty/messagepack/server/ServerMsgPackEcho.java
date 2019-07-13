package com.wise.netty.messagepack.server;

import com.wise.netty.messagepack.MsgPackDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 服务端
 */
public class ServerMsgPackEcho {

    public static final int PORT = 8899;

    public static void main(String[] args) throws InterruptedException {
        ServerMsgPackEcho serverMsgPackEcho = new ServerMsgPackEcho();
        System.out.println("服务器即将启动");
        serverMsgPackEcho.start();
    }

    public void start() throws InterruptedException {
        final MsgPackServerHandler serverHandler = new MsgPackServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            ServerBootstrap b = new ServerBootstrap();/*服务端启动必须*/
            b.group(group)/*将线程组传入*/
                .channel(NioServerSocketChannel.class)/*指定使用NIO进行网络传输*/
                .localAddress(new InetSocketAddress(PORT))/*指定服务器监听端口*/
                /*服务端每接收到一个连接请求，就会新启一个socket通信，也就是channel，
                所以下面这段代码的作用就是为这个子channel增加handle*/
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // 根据消息长度，从中剥离出完整的实际数据
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                                65535, // 两个字节，2的16次方。定义接收数据包的最大长度，如果发送的数据包超过此值，则抛出异常
                                0, // 长度属性部分的偏移值，0表示长度属性位于数据包头部
                                2, // 字段域长度，2个字节。长度属性的字节长度
                                0, // 协议体长度调节值，修正信息长度，如果设置为4，那么解码时再向后推4个字节
                                2 // 剪裁掉字段域。跳过字节数，如我们想跳过长度属性部分
                        ));
                        // 反序列化
                        ch.pipeline().addLast(new MsgPackDecoder());
                        // 将反序列化后的实体类交给业务处理
                        ch.pipeline().addLast(new MsgPackServerHandler());
                    }
                });
            ChannelFuture f = b.bind().sync();/*异步绑定到服务器，sync()会阻塞直到完成*/
            System.out.println("服务器启动完成，等待客户端的连接和数据.....");
            f.channel().closeFuture().sync();/*阻塞直到服务器的channel关闭*/
        } finally {
            group.shutdownGracefully().sync();/*优雅关闭线程组*/
        }
    }

}
