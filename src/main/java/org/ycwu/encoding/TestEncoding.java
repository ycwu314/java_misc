package org.ycwu.encoding;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

public class TestEncoding {

	@Test
	public void test() {

		// using high low surrogate to represent 4 bytes unicode
		char[] relievedFace = Character.toChars(0x1F60C); // Relieved face
		final String s = new String(relievedFace);
		int byteLength = s.getBytes(StandardCharsets.UTF_8).length;
		String escape = StringEscapeUtils.escapeJava(s);

		System.out.println(s);

		// stored as 2 chars, using high low surrogate to represent 4 bytes
		// unicode, it is somehow anti-human...
		System.out.println("string length:" + s.length());

		// 2 unicode chars of 2 bytes each, so totally 4 bytes
		System.out.println("byte length:" + byteLength);
		System.out.println("escape in java:" + escape);

		// \\uXXXX only support 4 hex letters, so recognized as 2 chars, see the
		// 'C'
		String s2 = "\u1F60C";
		System.out.println(s2);
	}

}
