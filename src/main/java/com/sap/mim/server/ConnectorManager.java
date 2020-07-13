package com.sap.mim.server;


import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectorManager {

    public static ChannelGroup                                  channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<Integer, Connector>  connectorConcurrentHashMap = new ConcurrentHashMap<>();

    public static void addConnector(Integer accountId, Connector connector) {
        connectorConcurrentHashMap.put(accountId, connector);
        channels.add(connector.getNioSocketChannel());
    }

    public static Connector findConnectorByAccountId(Integer accountId) {
        return connectorConcurrentHashMap.get(accountId);
    }
}
