package com.sap.mim.client.dubboclient;

import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class DubboBizInboundHandler extends SimpleChannelInboundHandler<SmartSIMProtocol> implements Callable<Object> {

    private Object request;

    private Object response;

    private Channel channel;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
       super.handlerAdded(ctx);
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, SmartSIMProtocol msg) throws Exception {
        response = msg;
        notify();
    }

    @Override
    public synchronized Object call() throws Exception {
        channel.writeAndFlush(request);
        wait();
        return response;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

}
