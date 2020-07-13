package com.sap.mim.server;

import com.sap.mim.bean.ChatMessage;
import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.*;

/**
 * 描述:负责接受所有客户端数据，过期数据清理，失效连接关闭
 */
public class Container {

    private static final LinkedBlockingQueue<SmartSIMProtocol>                      messageQueue = new LinkedBlockingQueue<>();
    private static final ExecutorService                                            analysisExecutorService = Executors.newFixedThreadPool(5);
    private static final ConcurrentMap<Integer, LinkedBlockingQueue<ChatMessage>>   waitForSendQueue = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Long, ChatMessage>                           sendedChatMessageQueue = new ConcurrentHashMap<>();

    public static void receiveSmartSIMProtocolMsg(ChannelHandlerContext ctx, SmartSIMProtocol smartSIMProtocol) {
        try {
            messageQueue.put(smartSIMProtocol);
            AnalysisSmartSIMProtocolTask analysisSmartSIMProtocolTask = new AnalysisSmartSIMProtocolTask(ctx, smartSIMProtocol);
            analysisExecutorService.submit(analysisSmartSIMProtocolTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void receiveChatMessage(ChatMessage chatMessage) throws InterruptedException {
        if (waitForSendQueue.containsKey(chatMessage.getReceiverId())) {
            waitForSendQueue.get(chatMessage.getReceiverId()).put(chatMessage);
        } else {
            LinkedBlockingQueue<ChatMessage> linkedBlockingQueue = new LinkedBlockingQueue<>();
            waitForSendQueue.putIfAbsent(chatMessage.getReceiverId(), linkedBlockingQueue);
            linkedBlockingQueue.put(chatMessage);
        }
    }


    public static void receiveSendChatMessage(ChatMessage chatMessage) {
        sendedChatMessageQueue.putIfAbsent(chatMessage.getMsgId(), chatMessage);
    }

    public static void removeSendChatMessage(Long msgId) {
        ChatMessage chatMessage = sendedChatMessageQueue.remove(msgId);
        chatMessage = null;
    }
}
