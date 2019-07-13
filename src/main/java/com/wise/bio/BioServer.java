package com.wise.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端
 */
public class BioServer {

    private static ServerSocket server;

    // 线程池，处理每个客户端的请求
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {
        try{
            server = new ServerSocket(8899);
            System.out.println("服务器已启动，端口号：" + 8899);

            while (true) {
                Socket socket= server.accept(); // 会一直阻塞，直到有socket与其建立连接
                System.out.println("有新的客户端连接----" );

                // 当有新的客户端接入时，打包成一个任务，投入线程池
                executorService.execute(new BioServerHandler(socket));
            }
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

}
