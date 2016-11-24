package DServer;

import java.io.*;
import java.net.Socket;

import application.model.User;
import application.model.message.*;



class ClientSession implements Runnable,CSConstant{
	private Socket socket;
	private ObjectInputStream objectFromClient;
	private ObjectOutputStream objectToClient;
	private boolean waiting;
	public ClientSession(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run() {
		try {
			objectToClient=new ObjectOutputStream(socket.getOutputStream());
			objectToClient.flush();
			objectFromClient=new ObjectInputStream(socket.getInputStream());
		while(true){
			work((Message)objectFromClient.readObject());
		}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			
		}
	}

	
	private void waitForAction() throws InterruptedException{
		//boolean waiting=true;
	     while(waiting){
	    	 Thread.sleep(100);
	     }
	     waiting=true;
	}

	
	private void work(Message message) throws ClassNotFoundException, IOException{
		switch(message.getType()){
			case(SEND_CARD):sendCard((SendCardMessage)message);break;
			case(FRIEND_ONLINE):friendOnline(message);break;
			case(ADD_FRIEND):addFriend(message);break;
			case(LIKE):like(message);break;
			case(LIKE_CANCEL):likeCancel(message);break;		
			case(SEARCH_HISTORY):searchHistory(message);break;
			case(LOGIN):login((LoginMessage)message);break;
			case(LOGOUT):logout((LogoutMessage)message);break;
			case(REGISTER):regeister(message);break;
		}
		
	}
	
	private void sendCard(SendCardMessage message){		
		
	}
	
	private void friendOnline(Message message){		
		
	}
	
	private void addFriend(Message message){		
		
	}
	
	private void like(Message message){		
	}
	
	private void likeCancel(Message message){
		
	}
	
	private void searchHistory(Message message){		
	}
	
	private void login(LoginMessage message) throws ClassNotFoundException, IOException {
		User user=(User)message.getUser();
		System.out.println("login...."+user.getUserName());
		
		boolean isUserFound=true;
		if(isUserFound){
			message.identify();
			objectToClient.writeObject(message);
		}
		else{
			objectToClient.writeObject(message);
		}
	}
	
	private void logout(LogoutMessage message) throws IOException{
		System.out.println("Server:logout....");
		message.Logout();
		objectToClient.writeObject(message);;
	}
	
	private void regeister(Message message){		
	}

	
	
}
