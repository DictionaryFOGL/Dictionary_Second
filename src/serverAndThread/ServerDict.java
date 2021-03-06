package serverAndThread;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import application.model.SearchHistory;
import application.model.User;
import application.model.WordCard;
import application.model.message.*;
import application.util.Encryption;
import database.DictionaryDB;

public class ServerDict implements CSConstant{
	private DictionaryDB db;
	private HashMap<String, CilentSession> userList;
	private HashSet<CilentSession> guests;

	public ServerDict() {
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
			if (user == null) {
				operator.localVerifyFailed();
				return null;
			} else {
				user.setStatus(true);
				guests.remove(operator);
				userList.put(name, operator);
				Iterator<String> online=userList.keySet().iterator();
				while(online.hasNext()) {
					String n=online.next();
					if(user.hasFriend(n)) {
						user.friendOnLine(n);
					}
				}
				broadCastOnLine(operator,name);
			}
		} catch (SQLException e) {
			System.out.println("register failed");
			e.printStackTrace();
		}
		for(String nam:user.getFriendList()) {
			System.out.println(nam+" "+user.friendStatus(nam));
		}
		return user;
	}

	public void userLogin(CilentSession operator, Message m) {
		LoginMessage login = (LoginMessage) m;
		String name = login.getName();
		if(isOnline(name)) {
			operator.localSimpleMessage(LOGIN_FAILED);
			return;
		}
		ArrayList<SearchHistory> history=null;
		try {
			history=db.SearchHistory(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		String pwdMd5 = login.getPwd();
		User user=loginBase(operator, name, pwdMd5);
		operator.localLogin(user,history);
		System.out.println("history sent");
	}
	
	public boolean userResetCards(CilentSession operator) {
		User usr=operator.getUser();
		String name=usr.getUserName();
		try {
			db.userResetCards(name, usr.getMailBox().size());
		} catch (SQLException e) {
			System.out.println("reset error");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public User userRegister(CilentSession operator, Message m) {
		LoginMessage login = (LoginMessage) m;
		String name = login.getName();
		String password = login.getPwd();
		Date date = new Date(System.currentTimeMillis());
		char gender = login.getGender();
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
		broadCastOffLine(name);
		return true;
	}
	
	public void userPwdChange(CilentSession operator, Message m) {
		LoginMessage login = (LoginMessage) m;
		String pwdnew=login.getPwd();
		try {
			boolean status=db.pwdChange(operator.getUser().getUserID(), pwdnew);
			System.out.println(operator.getUser().getUserID()+" "+pwdnew);
			if(status) {
				operator.localPwdChange(pwdnew);
				operator.getUser().setPassword(pwdnew);
			} else {
				operator.localSimpleMessage(PASSWORD_CHANGEFAILED);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean searchUsers(CilentSession operator, Message m) {
		SearchMessage message=(SearchMessage) m;
		String item=message.getKeyWord();
		ResultMessage R;
		try {
			R=db.searchAccount(item);
		} catch (SQLException e) {
			System.out.println("search account error");
			e.printStackTrace();
			return false;
		}
		operator.localSearchUser(R);
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
				friend.localGetFriend(operator.getUser().getUserName(),true);
				friend.getUser().addNewFriend(operator.getUser().getUserName(), true);
				operator.localGetFriend(name,true);
			} else {
				operator.getUser().addNewFriend(name, false);
				operator.localGetFriend(name,false);
			}
		} catch (SQLException e) {
			System.out.println("friend add error");
			e.printStackTrace();
		}
	}
	
	public void userLike(CilentSession operator, Message m) {
		LikeMessage message=(LikeMessage) m;
		int site=message.getSite();
		int status=message.isLikeOrNot() ? 1 : 0;
		boolean likeOrNot=message.isLikeOrNot();
		String key=message.getKeyWord();
		try {
			db.insertHistory(key, operator.getUser().getUserName(), site, status);
			db.like(operator.getUser().getUserName(), site, likeOrNot);
		} catch (SQLException e) {
			System.out.println("like dberror");
			e.printStackTrace();
		}
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
	
	public void userSendCard(CilentSession operator, Message m) {
		SendCardMessage message=(SendCardMessage) m;
		WordCard card=message.getCard();
		String name=message.getReceiverName();
		try {
			db.sendCard(card, name);
		} catch (SQLException e) {
			System.out.println("card insert failed");
			e.printStackTrace();
		}
		CilentSession target = userList.get(name);
		if (target != null) {
			target.localReceiveCard(card);
		}
	}
	
	public void userDeleteCard(CilentSession operator, Message m) {
		SendCardMessage message=(SendCardMessage) m;
		WordCard card=message.getCard();
		int receiverID=operator.getUser().getUserID();
		try {
			db.deleteCard(receiverID, card);
			operator.getUser().deleteWordCard(card);
		} catch (SQLException e) {
			System.out.println("delete card failed");
			e.printStackTrace();
		}
	}
	
	public void userDeleteFriend(CilentSession operator, Message m) {
		AddFriendMessage message=(AddFriendMessage) m;
		String name=message.getFriendName();
		User usr=operator.getUser();
		usr.deleteFriend(name);
		operator.localLoseFriend(name);
		try {
			db.deleteFriend(usr.getUserID(), name);
			CilentSession friend=userList.get(name);
			if(friend != null) {
				friend.getUser().deleteFriend(usr.getUserName());
				friend.localLoseFriend(usr.getUserName());
			}
		} catch (SQLException e) {
			System.out.println("delete friend failed");
			e.printStackTrace();
		}
	}
	
	public void broadCastOnLine(CilentSession operator,String name) {
		Collection<CilentSession> collection=userList.values();
		Iterator<CilentSession> iter=collection.iterator();
		while(iter.hasNext()) {
			CilentSession target=iter.next();
			if(target == operator) continue;
			User usr=target.getUser();
			if(usr.hasFriend(name)) {
				usr.friendOnLine(name);
				target.localFriendOnLine(name);
			}
		}
	}
	
	public void broadCastOffLine(String name) {
		Collection<CilentSession> collection=userList.values();
		Iterator<CilentSession> iter=collection.iterator();
		while(iter.hasNext()) {
			CilentSession target=iter.next();
			User usr=target.getUser();
			if(usr.hasFriend(name)) {
				usr.friendOffLine(name);
				target.localFriendOffLine(name);
			}
		}
	}

	public void guestQuit(CilentSession c) {
		guests.remove(c);
	}
	public void userstatus() {
		System.out.print("Users("+userList.size()+"): ");
		Iterator<String> iter=userList.keySet().iterator();
		while(iter.hasNext()) System.out.print(iter.next()+"  ");
		System.out.println();
		System.out.println("guests("+guests.size()+")");
	}
	public void addGuestSocket(CilentSession cs) {
		guests.add(cs);
	}
}
