package com.chatak.transit.afcs.server.util;

import java.security.SecureRandom;

/**
 * Utility class used for handling password, i.e. password encryption,
 * decryption password.
 */
public final class PasswordHandler {
	private static final int PASS_MIN_LENGTH = 4;
	private static final int PASS_MAX_LENGTH = 8;

	private PasswordHandler() {
		// Do nothing
	}

	/**
	 * Method to get System Generated Password
	 * 
	 * @return
	 */
	public static String getSystemGeneratedPassword(int length) {
		final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final String specialChars = "@!$";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			if (i == PASS_MIN_LENGTH || i == PASS_MAX_LENGTH) {
				sb.append(specialChars.charAt(rnd.nextInt(specialChars.length())));
			} else {
				sb.append(chars.charAt(rnd.nextInt(chars.length())));
			}
		}
		return sb.toString();
	}

}