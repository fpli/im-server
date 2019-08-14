package com.sap.mim.server;


import java.util.concurrent.ConcurrentHashMap;

public class ConnectorManager {

    private static final ConcurrentHashMap<Integer, Connector> connectorConcurrentHashMap = new ConcurrentHashMap<>();

    public static void addConnector(Integer accountId, Connector connector){
        connectorConcurrentHashMap.put(accountId, connector);
    }

    public static Connector findConnectorByAccountId(Integer accountId){
        return connectorConcurrentHashMap.get(accountId);
    }
}
