package DServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.SearchHistory;
import application.model.User;
import application.model.WordCard;
import application.model.message.AddFriendMessage;
import application.model.message.InsertHistoryMessage;
import application.model.message.LikeMessage;
import application.model.message.LoginMessage;
import application.model.message.LogoutMessage;
import application.model.message.Message;
import application.model.message.ResultMessage;
import application.model.message.SearchMessage;
import application.model.message.SendCardMessage;
import database.Database;
import database.DictionaryDB;

class ClientThread extends Thread implements CSConstant
{
	private PipedOutputStream out=new PipedOutputStream();
	private PipedInputStream in=new PipedInputStream();
	private Database DB;
	private User sessionAccount;
	private int cardSize=0;
	private int friendSize=0;
	private Socket socket;
	private ObjectInputStream objectFromClient;
	private ObjectOutputStream objectToClient;
	
	public ClientThread(Socket socket){
		this.socket=socket;
	}
	
	
	protected void finalize(){
		DB.connect();
		try {
			DB.userOnline(sessionAccount.getUserName(), 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DB.closeAll();
	}

	public PipedOutputStream getOutputStream()
	{
		return out;
	}
	public PipedInputStream getInputStream()
	{
		return in;
	}
	
	class PipeThread extends Thread{
		@Override
		public void run() {
			byte[] b=new byte[1024];
			while(true){
				try 
				{
					int num=in.read(b);
					if(num!=-1)
					{
						switch(new String(b,0,num)){
						case("USER"):{
							DB.connect();
							ResultMessage message=new ResultMessage(USER_UPDATE,DB.searchAccount(""));
							objectToClient.writeObject(message);
							DB.closeAll();
							break;
						}
						case("CARD"):{
							SearchMessage message=new SearchMessage(RECEIVE_CARD,sessionAccount.getUserName());
							sendCardsList((SearchMessage)message);
							break;
						}
						case("FRIEND"):{
							SearchMessage message=new SearchMessage(SEARCH_FRIEND,sessionAccount.getUserName());
							searchFriend(message); 
							break;
						}
						};
					}
				} 
				catch (IOException | SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
		}
		
	}
	public void userUpdate() throws IOException{
		RefreshEvent.setRefreshed(true);	
		RefreshEvent.setUserStateRefreshed(true);
	}
	
	public void newFriend() throws IOException{
		RefreshEvent.setRefreshed(true);	
		RefreshEvent.setFriendRefreshed(true);
	}
	
	public void newCard() throws IOException{
		RefreshEvent.setRefreshed(true);	
		RefreshEvent.setCardRefreshed(true);
	}
	
	@Override
	public void run() {
		try {
			objectToClient=new ObjectOutputStream(socket.getOutputStream());
			objectToClient.flush();
			objectFromClient=new ObjectInputStream(socket.getInputStream());
			DB=new DictionaryDB();
			(new PipeThread()).start();
			
		while(true){
			work((Message)objectFromClient.readObject());
		}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			
		}
	}

	private void work(Message message) throws ClassNotFoundException, IOException, SQLException{
		switch(message.getType()){
			case(SEND_CARD):sendCard((SendCardMessage)message);break;
			case(RECEIVE_CARD):sendCardsList((SearchMessage)message);break;
			case(FRIEND_ONLINE):friendOnline(message);break;
			case(ADD_FRIEND):addFriend((AddFriendMessage)message);break;
			case(LIKE):like((LikeMessage)message);break;
			case(LIKE_CANCEL):likeCancel((LikeMessage)message);break;		
			case(SEARCH_HISTORY):searchHistory((SearchMessage)message);break;
			case(INSERT_HISTORY):insertHistory((InsertHistoryMessage)message);break;
			case(LOGIN):login((LoginMessage)message);break;
			case(LOGOUT):logout((LogoutMessage)message);break;
			case(REGISTER):regeister((LoginMessage)message);break;
			case(SEARCH_USER):searchUser((SearchMessage)message);break;
			case(REFRESH_LIKE):sendLike();break;
			case(SEARCH_FRIEND):searchFriend((SearchMessage)message);break;
			default:break;
		}		
	}
	
	private void insertHistory(InsertHistoryMessage message) throws SQLException, IOException {
		DB.connect();
		if(DB.insertHistory(message.getKeyWord(),message.getUserName())){
			Message feedback=new Message(INSERT_HISTORY);
			objectToClient.writeObject(feedback);
		}
		DB.closeAll();
	}

	private void searchFriend(SearchMessage message) throws SQLException, IOException {
		DB.connect();
		ArrayList<User> friends=new ArrayList<User>();
		friends=DB.getFriends(message.getKeyWord());
		System.out.println(sessionAccount.getUserName()+"FriendSize "+friendSize+" "+friends.size());
		if(friendSize==friends.size()){
			DB.closeAll();
			return;
		}
		friendSize=friends.size();
		ResultMessage resultmessage=new ResultMessage(SEARCH_FRIEND,friends);
		objectToClient.writeObject(resultmessage);
		DB.closeAll();
	}

	private void sendLike() throws SQLException, IOException {
		DB.connect();
		LikeMessage message=new LikeMessage(LIKE,3,null);
		message.setWordslike(DB.getWordsLike());
		objectToClient.writeObject(message);
		DB.closeAll();
	}

	private void sendCardsList(SearchMessage message) throws SQLException, IOException {
		DB.connect();
		ArrayList<WordCard> tempCards=DB.getCard(message.getKeyWord());
		ResultMessage resultMessage=new ResultMessage(RECEIVE_CARD,tempCards);		
		if(tempCards.size()==cardSize){
			DB.closeAll();
			return;
		}	
		cardSize=tempCards.size();
		objectToClient.writeObject(resultMessage);
		DB.closeAll();
		
	}

	private void searchUser(SearchMessage message) throws SQLException, IOException {	
		DB.connect();
		ResultMessage resultMessage=new ResultMessage(SEARCH_USER,DB.searchAccount(message.getKeyWord()));
		objectToClient.writeObject(resultMessage);
		DB.closeAll();
	}

	private void sendCard(SendCardMessage message) throws SQLException, IOException{		
		DB.connect();
		DB.sendCard(message.getCard(), message.getReceiverName());
		DB.closeAll();
		Message sendMessage=new Message(SEND_CARD);
		newCard();
		objectToClient.writeObject(sendMessage);
	}
	
	private void friendOnline(Message message){		
		
	}
	
	private void addFriend(AddFriendMessage message) throws SQLException, IOException{		
		DB.connect();
		//Message feedback=new Message(ADD_FRIEND);
		if(DB.addFriends(message.getSender(), message.getReceiver())&&DB.addFriends( message.getReceiver(),message.getSender())){
			//objectToClient.writeObject(feedback);
			newFriend();
		}		
		DB.closeAll();
	}
	
	private void like(LikeMessage message) throws SQLException, IOException{	
		DB.connect();
		DB.like(message.getUserName(),message.getSite(),true);
		message.setWordslike(DB.getWordsLike());
		objectToClient.writeObject(message);
		DB.closeAll();
	}
	

	private void likeCancel(LikeMessage message) throws SQLException, IOException{
		DB.connect();
		DB.like(message.getUserName(),message.getSite(),true);
		message.setWordslike(DB.getWordsLike());
		objectToClient.writeObject(message);
		DB.closeAll();
	}
	
	private void searchHistory(SearchMessage message) throws SQLException, IOException{
		DB.connect();
		ArrayList<SearchHistory> history=DB.SearchHistory(message.getKeyWord());
		ResultMessage resultmessage=new ResultMessage(SEARCH_HISTORY,history);
		objectToClient.writeObject(resultmessage);
		DB.closeAll();
	}
	

	private void login(LoginMessage message) throws ClassNotFoundException, IOException, SQLException {
		boolean isUserFound=false;
		DB.connect();
		User user=(User)message.getUser();
		//System.out.println("login...."+user.getUserName());
		User account=null;
		try {
			account = DB.verify(user.getUserName(), user.getPwdMd5());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(account!=null){
			isUserFound=true;
		}
		if(isUserFound){
			message.identify();
			DB.userOnline(message.getUser().getUserName(), 1);
			message.getUser().setGender(account.getGender());
			message.getUser().setRegisterDate(account.getRegisterDate());
			sessionAccount=message.getUser();
			userUpdate();
			objectToClient.writeObject(message);		
		}
		else{
			objectToClient.writeObject(message);
		}
		DB.closeAll();
	}
	
	private void logout(LogoutMessage message) throws IOException, SQLException{
		DB.connect();
		message.Logout();
		DB.userOnline(sessionAccount.getUserName(), 0);
		objectToClient.writeObject(message);
		userUpdate();
		DB.closeAll();
	}
	
	private void regeister(LoginMessage message) throws IOException, SQLException{	
		if(message.getUser().getUserName().length()>=1){
			DB.connect();
			int result=DB.register(message.getUser().getUserName(), message.getUser().getPwdMd5(), message.getUser().getRegisterDate(), message.getUser().getGender());
			if(result==0){
				message.setRegistered(0);
				sessionAccount=message.getUser();
				DB.userOnline(sessionAccount.getUserName(), 1);
				userUpdate();
			}
			if(result==1){
				message.setRegistered(1);
			}
			
		}
		objectToClient.writeObject(message);
	}

	public User getSessionAccount() {
		return sessionAccount;
	}


	
}
