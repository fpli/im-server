package com.sap.mim.net;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 描述:客户端对应的Channel处理器
 */
public class ChildNioSocketChannelHandler extends SimpleChannelInboundHandler<SmartSIMProtocol> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, SmartSIMProtocol msg) throws Exception {
        System.out.println("Server接受的客户端的信息 :" + msg.toString());
        // 会写数据给客户端
        String str = "Hi I am Server ...";
        SmartSIMProtocol response = new SmartSIMProtocol();
        response.setContentLength(str.getBytes().length);
        response.setContent(str.getBytes());
        // 当服务端完成写操作后，关闭与客户端的连接
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // cause.printStackTrace();
        ctx.close();
    }
}

