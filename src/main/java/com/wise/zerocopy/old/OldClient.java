package com.wise.zerocopy.old;

import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author lingyuwang
 * @date 2019-06-16 9:06
 */
public class OldClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);

        String fileName = "D://jdk1.8.0_45_64bit.rar";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] byteArray = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(byteArray)) >= 0) {
            total += readCount;
            dataOutputStream.write(byteArray);
        }

        System.out.println("发送总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - startTime));
        // 发送总字节数：156492038，耗时：3085
        // 发送总字节数：156492038，耗时：4609
        // 发送总字节数：156492038，耗时：3816
        // 发送总字节数：156492038，耗时：4526
        // 发送总字节数：156492038，耗时：5280

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }

}
