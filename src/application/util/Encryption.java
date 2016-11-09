package application.util;

public class Encryption {
	public static String MD5(String clearText) {
		
	}
	public static int loopLeftShift(int num,int bit) {
		//在java下实现循环左移的方法
		return (num<<bit)|(num>>(Integer.SIZE-bit));
	}
}
