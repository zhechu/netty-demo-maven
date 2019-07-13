package com.wise.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 复制文件 测试
 * @author lingyuwang
 *
 */
public class CopyFileTest {

	public static void main(String[] args) throws Exception {
		// 直接使用 FileInputStream 和 FileOutputStream
		//testIoCopy(); // 性能极差，放弃测试 -> 6

		// 使用 Buffered 包装 Stream
		//testBioCopy(); // 9109ms 8974ms 8888ms -> 5

		// 使用 Buffered 包装 Reader 和 Writer
		//testRbioCopy(); // 3722ms 3829ms 3751ms -> 4

		// 使用 Stream、FileChannel 和 ByteBuffer（堆外内存）
		//testNioCopy(); // 2496ms 2575ms 2518ms -> 3

		// 使用 RandomAccessFile、FileChannel 和 ByteBuffer（堆外内存）
		//testRanNioCopy(); // 2005ms 2141ms 2122ms -> 2

		// 使用 内存映射 和 NIO 结合的方式
		testMapNioCopy(); // 1295ms 1175ms 839ms -> 1
	}

	public static void mapNioCopyFile(String resource, String destination)
			throws Exception {
		long length=0;
		RandomAccessFile raf=new RandomAccessFile(resource , "r");
		FileChannel fcr=raf.getChannel();
		length=fcr.size();
		//返回要读取文件的映射内存区块
		MappedByteBuffer mbb=fcr.map(FileChannel.MapMode.READ_ONLY, 0, length);
		ByteBuffer buffer=mbb.get(new byte[(int)length]);

		//要写入的文件
		RandomAccessFile raw=new RandomAccessFile(destination, "rw");
		FileChannel fcw=raw.getChannel();
		MappedByteBuffer mbbw=fcw.map(FileChannel.MapMode.READ_WRITE, 0, length);
		for(int i=0;i<length;i++){
			mbbw.put(i,buffer.get(i));
		}
		fcw.close();
		fcr.close();
		raf.close();
		raw.close();
	}

	public static void ranNioCopyFile(String resource, String destination)
			throws Exception {
		RandomAccessFile raf=new RandomAccessFile(resource , "r");
		RandomAccessFile raw=new RandomAccessFile(destination, "rw");
		// 读文件通道
		FileChannel readChannel = raf.getChannel();
		// 写文件通道
		FileChannel writeChannel = raw.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			buffer.clear();
			int len = readChannel.read(buffer); // 读入数据
			if (len == -1) {
				break;
			}
			buffer.flip();
			writeChannel.write(buffer); // 写入文件
		}
		readChannel.close();
		writeChannel.close();
		raw.close();
		raf.close();
	}

	public static void nioCopyFile(String resource, String destination)
			throws Exception {
		FileInputStream fis = new FileInputStream(resource);
		FileOutputStream fos = new FileOutputStream(destination);
		// 读文件通道
		FileChannel readChannel = fis.getChannel();
		// 写文件通道
		FileChannel writeChannel = fos.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			buffer.clear();
			int len = readChannel.read(buffer); // 读入数据
			if (len == -1) {
				break;
			}
			buffer.flip();
			writeChannel.write(buffer); // 写入文件
		}
		readChannel.close();
		writeChannel.close();
		fos.close();
		fis.close();
	}

	public static void rbioCopyFile(String resource, String destination)
			throws Exception {
		FileReader fr = new FileReader(resource);
		BufferedReader bf = new BufferedReader(fr);
		FileWriter fw = new FileWriter(destination);
		BufferedWriter bw = new BufferedWriter(fw);
		 String temp = null ;
         while ((temp = bf.readLine()) != null ){
        	 bw.write(temp);
         }
		bw.close();
		bf.close();
	}

	public static void ioCopyFile(String resource, String destination)
			throws Exception {
		FileInputStream fis = new FileInputStream(resource);
		FileOutputStream fos = new FileOutputStream(destination);
		int count = 0;
		while ((count = fis.read()) != -1) {
			fos.write(count);
		}
		fos.close();
		fis.close();
	}

	public static void bioCopyFile(String resource, String destination)
			throws Exception {
		FileInputStream fis = new FileInputStream(resource);
		BufferedInputStream bis = new BufferedInputStream(fis);
		FileOutputStream fos = new FileOutputStream(destination);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int count = 0;
		while ((count = bis.read()) != -1) {
			bos.write(count);
		}
		bos.close();
		bis.close();
	}

	public static void testIoCopy() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			ioCopyFile("D://filecopy.txt", "D://filecopy1.txt");
		}
		System.out.println("io耗时" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testRbioCopy() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			rbioCopyFile("D://filecopy.txt", "D://filecopy1.txt");
		}
		System.out.println("io耗时" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testBioCopy() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			bioCopyFile("D://filecopy.txt", "D://filecopy1.txt");
		}
		System.out.println("io耗时" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testNioCopy() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			nioCopyFile("D://filecopy.txt", "D://filecopy1.txt");
		}
		System.out.println("nio耗时" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testRanNioCopy() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			ranNioCopyFile("D://filecopy.txt", "D://filecopy1.txt");
		}
		System.out.println("ran nio耗时" + (System.currentTimeMillis() - start) + "ms");
	}

	public static void testMapNioCopy() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			mapNioCopyFile("D://filecopy.txt", "D://filecopy1.txt");
		}
		System.out.println("map nio耗时" + (System.currentTimeMillis() - start) + "ms");
	}

}