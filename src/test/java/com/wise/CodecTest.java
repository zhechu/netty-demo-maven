package com.wise;

import com.wise.netty.messagepack.User;
import com.wise.netty.messagepack.UserContact;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import java.io.IOException;

/**
 * 序列化测试
 */
public class CodecTest {

    @Test
    public void messagePackCodecTest() throws IOException {
        User user = new User();
        user.setAge(20);
        String userName = "张三";
        user.setUserName(userName);
        user.setId("zhagnsan");
        user.setUserContact(new UserContact(userName + "@xiangxue.com","133"));

        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(user);

        System.out.println("序列化后大小：" + raw.length);

        User rawUser = messagePack.read(raw, User.class);

        System.out.println("解析后的数据：" + rawUser);
    }

    @Test
    public void messagePackCodecByNettyTest() throws IOException {
        User user = new User();
        user.setAge(20);
        String userName = "张三";
        user.setUserName(userName);
        user.setId("zhagnsan");
        user.setUserContact(new UserContact(userName + "@xiangxue.com","133"));

        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(user);

        System.out.println("序列化后大小：" + raw.length);

        ByteBuf msgBuf = Unpooled.copiedBuffer(raw);

        byte[] bs = new byte[msgBuf.readableBytes()];

        msgBuf.getBytes(msgBuf.readerIndex(), bs, 0, bs.length);

        User rawUser = messagePack.read(bs, User.class);

        System.out.println("解析后的数据：" + rawUser);
    }

}
