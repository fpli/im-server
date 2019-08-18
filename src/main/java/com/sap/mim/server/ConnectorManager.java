package com.sap.mim.server;


import com.sap.mim.bean.ChatMessage;
import com.sap.mim.bean.ChatMessageType;
import com.sap.mim.bean.MessageType;
import com.sap.mim.net.ConstantValue;
import com.sap.mim.net.SmartSIMProtocol;
import com.sap.mim.util.MessageIdGenerator;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectorManager {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<Integer, Connector> connectorConcurrentHashMap = new ConcurrentHashMap<>();

    public static void addConnector(Integer accountId, Connector connector){
        connectorConcurrentHashMap.put(accountId, connector);
        channels.add(connector.getNioSocketChannel());
        new Thread(){
            //
            @Override
            public void run() {
                for(;;){
                    ConnectorManager.tuisong();
                    try {
                        Thread.sleep(60000 * 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static Connector findConnectorByAccountId(Integer accountId){
        return connectorConcurrentHashMap.get(accountId);
    }

    public static void tuisong(){
        connectorConcurrentHashMap.values().forEach((channel -> {

            try {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMsgId(MessageIdGenerator.getMsgId());
                chatMessage.setMessageType(MessageType.C2S);
                chatMessage.setChatMessageType(ChatMessageType.TEXT_MESSAGE);
                chatMessage.setSenderId(1001);
                chatMessage.setReceiverId(1002);
                chatMessage.setSendTime("2019年8月21日 下午13:50:23");
                chatMessage.setContent("通信回话消息".getBytes());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream       = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(chatMessage);
                byte[] content = byteArrayOutputStream.toByteArray();
                SmartSIMProtocol request = new SmartSIMProtocol();
                request.setHead_data(ConstantValue.HEAD_DATA);
                request.setContentLength(content.length);
                request.setContent(content);
                channel.getNioSocketChannel().writeAndFlush(request);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
    }
}
