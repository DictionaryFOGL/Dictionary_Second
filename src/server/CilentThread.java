package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import application.model.User;

public class CilentThread implements Runnable{
	private Socket socket=null;
	
	public CilentThread(Socket socket) {
		this.socket=socket;
	}
	
	public static void showReturn(Object obj) {
		User user=(User) obj;
		System.out.println("Received:");
		System.out.println(user.getUserID()+"  "+user.getGender()+"  "+user.getUserName()+"  "+user.getFriendList().get(0).getUserName());
		user.setPassword("changed");
		user.setGender('M');
	}
	
	@Override
	public void run() {
		try {
			InputStream in=socket.getInputStream();
			OutputStream out=socket.getOutputStream();
			ObjectInputStream ois=new ObjectInputStream(in);
			Object obj=ois.readObject();
			ObjectOutputStream oos=new ObjectOutputStream(out);
			System.out.println("Server: ");
			showReturn(obj);
			oos.writeObject(obj);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
