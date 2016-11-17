package wordspider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class YoudaoSpider extends Spider{

	public YoudaoSpider(String keyWord) {
		super(keyWord);
		url="http://dict.youdao.com/w/eng/"+keyWord;
		regex="<ul>.*?</ul>";
		init();
	}

	@Override
	public ArrayList<String> getResult() {
		return explanations;
	}

	@Override
	public boolean isfound() {
		if(getResult().size()!=0)
			  return true;
			else
				return false;
	}

	@Override
	protected void init() {
		Pattern p=Pattern.compile(regex,Pattern.DOTALL);
		Pattern p2=Pattern.compile("<li>.*?</li>");
		Pattern pd=Pattern.compile("<li>|</li>");
	    try {
			Document doc = Jsoup.connect(url).get();
			Matcher m = p.matcher(doc.html());
			Matcher md=null,m2=null;	
			if(m.find()){
				m2=p2.matcher(m.group());
			}
			while(m2.find()){
				md=pd.matcher(m2.group());
				StringBuffer sb=new StringBuffer();
				while(md.find()){
					md.appendReplacement(sb,"");
				}
				md.appendTail(sb);
				explanations.add(sb.toString());			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}
	

}
