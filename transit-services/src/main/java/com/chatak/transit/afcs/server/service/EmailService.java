package com.chatak.transit.afcs.server.service;

public interface EmailService {
	public void saveMail(final String toAddress, final String subject, final String messageBody,
			final String emailEventType);

	void sendMail();
}
