package DServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import application.model.*;
import application.model.message.*;
import application.model.message.Message;
public class Client implements CSConstant{
	
	private Socket socket;
	
	private ObjectInputStream objectFromServer;
	private ObjectOutputStream objectToServer;
	private String host="127.0.0.1";
	boolean waiting;
	boolean isLogin;
	private User account;
	
	public Client(){
		try {
            init();
			login("dreamgazer","123456",'M');
			//
			objectFromServer=new ObjectInputStream(socket.getInputStream());
			while(true){
				work((Message)objectFromServer.readObject());
				logout();
				Thread.sleep(1000);
			}
			
			//socket.close();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		} 	
	}
	
	private void init() throws UnknownHostException, IOException{
		socket=new Socket(host,8000);
		objectToServer=new ObjectOutputStream(socket.getOutputStream());
		objectToServer.flush();
		account=null;	
		isLogin=false;
	}
	private void work(Message message) throws ClassNotFoundException, IOException{
		switch(message.getType()){
		case(RECEIVE_CARD):receiveCard(message);break;
		case(FRIEND_ONLINE):friendOnline(message);break;
		case(NEW_FRIEND):newFriend(message);break;
		case(FRESH):fresh(message);break;		
		case(SEARCH_HISTORY):getSearchHistory(message);break;
		case(LOGIN):loginResult((LoginMessage) message);break;
		case(LOGOUT):logoutResult((LogoutMessage) message);break;
		case(REGISTER):regeisterResult(message);break;
		}
		
	}
	
	private void waitForAction(Message message) throws InterruptedException{
		//boolean waiting=true;
	     while(waiting){
	    	 Thread.sleep(100);
	     }
	     waiting=true;
	}
	
	private void receiveCard(Message message){		
		
	}
	
	private void friendOnline(Message message){		
		
	}
	
	private void newFriend(Message message){		
		
	}
	
	private void fresh(Message message){		
	}
	
	private void getSearchHistory(Message message){		
	}
	
	private void loginResult(LoginMessage message) throws IOException, ClassNotFoundException{		
			if(message.isIdentified()){
				isLogin=true;
				account=message.getUser();
				System.out.println(account.getUserName()+" login");
			}
			else{
				isLogin=false;
				System.out.println("login failed");
			}

	}
	
	private void logoutResult(LogoutMessage message) throws IOException{	
		if(message.isLogout()){
			System.out.println("Logout succeed");
			isLogin=false;
			account=null;
		}
		else{
			System.out.println("Logout failed");
		}
	}
	
	private void regeisterResult(Message message){		
	}
	
	private void login(String name,String passWord,char gender) throws IOException{
		LoginMessage message=new LoginMessage(LOGIN,new User(name,passWord,gender));
		objectToServer.writeObject(message);
	
	}
	
	private void logout() throws IOException{
		if(!isLogin){
			System.out.println("logout failed:not login yet");
			return;
		}
		LogoutMessage message=new LogoutMessage(LOGOUT);
		objectToServer.writeObject(message);
	}
	public static void main(String[] args){
		new Client();	
	}

}
