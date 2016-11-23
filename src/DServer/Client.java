package DServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import application.model.*;
public class Client implements CSConstant{

	private DataInputStream dataFromServer;
	private ObjectInputStream objectFromServer;
	private DataOutputStream dataToServer;
	private ObjectOutputStream objectToServer;
	private String host="127.0.0.1";
	boolean waiting;
	public Client(){
		try {
			Socket socket=new Socket(host,33208);
			dataFromServer=new DataInputStream(socket.getInputStream());
			dataToServer=new DataOutputStream(socket.getOutputStream());
			objectFromServer=new ObjectInputStream(socket.getInputStream());
			objectToServer=new ObjectOutputStream(socket.getOutputStream());			
			while(true){
				work(dataFromServer.readByte());	
				try {
					waitForAction();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}
	
	private void work(byte message){
		switch(message){
		case(RECEIVE_CARD):receiveCard();break;
		case(FRIEND_ONLINE):friendOnline();break;
		case(NEW_FRIEND):newFriend();break;
		case(FRESH):fresh();break;		
		case(SEARCH_HISTORY):getSearchHistory();break;
		case(LOGIN):loginResult();break;
		case(LOGOUT):logoutResult();break;
		case(REGISTER):regeisterResult();break;
		}
		
	}
	
	private void waitForAction() throws InterruptedException{
		//boolean waiting=true;
	     while(waiting){
	    	 Thread.sleep(100);
	     }
	     waiting=true;
	}
	
	private void receiveCard(){		
		
	}
	
	private void friendOnline(){		
		
	}
	
	private void newFriend(){		
		
	}
	
	private void fresh(){		
	}
	
	private void getSearchHistory(){		
	}
	
	private void loginResult(){		
	}
	
	private void logoutResult(){		
	}
	
	private void regeisterResult(){		
	}
	
	
	public static void main(String[] args){
		new Client();
		
	}

}
