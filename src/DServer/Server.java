package DServer;
import application.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ObjectInputStream inputFromClient;
	private ObjectOutputStream outputToClient;
	
	public Server() throws ClassNotFoundException{
		try {
			ServerSocket serversocket=new ServerSocket(33208);
			
			while(true){
				Socket socket=serversocket.accept();
				System.out.println("working....");
				System.out.println(socket.getPort());
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getLocalAddress());
				Thread thread=new Thread(new ClientProcess(socket));
				thread.start();
				thread.join();
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}		
	}
	class ClientProcess implements Runnable{
		private Socket socket;
		
		public ClientProcess(Socket socket){
			this.socket=socket;
		}

		@Override
		public void run() {
			try {
					inputFromClient=new ObjectInputStream(socket.getInputStream());
					Object object=inputFromClient.readObject();
					System.out.println("received:"+((User)object).getUserName()+" "+((User)object).getGender());
					outputToClient=new ObjectOutputStream(socket.getOutputStream());
					outputToClient.writeChars("received");
				

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			finally{
				try {
					inputFromClient.close();
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
