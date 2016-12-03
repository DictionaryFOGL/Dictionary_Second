package DServer;

import java.io.IOException;
import java.util.ArrayList;
import application.model.*;

interface Client{
	
	public void waitForAction() throws ClassNotFoundException, IOException;
	
	public void refreshCards() throws IOException;
	
	public void refreshHistory() throws IOException;

	public void refreshAll() throws IOException;
	
	public WordsLike getLike();
	
	public void refreshLike() throws IOException;

	public User getUser();
	
	public boolean isCardSended();
	
	public boolean isFriendAdded();
	
	public boolean isHistoryRecorded();
	
	public ArrayList<User> searchUserResult();
	
	public void searchUser(String keyWord) throws IOException;
	
	public void insertHistory(String keyWord) throws IOException;
	
	public void addFriend(String receiver) throws IOException;
	
	public ArrayList<User> getFriend();
	
	public void like_cancel(Website website) throws IOException;

	public void like(Website website) throws IOException;
	
	public ArrayList<SearchHistory> getHistory();
	
	public void refreshFriendList() throws IOException;
	
	public ArrayList<WordCard> getCards() throws IOException;
	
	public void register(User user) throws IOException;
	
	public void login(String name,String passWord) throws IOException;
	
	public void modifyUserInfo(User user);
	
	public void logout() throws IOException;
	
	public void sendCard(String receiver,WordCard card) throws IOException;

}