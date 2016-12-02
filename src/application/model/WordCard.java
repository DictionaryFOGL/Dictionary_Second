package application.model;

import java.util.Date;

public class WordCard {
	private Word word;
	private String senderName;
	private String saySomething;
	private int senderID;
	private Date time;
	private int site;
	
	public WordCard(Word word, String senderName, String saySomething, int senderID, Date time, int site) {
		this.word = word;
		this.senderName = senderName;
		this.saySomething=saySomething;
		this.senderID = senderID;
		this.time = time;
		this.site = site;
	}

	public WordCard(Word word) {
		this.word = word;
	}
	
	public Word getWord() {
		return word;
	}
	
	public void setSenderName(String senderName) {
		this.senderName=senderName;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getSaySomething() {
		return saySomething;
	}

	public int getSenderID() {
		return senderID;
	}

	public Date getTime() {
		return time;
	}

	public String getSite() {
		if(site == 2) return "fanyi.baidu.com";
		else if(site == 1) return "www.baidu.com";
		else return "www.bing.com/translator";
	}

	public boolean isTheSameOne(WordCard another) {
		if(word.equals(another.word)) return true;
		if(senderID == another.getSenderID()) return true;
		if(site == another.site) return true;
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		WordCard another=(WordCard) obj;
		return isTheSameOne(another);
	}
}
