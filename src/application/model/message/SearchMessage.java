package application.model.message;

public class SearchMessage extends Message {
	private String keyWord;

	SearchMessage(byte type,String keyWord) {
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
