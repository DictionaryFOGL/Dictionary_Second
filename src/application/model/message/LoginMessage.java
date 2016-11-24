package application.model.message;

import application.model.*;
import DServer.*;

public class LoginMessage extends Message{

	private  User account;
	private boolean identified;
	
	public LoginMessage(byte type,User account){
		super(type);
		this.account=account;
		identified=false;
	}

	@Override
	public byte getType() {
		return type;
	}
	
	public User getUser(){
		return account;
	}

	public boolean isIdentified(){
		return identified;
	}
	
	public void identify(){
		identified=true;
	}
}
