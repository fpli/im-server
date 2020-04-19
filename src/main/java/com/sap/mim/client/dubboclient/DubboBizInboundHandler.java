package com.sap.mim.client.dubboclient;

import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class DubboBizInboundHandler extends SimpleChannelInboundHandler<SmartSIMProtocol> implements Callable<Object> {

    private Object request;

    private Object response;

    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, SmartSIMProtocol msg) throws Exception {
        response = msg;
        notify();
    }


    @Override
    public synchronized Object call() throws Exception {
        this.ctx.writeAndFlush(request);
        wait();
        return response;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

}
