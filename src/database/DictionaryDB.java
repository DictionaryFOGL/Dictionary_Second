package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import application.model.*;
import application.model.message.ResultMessage;
import application.util.Encryption;
import serverAndThread.CSConstant;

public class DictionaryDB implements Database, DBConstant ,CSConstant{
	private Connection conn = null;
	private Statement stat = null;
	private String local = "jdbc:mysql://127.0.0.1:3306/?characterEncoding=utf8";// local地址可改为服务器地址
	private String usr = "root";
	private String pwd = "root";
	private HashMap<String, Integer> nameToId=new HashMap<String, Integer>();
	private HashMap<Integer, String> idToName=new HashMap<Integer, String>();

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(local, usr, pwd);
			stat = conn.createStatement();
			stat.executeQuery("USE " + dbName);
			ResultSet result = stat.executeQuery("select * from "+sheet1);
			while(result.next()) {
				nameToId.put(result.getString(2), result.getInt(1));
				idToName.put(result.getInt(1), result.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void closeAll() {
		try {
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User verify(String name, String pwdMd5) throws SQLException {
		User account = null;
		String command="select * from usermessage where name = '" + name + "';";
		ResultSet result = stat.executeQuery(command);
		result.last();
		if (result.getRow() == 0 || !result.getString(5).equals(pwdMd5)) {
			account = null;
		} else {
			int id=result.getInt(1);
			String pwd=result.getString(4);
			char gen=result.getString(3).charAt(0);
			Date date=result.getDate(6);
			command="select * from "+sheet4+" where ID='"+id+"';";
			ResultSet result1=stat.executeQuery(command);
			result1.last();
			if(result1.getRow() == 0) return null;
			account=new User(id,name,pwd,gen,date,result1.getInt(2),result1.getInt(4),result1.getInt(3));
			ArrayList<String> friends=getFriends(id);
			account.setFriendList(friends);
			account.setMailBox(getCard(id));
		}
		return account;
	}

	@Override
	public void register(String name, String passWord, Date date, char gender) throws SQLException {
		String MD5 = Encryption.MD5(passWord);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stat.executeUpdate("insert into " + sheet1 + "(name,gender,password,pwdmd5,signtime) " + "values('" + name
				+ "','" + gender + "','" + passWord + "','" + MD5 + "','" + sdf.format(date) + "');");
		ResultSet re=stat.executeQuery("select * from "+sheet1+" where name='"+name+"';");
		re.last();
		int id=re.getInt(1);
		idToName.put(id, name);
		nameToId.put(name, id);
		stat.executeUpdate("insert into "+sheet4+" (ID,baidu,bing,youdao) values ("+id+",0,0,0);");
	}

	@Override
	public ResultMessage searchAccount(String keyWord) throws SQLException {
		ArrayList<String> users = new ArrayList<String>();
		User target=null;
		ResultSet result = stat.executeQuery("select * from usermessage where name like (\"%" + keyWord + "%\");");
		while (result.next()) {
			String name=result.getString(2);
			if(!name.equals(keyWord)) {
				users.add(name);
			} else {
				int id=result.getInt(1);
				String pwd=result.getString(4);
				String pwdmd5=result.getString(5);
				Date date=result.getDate(6);
				char gender=result.getString(3).charAt(0);
				String command="select * from "+sheet4+" where ID='"+id+"';";
				result = stat.executeQuery(command);
				result.last();
				target=new User(id, name, pwd, gender, date, result.getInt(2), result.getInt(4), result.getInt(3));
			}
		}
		return new ResultMessage(SEARCH_USER, users, target);
	}

	@Override
	public void sendCard(WordCard card, String receiver) throws SQLException {
		int senderId = -1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '" + card.getSenderName() + "';");
		result.last();
		senderId = result.getInt(1);
		int receiverId = -1;
		result = stat.executeQuery("select * from usermessage where name = '" + receiver + "';");
		result.last();
		if (result.getRow() != 0) {
			receiverId = result.getInt(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stat.executeUpdate("INSERT INTO " + sheet3 + "(senderID,receiverID,words,trans,url,time,say) " + "values('"
					+ senderId + "','" + receiverId + "','" + card.getWord().getWords() + "','" +card.getWord().showTranslation() + "','" + card.getSite() + "','"
					+ sdf.format(card.getTime()) + "','" + card.getSaySomething() + "')");
		}
	}

	@Override
	public ArrayList<SearchHistory> SearchHistory(String userName) throws SQLException {
		int userId = nameToId.get(userName);
		ArrayList<SearchHistory> history = new ArrayList<SearchHistory>();
		ResultSet result = stat.executeQuery("select * from history where userId =" + userId + ";");
		while (result.next()) {
			history.add(new SearchHistory(result.getString(3), result.getInt(4), result.getInt(5),result.getInt(6)));
		}
		return history;
	}

	@Override
	public ArrayList<WordCard> getCard(int receiverId) throws SQLException {
		ArrayList<WordCard> cardList = new ArrayList<WordCard>();
		ResultSet result = stat.executeQuery("select * from mailbox where receiverId =" + receiverId + ";");
		while (result.next()) {
			int siteNumber = result.getInt(5);
			cardList.add(new WordCard(new Word(result.getString(3), result.getString(4)), idToName.get(result.getInt(1)), result.getString(7), result.getInt(1),
					result.getDate(6), siteNumber));
			System.out.println(result.getDate(6));
		}
		return cardList;
	}

	@Override
	public void search(String keyWord, int userId) throws SQLException {
		stat.executeUpdate("INSERT INTO " + sheet2 + "(keyWord,userId,baidu,bing,youdao) " + "values('" + keyWord
				+ "','" + userId + "','" + 0 + "','" + 0 + "','" + 0 + "')");

	}

	@Override
	public void like(String userName, int site, boolean islike) throws SQLException {
		int userId = nameToId.get(userName);
		ResultSet result = stat.executeQuery("select * from " + sheet4 + " where ID = " + userId + ";");
		result.last();
		int p;
		if (islike) {
			p = 1;
		} else {
			p = -1;
		}
		int baidu = 0, youdao = 0, bing = 0;
		if (result.getRow() == 0) {
			switch (site) {
			case (0):
				baidu += p;
				break;
			case (1):
				bing += p;
				break;
			case (2):
				youdao += p;
				break;
			}
			stat.executeUpdate("INSERT INTO " + sheet4 + "(ID,baidu,bing,youdao) " + "values('" + userId + "','" + baidu
					+ "','" + bing + "','" + youdao + "')");
		} else {
			baidu = result.getInt(2);
			bing = result.getInt(3);
			youdao = result.getInt(4);
			switch (site) {
			case (0):
				baidu += p;
				break;
			case (1):
				bing += p;
				break;
			case (2):
				youdao += p;
				break;
			}
			stat.executeUpdate("delete from " + sheet4 + " where ID = " + userId + ";");
			stat.executeUpdate("INSERT INTO " + sheet4 + "(ID,baidu,bing,youdao) " + "values('" + userId + "','" + baidu
					+ "','" + bing + "','" + youdao + "')");// 为何下文update语句更新无效???
			/*
			 * stat.executeUpdate("update "+sheet4+ " set baidu = '"+baidu+"'" +
			 * " where ID = "+userId+";"); stat.executeUpdate("update "+sheet4+
			 * " set baidu = "+bing+"" + " where ID = "+userId+";");
			 * stat.executeUpdate("update "+sheet4+ " set baidu = "+youdao+"" +
			 * " where ID = "+userId+";");
			 */
			// closeAll();
		}
	}

	@Override
	public WordsLike getWordsLike() throws SQLException {
		// connect();
		ResultSet result = stat.executeQuery("select * from " + sheet4 + ";");
		int baidu = 0, youdao = 0, bing = 0;
		while (result.next()) {
			baidu += result.getInt(2);
			bing += result.getInt(3);
			youdao += result.getInt(4);
		}
		return new WordsLike(baidu, bing, youdao);
	}

	@Override
	public boolean addFriends(String userName, String friendName) throws SQLException {
		int userId = -1;
		ResultSet result = stat.executeQuery("select * from "+sheet1+" where name = '" + userName + "';");
		result.last();
		if (result.getRow() == 0) {
			return false;
		}
		userId = result.getInt(1);
		int friendId = -1;
		result = stat.executeQuery("select * from "+sheet1+" where name = '" + friendName + "';");
		result.last();
		if (result.getRow() == 0) {
			return false;
		}
		friendId = result.getInt(1);
		if(friendId < userId) {
			int tmp=userId;
			userId=friendId;
			friendId=tmp;
		}
		stat.executeUpdate("insert into " + sheet5 + "(ID1,ID2) " + "values('" + userId + "','"
				+ friendId + "');");
		return true;
	}
	
	public boolean deleteFriend(int userID,String friendName) throws SQLException {
		int friendId = -1;
		ResultSet result = stat.executeQuery("select * from "+sheet1+" where name = '" + friendName + "';");
		result.last();
		friendId = result.getInt(1);
		if(friendId < userID) {
			int tmp=userID;
			userID=friendId;
			friendId=tmp;
		}
		String command="delete from "+sheet5+" where ID1='"+userID+"' and ID2='"+friendId+"';";
		int affect=-1;
		affect=stat.executeUpdate(command);
		if(affect == 1) return true;
		else return false;
	}

	@Override
	public ArrayList<String> getFriends(int userId) throws SQLException {
		ArrayList<Integer> idArray = new ArrayList<Integer>();
		ArrayList<String> users = new ArrayList<String>();
		String command="select * from "+sheet5+" where ID1 ='" + userId + "';";
		ResultSet result = stat.executeQuery(command);
		while (result.next()) {
			idArray.add(result.getInt(2));
		}
		result = stat.executeQuery("select * from "+sheet5+" where ID2 =" + userId + ";");
		while (result.next()) {
			idArray.add(result.getInt(1));
		}
		for (int i = 0; i < idArray.size(); i++) {
			String name=idToName.get((int) idArray.get(i));
			users.add(name);
			System.out.println(name);
		}
		return users;
	}

	@Override
	public boolean insertHistory(String keyWord, String userName, int site, int status) throws SQLException {
		int userId = -1;
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultSet result = stat.executeQuery("select * from "+sheet1+" where name = '" + userName + "';");
		result.last();
		if (result.getRow() == 0) {
			return false;
		}
		userId = result.getInt(1);
		result = stat.executeQuery(
				"select * from " + sheet2 + " where userId = '" + userId + "' and keyWord = '" + keyWord + "';");
		result.last();
		if (result.getRow() != 0) {
			int affect=0;
			if(site == 2) {
				affect+=stat.executeUpdate("UPDATE "+sheet2+" SET youdao='"+status+"' WHERE userId='"+userId+"' AND keyWord='"+keyWord+"'");
			} else if(site == 1){
				affect+=stat.executeUpdate("UPDATE "+sheet2+" SET bing='"+status+"' WHERE userId='"+userId+"' AND keyWord='"+keyWord+"'");
			} else {
				affect+=stat.executeUpdate("UPDATE "+sheet2+" SET baidu='"+status+"' WHERE userId='"+userId+"' AND keyWord='"+keyWord+"'");
			}
			long time=System.currentTimeMillis();
			affect+=stat.executeUpdate("UPDATE "+sheet2+" SET searchTime='"+formatter.format(time)+"' WHERE userId='"+userId+"' AND keyWord='"+keyWord+"'");
			if(affect == 2) return true;
			else return false;
		} else {
			int affect=0;
			long time=System.currentTimeMillis();
			if(site == 2) {
				affect=stat.executeUpdate("INSERT INTO " + sheet2 + "(keyWord,userId,youdao,searchTime)" + "values('" + keyWord + "','"
						+ userId + "','" + status + "','" + formatter.format(time) + "')");
			} else if(site == 1) {
				affect=stat.executeUpdate("INSERT INTO " + sheet2 + "(keyWord,userId,bing,searchTime)" + "values('" + keyWord + "','"
						+ userId + "','" + status + "','" + formatter.format(time) + "')");
			} else {
				affect=stat.executeUpdate("INSERT INTO " + sheet2 + "(keyWord,userId,baidu,searchTime)" + "values('" + keyWord + "','"
						+ userId + "','" + status + "','" + formatter.format(time) + "')");
			}
			if(affect == 1) return true;
			else return false;
		}
	}

	@Override
	public boolean isUserNameRepeated(String userName) throws SQLException {
		ResultSet result = stat.executeQuery("select * from usermessage where name = '" + userName + "';");
		result.last();
		if (result.getRow() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public void userOnline(String userName, int status) throws SQLException {
		ResultSet result = stat.executeQuery("select * from "+sheet1+" where name = '" + userName + "';");
		result.last();
		if (result.getRow() != 0) {
			stat.executeUpdate(
					"update " + sheet1 + " set Online = '" + status + "'" + " where name = '" + userName + "';");
		}

	}
	
	public boolean userResetCards(String userName,int before) throws SQLException {
		int userId = -1;
		ResultSet result = stat.executeQuery("select * from "+sheet1+" where name = '" + userName + "';");
		result.last();
		if (result.getRow() == 0) {
			return false;
		}
		userId = result.getInt(1);
		int after=stat.executeUpdate("delete from "+sheet3+" where receiverID='"+userId+"';");
		if(before == after) return true;
		else return false;
	}
	
	public boolean deleteCard(int receiverId,WordCard card) throws SQLException {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int affect=-1;
		String command="delete from "+sheet3+" where senderID='"+card.getSenderID()+"' and receiverID='"
				+receiverId+ "' and time='"+formatter.format(card.getTime())+"' and say='"+card.getSaySomething()+"';";
		System.out.println(command);
		affect=stat.executeUpdate(command);
		if(affect == 1) return true;
		else return false;
	}
	
	public boolean pwdChange(int id,String pwd) throws SQLException {
		String pwdmd5=Encryption.MD5(pwd);
		String command="update "+sheet1+" set password='"+pwd+"',pwdmd5='"+pwdmd5+"' where ID="+id+";";
		int affect=stat.executeUpdate(command);
		System.out.println(affect);
		if(affect == 1) return true;
		else return false;
	}
	
	public boolean genderChage(int id,char gender) throws SQLException {
		String command="update usermessage set gender='"+gender+"' where ID="+id+";";
		int affect=stat.executeUpdate(command);
		if(affect == 1) return true;
		else return false;
	}
	
	public static void main(String[] args) throws SQLException {
		DictionaryDB db=new DictionaryDB();
		WordCard card=new WordCard(new Word("hhh","是的"),"jk","quuu",2,new Date(233333333),0);
		db.connect();
		System.out.println(db.pwdChange(3, "12345"));
	}
}
