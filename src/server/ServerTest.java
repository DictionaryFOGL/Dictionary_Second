package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerTest {
	public static void main(String[] args) throws IOException, InterruptedException{
		int servPort=20123;
		ServerSocket servSock=new ServerSocket(servPort);
		System.out.println("Server starts working...");
		while(true) {
			Socket clntSock=servSock.accept();
			SocketAddress clientAddress=clntSock.getRemoteSocketAddress();
			System.out.println("Handling client at "+clientAddress);
			Thread test=new Thread(new CilentThread(clntSock));
			test.start();
			test.join();
		}
	}

}
