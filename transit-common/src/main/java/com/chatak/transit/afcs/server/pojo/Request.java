/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

public class Request implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
