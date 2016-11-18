package application.model;

import java.util.ArrayList;

public class Word {
	private String words;
	private ArrayList<String> translation=new ArrayList<String>();
	public Word(String words, String translation) {
		this.words=words;
		if(translation != null) this.translation.add(translation);
	}
	
	public Word(String words) {
		this(words,null);
	}
	
	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}
	
	public ArrayList<String> getTranslation() {
		return translation;
	}

	public void setTranslation(ArrayList<String> translation) {
		this.translation = translation;
	}

}
