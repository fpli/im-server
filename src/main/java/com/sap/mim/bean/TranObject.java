package com.sap.mim.bean;

import com.sap.mim.global.Result;

import java.io.*;

/**
 * 描述: 传输对象模型
 */
public class TranObject implements Externalizable {

	private Object         object;
	private TranObjectType tranType;
	private Result         result;
	private String         sendTime;
	private int            sendId;
	private String         sendName;
	private int            receiveId;

	public TranObject(){}

	public TranObject(Object object, TranObjectType tranType) {
		this.object = object;
		this.tranType = tranType;
	}

	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public int getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public TranObjectType getTranType() {
		return tranType;
	}
	public void setTranType(TranObjectType tranType) {
		this.tranType = tranType;
	}


	@Override
	public void writeExternal(ObjectOutput out) throws IOException {

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

	}
}
