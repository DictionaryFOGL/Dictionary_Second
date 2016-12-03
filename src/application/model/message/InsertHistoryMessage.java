package application.model.message;

public class InsertHistoryMessage extends Message{

	private String keyWord;
	private String userName;
	
	public InsertHistoryMessage(byte type,String keyWord,String userName) {
		super(type);
		setKeyWord(keyWord);
		setUserName(userName);
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
