package application.model.message;

import application.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginMessage extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5121814563158719568L;
	private String name;
	private String pwd;
	private char gender;
	private User account;
	private ArrayList<SearchHistory> history;

	public LoginMessage(byte type,String name,String pwdMd5,char gender){
		super(type);
		this.name=name;
		this.pwd=pwdMd5;
		this.gender=gender;
	}

	public ArrayList<SearchHistory> getHistory() {
		return history;
	}

	public char getGender() {
		return gender;
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

	public String getPwd() {
		return pwd;
	}
	
	public User getAccount() {
		return account;
	}
}
