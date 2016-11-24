package application.model.message;

import application.model.*;

public class SendCardMessage extends Message{
	private User reciver;
	private Word word;

	SendCardMessage(byte type,User reciver,Word word) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public User getReciver() {
		return reciver;
	}

	public Word getWord() {
		return word;
	}



}
