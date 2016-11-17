package application.util;

import java.util.ArrayList;

public class BaiduSpider extends Spider {

	public BaiduSpider() {
		//Baidu site=0
		super("http://dict.baidu.com/s?wd=", "<p><strong>.*</strong><span>.*</span></p>");
	}

	@Override
	protected ArrayList<String> process(String words) {
		// TODO Auto-generated method stub
		return null;
	}

}
