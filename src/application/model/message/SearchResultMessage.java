package application.model.message;

import application.model.User;

public class SearchResultMessage extends Message {
	User result;

	public SearchResultMessage(byte type, User result) {
		super(type);
		this.result = result;
	}

	public User getResult() {
		return result;
	}
	
}
