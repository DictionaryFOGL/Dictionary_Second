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
		//System.out.println(name+' '+passWord);
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+name+"';");
		result.last();
		if(result.getRow()==0||!result.getString(4).equals(passWord)){
		    account=null;
		}
		else{
			account=new User(result.getString(2),result.getString(4),result.getString(3).charAt(0), null);				
		}
		return account;
	}

	@Override
	public int register(String name, String passWord, java.util.Date date, char gender) throws SQLException {
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+name+"';");
		result.last();
		if(result.getRow()!=0){
			return 1;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		stat.executeUpdate("INSERT INTO "+sheet1+"(name,gender,password,pwdmd5,signtime) "
				+ "values('"+name+"','"+gender+"','"+passWord+"','"+""+"','"+sdf.format(date)+"')");
		return 0;
	}

	@Override
	public ArrayList<User> searchAccount(String keyWord) throws SQLException {
		ArrayList<User> users=new ArrayList<User>();
		ResultSet result = stat.executeQuery("select * from usermessage where name like (\"%"+keyWord+"%\");");
		
		 while(result.next())
		   {
			 users.add(new User(result.getString(2),result.getString(4),result.getString(3).charAt(0),result.getDate(6)));	
		   }
		return users;
	}

	@Override
	public void sendCard(WordCard card,String receiver) throws SQLException {
		int senderId=-1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+card.getSenderName()+"';");
		result.last();
		senderId=result.getInt(1);
		int receiverId=-1;
		result = stat.executeQuery("select * from usermessage where name = '"+receiver+"';");
		result.last();
		if(result.getRow()!=0){
			receiverId=result.getInt(1);
			Date sendDate=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stat.executeUpdate("INSERT INTO "+sheet3+"(senderID,receiverID,words,url,time,say) "
					+ "values('"+senderId+"','"+receiverId+"','"+card.getWord().getWords()+"','"+card.getSite()+"','"+sdf.format(sendDate)+"','"+card.getSaySomething()+"')");
		}				
	}

	@Override
	public ArrayList<SearchHistory> SearchHistory(String userName) throws SQLException {
		int userId=-1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+userName+"';");
		result.last();
		if(result.getRow()==0){
			return null;
		}
		userId=result.getInt(1);
		ArrayList<SearchHistory> history=new ArrayList<SearchHistory>();
		result = stat.executeQuery("select * from history where userId ="+userId+";");
		while(result.next())
		{
			 history.add(new SearchHistory(result.getString(2),result.getInt(3),result.getInt(4),result.getInt(5),result.getInt(6)));	
		}		
		return history;
	}

	@Override
	public ArrayList<WordCard> getCard(String receiver) throws SQLException{
		int receiverId=-1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+receiver+"';");
		result.last();
		if(result.getRow()==0){
			return null;
		}
		receiverId=result.getInt(1);
		ArrayList<WordCard> cardList=new ArrayList<WordCard>();
		result = stat.executeQuery("select * from mailbox where receiverId ="+receiverId+";");
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
	public void like(String userName, int site,boolean islike) throws SQLException {
		if(site==3){
			return;
		}
		int userId=0;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+userName+"';");
		result.last();
		if(result.getRow()==0){
			return;
		}
		userId=result.getInt(1);
		result = stat.executeQuery("select * from "+sheet4+" where ID = "+userId+";");
		result.last();
		int p;
		if(islike){
			p=1;
		}
		else{
			p=-1;
		}
		int baidu=0,youdao=0,bing=0;
		if(result.getRow()==0){
			switch(site){
			case(0):baidu+=p;break;
			case(1):bing+=p;break;
			case(2):youdao+=p;break;
			}
			stat.executeUpdate("INSERT INTO "+sheet4+"(ID,baidu,bing,youdao) "
					+ "values('"+userId+"','"+baidu+"','"+bing+"','"+youdao+"')");
		}
		else{
			baidu=result.getInt(2);
			bing=result.getInt(3);
			youdao=result.getInt(4);
			switch(site){
			case(0):baidu+=p;break;
			case(1):bing+=p;break;
			case(2):youdao+=p;break;
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
		return new WordsLike(baidu,bing,youdao);
	}


	@Override
	public boolean addFriends(String userName,String friendName) throws SQLException {
		int userId=-1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+userName+"';");
		result.last();
		if(result.getRow()==0){
			return false;
		}
		userId=result.getInt(1);
		int friendId=-1;
		result = stat.executeQuery("select * from usermessage where name = '"+friendName+"';");
		result.last();
		if(result.getRow()==0){
			return false;
		}
		friendId=result.getInt(1);
		stat.executeUpdate("INSERT INTO "+sheet5+"(user,friend,relationship) "
				+ "values('"+userId+"','"+friendId+"','"+-1+"')");
		return true;
	}


	@Override
	public ArrayList<User> getFriends(String userName) throws SQLException {
		int userId=-1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+userName+"';");
		result.last();
		if(result.getRow()==0){
			return null;
		}
		userId=result.getInt(1);
		ArrayList<Integer> idArray=new ArrayList<Integer>();
		ArrayList<User> users=new ArrayList<User>();
		result = stat.executeQuery("select * from relationship where user ="+userId+";");
		 while(result.next())
		 {	 
			 idArray.add(result.getInt(3));	
		 }
		 for(int i=0;i<idArray.size();i++){
			 result = stat.executeQuery("select * from usermessage where ID ="+(int)idArray.get(i)+";");
			 result.last();
			 users.add(new User(result.getString(2),"",result.getString(3).charAt(0),result.getDate(6)));
		 }
		return users;
	}

	@Override
	public boolean insertHistory(String keyWord, String userName) throws SQLException {
		int userId=-1;
		ResultSet result = stat.executeQuery("select * from usermessage where name = '"+userName+"';");
		result.last();
		if(result.getRow()==0){
			return false;
		}
		userId=result.getInt(1);
		result = stat.executeQuery("select * from "+sheet2+" where userId = '"+userId+"' and keyWord = '"+keyWord+"';");
		result.last();
		if(result.getRow()!=0){
			return true;
		}
		stat.executeUpdate("INSERT INTO "+sheet2+"(keyWord,userId,baidu,bing,youdao)"
				+ "values('"+keyWord+"','"+userId+"','"+0+"','"+0+"','"+0+"')");
		return true;
	}
}
