package com.chatak.transit.afcs.server.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionUtil {
	private EncryptionUtil() {
		// Do nothing
	}

	/**
	 * Password Hashing and will be stored in DB
	 * 
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	public static boolean isPasswordMatches(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
