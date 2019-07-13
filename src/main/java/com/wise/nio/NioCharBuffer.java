package com.wise.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author lingyuwang
 * @date 2019-06-14 22:19
 */
public class NioCharBuffer {

    public static void main(String[] args) throws Exception {
        String inputFile = "D://NioCharBuffer_In.txt";
        String outputFile = "D://NioCharBuffer_On.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        long inputLength = inputFileChannel.size();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

//        Charset charset = Charset.forName("UTF-8");
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);

        System.out.println(charBuffer.get(6));
        System.out.println(charBuffer.get(7));
        System.out.println(charBuffer.get(8));

        ByteBuffer outputData = encoder.encode(charBuffer);

        outputFileChannel.write(outputData);

        outputFileChannel.close();
        inputFileChannel.close();
    }

}
