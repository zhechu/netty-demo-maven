package com.wise.nio.example.client;

import java.util.Scanner;

/**
 * 客户端
 */
public class NioClient {

    private static NioClientHandle nioClientHandle;

    public static void start(){
        if (nioClientHandle != null) nioClientHandle.stop();
        nioClientHandle = new NioClientHandle("127.0.0.1", 8899);
        new Thread(nioClientHandle, "Client").start();
    }

    /**
     * 向服务器发送消息
     * @param msg
     * @return
     * @throws Exception
     */
    public static boolean sendMsg(String msg) throws Exception{
        nioClientHandle.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) throws Exception {
        start();
        Scanner scanner = new Scanner(System.in);
        while (NioClient.sendMsg(scanner.next()));
    }

}
