package com.sap.mim.client;


import com.sap.mim.net.SmartSIMDecoder;
import com.sap.mim.net.SmartSIMEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ProduceInitializer extends ChannelInitializer<NioSocketChannel> {

    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pi = ch.pipeline();
        pi.addLast(new IdleStateHandler(60, 30, 0));
        // 添加自定义协议的编解码工具
        pi.addLast(new SmartSIMEncoder());
        pi.addLast(new SmartSIMDecoder());
        pi.addLast(new ClientBizInboundHandler());
    }
}