package application.model.message;

import java.io.Serializable;

public class InsertHistoryMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7187252627850702895L;
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
