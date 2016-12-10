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
	private Socket socket;
	private ObjectInputStream objectFromClient;
	private ObjectOutputStream objectToClient;
	
	public ClientThread(Socket socket){
		this.socket=socket;
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
						System.out.println("The receiver Thread receices "+new String(b,0,num));
						SearchMessage message=new SearchMessage(RECEIVE_CARD,sessionAccount.getUserName());
						sendCardsList((SearchMessage)message);
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
		ResultMessage resultMessage=new ResultMessage(RECEIVE_CARD,DB.getCard(message.getKeyWord()));
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
		objectToClient.writeObject(sendMessage);
	}
	
	private void friendOnline(Message message){		
		
	}
	
	private void addFriend(AddFriendMessage message) throws SQLException, IOException{		
		DB.connect();
		Message feedback=new Message(ADD_FRIEND);
		if(DB.addFriends(message.getSender(), message.getReceiver())){
			objectToClient.writeObject(feedback);
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
	

	private void login(LoginMessage message) throws ClassNotFoundException, IOException {
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
			message.getUser().setGender(account.getGender());
			message.getUser().setRegisterDate(account.getRegisterDate());
			sessionAccount=message.getUser();
			userUpdate();
			objectToClient.writeObject(message);
			
		}
		else{
			objectToClient.writeObject(message);
		}
	}
	
	private void logout(LogoutMessage message) throws IOException{
		message.Logout();
		objectToClient.writeObject(message);
		userUpdate();
	}
	
	private void regeister(LoginMessage message) throws IOException, SQLException{	
		if(message.getUser().getUserName().length()>=1){
			DB.connect();
			int result=DB.register(message.getUser().getUserName(), message.getUser().getPwdMd5(), message.getUser().getRegisterDate(), message.getUser().getGender());
			if(result==0){
				message.setRegistered(0);
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
