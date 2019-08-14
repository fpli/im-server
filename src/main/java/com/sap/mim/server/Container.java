package com.sap.mim.server;

import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述:负责接受所有客户端数据，过期数据清理，失效连接关闭
 */
public class Container {

    private static final LinkedBlockingQueue<SmartSIMProtocol> messageQueue = new LinkedBlockingQueue<>();
    private static final ExecutorService analysisExecutorService = Executors.newFixedThreadPool(5);

    public static void receiveSmartSIMProtocolMsg(ChannelHandlerContext ctx, SmartSIMProtocol smartSIMProtocol){
        try {
            messageQueue.put(smartSIMProtocol);
            AnalysisSmartSIMProtocolTask analysisSmartSIMProtocolTask = new AnalysisSmartSIMProtocolTask(ctx, smartSIMProtocol);
            analysisExecutorService.submit(analysisSmartSIMProtocolTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
