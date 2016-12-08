package unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import application.util.ValidInput;

public class ValidInputTest {

	@Test
	public void testUserName1() {
		String s="你好  ";
		assertEquals(false, ValidInput.validUsername(s));
	}

	@Test
	public void testUserName2() {
		String s="f   ";
		assertEquals(false, ValidInput.validUsername(s));
	}

	@Test
	public void testUserName3() {
		String s="rewrgdf122";
		assertEquals(true, ValidInput.validUsername(s));
	}

	@Test
	public void testUserName4() {
		String s="sssssssssssssssssssssssss";
		assertEquals(false, ValidInput.validUsername(s));
	}

	@Test
	public void testUserName5() {
		String s="你1好s123";
		assertEquals(true, ValidInput.validUsername(s));
	}

	@Test
	public void testpwd1() {
		String s="q";
		assertEquals(false, ValidInput.validPwd(s));
	}

	@Test
	public void testpwd2() {
		String s="cShskel1222";
		assertEquals(true, ValidInput.validPwd(s));
	}

	@Test
	public void testpwd3() {
		String s="qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
		assertEquals(false, ValidInput.validPwd(s));
	}

	@Test
	public void testpwd4() {
		String s=" %^&ssss";
		assertEquals(false, ValidInput.validPwd(s));
	}

	@Test
	public void testpwd5() {
		String s="     ";
		assertEquals(false, ValidInput.validPwd(s));
	}

	@Test
	public void testfriendSearch1() {
		String s="%^&你2好%^&*^ z ";
		assertEquals("你2好z", ValidInput.friendSearchProcessed(s));
	}

	@Test
	public void testfriendSearch2() {
		String s="你好你好你好你好你1好你好你好你好你好你好你好";
		s=ValidInput.friendSearchProcessed(s);
		assertEquals(10, s.length());
		assertEquals("你好你好你好你好你1", s);
	}

	@Test
	public void testfriendSearch3() {
		String s=" $%^*(&*(^";
		assertEquals(null, ValidInput.friendSearchProcessed(s));
	}

	@Test
	public void testfriendSearch4() {
		String s="woshi122";
		assertEquals("woshi122", ValidInput.friendSearchProcessed(s));
	}

}
