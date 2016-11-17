package wordspider;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		BingSpider y=new BingSpider("yes");
		System.out.println(y.isfound());
		ArrayList<String> ss=y.getResult();
		for(String s:ss)
			System.out.println(s);
		System.out.println();
		
		BaiduSpider yy=new BaiduSpider("There is a boy");
		System.out.println(yy.isfound());
		ArrayList<String> sss=yy.getResult();
		for(String s:sss)
			System.out.println(s);
		System.out.println();
		
		YoudaoSpider yyy=new YoudaoSpider("There is a boy");
		System.out.println(yyy.isfound());
		ArrayList<String> ssss=yyy.getResult();
		for(String s:ssss)
			System.out.println(s);
	}

}
