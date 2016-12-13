package database;

import java.sql.SQLException;
import java.util.ArrayList;

import application.model.*;

public interface Database {	
	
	public void connect();
	
	public void closeAll();

	public User verify(String name, String passWord) throws SQLException;

	public void register(String name, String passWord, java.util.Date date, char gender) throws SQLException;

	public ArrayList<User> searchAccount(String keyWord) throws SQLException;

	public void sendCard(WordCard card,String receiver) throws SQLException;

	public void search(String keyWord, int userId) throws SQLException;
	
	public ArrayList<SearchHistory> SearchHistory(String userName) throws SQLException;
	
	public boolean insertHistory(String keyWord,String userName) throws SQLException;

	public ArrayList<WordCard> getCard(String receiver) throws SQLException;
	
	public void like(String userName,int site,boolean islike) throws SQLException;
	
	public WordsLike getWordsLike() throws SQLException;
	
	public boolean addFriends(String userName,String friendName) throws SQLException;
	
	public ArrayList<User> getFriends(String userName) throws SQLException;
	
	public boolean isUserNameRepeated(String userName) throws SQLException;
	
	public void userOnline(String userName,int status) throws SQLException;//1 online,0 offline
}
