package application.util;

public class Encryption {
	public static String MD5(String clearText) {
		
	}
	public static int loopLeftShift(int num,int bit) {
		//��java��ʵ��ѭ�����Ƶķ���
		return (num<<bit)|(num>>(Integer.SIZE-bit));
	}
}
