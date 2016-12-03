package application.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidInput {
	public static boolean validUsername(String userName) {
		Pattern p=Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]{2,10}$");
		return patternCheck(p, userName);
	}
	public static boolean validPwd(String password) {
		Pattern p=Pattern.compile("^[a-zA-Z0-9]{4,16}$");
		return patternCheck(p, password);
	}
	//见上次词典的实现(超过字符限制可以使其变色？)
	public static String wordSearchProcessed(String input) {
		//TODO
	}
	//用户名不允许空格，字符数量限制等
	public static String friendSearchProcessed(String input) {
		//TODO
	}
	private static boolean patternCheck(Pattern p,String content) {
		Matcher m=p.matcher(content);
		if(!m.find()) return false;
		else return true;
	}
}
