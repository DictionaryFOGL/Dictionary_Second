package database;

import java.sql.SQLException;
import java.util.ArrayList;

import application.model.*;

public interface Database {	
	
	public void connect();
	
	public void closeAll();

	public User verify(String name, String passWord) throws SQLException;

	public void register(String name, String passWord, java.sql.Date date, char gender) throws SQLException;

	public ArrayList<User> searchAccount(String keyWord) throws SQLException;

	public void sendCard(WordCard card, int receiverId) throws SQLException;

	public void search(String keyWord, int userId) throws SQLException;
	
	public ArrayList<SearchHistory> SearchHistory(int userId) throws SQLException;

	public ArrayList<WordCard> getCard(int receiverId) throws SQLException;
	
	public void like(int userId,int site) throws SQLException;
	
	public WordsLike getWordsLike() throws SQLException;
	
	public void addFriends(int userId,int friendId) throws SQLException;
	
	public ArrayList<User> getFriends(int userId) throws SQLException;
}
