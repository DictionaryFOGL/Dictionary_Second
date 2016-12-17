package serverAndThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import application.Main;
import application.model.SearchHistory;
import application.model.User;
import application.model.WordCard;
import application.model.message.AddFriendMessage;
import application.model.message.LoginMessage;
import application.model.message.Message;
import application.model.message.SendCardMessage;
import application.util.InformationDialog;
import javafx.application.Platform;

public class DictionaryFOGLClient implements CSConstant,Runnable {
	private Main mainApp;
	private ArrayList<SearchHistory> history;
	private User user;
	private Socket socket;
	private ObjectInputStream objectFromServer;
	
	public DictionaryFOGLClient(Main mainApp,Socket socket, ObjectInputStream objectFromServer,ArrayList<SearchHistory> history) {
		super();
		this.mainApp=mainApp;
		this.socket = socket;
		this.objectFromServer = objectFromServer;
		this.history=history;
	}

	public User getUser() {
		return user;
	}

	@Override
	public void run() {
		Object obj;
		try {
			while((obj=objectFromServer.readObject()) !=null) {
				Message message=(Message) obj;
				work(message);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("client read error");
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				System.out.println("socket close error");
				e1.printStackTrace();
			}
		}	
	}

	//TODO All the UI show
	private void work(Message message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				switch (message.getType()) {
				case (SEND_SUCCESS):
					InformationDialog.sendSucceeded();
					break;
				case (RECEIVE_CARD):
					receiveCard(message);
					break;
				case (FRIEND_ONLINE):
					friendOnline(message);
					break;
				case (FRIEND_OFFLINE):
					friendOffline(message);
					break;
				case (NEW_FRIEND):
					newFriend(message);
					break;
				//Äã±»¶Ô·½É¾³ý
				case (DELETE_FRIEND):
					hasbeenDeleted(message);
					break;
				case (LOGIN_FAILED):
					InformationDialog.loginFailed();
					break;
				case (LOGIN_SUCCESS):
					login(message);
					break;
				case (SEARCH_USER):
					
					break;
				case (REGISTER_SUCCESS):
					login(message);
					System.out.println("success");
					break;
				case (EXISTNAME):
					InformationDialog.existName();
					break;
				case (LOGUT_SUCCESS):
					//this.user=null;
					break;
				default:
					break;
				}
			}
		});
		
	}
	
	public void receiveCard(Message m) {
		SendCardMessage message=(SendCardMessage) m;
		WordCard card=message.getCard();
		user.addWordCard(card);
	}
	
	public void friendOnline(Message m){		
		AddFriendMessage message=(AddFriendMessage) m;
		String name=message.getFriendName();
		user.friendOnLine(name);
	}
	
	public void friendOffline(Message m){		
		AddFriendMessage message=(AddFriendMessage) m;
		String name=message.getFriendName();
		user.friendOffLine(name);
	}
	
	public void login(Message m) {
		LoginMessage message=(LoginMessage) m;
		user=message.getAccount();
		ArrayList<SearchHistory> h=message.getHistory();
		if(h != null) {
			for(SearchHistory s:h)
				history.add(s);
		}
		System.out.println("history looged");
	}
	
	public void newFriend(Message m){		
		AddFriendMessage message=(AddFriendMessage) m;
		String friendName=message.getFriendName();
		user.addNewFriend(friendName, true);
	}
	
	public void hasbeenDeleted(Message m) {
		AddFriendMessage message=(AddFriendMessage) m;
		String friendName=message.getFriendName();
		user.deleteFriend(friendName);
	}
}
