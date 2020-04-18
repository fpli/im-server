package com.sap.mim.client.dubboclient;

import com.sap.mim.net.SmartSIMDecoder;
import com.sap.mim.net.SmartSIMEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private DubboBizInboundHandler bizInboundHandler;

    public ClientChannelInitializer(DubboBizInboundHandler bizInboundHandler) {
        this.bizInboundHandler = bizInboundHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(10, 0, 0));
        pipeline.addLast(new SmartSIMEncoder());
        pipeline.addLast(new SmartSIMDecoder());
        // 处理网络IO
        pipeline.addLast(bizInboundHandler);
    }

}
