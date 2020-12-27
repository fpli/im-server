package com.sap.mim.netty.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.TimeUnit;


public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // add task to another thread
    static final EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(16);

    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE是一个全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 表示连接建立,一旦连接，第一个被执行 将channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将客户端加入的信息推送给其他客户端
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "进入聊天\n");
        channelGroup.add(channel);
    }

    // 表示断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将客户端加入的信息推送给其他客户端
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
        System.out.println("channelGroup size:\t" + channelGroup.size());
    }

    // 表示channel处于活动状态，提示用户上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线");
    }

    //// 表示channel处于不活动状态，提示用户离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(Thread.currentThread().getName());
        channelGroup.forEach((ch) -> {
            if (ch != channel) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息:" + msg + "\n");
            } else {
                channel.writeAndFlush("[自己]发送了消息" + msg + "\n");
            }
        });

        eventExecutorGroup.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName());
                ctx.writeAndFlush("dfdfdff");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
