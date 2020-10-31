package com.chatak.transit.afcs.email.util;

import java.sql.Date;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chatak.transit.afcs.server.pojo.EmailProperties;

/**
 * Email client utility to send email
 */
public class EmailServiceClient {

	private static Logger logger = LoggerFactory.getLogger(EmailServiceClient.class);

	private static ThreadLocal<EmailServiceClient> threadLocal = ThreadLocal.withInitial(EmailServiceClient :: new);
		

	public static EmailServiceClient get() {
		return threadLocal.get();
	}

	/**
	 * Method to send Email
	 * 
	 * @param toAddress
	 * @param subject
	 * @param messageBody
	 */
	public boolean sendMail(String toAddress, String subject, String messageBody, EmailProperties emailProperties) {
		try {
			Authenticator authenticator = EmailServiceProviderUtil.getEmailAuthenticator(emailProperties.getUserName(),
					emailProperties.getPswd(), emailProperties.getSmptHost(), emailProperties.getSmtpStarttlsEnable(),
					emailProperties.getSmptPort());
			java.util.Properties properties = EmailServiceProviderUtil.getEmailMessageProperty();
			Session mailSession = Session.getInstance(properties, authenticator);
			Message email = new MimeMessage(mailSession);
			email.setFrom(new InternetAddress(emailProperties.getFromAddress()));
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			email.setSubject(subject);
			email.setSentDate(new Date(System.currentTimeMillis()));
			email.setContent(messageBody, org.springframework.http.MediaType.TEXT_HTML_VALUE);
			Transport.send(email);
			return true;
		} catch (Exception e) {
			logger.error("MAIL_ERROR", "Email sending failed - " + e.getMessage(), e);
		}
		return false;
	}

}
