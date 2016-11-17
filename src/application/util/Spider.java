package application.util;

import java.util.ArrayList;

import application.model.*;

public abstract class Spider {
	private String originalURL;
	private String url;
	private String regex;
	
	public Spider(String url, String regex) {
		originalURL = url;
		this.url=originalURL;
		this.regex = regex;
	}

	public Word getResult(String words) {
		url=url+words;
		//此处调用process方法
		ArrayList<String> explanation=process(words);
		url=originalURL;
		if(explanation.size() != 0) {
			Word targetWord=new Word(words);
			targetWord.setTranslation(explanation);
			return targetWord;
		} else return null;
	}
	
	//继承类要求实现此方法
	protected abstract ArrayList<String> process(String words);
}
