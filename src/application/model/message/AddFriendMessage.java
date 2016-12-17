package application.model.message;

import java.io.Serializable;

public class AddFriendMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800306582117499225L;
	private String friendName;
	
	public AddFriendMessage(byte type,String friendName) {
		super(type);
		this.friendName=friendName;
	}

	public String getFriendName() {
		return friendName;
	}
	
}
