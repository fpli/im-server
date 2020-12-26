package com.sap.mim.net;

import com.sap.mim.bean.ACKMessage;
import com.sap.mim.bean.MessageTypeEnum;
import com.sap.mim.server.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 描述:客户端对应的Channel处理器
 */
public class ChildNioSocketChannelHandler extends SimpleChannelInboundHandler<SmartSIMProtocol> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " is connected");
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " is disconnected.");
        System.out.println("channelGroup size:" + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SmartSIMProtocol msg) throws Exception {
        Container.receiveSmartSIMProtocolMsg(ctx, msg);
        SmartSIMProtocol response = new SmartSIMProtocol();
        ACKMessage ackMessage = new ACKMessage();
        ackMessage.setMessageType(MessageTypeEnum.ACK);
        ackMessage.setMsgId(12L);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ackMessage);
        response.setHead_data(ConstantValue.HEAD_DATA);
        byte[] data = baos.toByteArray();
        response.setContentLength(data.length);
        response.setContent(data);
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        SmartSIMProtocol response = new SmartSIMProtocol();
        ACKMessage ackMessage = new ACKMessage();
        ackMessage.setMessageType(MessageTypeEnum.ACK);
        ackMessage.setMsgId(12L);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ackMessage);
        response.setHead_data(ConstantValue.HEAD_DATA);
        byte[] data = baos.toByteArray();
        response.setContentLength(data.length);
        response.setContent(data);
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " channelInactive");
    }
}

