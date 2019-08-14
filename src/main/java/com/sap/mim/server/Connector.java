package com.sap.mim.server;

import com.sap.mim.bean.ChatMessage;
import com.sap.mim.bean.TranObject;
import com.sap.mim.bean.User;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * 描述:每个Connector代表一个已经登录的活动连接
 */
public class Connector {

    private User account;

    private NioSocketChannel nioSocketChannel;

    public void sentChatMessage(ChatMessage chatMessage){
        nioSocketChannel.writeAndFlush(chatMessage);
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public NioSocketChannel getNioSocketChannel() {
        return nioSocketChannel;
    }

    public void setNioSocketChannel(NioSocketChannel nioSocketChannel) {
        this.nioSocketChannel = nioSocketChannel;
    }
}
