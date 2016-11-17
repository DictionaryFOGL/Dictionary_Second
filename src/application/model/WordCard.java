package application.model;

import java.util.Date;

public class WordCard {
	private Word word;
	private String sender;
	private Date time;
	private int site;
	
	public WordCard(Word word, String sender, Date time, int site) {
		this.word = word;
		this.sender = sender;
		this.time = time;
		this.site = site;
	}

	public WordCard(Word theWord) {
		this.word = word;
	}
	
	
}
