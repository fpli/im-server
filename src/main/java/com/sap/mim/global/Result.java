package com.sap.mim.global;

public enum Result {
	ACCOUNT_EXISTED,     //账号已注册
	ACCOUNT_CAN_USE,	 //账号可用
	REGISTER_SUCCESS,	 //注册成功
	REGISTER_FAILED,	 //注册失败
	LOGIN_SUCCESS,		 //登录成功
	LOGIN_FAILED,		 //登录失败
	MAKE_FRIEND_REQUEST, //添加好友请求
	FRIEND_REQUEST_RESPONSE_REJECT,//拒绝添加好友
	FRIEND_REQUEST_RESPONSE_ACCEPT //接受添加好友
}
