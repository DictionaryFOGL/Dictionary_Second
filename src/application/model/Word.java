package application.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Word  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6923765951036647041L;
	private String words;
	private ArrayList<String> translation=new ArrayList<String>();
	
	public Word(String words, ArrayList<String> translation) {
		this.words = words;
		this.translation = translation;
	}

	public Word(String words, String translation) {
		this.words=words;
		if(translation.length() != 0 && translation != null)
			this.translation.add(translation);
	}
	
	@Override
	public boolean equals(Object obj) {
		Word another=(Word) obj;
		if(words.equals(another.words)) return true;
		if(showTranslation().equals(another.showTranslation())) return true;
		return false;
	}
	
	public Word(String words) {
		this.words=words;
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
		return translation.toString().replaceAll(", ", "\n").replaceAll("\\[|\\]", "");
	}
}
