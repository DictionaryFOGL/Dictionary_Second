package application.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.mysql.fabric.xmlrpc.base.Array;

import application.util.*;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userID;
	private boolean online;
	private String userName;
	private String password;
	private String pwdMd5;
	private Date registerDate;
	private char gender;
	private ArrayList<WordCard> mailBox=new ArrayList<WordCard>();
	private HashMap<String,Boolean> friendList=new HashMap<String,Boolean>();
	private int baidu,youdao,bing;
	
	public User(int userID,String userName, String password, char gender,Date registerDate,int baidu,int youdao,int bing) {
		this(userName,password,gender,registerDate);
		this.baidu=baidu;
		this.youdao=youdao;
		this.bing=bing;
	}
	public User(String userName, String password, char gender,Date registerDate) {
		this(userName,password,registerDate);
		this.gender=gender;
	}
	
	public int getUserID() {
		return userID;
	}

	public User(String userName, String password, Date registerDate) {
		this.setRegisterDate(new Date());
		this.userName = userName;
		this.password = password;
		pwdMd5=Encryption.MD5(password);
		this.gender='s';
		baidu=0;
		youdao=0;
		bing=0;
	}

	public String getUserName() {
		return userName;
	}

	public boolean setPassword(String password) {
		if(ValidInput.validPwd(password)) {
			this.password = password;
			pwdMd5=Encryption.MD5(password);
			return true;
		} else return false;
	}
	
	public String getPassword() {
		return password;
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

	public int getBaidu() {
		return baidu;
	}
	
	public void baiduLike() {
		baidu++;
	}
	
	public void baiduDisLike() {
		if(baidu>0) baidu--;
		else throw new IndexOutOfBoundsException();
	}
	
	public int getYoudao() {
		return youdao;
	}

	public void youdaoLike() {
		youdao++;
	}
	
	public void youdaoDisLike() {
		if(youdao>0) youdao--;
		else throw new IndexOutOfBoundsException();
	}

	public int getBing() {
		return bing;
	}
	
	public void bingLike() {
		bing++;
	}
	
	public void bingDisLike() {
		if(bing>0) bing--;
		else throw new IndexOutOfBoundsException();
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
	
	public ArrayList<String> getFriendList() {
		return new ArrayList<String>(friendList.keySet());
	}
	
	public boolean hasFriend(String name) {
		return friendList.containsKey(name);
	}
	
	public void friendOnLine(String friend) {
		friendList.put(friend, true);
	}
	
	public void friendOffLine(String friend) {
		friendList.put(friend, false);
	}
	
	public boolean addNewFriend(String friend,boolean status) {
		if(friendList.get(friend) != null) return false;
		else {
			friendList.put(friend, status);
			return true;
		}
	}
	
	public boolean deleteFriend(String friend) {
		if(friendList.get(friend) != null) {
			friendList.remove(friend);
			return true;
		} else return false;
	}
	
	public void setFriendList(ArrayList<String> l){
		for(String s:l) {
			friendList.put(s, false);
		}
	}
	
	public void resetAll() {
		mailBox.clear();
		password="123456";
		pwdMd5=Encryption.MD5(password);
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

	public boolean getStatus() {
		return online;
	}

	public void setStatus(boolean status) {
		this.online = status;
	}
}
