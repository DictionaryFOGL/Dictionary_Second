package serverAndThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.model.message.AddFriendMessage;
import application.model.message.LoginMessage;
import application.model.message.Message;
import application.model.message.SendCardMessage;
import application.util.InformationDialog;

public class DictionaryFOGLClient implements CSConstant,Runnable {
	private Main mainApp;
	private User user;
	private Socket socket;
	private ObjectInputStream objectFromServer;
	
	public DictionaryFOGLClient(Main mainApp,Socket socket, ObjectInputStream objectFromServer) {
		super();
		this.mainApp=mainApp;
		this.socket = socket;
		this.objectFromServer = objectFromServer;
	}

	public User getUser() {
		return user;
	}

	@Override
	public void run() {
		while (true) {
			javafx.application.Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						work((Message) objectFromServer.readObject());
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
						System.out.println("client socket readObj error");
						try {
							socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
							System.out.println("client socket close error");
						}
					}
				}
			});
		}
	}

	//TODO All the UI show
	private void work(Message message) {
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
		case (DELETE_FRIEND):
			
			break;
		case (LOGIN_FAILED):
			InformationDialog.loginFailed();
			break;
		case (LOGIN_SUCCESS):
			login(message);
			break;
		case (SEARCH_RESULT):
			
			break;
		case (REGISTER_SUCCESS):
			login(message);
			break;
		case (EXISTNAME):
			InformationDialog.existName();
			break;
		case (LOGUT_SUCCESS):
			this.user=null;
			break;
		default:
			break;
		}
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
		this.user=message.getAccount();
	}
	
	public void newFriend(Message m){		
		AddFriendMessage message=(AddFriendMessage) m;
		String friendName=message.getFriendName();
		user.addNewFriend(friendName, true);
	}
}
