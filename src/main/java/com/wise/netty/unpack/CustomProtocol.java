package com.wise.netty.unpack;

/**
 * @author lingyuwang
 * @date 2019-06-29 23:05
 */
public class CustomProtocol {

    private int length;

    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
