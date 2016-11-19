package application.model;

import java.util.Date;

public class WordCard {
	private Word word;
	private String senderName;
	private int senderID;
	private Date time;
	private int site;
	public WordCard(Word word, String senderName, int senderID, Date time, int site) {
		this.word = word;
		this.senderName = senderName;
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

	public String getSenderName() {
		return senderName;
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
