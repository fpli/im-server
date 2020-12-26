package com.sap.mim.client;

import com.sap.mim.bean.MessageModel;
import com.sap.mim.net.ConstantValue;
import com.sap.mim.net.SmartSIMProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

public class NetService {

    private static volatile NetService netService;

    private static final InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 5000);

    private static AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool> poolMap;

    private NetService() {
    }

    public static NetService getNetService() {
        if (null == netService) {
            synchronized (NetService.class) {
                if (null == netService) {
                    netService = new NetService();
                    netService.build();
                }
            }
        }
        return netService;
    }

    /**
     * 连接服务器
     */
    public void build() {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 客户端辅助启动类 对客户端配置
        Bootstrap strap = new Bootstrap();
        strap.group(group)//
                .channel(NioSocketChannel.class)//
                .option(ChannelOption.TCP_NODELAY, true)//
                .option(ChannelOption.SO_KEEPALIVE, true);

        poolMap = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(InetSocketAddress remoteAddress) {
                return new FixedChannelPool(strap.remoteAddress(remoteAddress), new NettyChannelPoolHandler(), 1);
            }
        };

    }

    public void sendMessageModel(MessageModel messageModel) {
        final FixedChannelPool pool = poolMap.get(remoteAddress);
        Future<Channel> f = pool.acquire();
        f.addListener((FutureListener<Channel>) f1 -> {
            if (f1.isSuccess()) {
                Channel ch = f1.getNow();
                System.out.println(ch.id());
                // 获得要发送信息的字节数组
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(messageModel);
                byte[] content = byteArrayOutputStream.toByteArray();
                SmartSIMProtocol request = new SmartSIMProtocol();
                request.setHead_data(ConstantValue.HEAD_DATA);
                request.setContentLength(content.length);
                request.setContent(content);
                ch.writeAndFlush(request);
                // Release back to pool
                pool.release(ch);
            } else {
                f1.cause().printStackTrace();
            }
        });
    }
}
