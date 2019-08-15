package com.sap.mim.net;

import com.sap.mim.bean.ACKMessage;
import com.sap.mim.server.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 描述:客户端对应的Channel处理器
 */
public class ChildNioSocketChannelHandler extends SimpleChannelInboundHandler<SmartSIMProtocol> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, SmartSIMProtocol msg) throws Exception {
        Container.receiveSmartSIMProtocolMsg(ctx, msg);
        SmartSIMProtocol response  = new SmartSIMProtocol();
        ACKMessage ackMessage      = new ACKMessage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos     = new ObjectOutputStream(baos);
        oos.writeObject(ackMessage);
        response.setHead_data(ConstantValue.HEAD_DATA);
        byte[] data = baos.toByteArray();
        response.setContentLength(data.length);
        response.setContent(data);
        ctx.writeAndFlush(response);
        baos = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // cause.printStackTrace();
        ctx.close();
    }
}

