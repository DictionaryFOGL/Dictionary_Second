package DServer;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.*;
import application.model.message.*;
import application.model.message.Message;
import database.*;



class ClientSession implements Runnable,CSConstant{
	private Database DB;
	private Socket socket;
	private ObjectInputStream objectFromClient;
	private ObjectOutputStream objectToClient;
	private boolean waiting;
	public ClientSession(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run() {
		try {
			objectToClient=new ObjectOutputStream(socket.getOutputStream());
			objectToClient.flush();
			objectFromClient=new ObjectInputStream(socket.getInputStream());
			DB=new DictionaryDB();
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

	private void waitForAction() throws InterruptedException{
		//boolean waiting=true;
	     while(waiting){
	    	 Thread.sleep(100);
	     }
	     waiting=true;
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
			objectToClient.writeObject(message);
		}
		else{
			objectToClient.writeObject(message);
		}
	}
	
	private void logout(LogoutMessage message) throws IOException{
		message.Logout();
		objectToClient.writeObject(message);
	}
	
	private void regeister(LoginMessage message) throws IOException, SQLException{	
		if(message.getUser().getUserName().length()>1){
			DB.connect();
			int result=DB.register(message.getUser().getUserName(), message.getUser().getPwdMd5(), message.getUser().getRegisterDate(), message.getUser().getGender());
			if(result==0){
				message.setRegistered(0);
			}
			if(result==1){
				message.setRegistered(1);
			}
			
		}
		objectToClient.writeObject(message);
	}

	
	
}
