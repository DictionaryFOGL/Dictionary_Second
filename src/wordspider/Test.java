package wordspider;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		YoudaoSpider y=new YoudaoSpider("no boy");
		System.out.println(y.isfound());
		ArrayList<String> ss=y.getResult();
		for(String s:ss)
			System.out.println(s);
	}

}
