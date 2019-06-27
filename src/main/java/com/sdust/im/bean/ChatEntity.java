package com.sdust.im.bean;

import java.io.Serializable;

/**
 * 描述:聊天信息模型(发送方消息|接收方消息)
 */
public class ChatEntity implements Serializable{

	private static final long serialVersionUID = 3149570372342194659L;

	public static final int  RECEIVE = 0;//该消息为接收方消息
	public static final int SEND = 1;

	private int senderId;// 发送方id
	private int receiverId;// 接收方id
	private String sendDate;// 发送日期
	private int messageType;// 消息类型:文本消息|语音消息|视频消息|图文消息|图片消息...
	private String content;// 文本消息文本内容

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	
	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getSendTime() {
		return sendDate;
	}

	public void setSendTime(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
