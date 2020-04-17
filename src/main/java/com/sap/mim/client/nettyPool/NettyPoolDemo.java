package com.sap.mim.client.nettyPool;

import com.sap.mim.client.ClientBizInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

/**
 * Netty自带连接池的使用
 * 一、类介绍
 * 1.ChannelPool——连接池接口
 * <p>
 * 2.SimpleChannelPool——实现ChannelPool接口，简单的连接池实现
 * <p>
 * 3.FixedChannelPool——继承SimpleChannelPool，有大小限制的连接池实现
 * <p>
 * 4.ChannelPoolMap——管理host与连接池映射的接口
 * <p>
 * 5.AbstractChannelPoolMap——抽象类，实现ChannelPoolMap接口
 */
public class NettyPoolDemo {
    // key为目标host，value为目标host的连接池
    public static ChannelPoolMap<String, FixedChannelPool> poolMap;
    private static final Bootstrap bootstrap = new Bootstrap();

    static {
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.remoteAddress("localhost", 8080);
    }


    /**
     * netty连接池使用
     */
    public void init() {
        poolMap = new AbstractChannelPoolMap<String, FixedChannelPool>() {

            @Override
            protected FixedChannelPool newPool(String key) {
                ChannelPoolHandler handler = new ChannelPoolHandler() {
                    /**
                     * 使用完channel需要释放才能放入连接池
                     *
                     */
                    @Override
                    public void channelReleased(Channel ch) throws Exception {
                        // 刷新管道里的数据
                        // ch.writeAndFlush(Unpooled.EMPTY_BUFFER); // flush掉所有写回的数据
                        System.out.println("channelReleased......");
                    }

                    /**
                     * 当链接创建的时候添加channelHandler，只有当channel不足时会创建，但不会超过限制的最大channel数
                     *
                     */
                    @Override
                    public void channelCreated(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                        ch.pipeline().addLast(new StringDecoder());
                        // 绑定channel的handler
                        ch.pipeline().addLast(new ClientBizInboundHandler());
                    }

                    /**
                     *  获取连接池中的channel
                     *
                     */
                    @Override
                    public void channelAcquired(Channel ch) throws Exception {
                        System.out.println("channelAcquired......");
                    }
                };

                return new FixedChannelPool(bootstrap, handler, 50); //单个host连接池大小
            }
        };

    }

    /**
     * 发送请求
     *
     * @param msg     请求参数
     * @param command 请求方法
     * @return
     */
    public Object send(final Object msg, final String command) {
        //封装请求数据
        final Object request = new Object();


        //从连接池中获取连接
        final FixedChannelPool pool = poolMap.get("localhost");
        //申请连接，没有申请到或者网络断开，返回null
        Future<Channel> future = pool.acquire();
        future.addListener(new FutureListener<Channel>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
                //给服务端发送数据
                Channel channel = future.getNow();
                channel.writeAndFlush(request);

                System.out.println(channel.id());
                // 连接放回连接池，这里一定记得放回去
                pool.release(channel);
            }
        });

        //获取服务端返回的数据

        return null;

    }
}
