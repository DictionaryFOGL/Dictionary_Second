package application.model;

import java.io.Serializable;
import java.util.ArrayList;

import application.util.*;

public class User implements Serializable{
	private static int userNumber=0;
	
	private int userID;
	private String userName;
	private String password;
	private String pwdMd5;
	private char gender;
	private ArrayList<WordCard> mailBox=new ArrayList<WordCard>();
	private ArrayList<User> friendList=new ArrayList<User>();
	
	public User(String userName, String password, char gender) {
		userNumber++;
		this.userID=userNumber;
		this.userName = userName;
		this.password = password;
		//pwdMd5=Encryption.MD5(password);
		this.gender = gender;
	}
	
	public int getUserID() {
		return userID;
	}

	public User(String userName, String password) {
		this(userName,password,'s');
	}

	public String getUserName() {
		return userName;
	}

	public boolean setPassword(String password) {
		if(ValidInput.validPwd(password)) {
			this.password = password;
			//pwdMd5=Encryption.MD5(password);
			return true;
		} else return false;
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
	
	public boolean addNewFriend(User friend) {
		if(friendList.contains(friend)) return false;
		else {
			friendList.add(friend);
			return true;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		User friend=(User) obj;
		if(friend.userID == userID) return true;
		else return false;
	}
}
