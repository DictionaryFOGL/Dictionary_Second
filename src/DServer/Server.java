package DServer;
import application.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements CSConstant{

	public Server() throws ClassNotFoundException{
		try {
			ServerSocket serversocket=new ServerSocket(33208);
			while(true){
				Socket socket=serversocket.accept();
				System.out.println("working....");
				System.out.println(socket.getPort());
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getLocalAddress());
				Thread thread=new Thread(new ClientSession(socket));
				thread.start();
				thread.join();
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
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
