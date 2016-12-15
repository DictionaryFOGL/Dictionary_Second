package serverAndThread;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import application.model.User;
import application.model.message.*;

public class CilentSession implements Runnable,CSConstant {
	private Socket socket=null;
	private User user=null;
	private ServerDict server=null;
	private ObjectInputStream in=null;
	private ObjectOutputStream out=null;
	
	public CilentSession(Socket socket, ServerDict server, ObjectInputStream in, ObjectOutputStream out) {
		super();
		this.socket = socket;
		this.server = server;
		this.in = in;
		this.out = out;
	}
	@Override
	public void run() {
		Object obj=null;
		boolean done=false;
		try {
			while(!done && (obj=in.readObject()) != null) {
				Message message=(Message) obj;
				switch (message.getType()) {
				case (SEND_CARD):
					sendCard(message);
					break;
				case (ADD_FRIEND):
					server.userAddFriend(this, message);
					break;
				case (LIKE):
					server.userLike(this, message);
					break;
				case (LOGIN):
					User u = server.userLogin(this, message);
					if (u != null) localLogin(u);
					break;
				case (LOGOUT):
					if(server.userLogout(this.user.getUserName())) localLogout();
					break;
				case (REGISTER):
					User u1=server.userRegister(this, message);
					if (u != null) localLogin(u);
					break;
				case (SEARCH_USER):
					System.out.println(server.searchUsers(this, message));
					break;
				default:
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(IOException  e) {
			System.out.println("end of stream");
		} finally {
			socket.close();
			server.guestQuit(this);
		}
	}

	public void localLogin(User u) {
		try {
			this.user=u;
			LoginMessage message=new LoginMessage(LOGIN_SUCCESS, u);
			out.writeObject(message);
		} catch (IOException e) {
			System.out.println("localLogin failed");
			e.printStackTrace();
		}
	}
	
	public void localSearchUser(User u,ArrayList<String> likeList) {
		try {
			ResultMessage message=new ResultMessage(SEARCH_RESULT, likeList, u);
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void localLogout() {
		this.user=null;
		localSimpleMessage(LOGOUT);
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void localGetFriend(String name) {
		user.addNewFriend(name, true);
		AddFriendMessage friend=new AddFriendMessage(NEW_FRIEND, name);
		try {
			out.writeObject(friend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void localSimpleMessage(byte type) {
		try {
			Message message=new Message(type);
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
