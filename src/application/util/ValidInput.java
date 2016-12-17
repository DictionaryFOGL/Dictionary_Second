package application.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.Database;
import database.DictionaryDB;

public class ValidInput {
	public static String randomVerify() {
		Random r=new Random();
		String alpha="abcdefghijklmnopqrstuvwxzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String result=""+alpha.charAt(r.nextInt(50))+alpha.charAt(r.nextInt(50))+alpha.charAt(r.nextInt(50))+alpha.charAt(r.nextInt(50));
		return result;
	}
	public static boolean validUsername(String userName) {
		Pattern p=Pattern.compile("^[a-zA-Z0-9]{2,10}$");
		return patternCheck(p, userName);
	}
	public static boolean validPwd(String password) {
		Pattern p=Pattern.compile("^[a-zA-Z0-9]{4,16}$");
		return patternCheck(p, password);
	}
	//见上次词典的实现(超过字符限制可以使其变色？)
	public static String wordSearchProcessed(String words) {
		if(words == null) return null;
		ArrayList<String> reGroup=new ArrayList<String>();
		words=words.trim().toLowerCase();
		StringBuilder result=new StringBuilder(words);
		deleteInvalidForWords(result);
		String[] group=result.toString().split(" ");
		for(String item:group) {
			if(item.length() != 0)
				reGroup.add(item);
		}
		result=new StringBuilder();
		int all=reGroup.size();
		for(int i=0;i<all;i++) {
			String item=reGroup.get(i);
			if(item.length() != 0)
				result.append(item);
			if(i != all-1) result.append(" ");
		}
		if(result.length() == 0) return null;
		else return result.toString();
	}
	//用户名不允许空格，字符数量限制等
	public static String friendSearchProcessed(String input) {
		StringBuilder result=new StringBuilder(input);
		result=deleteInvalidForFriends(result);
		if(result.length()>10)
			result.delete(10, result.length());
		if(result.length() != 0) return result.toString();
		else return null;
	}
	private static boolean patternCheck(Pattern p,String content) {
		Matcher m=p.matcher(content);
		if(!m.find()) return false;
		else return true;
	}
	private static void deleteInvalidForWords(StringBuilder word) {
		Pattern check=Pattern.compile("[^a-zA-Z\\-' ]+");
		Matcher m=check.matcher(word);
		while(m.find()) {
			int start=m.start();
			int end=m.end();
			word.delete(start, end);
			m.reset();
		}
	}
	private static StringBuilder deleteInvalidForFriends(StringBuilder input) {
		Pattern p=Pattern.compile("[a-zA-Z0-9_\u4e00-\u9fa5]+");
		StringBuilder result=new StringBuilder();
		Matcher m=p.matcher(input);
		while(m.find()) {
			result.append(m.group());
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(randomVerify());
	}
}
