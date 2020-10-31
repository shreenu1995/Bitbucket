package com.chatak.transit.afcs.server.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public final class StringUtil {

	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	private static final String TOKEN_DELIMITER = "@@";

	private static final Pattern alphaPattern = Pattern.compile(".*[a-zA-Z0-9]+.*");

	private StringUtil() {
		// Do nothing
	}

	public static boolean isNull(Object input) {
		return (input == null);
	}

	public static boolean isNull(Object[] input) {
		return (input == null || input.length == 0);
	}

	public static boolean isNullEmpty(String input) {
		return (input == null || input.trim().isEmpty());
	}

	public static boolean isListNotNullNEmpty(List list) {
		return (list != null && !list.isEmpty());
	}

	public static boolean isListNullNEmpty(List list) {
		return (list == null || list.isEmpty());
	}

	public static boolean isArrayNullNEmpty(String[] array) {
		if (array != null) {
				return isListNullNEmpty(Arrays.asList(array));
		}
		return false;
	}

	public static String getEmailToken(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append(email);
		sb.append(TOKEN_DELIMITER);
		sb.append(System.currentTimeMillis());
		String token = null;
		try {
			token = Base64.encodeBase64URLSafeString(sb.toString().getBytes());
		} catch (Exception e) {
			logger.error("StringUtil class :: getEmailToken method :: exception", e);
		}
		return token;
	}

	public static String[] parseEmailToken(String emailToken, String tokenExpireTime) {

		if (isNullEmpty(emailToken)) {
			throw new IllegalArgumentException();
		}
		String token = null;
		try {
			token = new String(Base64.decodeBase64(emailToken.getBytes()));
		} catch (Exception e) {
			logger.error("StringUtil :: parseEmailToken method :: exception ", e);
			throw new IllegalArgumentException();
		}
		if (token.contains(TOKEN_DELIMITER)) {
			String[] emailTokens = token.split(TOKEN_DELIMITER);
			String tokeTime = emailTokens[emailTokens.length - 1];
			int minutes = Integer.parseInt(tokenExpireTime);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Long.valueOf(tokeTime));
			cal.add(Calendar.MINUTE, minutes);
			Calendar now = Calendar.getInstance();
			if (now.after(cal)) {
				throw new IllegalArgumentException();
			}
			return emailTokens;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method to escape underscore character in query string
	 * 
	 * @param rawStr
	 * @return
	 */
	public static String escapeUnderscoreQuery(String rawStr) {
		if (rawStr.contains("_") && !alphaPattern.matcher(rawStr).matches()) {
			return rawStr.replaceAll("_", "!_");
		}
		return rawStr;
	}
}
