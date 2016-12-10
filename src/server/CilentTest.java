package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import application.model.User;

public class CilentTest {
	public static void showReturn(Object obj) {
		User user=(User) obj;
		System.out.println("Received:");
		System.out.println(user.getUserID()+"  "+user.getGender()+"  "+user.getUserName()+"  "+user.getFriendList().get(0).getUserName());
	}
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String serverIP="127.0.0.1";
		int port=20123;
		
		Socket socket=new Socket(serverIP, port);
		System.out.println("Connect to server..."+socket.hashCode());
		InputStream in=socket.getInputStream();
		OutputStream out=socket.getOutputStream();
		System.out.println("in and out: "+in.hashCode()+" "+out.hashCode());
		
		ObjectOutputStream oos=new ObjectOutputStream(out);
		Scanner sc=new Scanner(System.in);
		Scanner scin=new Scanner(in);
		
		int i=0;
		while(true) {
			int a=sc.nextInt();
			if(a == 0) {
				oos.writeObject(new User("ahaha", "12345", null));
				System.out.println("send a==0 mode");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i == 0) System.out.println("Server: "+scin.nextLine());
				
				System.out.println("\t"+scin.nextLine());
				System.out.println("\t"+scin.nextLine());
			} else {
				User user=new User("ahaha", "12345", null);
				user.setGender('p');
				oos.writeObject(user);
				System.out.println("send stop mode");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i == 0) System.out.println("Server: "+scin.nextLine());
				
				System.out.println("\t"+scin.nextLine());
				System.out.println("\t"+scin.nextLine());
				break;
			}
			i++;
		}
		scin.close();
		socket.close();
		sc.close();
	}

}
