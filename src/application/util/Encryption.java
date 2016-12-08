package application.util;

public class Encryption {
	private static long OriginalLinkA;
	private static long OriginalLinkB;
	private static long OriginalLinkC;
	private static long OriginalLinkD;

	private static long LinkA=0x67452301L;
	private static long LinkB=0xEFCDAB89L;
	private static long LinkC=0x98BADCFEL;
	private static long LinkD=0x10325476L;
	
//	private static long LinkA=0x01234567;
//	private static long LinkB=0x89ABCDEF;
//	private static long LinkC=0xFEDCBA98;
//	private static long LinkD=0x76543210;
	
	private static long b,c,d;
	
	private static long[] M=new long[16];
	public static String MD5(String clearText) {
		groupedclearText(clearText);
		Round1();
		Round2();
		Round3();
		Round4();
		linkedSum();
		int A=(int) LinkA;
		int B=(int) LinkB;
		int C=(int) LinkC;
		int D=(int) LinkD;
		String result=Integer.toHexString(A)+Integer.toHexString(B)+Integer.toHexString(C)+Integer.toHexString(D);
		resetData();
		return result;
	}
	private static void groupedclearText(String clearText) {
		long length=clearText.length()*8;
		byte[] basic=clearText.getBytes();
		StringBuffer binaryComplete=new StringBuffer();
		StringBuffer binary64=new StringBuffer(Long.toBinaryString(length));
		for(byte b:basic) {
			StringBuffer BYTE=new StringBuffer(Long.toBinaryString(b));
			while(BYTE.length() < 8)
				BYTE.insert(0, '0');
			binaryComplete.append(BYTE);
		}
		if(binaryComplete.length() <= 448) binaryComplete.append('1');
		for(int i=binaryComplete.length();i<448;i++)
			binaryComplete.append('0');
		for(int i=binary64.length();i<64;i++)
			binary64.insert(0, '0');
		String binaryHIGH=binary64.substring(0,32);
		String binaryLOW=binary64.substring(32,64);
		binary64=new StringBuffer(LittleEndian(binaryHIGH)+LittleEndian(binaryLOW));
		binaryComplete.append(binary64);
		for(int i=0;i<16;i++) {
			int start=i*8;
			String subClearTextString=binaryComplete.substring(start,start+32);
			//subClearTextString=LittleEndian(subClearTextString);
			long subClearText=Long.valueOf(subClearTextString,2);
			M[i]=subClearText;
		}
	}
	private static void Round1() {
		long[] T={0xD76AA478,0xE8C7B756,0x242070DB,0xC1BDCEEE,0xF57C0FAF,0x4787C62A,0xA8304613,0xFD469501,0x698098D8,0x8B44F7AF,0xFFFF5BB1,0x895CD7BE,0x6B901122,0xFD987193,0xA679438E,0x49B40821};
		long[] S={7,12,17,22};
		OriginalLinkA=LinkA;
		OriginalLinkB=LinkB;
		OriginalLinkC=LinkC;
		OriginalLinkD=LinkD;
		RoundBase(T,S,1);
	}
	private static void Round2() {
		long[] T={0xF61E2562,0xC040B340,0x265E5A51,0xE9B6C7AA,0xD62F105D,0x02441453,0xD8A1E681,0xE7D3FBC8,0x21E1CDE6,0xC33707D6,0xF4D50D87,0x455A14ED,0xA9E3E905,0xFCEEA3F8,0x676F02D9,0x8D2A4C8A};
		long[] S={5,9,14,20};
		RoundBase(T,S,2);
	}
	private static void Round3() {
		long[] T={0xFFFA3942,0x8771F681,0x699D6122,0xFDE5380C,0xA4BEEA44,0x4BDECFA9,0xF6BB4B60,0xBEBFBC70,0x289B7EC6,0xEAA127FA,0xD4EF3085,0x04881D05,0xD9D4D039,0xE6DB99E5,0x1FA27CF8,0xC4AC5665};
		long[] S={4,11,16,23};
		RoundBase(T,S,3);
	}
	private static void Round4() {
		long[] T={0xF4292244,0x432AFF97,0xAB9423A7,0xFC93A039,0x655B59C3,0x8F0CCC92,0xFFEFF47D,0x85845DD1,0x6FA87E4F,0xFE2CE6E0,0xA3014314,0x4E0811A1,0xF7657E82,0xBD3AF235,0x2AD7D2BB,0xEB86D391};
		long[] S={6,10,15,21};
		RoundBase(T,S,4);
	}
	private static String LittleEndian(String element) {
		String first=element.substring(0,8);
		String second=element.substring(8,16);
		String third=element.substring(16,24);
		String forth=element.substring(24,32);
		return forth+third+second+first;
	}
	private static void RoundBase(long[] T,long[] S,long round) {
		for(int i=0;i<16;i++) {
			int k=i;
			
			if(i%4 == 0) bcdSet(LinkB,LinkC,LinkD);
			else if(i%4 == 1) bcdSet(LinkA,LinkB,LinkC);
			else if(i%4 == 2) bcdSet(LinkD,LinkA,LinkB);
			else bcdSet(LinkC,LinkD,LinkA);
			
			long Logicbcd;
			if(round == 1) Logicbcd=LogicFuncF(b,c,d);
			else if(round == 2) Logicbcd=LogicFuncG(b,c,d);
			else if(round == 3) Logicbcd=LogicFuncH(b,c,d);
			else Logicbcd=LogicFuncI(b,c,d);
			
			long result=LinkA+Logicbcd+M[k]+T[i];
			result=loopLeftShift(result,S[i%4]);
			result=result+b;
			
			if(i%4 == 0) LinkA=result;
			else if(i%4 == 1) LinkD=result;
			else if(i%4 == 2) LinkC=result;
			else LinkB=result;
		}
	}
	private static void bcdSet(long bb,long cc,long dd) {
		b=bb;
		c=cc;
		d=dd;
	}
	private static long LogicFuncF(long x,long y,long z) {
		return (x&y)|((~x)&z);
	}
	private static long LogicFuncG(long x,long y,long z) {
		return (x&z)|(y&(~z));
	}
	private static long LogicFuncH(long x,long y,long z) {
		return x^y^z;
	}
	private static long LogicFuncI(long x,long y,long z) {
		return y^(x&(~z));
	}
	private static void resetData() {
		LinkA=0x67452301;
		LinkB=0xEFCDAB89;
		LinkC=0x98BADCFE;
		LinkD=0x10325476;

		M=new long[16];
	}
	private static void linkedSum() {
		LinkA+=OriginalLinkA;
		LinkB+=OriginalLinkB;
		LinkC+=OriginalLinkC;
		LinkD+=OriginalLinkD;
	}
	private static long loopLeftShift(long result,long bit) {
		//±ê×¼Ñ­»·×óÒÆ
		return ((int) result<<bit)|((int) result>>(Long.SIZE-bit));
	}
	public static void main(String[] args) {
		String s="";
		s=MD5(s);
//		int s=0x12345678;
//		String ss=Integer.toBinaryString(s);
//		ss=LittleEndian(ss);
//		s=Integer.valueOf(ss, 2);
		System.out.print(s);
	}
}
