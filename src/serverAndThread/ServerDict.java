package serverAndThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import application.model.User;
import application.model.message.*;
import application.util.Encryption;
import database.DictionaryDB;

public class ServerDict extends ServerSocket implements CSConstant{
	private DictionaryDB db;
	private HashMap<String, CilentSession> userList;
	private HashSet<CilentSession> guests;

	public ServerDict(int port) throws IOException {
		super(port);
		userList = new HashMap<String, CilentSession>();
		guests = new HashSet<CilentSession>();
		db = new DictionaryDB();
		db.connect();
	}
	private boolean isOnline(String name) {
		return userList.get(name) != null ? true:false;
	}

	private User loginBase(CilentSession operator, String name, String pwdMd5) {
		User user=null;
		try {
			user = db.verify(name, pwdMd5);
			if (user == null)
				return null;
			else {
				user.setStatus(true);
				guests.remove(operator);
				userList.put(name, operator);
				broadCast();
			}
		} catch (SQLException e) {
			System.out.println("register failed");
			e.printStackTrace();
		}
		return user;
	}

	public User userLogin(CilentSession operator, Message m) {
		LoginMessage login = (LoginMessage) m;
		String name = login.getName();
		if(isOnline(name)) return null;
		String pwdMd5 = login.getPwdMd5();
		User user=loginBase(operator, name, pwdMd5);
		return user;
	}

	public User userRegister(CilentSession operator, Message m) {
		LoginMessage login = (LoginMessage) m;
		String name = login.getName();
		String password = login.getAccount().getPassword();
		Date date = login.getAccount().getRegisterDate();
		char gender = login.getAccount().getGender();
		try {
			if (db.isUserNameRepeated(name)) {
				operator.localSimpleMessage(EXISTNAME);
				return null;
			} else {
				db.register(name, password, date, gender);
				String pwdMd5 = Encryption.MD5(password);
				User user=loginBase(operator, name, pwdMd5);
				return user;
			}
		} catch (SQLException e) {
			System.out.println("register failed");
			e.printStackTrace();
			return null;
		}
	}
	public boolean userLogout(String name) {
		CilentSession operator= userList.remove(name);
		guests.add(operator);
		operator.localLogout();
		return true;
	}
	public boolean searchUsers(CilentSession operator, Message m) {
		SearchMessage message=(SearchMessage) m;
		String item=message.getKeyWord();
		CilentSession online=userList.get(item);
		User target=null;
		ArrayList<String> likeList=null;
		try {
			likeList=db.searchAccount(item);
		} catch (SQLException e) {
			System.out.println("search account");
			e.printStackTrace();
			return false;
		}
		if(online != null) {
			target=online.getUser();
			likeList.remove(item);
		}
		operator.localSearchUser(target, likeList);
		return true;
	}
	public void userAddFriend(CilentSession operator, Message m) {
		AddFriendMessage message=(AddFriendMessage) m;
		String name=message.getFriendName();
		try {
			db.addFriends(name, operator.getUser().getUserName());
			CilentSession friend=userList.get(name);
			if(friend != null) {
				operator.getUser().addNewFriend(name, true);
				friend.localGetFriend(operator.getUser().getUserName());
			} else {
				operator.getUser().addNewFriend(name, false);
			}
		} catch (SQLException e) {
			System.out.println("friend add error");
			e.printStackTrace();
		}
	}
	public void userLike(CilentSession operator, Message m) {
		LikeMessage message=(LikeMessage) m;
		int site=message.getSite();
		boolean likeOrNot=message.isLikeOrNot();
		String key=message.getKeyWord();
		if(site == 0) {
			if(likeOrNot) operator.getUser().baiduLike();
			else operator.getUser().baiduDisLike();
		} else if(site == 1) {
			if(likeOrNot) operator.getUser().bingLike();
			else operator.getUser().bingDisLike();
		} else if(site == 2) {
			if(likeOrNot) operator.getUser().youdaoLike();
			else operator.getUser().youdaoDisLike();
		}
		System.out.println("like renew");
	}
	public void broadCast() {
		// TODO 通知他人
		
	}


	public void guestQuit(CilentSession c) {
		guests.remove(c);
	}
	public void addGuestSocket(CilentSession cs) {
		guests.add(cs);
	}
}
