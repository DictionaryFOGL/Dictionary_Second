package application.model.message;

public class AddFriendMessage extends Message{

	private String sender;
	private String receiver;
	
	public AddFriendMessage(byte type,String sender,String receiver) {
		super(type);
		setSender(sender);
		setReceiver(receiver);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
