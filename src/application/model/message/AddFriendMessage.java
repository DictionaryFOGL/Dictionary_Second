package application.model.message;

public class AddFriendMessage extends Message{

	private String friendName;
	
	public AddFriendMessage(byte type,String friendName) {
		super(type);
		this.friendName=friendName;
	}

	public String getFriendName() {
		return friendName;
	}
	
}
