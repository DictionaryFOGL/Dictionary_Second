package DServer;
import application.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.TreeMap;

public class Server implements CSConstant {
	TreeMap < String,ClientThread > Sessions = new TreeMap < String , ClientThread> (); 
	TreeMap < String,PipedOutputStream > pipesout = new TreeMap < String , PipedOutputStream> ();  
	PipedOutputStream out=null,serverOut=null;
	PipedInputStream in=null,serverIn=null;
	ClientThread thread;
	public Server() throws ClassNotFoundException{
		try {
			ServerSocket serversocket=new ServerSocket(8000);
			System.out.println("Server working....");
			(new Thread(new PipeSession())).start();
			while(true) {
				Socket socket=serversocket.accept();
				System.out.println(socket.getPort());
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getLocalAddress());
				thread=new ClientThread(socket);
				Sessions.put(thread.toString(), thread);
				PipedOutputStream out=new PipedOutputStream();
				pipesout.put(thread.toString(),out);
				out.connect(thread.getInputStream());
				thread.start();
			}
		} catch (IOException  e) {
			
			e.printStackTrace();
		}		
	}


	class PipeSession implements Runnable{
		@Override
		public void run() {
			while(true){				
				try {
					Thread.sleep(100);//refresh every 100ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(RefreshEvent.isRefreshed()){
					RefreshEvent.setRefreshed(false);
					if(RefreshEvent.isCardRefreshed()){
						sendToSessions("CARD");
						RefreshEvent.setCardRefreshed(false);
					}
					if(RefreshEvent.isUserStateRefreshed()){
						sendToSessions("USER");
						RefreshEvent.setUserStateRefreshed(false);
					}
					if(RefreshEvent.isFriendRefreshed()){
						sendToSessions("FRIEND");
						RefreshEvent.setFriendRefreshed(false);
					}

				}
			}
				
		}
		
		private void sendToSessions(String s){
			Iterator<String> iter=pipesout.keySet().iterator();   
	        while (iter.hasNext())  
	        {  
	        	String str=iter.next(); 			        	
	        	try {
	        		out=pipesout.get(str);
					out.write(s.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public static void main(String[] args){
		try {
			new Server();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
