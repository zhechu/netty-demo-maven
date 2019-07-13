package com.wise.zerocopy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lingyuwang
 * @date 2019-06-16 9:37
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            //接收客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            //设置阻塞  如果有Selector那么设置成false
            socketChannel.configureBlocking(true);

            int readCount = 0;

            //数据不断的进来
            while (-1 != readCount) {
                byteBuffer.clear();

                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
