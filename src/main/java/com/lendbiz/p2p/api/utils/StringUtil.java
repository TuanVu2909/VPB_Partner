package com.lendbiz.p2p.api.utils;

public class StringUtil {

	public static boolean isEmty(String input) {
		if (input == null || input.isEmpty())
			return true;
		return false;
	}

	public static String removeSpaces(String str) {
		str = str.replaceAll("\\s\\s+", " ").trim();
		return str;
	}

}
