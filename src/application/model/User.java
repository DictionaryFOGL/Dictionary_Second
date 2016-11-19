package application.model;

import java.util.ArrayList;

import application.util.*;

public class User {
	private String userName;
	private String password;
	private String pwdMd5;
	private char gender;
	private ArrayList<WordCard> mailBox=new ArrayList<WordCard>();
	private ArrayList<User> friendList=new ArrayList<User>();
	
	public User(String userName, String password, char gender) {
		this.userName = userName;
		this.password = password;
		pwdMd5=Encryption.MD5(password);
		this.gender = gender;
	}

	public User(String userName, String password) {
		this(userName,password,'s');
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
		pwdMd5=Encryption.MD5(password);
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPwdMd5() {
		return pwdMd5;
	}

	public ArrayList<WordCard> getMailBox() {
		return mailBox;
	}
	
	public boolean addWordCard(WordCard newCard) {
		if(mailBox.contains(newCard)) return false;
		else {
			mailBox.add(newCard);
			return true;
		}
	}
	public ArrayList<User> getFriendList() {
		return friendList;
	}
	
	
}
