package DServer;

import java.io.*;
import java.net.Socket;

import application.model.User;


class ClientSession implements Runnable,CSConstant{
	private Socket socket;
	private DataInputStream dataFromClient;
	private ObjectInputStream objectFromClient;
	private DataOutputStream dataToClient;
	private ObjectOutputStream objectToClient;
	private boolean waiting;
	public ClientSession(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run() {
		try {
				dataFromClient=new DataInputStream(socket.getInputStream());
				dataToClient=new DataOutputStream(socket.getOutputStream());
				objectFromClient=new ObjectInputStream(socket.getInputStream());
				objectToClient=new ObjectOutputStream(socket.getOutputStream());

				while(true){
					work(dataFromClient.readByte());
					try {
						waitForAction();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				dataFromClient.close();
				dataToClient.close();
				objectFromClient.close();
				objectToClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private void waitForAction() throws InterruptedException{
		//boolean waiting=true;
	     while(waiting){
	    	 Thread.sleep(100);
	     }
	     waiting=true;
	}

	
	private void work(byte message){
		switch(message){
		case(SEND_CARD):sendCard();break;
		case(FRIEND_ONLINE):friendOnline();break;
		case(ADD_FRIEND):addFriend();break;
		case(LIKE):like();break;
		case(LIKE_CONCEL):like_concel();break;		
		case(SEARCH_HISTORY):searchHistory();break;
		case(LOGIN):login();break;
		case(LOGOUT):logout();break;
		case(REGISTER):regeister();break;
		}
		
	}
	
	private void sendCard(){		
		
	}
	
	private void friendOnline(){		
		
	}
	
	private void addFriend(){		
		
	}
	
	private void like(){		
	}
	
	private void like_concel(){
		
	}
	
	private void searchHistory(){		
	}
	
	private void login(){		
	}
	
	private void logout(){		
	}
	
	private void regeister(){		
	}

	
	
}
