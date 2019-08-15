package com.sap.mim.client;

import com.sap.mim.bean.ACKMessage;
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

        for (int i=0; i< 40; i++){

            ACKMessage ackMessage = new ACKMessage();
            ackMessage.setMessageType(MessageType.ACK);
            ackMessage.setMsgId(MessageIdGenerator.getMsgId());
            NetService.getNetService().sendMessageModel(ackMessage);
        }
    }

}
