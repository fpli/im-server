package com.sap.mim.server;

import com.sap.mim.bean.MessageModel;
import com.sap.mim.net.SmartSIMProtocol;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AnalysisSmartSIMProtocolTask implements Runnable {

    private ChannelHandlerContext   ctx;
    private SmartSIMProtocol        smartSIMProtocol;

    public AnalysisSmartSIMProtocolTask(ChannelHandlerContext ctx, SmartSIMProtocol smartSIMProtocol) {
        this.ctx = ctx;
        this.smartSIMProtocol = smartSIMProtocol;
    }

    @Override
    public void run() {
        try {
            byte[] data = smartSIMProtocol.getContent();
            ByteArrayInputStream baas = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baas);
            MessageModel messageModel = (MessageModel) ois.readObject();
            Engine.handleMessage(ctx, messageModel);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
