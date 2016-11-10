package application.util;

public class Encryption {
	private static int OriginalLinkA;
	private static int OriginalLinkB;
	private static int OriginalLinkC;
	private static int OriginalLinkD;

	private static int LinkA=0x67452301;
	private static int LinkB=0xEFCDAB89;
	private static int LinkC=0x98BADCFE;
	private static int LinkD=0x10325476;
	
	private static int b,c,d;
	
	private static int[] M=new int[16];
	//获得128位摘要的十六进制形式（即长度为32的字符串）
	public static String MD5(String clearText) {
		groupedclearText(clearText);
		Round1();
		Round2();
		Round3();
		Round4();
		linkedSum();
		resetData();
		return Integer.toHexString(LinkA)+Integer.toHexString(LinkB)+Integer.toHexString(LinkC)+Integer.toHexString(LinkD);
	}
	private static void groupedclearText(String clearText) {
		int hex=Integer.valueOf(clearText,16);
		StringBuffer binaryComplete=new StringBuffer(Integer.toBinaryString(hex));
		StringBuffer binary64=new StringBuffer(Integer.toBinaryString(hex));
		binaryComplete.append('1');
		for(int i=binaryComplete.length();i<448;i++)
			binaryComplete.append('0');
		if(binary64.length() <= 63) binary64.append('1');
		for(int i=binary64.length();i<64;i++)
			binary64.append('0');
		binaryComplete.append(binary64);
		for(int i=0;i<16;i++) {
			int start=i*8;
			String subClearTextString=binaryComplete.substring(start,start+32);
			int subClearText=Integer.valueOf(subClearTextString,2);
			M[i]=subClearText;
		}
	}
	private static void Round1() {
		int[] T={0xD76AA478,0xE8C7B756,0x242070DB,0xC1BDCEEE,0xF57C0FAF,0x4787C62A,0xA8304613,0xFD469501,0x698098D8,0x8B44F7AF,0xFFFF5BB1,0x895CD7BE,0x6B901122,0xFD987193,0xA679438E,0x49B40821};
		int[] S={7,12,17,22};
		OriginalLinkA=LinkA;
		OriginalLinkB=LinkB;
		OriginalLinkC=LinkC;
		OriginalLinkD=LinkD;
		RoundBase(T,S,1);
	}
	private static void Round2() {
		int[] T={0xF61E2562,0xC040B340,0x265E5A51,0xE9B6C7AA,0xD62F105D,0x02441453,0xD8A1E681,0xE7D3FBC8,0x21E1CDE6,0xC33707D6,0xF4D50D87,0x455A14ED,0xA9E3E905,0xFCEEA3F8,0x676F02D9,0x8D2A4C8A};
		int[] S={5,9,14,20};
		RoundBase(T,S,2);
	}
	private static void Round3() {
		int[] T={0xFFFA3942,0x8771F681,0x699D6122,0xFDE5380C,0xA4BEEA44,0x4BDECFA9,0xF6BB4B60,0xBEBFBC70,0x289B7EC6,0xEAA127FA,0xD4EF3085,0x04881D05,0xD9D4D039,0xE6DB99E5,0x1FA27CF8,0xC4AC5665};
		int[] S={4,11,16,23};
		RoundBase(T,S,3);
	}
	private static void Round4() {
		int[] T={0xF4292244,0x432AFF97,0xAB9423A7,0xFC93A039,0x655B59C3,0x8F0CCC92,0xFFEFF47D,0x85845DD1,0x6FA87E4F,0xFE2CE6E0,0xA3014314,0x4E0811A1,0xF7657E82,0xBD3AF235,0x2AD7D2BB,0xEB86D391};
		int[] S={6,10,15,21};
		RoundBase(T,S,4);
	}
	private static void RoundBase(int[] T,int[] S,int round) {
		for(int i=0;i<16;i++) {
			int k=i;
			
			if(i%4 == 0) bcdSet(LinkB,LinkC,LinkD);
			else if(i%4 == 1) bcdSet(LinkA,LinkB,LinkC);
			else if(i%4 == 2) bcdSet(LinkD,LinkA,LinkB);
			else bcdSet(LinkC,LinkD,LinkA);
			
			int Logicbcd;
			if(round == 1) Logicbcd=LogicFuncF(b,c,d);
			else if(round == 2) Logicbcd=LogicFuncG(b,c,d);
			else if(round == 3) Logicbcd=LogicFuncH(b,c,d);
			else Logicbcd=LogicFuncI(b,c,d);
			
			int result=LinkA+Logicbcd+M[k]+T[i];
			result=loopLeftShift(result,S[i%4]);
			result=result+b;
			
			if(i%4 == 0) LinkA=result;
			else if(i%4 == 1) LinkD=result;
			else if(i%4 == 2) LinkC=result;
			else LinkB=result;
		}
	}
	private static void bcdSet(int bb,int cc,int dd) {
		b=bb;
		c=cc;
		d=dd;
	}
	private static int LogicFuncF(int x,int y,int z) {
		return (x&y)|(~x&z);
	}
	private static int LogicFuncG(int x,int y,int z) {
		return (x&z)|(y&~z);
	}
	private static int LogicFuncH(int x,int y,int z) {
		return x^y^z;
	}
	private static int LogicFuncI(int x,int y,int z) {
		return y^(x&~z);
	}
	private static void resetData() {
		LinkA=0x67452301;
		LinkB=0xEFCDAB89;
		LinkC=0x98BADCFE;
		LinkD=0x10325476;

		M=new int[16];
	}
	private static void linkedSum() {
		LinkA+=OriginalLinkA;
		LinkB+=OriginalLinkB;
		LinkC+=OriginalLinkC;
		LinkD+=OriginalLinkD;
	}
	private static int loopLeftShift(int num,int bit) {
		//在java下实现循环左移的方法
		return (num<<bit)|(num>>(Integer.SIZE-bit));
	}
}
