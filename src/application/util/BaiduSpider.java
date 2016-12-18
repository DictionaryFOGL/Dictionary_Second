package application.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import application.model.Word;

public class BaiduSpider extends Spider{

	private String wordRegexd;
	private String sentenceRegexd;
	private Document doc;
	private Pattern p;
	private Pattern pd;
	private Matcher m;
	private Matcher md;
	
	public BaiduSpider(){
		super();
		init();
	}
	
	public BaiduSpider(String keyWord) {
		super(keyWord);
		init();
		setWord(keyWord);
	}
	
	private void init() {
		preUrl="http://dict.baidu.com/s?wd=";
		wordRegex="<p><strong>.*</strong><span>.*</span></p>";
		wordRegexd="<p><strong>|</strong><span>|</span></p>|amp;";
		sentenceRegex="\">.*</a> \n        <p></p> ";
		sentenceRegexd="</a> \n        <p></p> |\">";
	}

	@Override
	public Word getResult() {
		process(keyWord);
		if(explanations.size() == 0) {
			System.out.println(explanations.size());
			return null;
		} else {
			Word result=new Word(keyWord);
			result.setTranslation(explanations);
			return result;
		}
	}

	@Override
	public ArrayList<String> getSuggestion() {
		return suggestions;
	}



	@Override
	public void setWord(String keyWord) {
		explanations.clear();
		suggestions.clear();
		this.keyWord=keyWord;
	}
	
	private void processWords(){
		p=Pattern.compile(wordRegex);
		pd=Pattern.compile(wordRegexd);
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
			processWords();
		    if(explanations.size()==0){
		    	processSentence();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	public static void main(String[] args) {
		Spider s=new BingSpider();
		s.setWord("c");
		Word r=s.getResult();
		for(int i=0;i<r.getTranslation().size();i++){
			System.out.println(r.getTranslation().get(i));
		}
	}
}
