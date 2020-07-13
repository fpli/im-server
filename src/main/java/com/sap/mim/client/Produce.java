package com.sap.mim.client;


import com.sap.mim.bean.*;
import com.sap.mim.net.ConstantValue;
import com.sap.mim.net.SmartSIMProtocol;
import com.sap.mim.util.MessageIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Produce {

    private final String host;
    private final int port;
    private Channel channel;

    public Produce(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new ProduceInitializer());
            channel = bootstrap.connect(host, port).sync().channel();
            Random random = new Random();
            while (true) {
                LoginMessage loginMessage = new LoginMessage();
                loginMessage.setMessageType(MessageTypeEnum.C2S);
                loginMessage.setC2SMessageTypeEnum(C2SMessageTypeEnum.C_2_S_LOGIN);
                loginMessage.setMsgId(MessageIdGenerator.getMsgId());
                Account account = new Account();
                account.setAccount("123456");
                account.setPassword("111111");
                loginMessage.setAccount(account);
                sendMessageModel(loginMessage);
                System.out.println("消息发送--->" + 123456);
                Thread.currentThread().sleep(random.nextInt(10) * 1000);
            }
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Produce("localhost", 5000).run();
    }

    public void sendMessageModel(MessageModel messageModel) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(messageModel);
            byte[] content = byteArrayOutputStream.toByteArray();
            SmartSIMProtocol request = new SmartSIMProtocol();
            request.setHead_data(ConstantValue.HEAD_DATA);
            request.setContentLength(content.length);
            request.setContent(content);
            channel.writeAndFlush(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}