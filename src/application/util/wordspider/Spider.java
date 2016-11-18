package application.util.wordspider;

import java.util.ArrayList;

abstract class Spider {
	protected String keyWord;
	protected String url;
	protected String preUrl;
	protected String wordRegex;
	protected String sentenceRegex;
	protected String suggestionRegex;
	protected ArrayList<String> explanations;
	protected ArrayList<String> suggestions;
	protected boolean isWordFound;
	
	public Spider(){
		explanations=new ArrayList<String>();
		suggestions=new ArrayList<String>();
	}
	
	public Spider(String keyWord){
		this.keyWord=keyWord;
		explanations=new ArrayList<String>();
		suggestions=new ArrayList<String>();
	}
	
	public abstract void setWord(String keyWord);// set the keyWord for Searching
	
	public abstract ArrayList<String> getResult();// return the words OR SENTENCE

	public abstract String getExplanations();//the result joint into a single string
	
	public boolean isWordFound(){
		return isWordFound;
	}//whether the result is found by "word" rather than sentence;

	public abstract ArrayList<String> getSuggestion();//for suggestions of input,not work in baiduspider
}
