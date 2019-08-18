package com.sap.mim.server;

import com.sap.mim.bean.ACKMessage;
import com.sap.mim.bean.ChatMessage;
import com.sap.mim.bean.LoginMessage;
import com.sap.mim.bean.MessageModel;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述:引擎负责数据计算，离线存储，发送
 */
public class Engine {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void handleMessage(ChannelHandlerContext ctx, MessageModel messageModel){
        if (messageModel instanceof LoginMessage){
            LoginMessage loginMessage = (LoginMessage) messageModel;
            System.out.println(loginMessage);
            LoginTask loginTask       = new LoginTask(ctx, loginMessage);
            executorService.submit(loginTask);
        }

        if (messageModel instanceof ACKMessage){
            ACKMessage ackMessage = (ACKMessage) messageModel;
            Long ackmsgId = ackMessage.getMsgId();
            System.out.println(ackMessage);
            // 表示客户端接收成功，服务端可以清理这个消息
            Container.remoceSendChatMessage(ackmsgId);
        }
        // 服务端接收到客户端的聊天消息
        if (messageModel instanceof ChatMessage){
            ChatMessage chatMessage = (ChatMessage) messageModel;
            System.out.println(chatMessage);
            HandleChatMessageTask handleChatMessageTask = new HandleChatMessageTask(chatMessage);
            executorService.submit(handleChatMessageTask);
        }
    }
}
