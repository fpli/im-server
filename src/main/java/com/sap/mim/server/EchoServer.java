package com.sap.mim.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        //指定 NioEventLoopGroup 来接受和处理新连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //create ServerBootstrap instance
            ServerBootstrap b = new ServerBootstrap();
            //Specifies NIO transport, local socket address
            /*Adds handler to channel pipeline 指定通道类型为 NioServerSocketChannel
            设置 InetSocketAddress 让服务器监听某个端口已等待客户端连接
             */
            b.group(group).channel(NioServerSocketChannel.class).
                    localAddress(port).childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(0,0,5));
                    ch.pipeline().addLast(new EchoChildHandler());
                }
            });
            /*Binds server, waits for server to close, and releases resources
             * 绑定服务器等待直到绑定完成，调用 sync()方法会阻塞直到服务器完成绑定，
             * 然后服务器等待通道关闭，因为使用 sync()，所以关闭操作也会被阻塞。
             * 现在你可以关闭EventLoopGroup 和释放所有资源，包括创建的线程。
             * */
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on “" + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(65535).start();
    }

}
