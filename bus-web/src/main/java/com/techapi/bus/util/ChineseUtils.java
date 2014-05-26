package com.techapi.bus.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-18
 */
public class ChineseUtils {

	public static String messyConvert(String code) {
		// 判断是否乱码
		boolean bool = Charset.forName("ISO-8859-1").newEncoder()
				.canEncode(code);
		// boolean bool = ChineseUtils.isMessyCode(code);
		if (bool) {
			try {
				code = new String(code.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return code;
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {

				if (!isChinese(c)) {
					count = count + 1;
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		String str = "äºæ´ªå¹¿åº";
		try {
			String code = new String(str.getBytes("ISO8859-1"), "UTF-8");
			System.out.println(code);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(java.nio.charset.Charset.forName("UTF-8")
				.newEncoder().canEncode("于洪广场"));
		System.out.println(java.nio.charset.Charset.forName("ISO-8859-1")
				.newEncoder().canEncode("äºæ´ªå¹¿åº"));
	}
}
