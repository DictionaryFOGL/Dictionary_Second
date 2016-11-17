package application.util;

import java.util.ArrayList;

public class YoudaoSpider extends Spider{

	public YoudaoSpider() {
		//Bing site=2
		super("http://dict.youdao.com/w/eng/", "<ul>.*?</ul>");
	}

	@Override
	protected ArrayList<String> process(String words) {
		// TODO Auto-generated method stub
		return null;
	}

}
