package application.model.message;

import application.model.*;
import DServer.*;

public class LoginMessage extends Message{
	private String name;
	private String pwdMd5;
	private User account;

	public LoginMessage(byte type,String name,String pwdMd5){
		super(type);
		this.name=name;
		this.pwdMd5=pwdMd5;
	}

	public LoginMessage(byte type, User account) {
		super(type);
		this.account = account;
	}

	@Override
	public byte getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getPwdMd5() {
		return pwdMd5;
	}
	
	public User getAccount() {
		return account;
	}
}
