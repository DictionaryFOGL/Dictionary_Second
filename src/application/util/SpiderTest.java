package application.util;

public class SpiderTest {
	public SpiderTest(){
		 String test="There is a pell";
		 
	     BaiduSpider spider=new BaiduSpider(test);
	     System.out.println(spider.getExplanations());
	     System.out.println("Is Word Found:"+spider.isWordFound());
	     System.out.println("-----------Baidu done----------");

	     YoudaoSpider spider2=new YoudaoSpider(test);
	     System.out.println(spider2.getExplanations());
	     System.out.println("Is Word Found:"+spider2.isWordFound());
	     System.out.println("-----------Youdao done---------");
	     for(int i=0;i<spider2.getSuggestion().size();i++){
	    	 System.out.println(spider2.getSuggestion().get(i));
	     }
	     System.out.println("-----------Youdao suggestions done-----------");
	     BingSpider spider3=new BingSpider(test);
	     System.out.println(spider2.getExplanations());
	     System.out.println("Is Word Found:"+spider3.isWordFound());
	     System.out.println("-----------Bing done-----------");
	     for(int i=0;i<spider3.getSuggestion().size();i++){
	    	 System.out.println(spider3.getSuggestion().get(i));
	     }
	     System.out.println("-----------Bing suggestions done-----------");
	}
	
	public static void main(String[] args) {
		SpiderTest S=new SpiderTest();
	}
}
