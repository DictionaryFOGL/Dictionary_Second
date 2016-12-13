package serverAndThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;


public class MainStart {
	public static void main(String[] args) throws IOException {
		ServerDict servSock = new ServerDict(20123);
		System.out.println("Server starts working...");
		try {
			while (true) {
				Socket clntSock = servSock.accept();
				System.out.println(clntSock.hashCode());
				SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				ObjectInputStream ois=new ObjectInputStream(clntSock.getInputStream());
				ObjectOutputStream oos=new ObjectOutputStream(clntSock.getOutputStream());
				CilentSession cs=new CilentSession(clntSock,servSock,ois,oos);
				servSock.addGuestSocket(cs);
			} 
		} finally {
			servSock.close();
		}
	}
}
