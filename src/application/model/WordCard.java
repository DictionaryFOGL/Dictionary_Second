package application.model;

import java.io.Serializable;
import java.util.Date;

public class WordCard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4307660439468308688L;
	private Word word;
	private String senderName;
	private String saySomething;
	private int senderID;
	private Date time;
	private int site;
	
	public WordCard(Word word, String senderName, String saySomething, int senderID, Date time, int site) {
		this(word,senderName,saySomething,time,site);
		this.senderID = senderID;
	}
	
	public WordCard(Word word, String senderName, String saySomething, Date time, int site) {
		this.word = word;
		this.senderName = senderName;
		this.saySomething=saySomething;
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

	public int getSite() {
		return site;
	}
	
	public String getSiteStr() {
		if(site == 0) return "Baidu";
		else if(site == 2) return "Youdao";
		else return "Bing";
	}

	public boolean isTheSameOne(WordCard another) {
		if(word.equals(another.word)) return true;
		if(senderID == another.getSenderID()) return true;
		if(site == another.site) return true;
		return false;
	}
}
