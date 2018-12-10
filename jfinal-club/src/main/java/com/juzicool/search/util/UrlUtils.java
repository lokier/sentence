package com.juzicool.search.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {
	
	public static String encodeUtf8(String value) {
		try {
			return URLEncoder.encode(value, "utf8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
