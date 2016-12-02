package application.model;


import java.util.ArrayList;
import java.util.Date;

import application.util.*;

public class User {
	private static int userNumber=0;
	
	private int userID;
	private String userName;
	private String password;
	private String pwdMd5;
	private Date registerDate;
	private char gender;
	private ArrayList<WordCard> mailBox=new ArrayList<WordCard>();
	private ArrayList<User> friendList=new ArrayList<User>();
	
	public User(String userName, String password, char gender) {
		userNumber++;
		this.setRegisterDate(new Date());
		this.userID=userNumber;
		this.userName = userName;
		this.password = password;
		//pwdMd5=Encryption.MD5(password);
		this.gender = gender;
	}
	
	public User(String userName, String password, char gender,Date registerDate) {
		this.setRegisterDate(registerDate);
		this.userID=0;
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
	
	public void deleteWordCard(WordCard newCard) {
		mailBox.remove(newCard);
	}
	
	public void clearMailbox() {
		mailBox.clear();
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
	
	public boolean deleteFriend(User friend) {
		if(friendList.contains(friend)) {
			friendList.remove(friend);
			return true;
		} else return false;
	}
	
	public void resetAll() {
		mailBox.clear();
		password="123456";
		//pwdMd5=Encryption.MD5(password);
		friendList.clear();
	}
	
	@Override
	public boolean equals(Object obj) {
		User friend=(User) obj;
		if(friend.userID == userID) return true;
		else return false;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}
