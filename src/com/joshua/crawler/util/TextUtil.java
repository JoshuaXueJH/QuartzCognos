package com.joshua.crawler.util;

/**
 * 
 * @author XueJianhua
 *
 */
public class TextUtil {
	public static boolean isEmpty(String str) {
		if (str == null || str.equals("") || str.length() == 0)
			return true;
		return false;
	}
}
