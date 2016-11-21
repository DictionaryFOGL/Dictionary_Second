package DServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import application.model.*;
public class Client {

	public static void main(String[] args){
		String host="127.0.0.1";
		User user=new User("dreamazger","mylife4aiur",'M');//Encoding algorithm problem?
		try {
			Socket socket=new Socket(host,33208);
			ObjectOutputStream toServer=new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(user);
			DataInputStream fromServer=new DataInputStream(socket.getInputStream());
			System.out.println(fromServer.read());
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}

}
