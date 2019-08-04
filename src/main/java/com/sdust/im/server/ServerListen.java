package com.sdust.im.server;

import com.sdust.im.client.ClientActivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述:服务端启动入口
 */
public class ServerListen {

	private static final int PORT = 8399;
	private ServerSocket server;

	public static void main(String args[]){
	   new ServerListen().begin();
	}

	public void begin(){
		try {
			server = new ServerSocket(PORT);
			System.out.println("服务器已经启动...");
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					close();
				}
			});
		} catch (IOException e) {
			System.out.println("服务器启动失败");
			e.printStackTrace();
		}
		while(true){
			try {
				Socket client = server.accept();//该方法是阻塞方法
				client.setKeepAlive(true);
				new ClientActivity(this, client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得在线用户
	 */
    public ClientActivity getClientByID(int id){
    	return OnMap.getInstance().getClientById(id);
    }

    public void closeClientByID(int id){
    	OnMap.getInstance().removeClient(id);
    }

    public void addClient(int id, ClientActivity ca0){
    	OnMap.getInstance().addClient(id, ca0);
    }

    public boolean contatinId(int id){
    	return OnMap.getInstance().isContainId(id);
    }

    public void close(){
    	try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public int  size() {
		return OnMap.getInstance().size();
	}
}
