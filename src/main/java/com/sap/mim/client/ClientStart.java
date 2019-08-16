package com.sap.mim.client;

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
    }

}
