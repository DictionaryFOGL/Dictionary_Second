package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import application.model.User;

public class CilentThread implements Runnable{
	private Socket socket=null;
	private ServerSocket server=null;
	private User user=null;
	
	public CilentThread(Socket socket,ServerSocket server) {
		this.socket=socket;
		this.server=server;
	}
	
	public static void showReturn(User user) {
		user.setPassword("changed");
		user.setGender('M');
		user.addNewFriend(new User("ss","2333", null));
	}
	
	@Override
	public void run() {
		System.out.println("Thread start...");
		try {
			InputStream ins=null;
			OutputStream outs=null;
			try {
				ins = socket.getInputStream();
				outs = socket.getOutputStream();
				ObjectInputStream in = new ObjectInputStream(ins);
				PrintWriter out = new PrintWriter(outs,true);
				
				out.println("Hello! Bye to exit");
				boolean done=false;
				Object readfor=null;
				
				while(!done && (readfor=in.readObject()) != null) {
					User user=(User) readfor;
					out.println("Echo(original): "+user.getUserName()+"\t"+user.getGender()+"\t"+user.getFriendList().size());
					showReturn(user);
					out.println("Echo(change): "+user.getUserName()+"\t"+user.getGender()+"\t"+user.getFriendList().size());
					if(user.getGender() == 'p')
						done=true;
				}
				in.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ins.close();
				System.out.println("Socket out");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
