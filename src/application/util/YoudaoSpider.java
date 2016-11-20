package application.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class YoudaoSpider extends Spider{
	
	private String suggestionRegexd;
	private String wordRegex2;
	private String wordRegex2d;
	private String sentenceRegexd;
	private String translationRegexd;
	private Document doc;
	private Pattern p;
	private Pattern p2;
	private Pattern pd;
	private Matcher m;
	private Matcher m2;
	private Matcher md;
	
	public YoudaoSpider(){
		super();
		init();
	}

	public YoudaoSpider(String keyWord) {
		super(keyWord);
		init();
		setWord(keyWord);
	}
	
	private void init(){
		preUrl="http://dict.youdao.com/w/eng/";
		wordRegex="<div class=\"trans-container\"> \n         <ul>.*?</ul>";
		wordRegex2="<li>.*?</li>";
		wordRegex2d="<li>|</li>";
		sentenceRegex="&nbsp;</a> \n            <span>.*</span> \n           </div> ";
		sentenceRegexd="&nbsp;</a> \n            <span>|</span> \n           </div> ";
		translationRegex="<p>.*</p> \n          <p>以上为机器翻译结果";
		translationRegexd="<p>|</p> \n          <p>以上为机器翻译结果";
		suggestionRegex="class=\"search-js\">.*</a></span>";
		suggestionRegexd="class=\"search-js\">|</a></span>";
	}

	@Override
	public ArrayList<String> getResult() {
		return explanations;
	}


	@Override
	public void setWord(String keyWord) {
		explanations.clear();
		suggestions.clear();
		this.keyWord=keyWord;
		process(keyWord);	
	}

	private void processTranslation(){
		p=Pattern.compile(translationRegex);
		pd=Pattern.compile(translationRegexd);
		m = p.matcher(doc.html());
		while(m.find()){
			md=pd.matcher(m.group());
			StringBuffer sb=new StringBuffer();
			while(md.find()){
				md.appendReplacement(sb,"");
			}
			md.appendTail(sb);
			explanations.add(sb.toString());
		}
	}
	
	private void processSuggestion(){
		p=Pattern.compile(suggestionRegex);
		pd=Pattern.compile(suggestionRegexd);
		m = p.matcher(doc.html());
		while(m.find()){
			md=pd.matcher(m.group());
			StringBuffer sb=new StringBuffer();
			while(md.find()){
				md.appendReplacement(sb,"");
			}
			md.appendTail(sb);
			suggestions.add(sb.toString());
		}
	}
	
	private void processWord(){
		p=Pattern.compile(wordRegex,Pattern.DOTALL);
		p2=Pattern.compile(wordRegex2);
		pd=Pattern.compile(wordRegex2d);
		m = p.matcher(doc.html());
		if(m.find()){
			m2=p2.matcher(m.group());
			while(m2.find()){
				md=pd.matcher(m2.group());
				StringBuffer sb=new StringBuffer();
				while(md.find()){
					md.appendReplacement(sb,"");
				}
				md.appendTail(sb);
				explanations.add(sb.toString());			
			}
		}
	}
	
	private void processSentence(){
		p=Pattern.compile(sentenceRegex);
    	m = p.matcher(doc.html());
    	pd=Pattern.compile(sentenceRegexd);
    	while(m.find()){
			md=pd.matcher(m.group());
			StringBuffer sb=new StringBuffer();
			while(md.find()){
				md.appendReplacement(sb,"");
			}
			md.appendTail(sb);
			explanations.add(sb.toString());
			
    	}		
	}
	
	private void process(String keyWord) {
		url=preUrl+keyWord;
	    try {
			doc = Jsoup.connect(url).get();	
			processWord();
			if(explanations.size()==0){
				processSentence();
				processSuggestion();
			}
			if(explanations.size()==0){
				processTranslation();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}

	@Override
	public ArrayList<String> getSuggestion() {
		return suggestions;
	}
	
}
