package serverAndThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;


public class MainStart {
	public static void main(String[] args) throws IOException {
		ServerDict server = new ServerDict();
		ServerSocket servSock=new ServerSocket(20123);
		System.out.println("Server starts working...");
		try {
			while (true) {
				Socket clntSock = servSock.accept();
				SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
				ObjectOutputStream oos=new ObjectOutputStream(clntSock.getOutputStream());
				oos.flush();
				ObjectInputStream ois=new ObjectInputStream(clntSock.getInputStream());
				CilentSession cs=new CilentSession(clntSock,server,ois,oos);
				server.addGuestSocket(cs);
				System.out.println("Handling client at " + clientAddress);
				Thread newone=new Thread(cs);
				newone.start();
			} 
		} finally {
			servSock.close();
		}
	}
}
