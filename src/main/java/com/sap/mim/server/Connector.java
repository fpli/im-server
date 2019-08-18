package com.sap.mim.server;

import com.sap.mim.bean.Account;
import com.sap.mim.bean.ChatMessage;
import com.sap.mim.net.ConstantValue;
import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * 描述:每个Connector代表一个已经登录的活动连接
 */
public class Connector {

    private Account account;

    private NioSocketChannel nioSocketChannel;

    public void sentChatMessage(ChatMessage chatMessage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream       = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(chatMessage);
        byte[] content = byteArrayOutputStream.toByteArray();
        SmartSIMProtocol response = new SmartSIMProtocol();
        response.setHead_data(ConstantValue.HEAD_DATA);
        response.setContentLength(content.length);
        response.setContent(content);
        nioSocketChannel.writeAndFlush(response);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public NioSocketChannel getNioSocketChannel() {
        return nioSocketChannel;
    }

    public void setNioSocketChannel(NioSocketChannel nioSocketChannel) {
        this.nioSocketChannel = nioSocketChannel;
    }
}
