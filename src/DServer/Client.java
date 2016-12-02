package DServer;

import java.util.ArrayList;
import application.model.*;

interface Client{
	public void refresh();

	public void searchUser(String keyWord);
	
	public void searchWord(String keyWord);
	
	public void addFriend(User receiver);
	
	public void like_cancel(Website website);

	public void like(Website website);
	
	public ArrayList<SearchHistory> getHistory();
	
	public ArrayList<WordCard> getCards();
	
	public void register(User user);
	
	public void login(String name,String passWord);
	
	public void modifyUserInfo(User user);
	
	public void logout();
}