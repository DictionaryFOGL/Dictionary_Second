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

	public WordCard(Word theWord) {
		this.word = word;
	}
	
	
}
