package application.model.message;

import application.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String pwdMd5;
	private User account;
	private ArrayList<SearchHistory> history;

	public LoginMessage(byte type,String name,String pwdMd5){
		super(type);
		this.name=name;
		this.pwdMd5=pwdMd5;
	}

	public ArrayList<SearchHistory> getHistory() {
		return history;
	}

	public LoginMessage(byte type, User account,ArrayList<SearchHistory> history) {
		super(type);
		this.account = account;
		this.history=history;
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
