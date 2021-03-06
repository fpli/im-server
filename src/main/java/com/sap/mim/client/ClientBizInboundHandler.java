package com.sap.mim.client;

import com.sap.mim.bean.*;
import com.sap.mim.net.SmartSIMProtocol;
import com.sap.mim.util.MessageIdGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * 描述:客户端业务数据接收处理器
 */
public class ClientBizInboundHandler extends SimpleChannelInboundHandler<SmartSIMProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ACKMessage ackMessage = new ACKMessage();
        ackMessage.setMessageType(MessageTypeEnum.ACK);
        ackMessage.setMsgId(MessageIdGenerator.getMsgId());
        NetService.getNetService().sendMessageModel(ackMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SmartSIMProtocol msg) throws Exception {
        byte[] data = msg.getContent();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object message = objectInputStream.readObject();
        if (message instanceof ACKMessage) {
            ACKMessage ackMessage = (ACKMessage) message;
            Long msgId = ackMessage.getMsgId();
            // 处理客户端已发送的消息
            System.out.println(ackMessage.toString() + msgId);
        }

        if (message instanceof ChatMessage) {
            ChatMessage chatMessage = (ChatMessage) message;
            System.out.println(chatMessage);
            ACKMessage ackMessage = new ACKMessage();
            ackMessage.setMessageType(MessageTypeEnum.ACK);
            ackMessage.setMsgId(((ChatMessage) message).getMsgId());
            NetService.getNetService().sendMessageModel(ackMessage);
        }

        if (message instanceof LoginResultMessage) {
            LoginResultMessage loginResultMessage = (LoginResultMessage) message;
            System.out.println(loginResultMessage);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}