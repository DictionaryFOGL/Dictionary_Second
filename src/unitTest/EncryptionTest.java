package unitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.util.Encryption;

public class EncryptionTest {

	@Test
	public void test1() {
		String pwd="GODBLESSME";
		String pwdMd5=Encryption.MD5(pwd);
		assertEquals("7C86E81BD86F396664D3AD62AF064277", pwdMd5);
	}

	@Test
	public void test2() {
		String pwd="fdas34522";
		String pwdMd5=Encryption.MD5(pwd);
		assertEquals("86F5D4BE9D9B4A469CC2648D75AB5F7B", pwdMd5);
	}

	@Test
	public void test3() {
		String pwd="1324gdDSDJLKASbm";
		String pwdMd5=Encryption.MD5(pwd);
		assertEquals("3EB4F94C0B72A92CAB89C981D2307E6A", pwdMd5);
	}

	@Test
	public void test4() {
		String pwd="c739rHKJ";
		String pwdMd5=Encryption.MD5(pwd);
		assertEquals("3EB4F94C0B72A92CAB89C981D2307E6A", pwdMd5);
	}

	@Test
	public void test5() {
		String pwd="a";
		String pwdMd5=Encryption.MD5(pwd);
		assertEquals("0CC175B9C0F1B6A831C399E269772661", pwdMd5);
	}

}
