package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerTest {
	private static final int BUFSIZE=32;
	public static void main(String[] args) throws IOException{
		if(args.length != 1)
			throw new IllegalArgumentException("Parameter(s):<port>");
		int servPort=Integer.parseInt(args[0]);
		ServerSocket servSock=new ServerSocket(servPort);
		int recvMsgSize;
		byte[] receiveBuf=new byte[BUFSIZE];
		while(true) {
			Socket clntSock=servSock.accept();
			SocketAddress clientAddress=clntSock.getRemoteSocketAddress();
			System.out.println("Handling client at "+clientAddress);
			new Thread(new CilentThread(clntSock)).start();
			clntSock.close();
		}
	}

}
