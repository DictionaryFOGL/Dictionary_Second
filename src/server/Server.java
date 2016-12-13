package server;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import application.model.*;

public class Server implements DatabaseConstant{
	/*
	 * ���Ӳ���
	 */
	//�����ݿ��в����µ��û�����
	public boolean newUser(User user) {
		return true;
	}
	//��һ���µĺ���
	public boolean addNewFriend(int id) {
		return true;
	}
	//����һ�ŵ��ʿ�
	public boolean sendWordCard() {
		return true;
	}
	//����
	public boolean likeWord() {
		return true;
	}
	//ȡ������
	public boolean cancelLikeWord() {
		return true;
	}
	
	
	/*
	 * ɾ������
	 */
	//ɾ���ҵĺ���
	public boolean deleteFriend(int id) {
		return true;
	}
	//ɾ�������еĺ�����
	public boolean deleteAllFriend() {
		//TODO ���������deleteFriend����ɾ�����еĺ���
		return true;
	}
	//ɾ���ҵĵ��ʿ�
	public boolean deleteCard() {
		return true;
	}
	//ɾ���������յ��ĵ��ʿ�
	public boolean deleAllCard() {
		//TODO ���������deleteCard����ɾ�����еĿ�Ƭ
		return true;
	}
	
	
	/*
	 * ��������
	 */
	//�û���½
	public User login(String userName,String pwdMd5) {
		return null;
		//TODO �����û����Լ������Md5�����ж��Ƿ���ڸ��û���������������ݿ�����ȡ�����ݹ���һ��User���󷵻أ������ڷ���null
		
	}
	//�����û����ж��Ƿ���ڸ��û�
	public boolean presentUser(String userName) {
		return true;
	}
	
	public void logInToList(String name) {
		
	}
	public static void main(String[] args) throws IOException, InterruptedException{
		int servPort=20123,i=0;
		ServerSocket servSock=new ServerSocket(servPort);
		System.out.println("Server starts working...");
		try {
			while (true) {
				Socket clntSock = servSock.accept();
				System.out.println(clntSock.hashCode());
				SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				CilentSession ct=new CilentSession(clntSock,servSock);
				Thread test = new Thread(ct);
				test.start();
			} 
		} finally {
			servSock.close();
		}
	}

}
