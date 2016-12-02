package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import application.model.*;

public class Server implements DatabaseConstant{
	/*
	 * 增加部分
	 */
	//往数据库中插入新的用户数据
	public boolean newUser(User user) {
		return true;
	}
	//加一个新的好友
	public boolean addNewFriend(int id) {
		return true;
	}
	//发送一张单词卡
	public boolean sendWordCard() {
		return true;
	}
	//点赞
	public boolean likeWord() {
		return true;
	}
	//取消点赞
	public boolean cancelLikeWord() {
		return true;
	}
	
	
	/*
	 * 删除部分
	 */
	//删除我的好友
	public boolean deleteFriend(int id) {
		return true;
	}
	//删除我所有的好朋友
	public boolean deleteAllFriend() {
		//TODO 调用上面的deleteFriend函数删除所有的好友
		return true;
	}
	//删除我的单词卡
	public boolean deleteCard() {
		return true;
	}
	//删除我所有收到的单词卡
	public boolean deleAllCard() {
		//TODO 调用上面的deleteCard函数删除所有的卡片
		return true;
	}
	
	
	/*
	 * 搜索部分
	 */
	//用户登陆
	public User login(String userName,String pwdMd5) {
		return null;
		//TODO 根据用户名以及密码的Md5加密判断是否存在该用户，存在则根据数据库中提取的数据构建一个User对象返回，不存在返回null
		
	}
	//根据用户名判断是否存在该用户
	public boolean presentUser(String userName) {
		return true;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		int servPort=20123;
		ServerSocket servSock=new ServerSocket(servPort);
		System.out.println("Server starts working...");
		try {
			while (true) {
				Socket clntSock = servSock.accept();
				SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				Thread test = new Thread(new CilentThread(clntSock,servSock));
				test.start();
			} 
		} finally {
			servSock.close();
		}
	}

}
