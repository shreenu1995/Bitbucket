package com.afcs.web.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	private StringUtils() {
		// Do Nothing
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List endIndex(int size, int index) {
		List list = new ArrayList();
		if (size == 0) {
			list.add(1);
		}
		for (int i = 1; i <= size / index; i++) {
			list.add((i * index));

		}
		return list;
	}

	public static String[] convertArray(String data) {
		if (!isNullEmpty(data)) {
			return data.split(",");
		}
		return new String[0];
	}

	public static boolean isNullEmpty(String input) {
		return (input == null || "".equals(input.trim()));
	}
}
