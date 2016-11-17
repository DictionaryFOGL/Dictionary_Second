package wordspider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BaiduSpider extends Spider{

	public BaiduSpider(String keyWord) {
		super(keyWord);
		url="http://dict.baidu.com/s?wd="+keyWord;
		regex="<p><strong>.*</strong><span>.*</span></p>";
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
		Pattern pd=Pattern.compile("<p><strong>|</strong><span>|</span></p>|amp;");
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
