package application.model.message;

import java.io.Serializable;

public class AddFriendMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800306582117499225L;
	private String friendName;
	private boolean online;
	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public AddFriendMessage(byte type,String friendName) {
		super(type);
		this.friendName=friendName;
	}

	public String getFriendName() {
		return friendName;
	}
	
}
