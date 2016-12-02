package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.model.*;

public class DictionaryDB implements Database,DBConstant{
	private Connection conn=null;
	private Statement stat=null;
	private String local="jdbc:mysql://127.0.0.1:3306";//local地址可改为服务器地址
	private String usr="root";
	private String pwd="24601";
	
	
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(local,usr,pwd);
			stat=conn.createStatement();
			stat.executeQuery("USE "+dbName);
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
	public User verify(String name, String passWord) throws SQLException {
		User account=null;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+name+"';");
		result.last();
		if(result.getRow()==0||!result.getString(4).equals(passWord)){
		    account=null;
		}
		else{
			account=new User(result.getString(2),result.getString(4),result.getString(3).charAt(0));				
		}
		return account;
	}

	@Override
	public void register(String name, String passWord, java.sql.Date date, char gender) throws SQLException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stat.executeUpdate("INSERT INTO "+sheet1+"(name,gender,password,pwdmd5,signtime) "
				+ "values('"+name+"','"+gender+"','"+passWord+"','"+"error"+"','"+sdf.format(date)+"')");
	}

	@Override
	public ArrayList<User> searchAccount(String keyWord) throws SQLException {
		ArrayList<User> users=new ArrayList<User>();
		ResultSet result = stat.executeQuery("select * from usermessage where name like (\"%"+keyWord+"%\");");
		System.out.println("select * from usermessage where name like (\"%"+keyWord+"%\");");
		 while(result.next())
		   {
			 users.add(new User(result.getString(2),result.getString(4),result.getString(3).charAt(0),result.getDate(6)));	
		   }
		return users;
	}

	@Override
	public void sendCard(WordCard card, int receiverId) throws SQLException {
		Date sendDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stat.executeUpdate("INSERT INTO "+sheet3+"(senderID,receiverID,words,url,time,say) "
				+ "values('"+card.getSenderID()+"','"+receiverId+"','"+card.getWord().getWords()+"','"+card.getSite()+"','"+sdf.format(sendDate)+"','"+card.getSaySomething()+"')");	
	}

	@Override
	public ArrayList<SearchHistory> SearchHistory(int userId) throws SQLException {
		ArrayList<SearchHistory> history=new ArrayList<SearchHistory>();
		ResultSet result = stat.executeQuery("select * from history where userId ="+userId+";");
		 while(result.next())
		   {
			 history.add(new SearchHistory(result.getString(2),result.getInt(3),result.getInt(4),result.getInt(5),result.getInt(6)));	
		   }
		
		return history;
	}

	@Override
	public ArrayList<WordCard> getCard(int receiverId) throws SQLException{
		ArrayList<WordCard> cardList=new ArrayList<WordCard>();
		ResultSet result = stat.executeQuery("select * from mailbox where receiverId ="+receiverId+";");
		 while(result.next())
		 {	 
			 int siteNumber=0;
			 switch(result.getString(4)){
			 case"fanyi.baidu.com":siteNumber=2;break;
			 case"www.baidu.com":siteNumber=1;break;
			 case"www.bing.com/translator":siteNumber=0;break;
			 }

			 cardList.add(new WordCard(new Word(result.getString(3),""),null,result.getString(6),result.getInt(1),result.getDate(5),siteNumber));	
		 }
		 for(int i=0;i<cardList.size();i++){
			 ResultSet name = stat.executeQuery("select * from usermessage where id = '"+cardList.get(i).getSenderID()+"';");
				name.last();
				String senderName=name.getString(2);
				WordCard card=cardList.get(i);
				card.setSenderName(senderName);
				cardList.set(i,card);
		 }		 
		return cardList;
	}

	@Override
	public void search(String keyWord, int userId) throws SQLException {
		stat.executeUpdate("INSERT INTO "+sheet2+"(keyWord,userId,baidu,bing,youdao) "
				+ "values('"+keyWord+"','"+userId+"','"+0+"','"+0+"','"+0+"')");	
		
	}

	
	@Override
	public void like(int userId, int site) throws SQLException {
		//connect();
		ResultSet result = stat.executeQuery("select * from "+sheet4+" where ID = "+userId+";");
		result.last();
		int baidu=0,youdao=0,bing=0;
		if(result.getRow()==0){
			switch(site){
			case(0):baidu++;break;
			case(1):bing++;break;
			case(2):youdao++;break;
			}
			stat.executeUpdate("INSERT INTO "+sheet4+"(ID,baidu,bing,youdao) "
					+ "values('"+userId+"','"+baidu+"','"+bing+"','"+youdao+"')");
		}
		else{
			baidu=result.getInt(2);
			bing=result.getInt(3);
			youdao=result.getInt(4);
			switch(site){
			case(0):baidu++;break;
			case(1):bing++;break;
			case(2):youdao++;break;
			}
			stat.executeUpdate("delete from "+sheet4+
					" where ID = "+userId+";");
			stat.executeUpdate("INSERT INTO "+sheet4+"(ID,baidu,bing,youdao) "
					+ "values('"+userId+"','"+baidu+"','"+bing+"','"+youdao+"')");//为何下文update语句更新无效???
			/*
			stat.executeUpdate("update "+sheet4+
							" set baidu = '"+baidu+"'"
							+ " where ID = "+userId+";");
			stat.executeUpdate("update "+sheet4+
							" set baidu = "+bing+""
							+ " where ID = "+userId+";");
			stat.executeUpdate("update "+sheet4+
							" set baidu = "+youdao+""
							+ " where ID = "+userId+";");
			*/
			//closeAll();
		}
	}

	@Override
	public WordsLike getWordsLike() throws SQLException {
		//connect();
		ResultSet result = stat.executeQuery("select * from "+sheet4+";");
		int baidu=0,youdao=0,bing=0;
		 while(result.next())
		   {
			    baidu+=result.getInt(2);
				bing+=result.getInt(3);
				youdao+=result.getInt(4);
		   }
		 //closeAll();
		return new WordsLike(baidu,bing,youdao);
	}


	@Override
	public void addFriends(int userId, int friendId) throws SQLException {
		stat.executeUpdate("INSERT INTO "+sheet5+"(user,friend,relationship) "
				+ "values('"+userId+"','"+friendId+"','"+-1+"')");
	}


	@Override
	public ArrayList<User> getFriends(int userId) throws SQLException {
		ArrayList<Integer> idArray=new ArrayList<Integer>();
		ArrayList<User> users=new ArrayList<User>();
		ResultSet result = stat.executeQuery("select * from relationship where user ="+userId+";");
		 while(result.next())
		 {	 
			 idArray.add(result.getInt(3));
	
		 }
		 for(int i=0;i<idArray.size();i++){
			 result = stat.executeQuery("select * from usermessage where ID ="+(int)idArray.get(i)+";");
			 users.add(new User(result.getString(2),"",result.getString(3).charAt(0),result.getDate(6)));
		 }

		return users;
	}


}
