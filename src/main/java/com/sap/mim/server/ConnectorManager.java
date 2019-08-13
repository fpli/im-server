package com.sap.mim.server;


import java.util.concurrent.ConcurrentHashMap;

public class ConnectorManager {

    private ConcurrentHashMap<Integer, Connector> connectorConcurrentHashMap = new ConcurrentHashMap<>();

    public void addConnector(Connector connector){
        connectorConcurrentHashMap.put(1, connector);
    }

    public Connector findConnectorByAccountId(Integer accountId){
        return connectorConcurrentHashMap.get(accountId);
    }
}
