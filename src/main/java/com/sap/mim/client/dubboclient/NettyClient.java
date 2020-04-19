package com.sap.mim.client.dubboclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static DubboBizInboundHandler handler;

    private static ExecutorService executorService = new ThreadPoolExecutor(2,5,3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    private static int count;

    public NettyClient() {

    }

    public Object getBean(Class<?> serviceClass){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, (proxy, method, args) -> {
            if (handler==null){
                init();
            }
            //System.out.println("count="+ (++count));
            handler.setRequest(args[0]);
            return executorService.submit(handler).get();
        });
    }

    private static void init(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            handler = new DubboBizInboundHandler();
            ClientChannelInitializer clientChannelInitializer = new ClientChannelInitializer(handler);
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class)//
                    .option(ChannelOption.TCP_NODELAY, true)//
                    .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(clientChannelInitializer);

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 5000).sync();
            //channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //workerGroup.shutdownGracefully();
        }
    }
}
