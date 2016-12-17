package application.model.message;

import java.io.Serializable;

public class SearchMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2004650355884995940L;
	private String keyWord;

	public SearchMessage(byte type,String keyWord) {
		super(type);
		setKeyWord(keyWord);
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	

}
