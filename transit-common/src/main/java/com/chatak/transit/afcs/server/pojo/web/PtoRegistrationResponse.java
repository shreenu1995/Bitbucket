package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class PtoRegistrationResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -2657575205815437113L;

	private String ptoId;

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

}
