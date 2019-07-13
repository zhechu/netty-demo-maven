package com.wise.aio.server;

/**
 * 服务端
 */
public class AioServer {

    private static AioServerHandler serverHandle;

    // 统计客户端个数
    public volatile static long clientCount = 0;

    public static void main(String[] args) {
        serverHandle = new AioServerHandler(8899);
        new Thread(serverHandle, "Server").start();
    }

}
