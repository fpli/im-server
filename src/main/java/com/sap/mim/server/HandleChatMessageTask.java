package com.sap.mim.server;

import com.sap.mim.bean.ChatMessage;
import com.sap.mim.bean.MessageType;
import com.sap.mim.util.MessageIdGenerator;

import java.io.IOException;

public class HandleChatMessageTask implements Runnable{

    private ChatMessage chatMessage;

    public HandleChatMessageTask(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public void run() {
        try {
            int receiverId = chatMessage.getReceiverId();
            chatMessage.setMsgId(MessageIdGenerator.getMsgId());
            chatMessage.setMessageType(MessageType.S2C);
            // 判断接收方是否在线，在线则直接发送，不在线则保存离线数据
            Connector connector = ConnectorManager.findConnectorByAccountId(receiverId);
            if (null == connector){
                Container.receiveChatMessage(chatMessage);
            } else {
                Container.receiveSendChatMessage(chatMessage);
                connector.sendChatMessage(chatMessage);
            }
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
