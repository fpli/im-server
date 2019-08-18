package com.sap.mim.client;

import com.sap.mim.bean.ChatMessage;
import com.sap.mim.bean.ChatMessageType;
import com.sap.mim.bean.LoginMessage;
import com.sap.mim.bean.MessageType;
import com.sap.mim.util.MessageIdGenerator;

public class ClientStart {

    public static void main(String[] args) {

        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessageType(MessageType.C2S);
        loginMessage.setMsgId(MessageIdGenerator.getMsgId());
        loginMessage.setAccountNo("ggggggggggg");
        loginMessage.setPassword("123456");
        NetService.getNetService().sendMessageModel(loginMessage);
        System.out.println("消息发送--->"+123456);


        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMsgId(MessageIdGenerator.getMsgId());
        chatMessage.setMessageType(MessageType.C2S);
        chatMessage.setChatMessageType(ChatMessageType.TEXT_MESSAGE);
        chatMessage.setSenderId(1001);
        chatMessage.setReceiverId(1002);
        chatMessage.setSendTime("2019年8月21日 下午13:50:23");
        chatMessage.setContent("通信回话消息".getBytes());
        NetService.getNetService().sendMessageModel(chatMessage);
        System.out.println("通话消息发送--->"+123456);

    }

}
