package com.sap.mim.client.dubboclient;

import com.sap.mim.bean.Account;
import com.sap.mim.bean.C2SMessageType;
import com.sap.mim.bean.LoginMessage;
import com.sap.mim.bean.MessageType;
import com.sap.mim.net.ConstantValue;
import com.sap.mim.net.SmartSIMProtocol;
import com.sap.mim.util.MessageIdGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServiceConsumer {

    public static void main(String[] args) throws IOException {
        NettyClient nettyClient = new NettyClient();
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessageType(MessageType.C2S);
        loginMessage.setC2SMessageType(C2SMessageType.C_2_S_LOGIN);
        loginMessage.setMsgId(MessageIdGenerator.getMsgId());
        Account account = new Account();
        account.setAccount("123456");
        account.setPassword("111111");
        loginMessage.setAccount(account);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(loginMessage);
        byte[] content = byteArrayOutputStream.toByteArray();
        SmartSIMProtocol request = new SmartSIMProtocol();
        request.setHead_data(ConstantValue.HEAD_DATA);
        request.setContentLength(content.length);
        request.setContent(content);
        ServiceI serviceBean = (ServiceI) nettyClient.getBean(ServiceI.class);
        Object result  = serviceBean.invoke(request);
        System.out.println(result);
        System.out.println(serviceBean.invoke(request));
        System.out.println(serviceBean.invoke(request));
    }
}
