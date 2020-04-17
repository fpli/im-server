package com.sap.mim.client.dubboclient;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private DubboBizInboundHandler handler = new DubboBizInboundHandler();

    private static ExecutorService executorService = new ThreadPoolExecutor(2,5,3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    private static int count;

    public Object getBean(Class<?> serviceClass){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, (proxy, method, args) -> {
            System.out.println("count="+ (++count));
            handler.setRequest(args[0]);
            return executorService.submit(handler);
        });
    }

}
