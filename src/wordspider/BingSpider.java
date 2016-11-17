package wordspider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BingSpider extends Spider{

	public BingSpider(String keyWord) {
		super(keyWord);
		url="http://cn.bing.com/dict/search?q="+keyWord;
		regex="<li><span class=\"pos\">.*</span><span class=\"def\"><span>.*</span></span></li>|<li><span class=\"pos web\">.*</span><span class=\"def\"><span>.*</span></span></li>";
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
		Pattern p=Pattern.compile(regex);
		Pattern pd=Pattern.compile("<li><span class=\"pos\">|</span><span class=\"def\"><span>|</span></span></li>|<li><span class=\"pos web\">.*</span><span class=\"def\"><span>");;
	    try {
			Document doc = Jsoup.connect(url).get();
			Matcher m = p.matcher(doc.html());
			Matcher md;
			while(m.find()){
				md=pd.matcher(m.group());
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
