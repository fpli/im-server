package com.sap.mim.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ChildChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        // 添加自定义协议的编解码工具
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(60, 30, 0));
        pipeline.addLast(new MyHandler());
        pipeline.addLast(new SmartSIMEncoder());
        pipeline.addLast(new SmartSIMDecoder());
        // 处理网络IO
        pipeline.addLast(new ChildNioSocketChannelHandler());
    }

}
