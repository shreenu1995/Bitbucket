package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class BlacklistedAccountResponse extends WebResponse implements Serializable  {

	private static final long serialVersionUID = -2253406980964865681L;

	private List<BlacklistedAccountRequest> blacklistedAccountList;
	
	private int noOfrecords;

	public List<BlacklistedAccountRequest> getBlacklistedAccountList() {
		return blacklistedAccountList;
	}

	public void setBlacklistedAccountList(List<BlacklistedAccountRequest> blacklistedAccountList) {
		this.blacklistedAccountList = blacklistedAccountList;
	}

	public int getNoOfrecords() {
		return noOfrecords;
	}

	public void setNoOfrecords(int noOfrecords) {
		this.noOfrecords = noOfrecords;
	}
}
