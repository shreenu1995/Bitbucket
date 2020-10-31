package com.chatak.transit.afcs.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class Properties {

	public Properties() {
		// Do nothing
	}

	@Autowired
	private Environment env;

	public String getProperty(String key) {
		return env.getProperty(key);
	}
}
