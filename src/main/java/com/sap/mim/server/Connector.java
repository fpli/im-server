package com.sap.mim.server;

import com.sap.mim.bean.TranObject;
import com.sap.mim.bean.User;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * 描述:每个Connector代表一个已经登录的活动连接
 */
public class Connector {

    private User account;

    private NioSocketChannel ch;

    public void sendTranObject(TranObject tranObject){
        ch.writeAndFlush(tranObject);
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public NioSocketChannel getCh() {
        return ch;
    }

    public void setCh(NioSocketChannel ch) {
        this.ch = ch;
    }
}
