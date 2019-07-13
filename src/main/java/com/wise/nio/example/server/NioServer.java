package com.wise.nio.example.server;

/**
 * 服务端
 */
public class NioServer {

    private static NioServerHandle nioServerHandle;

    public static void start() {
        if (nioServerHandle != null) nioServerHandle.stop();
        nioServerHandle = new NioServerHandle(8899);
        new Thread(nioServerHandle, "Server").start();
    }

    public static void main(String[] args){

        start();
    }

}
