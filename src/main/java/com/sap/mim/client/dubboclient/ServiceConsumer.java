package com.sap.mim.client.dubboclient;

public class ServiceConsumer {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        ServiceI serviceBean = (ServiceI) nettyClient.getBean(ServiceI.class);
        Object result  = serviceBean.invoke("param1");
        System.out.println(result);
    }
}
