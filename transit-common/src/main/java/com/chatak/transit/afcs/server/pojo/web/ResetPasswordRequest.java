package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {
	
	private static final long serialVersionUID = 1549484126326223269L;
	
	private String newPassword;
	
	private String conformNewPassword;
	
	private String emailToken;

	public String getNewPassword () {
		return newPassword;
	}

	public void setNewPassword (String newPswd) {
		this.newPassword = newPswd;
	}

	public String getConformNewPassword () {
		return conformNewPassword;
	}

	public void setConformNewPassword (String conformNewPswd) {
		this.conformNewPassword = conformNewPswd;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("emailToken=");
		builder.append(emailToken);
		return builder.toString();
	}

}
