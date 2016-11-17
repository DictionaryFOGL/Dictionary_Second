package application.util;

import java.util.ArrayList;

public class BingSpider extends Spider {

	public BingSpider() {
		//Bing site=1
		super("http://cn.bing.com/dict/search?q=", "<li><span class=\"pos\">.*</span><span class=\"def\"><span>.*</span></span></li>|<li><span class=\"pos web\">.*</span><span class=\"def\"><span>.*</span></span></li>");
	}

	@Override
	protected ArrayList<String> process(String words) {
		// TODO Auto-generated method stub
		return null;
	}

}
