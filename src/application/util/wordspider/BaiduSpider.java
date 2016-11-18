package application.util.wordspider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
	public ArrayList<String> getResult() {
		return explanations;
	}

	@Override
	public ArrayList<String> getSuggestion() {
		return suggestions;
	}

	@Override
	public boolean isWordFound() {
		return isWordFound;
	}

	@Override
	public void setWord(String keyWord) {
		explanations.clear();
		suggestions.clear();
		isWordFound=true;
		this.keyWord=keyWord;
		process(keyWord);
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
				isWordFound=false;
		    	processSentence();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}  

		
	}

	@Override
	public String getExplanations() {
		StringBuffer str=new StringBuffer();
		for(int i=0;i<getResult().size();i++){
	    	 str=str.append(getResult());
	    	 if(i!=getResult().size()-1){
	    		 str=str.append("/");
	    	 }
	     }
		return str.toString();
	}
}
