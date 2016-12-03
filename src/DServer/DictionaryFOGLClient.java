package DServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import application.model.*;
import application.model.message.*;
import application.model.message.Message;
public class DictionaryFOGLClient implements Client,CSConstant{
	
	private Socket socket;
	
	private ObjectInputStream objectFromServer;
	private ObjectOutputStream objectToServer;
	private String host="127.0.0.1";
	private boolean waiting;
	private boolean isLogin;
	private boolean isSended;
	private boolean isAdded;
	private boolean isRecorded;
	private User account;
	private ArrayList<User>userList;
	private ArrayList<WordCard>cards;
	private ArrayList<User>friendList;
	private ArrayList<SearchHistory>searchhistory;
	private WordsLike wordslike;
	
	public DictionaryFOGLClient(){
		try {
            init();
			objectFromServer=new ObjectInputStream(socket.getInputStream());		
			//socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}
	
	private void init() throws UnknownHostException, IOException{
		socket=new Socket(host,8000);
		objectToServer=new ObjectOutputStream(socket.getOutputStream());
		objectToServer.flush();
		account=null;	
		isLogin=false;
		isAdded=false;
		userList=null;		
		isSended=false;
		searchhistory=null;
		isRecorded=false;
		//refreshAll();
	}
	
	@Override
	public void waitForAction() throws ClassNotFoundException, IOException {
		while(true){
			work((Message)objectFromServer.readObject());			
		}
		
	}
	
	private void work(Message message) throws ClassNotFoundException, IOException{
		switch(message.getType()){
		case(RECEIVE_CARD):receiveCard((ResultMessage)message);break;
		case(SEND_CARD):sendCardResult();break;
		case(FRIEND_ONLINE):friendOnline(message);break;
		case(NEW_FRIEND):newFriend(message);break;
		case(REFRESH):fresh(message);break;		
		case(LIKE):refreshLike((LikeMessage)message);break;
		case(INSERT_HISTORY):insertResult();break;
		case(LIKE_CANCEL):refreshLike((LikeMessage)message);break;
		case(SEARCH_HISTORY):getSearchHistory((ResultMessage)message);break;
		case(LOGIN):loginResult((LoginMessage) message);break;
		case(LOGOUT):logoutResult((LogoutMessage) message);break;
		case(REGISTER):regeisterResult((LoginMessage)message);break;
		case(SEARCH_USER):searchUserResult((ResultMessage)message);break;
		case(ADD_FRIEND):addFriendResult(message);break;
		case(SEARCH_FRIEND):getFriendResult((ResultMessage)message);break;
		}		
	}
	
	private void insertResult() {
		isRecorded=true;	
	}

	private void getFriendResult(ResultMessage message) {
		friendList=message.getResults();
	}

	private void addFriendResult(Message message) {
		isAdded=true;
		
	}

	private void refreshLike(LikeMessage message) {
		wordslike=message.getWordslike();		
	}

	private void sendCardResult() {
		isSended=true;		
	}

	private void searchUserResult(ResultMessage message) {
		userList=message.getResults();
	}

	private void waitForAction(Message message) throws InterruptedException{
		//boolean waiting=true;
	     while(waiting){
	    	 Thread.sleep(100);
	     }
	     waiting=true;
	}
	
	public void receiveCard(ResultMessage message){		
		cards=message.getResults();
	}
	
	public void friendOnline(Message message){		
		
	}
	
	public void newFriend(Message message){		
		
	}
	
	public void fresh(Message message){		
	}
	
	public void getSearchHistory(ResultMessage message){	
		searchhistory=message.getResults();
	}

	public void loginResult(LoginMessage message) throws IOException, ClassNotFoundException{		
			if(message.isIdentified()){
				isLogin=true;
				account=message.getUser();
				System.out.println(account.getUserName()+" login succeeded");
			}
			else{
				isLogin=false;
				System.out.println("login failed");
			}

	}
	
	public void logoutResult(LogoutMessage message) throws IOException{	
		if(message.isLogout()){
			System.out.println("Logout succeeded");
			isLogin=false;
			account=null;
		}
		else{
			System.out.println("Logout failed");
		}
	}
	
	public void regeisterResult(LoginMessage message){	
		if(message.getRegistered()==0){
			isLogin=true;
			account=message.getUser();
			System.out.println(account.getUserName()+" register succeeded");
		}
		else if(message.getRegistered()==1){
			isLogin=false;
			System.out.println("error:user name already exists!");
		}
	}
	
	public void login(String name,String passWord) throws IOException{
		LoginMessage message=new LoginMessage(LOGIN,new User(name,passWord));
		objectToServer.writeObject(message);	
	}
	
	public void logout() throws IOException{
		if(!isLogin){
			System.out.println("logout failed:not login yet");
			return;
		}
		LogoutMessage message=new LogoutMessage(LOGOUT);
		objectToServer.writeObject(message);
	}

	@Override
	public void refreshCards() throws IOException {
		SearchMessage message=new SearchMessage(RECEIVE_CARD,account.getUserName());
		objectToServer.writeObject(message);
	}

	@Override
	public void searchUser(String keyWord) throws IOException {
		SearchMessage message=new SearchMessage(SEARCH_USER,keyWord);
		objectToServer.writeObject(message);
	}

	@Override
	public void insertHistory(String keyWord) throws IOException {
		InsertHistoryMessage message=new InsertHistoryMessage(INSERT_HISTORY,keyWord,account.getUserName());
		objectToServer.writeObject(message);	
	}

	@Override
	public void addFriend(String receiver) throws IOException {
		AddFriendMessage message=new AddFriendMessage(ADD_FRIEND,account.getUserName(),receiver);
		objectToServer.writeObject(message);
	}

	@Override
	public void like_cancel(Website website) throws IOException {
		int site;	
		switch(website){
		case Baidu:site=0;break;
		case Youdao:site=2;break;
		case Bing:site=1;break;
		default:site=3;
		}		
		LikeMessage message=new LikeMessage(LIKE_CANCEL,site,account.getUserName());
		objectToServer.writeObject(message);
		
	}

	@Override
	public void like(Website website) throws IOException {
		int site;	
		switch(website){
		case Baidu:site=0;break;
		case Youdao:site=2;break;
		case Bing:site=1;break;
		default:site=3;
		}		
		LikeMessage message=new LikeMessage(LIKE,site,account.getUserName());
		objectToServer.writeObject(message);
	}

	@Override
	public ArrayList<SearchHistory> getHistory() {
		return searchhistory;
	}

	@Override
	public ArrayList<WordCard> getCards() throws IOException {
		return cards;
	}

	@Override
	public void register(User user) throws IOException {
		LoginMessage message=new LoginMessage(REGISTER,user);
		objectToServer.writeObject(message);
		
	}

	@Override
	public void modifyUserInfo(User user) {
		// TODO Auto-generated method stub		
	}

	@Override
	public User getUser() {
		return account;
	}

	@Override
	public ArrayList<User> searchUserResult() {
		return userList;
	}

	@Override
	public void sendCard(String receiver,WordCard card) throws IOException {
		SendCardMessage message=new SendCardMessage(SEND_CARD,receiver,card);
		objectToServer.writeObject(message);
	}

	@Override
	public boolean isCardSended() {
		if(isSended){
			isSended=false;
			return true;
		}
		return isSended;
	}

	@Override
	public WordsLike getLike() {
		return wordslike;		
	}

	@Override
	public void refreshLike() throws IOException{
		LogoutMessage message=new LogoutMessage(REFRESH_LIKE);
		objectToServer.writeObject(message);
	}

	@Override
	public boolean isFriendAdded() {
		if(isAdded){
			isAdded=false;
			return true;
		}
		return isAdded;
	}
	
	@Override
	public ArrayList<User> getFriend() {
		return friendList;
	}

	@Override
	public void refreshFriendList() throws IOException {
		SearchMessage message=new SearchMessage(SEARCH_FRIEND,account.getUserName());
		objectToServer.writeObject(message);	
	}

	@Override
	public void refreshAll() throws IOException {
		refreshFriendList();
		refreshLike();
		refreshCards();
		
	}

	
	@Override
	public boolean isHistoryRecorded() {
		if(isRecorded){
			isRecorded=false;
			return true;
		}
		return isRecorded;
	}


	@Override
	public void refreshHistory() throws IOException {
		SearchMessage message=new SearchMessage(SEARCH_HISTORY,account.getUserName());
		objectToServer.writeObject(message);	
		
	}
	

}
