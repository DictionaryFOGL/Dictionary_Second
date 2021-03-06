package application.util;

import java.util.ArrayList;

import application.model.Word;

public abstract class Spider {
	protected String keyWord;
	protected String url;
	protected String preUrl;
	protected String wordRegex;
	protected String sentenceRegex;
	protected String translationRegex;
	protected String suggestionRegex;
	protected ArrayList<String> explanations;
	protected ArrayList<String> suggestions;
	
	public Spider(){
		explanations=new ArrayList<String>();
		suggestions=new ArrayList<String>();
	}
	
	public Spider(String keyWord){
		this.keyWord=keyWord;
		explanations=new ArrayList<String>();
		suggestions=new ArrayList<String>();
	}
	
	public String getPreUrl() {
		return preUrl;
	}
	
	public abstract void setWord(String keyWord);// set the keyWord for Searching
	
	public abstract Word getResult();// return the words OR SENTENCE
	
	public abstract ArrayList<String> getSuggestion();//for suggestions of input,not work in baiduspider
}
