package com.sap.mim.server;

import com.sap.mim.DataBase.UserDao;
import com.sap.mim.bean.LoginMessage;
import com.sap.mim.bean.Account;
import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;


public class LoginTask implements Runnable {

    private ChannelHandlerContext ctx;
    private LoginMessage          loginMessage;


    public LoginTask(ChannelHandlerContext ctx, LoginMessage loginMessage) {
        this.ctx          = ctx;
        this.loginMessage = loginMessage;
    }


    @Override
    public void run() {
        Account account = new Account();
        account.setAccount(loginMessage.getAccountNo());
        account.setPassword(loginMessage.getPassword());
        boolean isExisted =  UserDao.login(account);
        if (isExisted){
            Connector connector = new Connector();
            connector.setAccount(account);
            connector.setNioSocketChannel((NioSocketChannel) ctx.channel());
            ConnectorManager.addConnector(account.getId(), connector);
        } else {

        }


        SmartSIMProtocol response  = new SmartSIMProtocol();

        ctx.writeAndFlush(response);
    }
}
