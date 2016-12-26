package org.ycwu.locale;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

public class TestLocale {

	
	@Test
	public void test() throws UnsupportedEncodingException {
		String s1=new String("ÖÐÎÄ");
		System.out.println( URLEncoder.encode(s1, "UTF8"));
		System.out.println( URLEncoder.encode(s1, "GBK"));
	 
	 
	}

}
