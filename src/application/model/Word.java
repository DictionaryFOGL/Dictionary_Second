package application.model;

import java.util.ArrayList;

public class Word {
	private String words;
	private ArrayList<String> translation=new ArrayList<String>();
	public Word(String words, String translation) {
		this.words=words;
		if(translation != null) this.translation.add(translation);
	}
	
	@Override
	public boolean equals(Object obj) {
		Word another=(Word) obj;
		if(words.equals(another.words)) return true;
		if(showTranslation().equals(another.showTranslation())) return true;
		return false;
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

	public String showTranslation() {
		return translation.toString().replaceAll(", ", "/").replaceAll("\\[|\\]", "");
	}
}
