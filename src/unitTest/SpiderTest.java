package unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import application.model.Word;
import application.util.YoudaoSpider;
import application.util.BaiduSpider;
import application.util.BingSpider;
import application.util.Spider;

public class SpiderTest {

	@Test
	public void testYoudao1() {
		Spider s=new YoudaoSpider();
		s.setWord("fdsfdgsfh");
		Word r=s.getResult();
		assertEquals(null, r);
	}
	
	@Test
	public void testBaidu2() {
		Spider s=new BaiduSpider();
		s.setWord("fdsfdgsfh");
		Word r=s.getResult();

		assertEquals(1, r.getTranslation().size());
	}
	
	@Test
	public void testBaidu3() {
		Spider s=new BaiduSpider();
		s.setWord("you");
		Word r=s.getResult();
		assertEquals(1, r.getTranslation().size());
	}
	
	@Test
	public void testBing1() {
		Spider s=new BingSpider();
		s.setWord("hi");
		Word r=s.getResult();
		System.out.println(r.showTranslation());
		assertArrayEquals(new int[]{3,5}, new int[]{r.getTranslation().size(),s.getSuggestion().size()});
		for(String ss:r.getTranslation()) {
			assertEquals(false, ss.indexOf("<span>") != -1);
			assertEquals(false, ss.indexOf("</span>") != -1);
			assertEquals(false, ss.indexOf("<a href=") != -1);
		}
	}
	
	@Test
	public void testBing2() {
		Spider s=new BingSpider();
		s.setWord("new");
		Word r=s.getResult();
		System.out.println(r.showTranslation());
		assertArrayEquals(new int[]{5,5}, new int[]{r.getTranslation().size(),s.getSuggestion().size()});
		for(String ss:r.getTranslation()) {
			assertEquals(false, ss.indexOf("<span>") != -1);
			assertEquals(false, ss.indexOf("</span>") != -1);
			assertEquals(false, ss.indexOf("<a href=") != -1);
		}
	}
	
	@Test
	public void testBing3() {
		Spider s=new BingSpider();
		s.setWord("hello friend");
		Word r=s.getResult();
		System.out.println(r.showTranslation());//becuz r==null 
		assertArrayEquals(new int[]{1,5}, new int[]{r.getTranslation().size(),s.getSuggestion().size()});
	}
	
	@Test
	public void testBing4() {
		Spider s=new BingSpider();
		s.setWord("new");
		Word r=s.getResult();
		System.out.println(r.showTranslation());
		assertArrayEquals(new int[]{5,5}, new int[]{r.getTranslation().size(),s.getSuggestion().size()});
		//wrong translation size and Suggestion size check
	}
	
	@Test
	public void testBing5() {
		Spider s=new BingSpider();
		s.setWord("genuinesfe");
		
		Word r=s.getResult();
		assertEquals(null, r);
		assertEquals(5, s.getSuggestion().size());
	}
	
	@Test
	public void testBing6() {
		Spider s=new BingSpider();
		s.setWord("pelll");
		Word r=s.getResult();
		assertEquals(8, s.getSuggestion().size());
		assertEquals(null, r);
	}
	
	@Test
	public void testBing7() {
		Spider s=new BingSpider();
		s.setWord("ssssssssssssssssssssssssssssssssssssssssssss");
		Word r=s.getResult();

		//System.out.print("show: "+r.showTranslation());
		assertEquals(null,r);
	}
	
	@Test
	public void testBing8() {
		Spider s=new BingSpider();
		s.setWord("sb");
		Word r=s.getResult();

		System.out.print("show: "+r.showTranslation());
		assertEquals(2,r.getTranslation().size());
	}
	
	@Test
	public void testBing9() {
		Spider s=new BingSpider();
		s.setWord("XXX");
		Word r=s.getResult();

		System.out.print("show: "+r.showTranslation());
		assertEquals(2,r.getTranslation().size());
	}
	
	@Test
	public void testBing10() {
		Spider s=new BingSpider();
		s.setWord("c");
		Word r=s.getResult();

		System.out.print("show: "+r.showTranslation());
		assertEquals(2,r.getTranslation().size());
	}
	
	@Test
	public void testBing11() {
		Spider s=new BingSpider();
		s.setWord("c");
		Word r=s.getResult();

		System.out.print("show: "+r.showTranslation());
		assertEquals(2,r.getTranslation().size());
	}

	@Test
	public void testYoudao2() {
		Spider s=new YoudaoSpider();
		s.setWord("cries");
		Word r=s.getResult();
		assertEquals(2, r.getTranslation().size());
		//wrong Suggestion size check Suggestion size should be zero

		//System.out.println(s.getSuggestion().get(0));
		assertEquals(2, r.getTranslation().size());
	}

	@Test
	public void testYoudaonn() {
		Spider s=new YoudaoSpider();
		s.setWord("mlp");
		Word r=s.getResult();
		assertEquals(1, r.getTranslation().size());
		//wrong Suggestion size check Suggestion size should be zero

		System.out.println(r.showTranslation());
	}

	@Test
	public void testYoudao3() {
		Spider s=new YoudaoSpider();
		s.setWord("pelll");
		Word r=s.getResult();
		assertEquals(null, r);
		assertEquals("pell", s.getSuggestion().get(0));
	}
}
