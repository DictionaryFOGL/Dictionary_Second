package application.model.message;

import application.model.WordsLike;

public class LikeMessage extends Message {

	private int site;
	private String userName;
	private WordsLike wordslike;
	public LikeMessage(byte type,int site,String userName) {
		super(type);
		setSite(site);
		setUserName(userName);
	}
	
	public int getSite() {
		return site;
	}
	public void setSite(int site) {
		this.site = site;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public WordsLike getWordslike() {
		return wordslike;
	}

	public void setWordslike(WordsLike wordslike) {
		this.wordslike = wordslike;
	}
	

}
