package application.util;

public class SpiderTest {
	public SpiderTest(){
		 String test="i have a pen,i have an apple.";
		 
	     BaiduSpider spider=new BaiduSpider(test);
	     for(int i=0;i<spider.getResult().size();i++){
	    	 System.out.println(spider.getResult().get(i));
	     }
	     System.out.println("-----------Baidu done----------");

	     YoudaoSpider spider2=new YoudaoSpider(test);
	     for(int i=0;i<spider2.getResult().size();i++){
	    	 System.out.println(spider2.getResult().get(i));
	     }
	     System.out.println("-----------Youdao done---------");
	     for(int i=0;i<spider2.getSuggestion().size();i++){
	    	 System.out.println(spider2.getSuggestion().get(i));
	     }
	     System.out.println("-----------Youdao suggestions done-----------");
	     BingSpider spider3=new BingSpider(test);
	     for(int i=0;i<spider3.getResult().size();i++){
	    	 System.out.println(spider3.getResult().get(i));
	     }
	     System.out.println("-----------Bing done-----------");
	     for(int i=0;i<spider3.getSuggestion().size();i++){
	    	 System.out.println(spider3.getSuggestion().get(i));
	     }
	     System.out.println("-----------Bing suggestions done-----------");
	}
	
	public static void main(String[] args) {
		new SpiderTest();
	}
}
