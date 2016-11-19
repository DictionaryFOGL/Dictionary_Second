package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import application.model.User;

public class CilentTest {
	public static void showReturn(Object obj) {
		User user=(User) obj;
		System.out.println("Received:");
		System.out.println(user.getUserID()+"  "+user.getGender()+"  "+user.getUserName()+"  "+user.getFriendList().get(0).getUserName());
	}
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		String serverIP="127.0.0.0";
		int port=20123;
		
		User newUser=new User("ahaha", "12345");
		newUser.addNewFriend(new User("admin", "23333"));
		
		Socket socket=new Socket(serverIP, port);
		System.out.println("Connect to server");
		InputStream in=socket.getInputStream();
		OutputStream out=socket.getOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(out);
		ObjectInputStream ois=new ObjectInputStream(in);
		oos.writeObject(newUser);
		oos.close();
		socket.shutdownOutput();
		
		Object obj=ois.readObject();
		
		showReturn(obj);
	}

}
