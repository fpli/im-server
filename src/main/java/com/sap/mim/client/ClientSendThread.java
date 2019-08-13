package com.sap.mim.client;

import com.sap.mim.bean.TranObject;


/**
 * 描述:消息发送线程
 */
public class ClientSendThread implements Runnable {

	private ClientActivity mClient;
	private boolean isRunning;

	public ClientSendThread(ClientActivity mClient) {
		this.mClient = mClient;
		this.isRunning = true;
	}

	@Override
	public void run() {
		while (isRunning) {
			TranObject tran = mClient.removeQueueEle();
			if (tran == null){
				continue;
			}
			mClient.send(tran);
		}
	}

	public void close() {
		isRunning = false;
	}
}
