package com.sap.mim.server;

import com.sap.mim.DataBase.UserDao;
import com.sap.mim.bean.LoginMessage;
import com.sap.mim.bean.User;
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
        User user = new User();
        user.setAccount(loginMessage.getAccountNo());
        user.setPassword(loginMessage.getPassword());
        boolean isExisted =  UserDao.login(user);
        if (isExisted){
            Connector connector = new Connector();
            connector.setAccount(user);
            connector.setNioSocketChannel((NioSocketChannel) ctx.channel());
            ConnectorManager.addConnector(user.getId(), connector);
        } else {

        }


        SmartSIMProtocol response  = new SmartSIMProtocol();

        ctx.writeAndFlush(response);
    }
}
