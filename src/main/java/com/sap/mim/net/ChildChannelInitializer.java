package com.sap.mim.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ChildChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        // 添加自定义协议的编解码工具
        ch.pipeline().addLast(new IdleStateHandler(10,0,0));
        ch.pipeline().addLast(new SmartSIMEncoder());
        ch.pipeline().addLast(new SmartSIMDecoder());
        // 处理网络IO
        ch.pipeline().addLast(new ChildNioSocketChannelHandler());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

}
