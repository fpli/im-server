package com.sap.mim.server;

import com.sap.mim.bean.ChatMessage;
import com.sap.mim.util.MessageIdGenerator;

public class HandleChatMessageTask implements Runnable{

    private ChatMessage chatMessage;

    public HandleChatMessageTask(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public void run() {
        int receiverId = chatMessage.getReceiverId();
        // 判断接收方是否在线，在线则直接发送，不在线则保存离线数据
        Connector connector = ConnectorManager.findConnectorByAccountId(receiverId);
        if (null == connector){

        } else {
            chatMessage.setMsgId(MessageIdGenerator.getMsgId());
            connector.sentChatMessage(chatMessage);
        }
    }
}
