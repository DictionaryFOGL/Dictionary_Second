package application.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BingSpider extends Spider{
	
	private String suggestionRegexd;
	private String suggestionRegex2;
	private String suggestionRegex2d;
	private String wordRegexd;
	private String sentenceRegexd;
	private Document doc;
	private Pattern p;
	private Pattern pd;
	private Matcher m;
	private Matcher md;
	
	public BingSpider() {
		super();
		init();
		process(keyWord); 
	}
	
	public BingSpider(String keyWord) {
		super(keyWord);
		init();
		setWord(keyWord);
	}
	
	private void init(){
		preUrl="http://cn.bing.com/dict/search?q=";
		wordRegex="<li><span class=\"pos\">.*</span><span class=\"def\"><span>.*</span></span></li>|<li><span class=\"pos web\">.*</span><span class=\"def\"><span>.*</span></span></li>";
		wordRegexd="<li><span class=\"pos\">|</span><span class=\"def\"><span>|</span></span></li>|<li><span class=\"pos web\">.*</span><span class=\"def\"><span>";
		sentenceRegex="<a class=\"p1-9\" name=\"translation_sen_id.*1\">.*</a>|<span>.*</span>\n       <a class=\"p1-9\"|<span>.*</span>\n      </div>\n     </div>";
		sentenceRegexd="<a class=\"p1-9\" name=\"translation_sen_id.*1\">|</a>|<span>|</span>\n       <a class=\"p1-9\"|</span>\n      </div>\n     </div>";
		suggestionRegex="1\">.*</a>\n      <div class=\"df_wb_text\">";
		suggestionRegexd="1\">|</a>\n      <div class=\"df_wb_text\">";
		suggestionRegex2="&amp;FORM=BDVSP6.*1\">.*</a></li>";
		suggestionRegex2d="&amp;FORM=BDVSP6.*1\">|</a></li>";
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
		StringBuffer str=new StringBuffer();
    	p=Pattern.compile(sentenceRegex);
    	m = p.matcher(doc.html());
    	pd=Pattern.compile(sentenceRegexd);
    	while(m.find()){
			md=pd.matcher(m.group());
			//System.out.println(m.group());
			StringBuffer sb=new StringBuffer();
			while(md.find()){
				//System.out.println(md.group());
				md.appendReplacement(sb,"");
			}
			md.appendTail(sb);
			str=str.append(sb);
			
		}
    	explanations.add(str.toString());
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
		if(suggestions.size()==0){
		p=Pattern.compile(suggestionRegex2);
		pd=Pattern.compile(suggestionRegex2d);
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
	}
	
	private void process(String keyWord) {
		url=preUrl+keyWord;
	    try {
			doc = Jsoup.connect(url).get();
			processWords();
			if(explanations.size()==0){
				processSentence();
		    }
			processSuggestion();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<String> getSuggestion() {
		return suggestions;
	}

}
