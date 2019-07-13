package com.wise.zerocopy.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lingyuwang
 * @date 2019-06-16 10:02
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "D://jdk1.8.0_45_64bit.rar";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long position = 0;
        long size = fileChannel.size();
        long total = 0;
        long startTime = System.currentTimeMillis();
        while (position < size) {
            long transfer = fileChannel.transferTo(position, fileChannel.size(), socketChannel);
            if (transfer <= 0) {
                break;
            }
            total += transfer;
            position += transfer;
        }

        System.out.println("发送总字节数：" + total + "，耗时： " + (System.currentTimeMillis() - startTime));
        // 发送总字节数：156492038，耗时： 2564
        // 发送总字节数：156492038，耗时： 2280
        // 发送总字节数：156492038，耗时： 2740
        // 发送总字节数：156492038，耗时： 2919
        // 发送总字节数：156492038，耗时： 2739

        fileChannel.close();
        socketChannel.close();
    }

}
