package com.sap.mim.server;

import com.sap.mim.DataBase.AccountManager;
import com.sap.mim.bean.*;
import com.sap.mim.net.ConstantValue;
import com.sap.mim.net.SmartSIMProtocol;
import com.sap.mim.util.MessageIdGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;


public class LoginTask implements Runnable {

    private ChannelHandlerContext ctx;
    private LoginMessage loginMessage;


    public LoginTask(ChannelHandlerContext ctx, LoginMessage loginMessage) {
        this.ctx = ctx;
        this.loginMessage = loginMessage;
    }


    @Override
    public void run() {
        Account account = loginMessage.getAccount();
        LoginResultMessage loginResultMessage = new LoginResultMessage();
        loginResultMessage.setMsgId(MessageIdGenerator.getMsgId());
        loginResultMessage.setMessageType(MessageTypeEnum.S2C);
        loginResultMessage.setS2CMessageTypeEnum(S2CMessageTypeEnum.S_2_C_LOGIN_RESULT);
        account = AccountManager.login(account);
        boolean isExisted = account != null ? true : false;
        if (isExisted) {
            Connector connector = new Connector();
            connector.setAccount(account);
            connector.setNioSocketChannel((NioSocketChannel) ctx.channel());
            ConnectorManager.addConnector(account.getId(), connector);
            loginResultMessage.setCode(0);
            loginResultMessage.setAccount(account);
            loginResultMessage.setMessage("success");
        } else {
            loginResultMessage.setCode(1);
            loginResultMessage.setAccount(null);
            loginResultMessage.setMessage("failed, account not exits");
            Connector connector = new Connector();
            connector.setAccount(account);
            connector.setNioSocketChannel((NioSocketChannel) ctx.channel());
            ConnectorManager.addConnector(new Random().nextInt(), connector);
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(loginResultMessage);
            SmartSIMProtocol response = new SmartSIMProtocol();
            response.setHead_data(ConstantValue.HEAD_DATA);
            byte[] data = byteArrayOutputStream.toByteArray();
            response.setContentLength(data.length);
            response.setContent(data);
            ctx.channel().writeAndFlush(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
