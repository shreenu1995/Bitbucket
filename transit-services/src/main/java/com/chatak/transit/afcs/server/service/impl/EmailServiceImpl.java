package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.email.util.EmailServiceClient;
import com.chatak.transit.afcs.email.util.EmailThreadLocal;
import com.chatak.transit.afcs.server.dao.EmailServiceDAO;
import com.chatak.transit.afcs.server.dao.model.EmailMaster;
import com.chatak.transit.afcs.server.enums.EmailStatus;
import com.chatak.transit.afcs.server.pojo.EmailData;
import com.chatak.transit.afcs.server.pojo.EmailProperties;
import com.chatak.transit.afcs.server.service.EmailService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {
	
	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private EmailServiceDAO emailServiceDAO;
	
	@Value("${afcs.email.from.address}")
	private String fromAddress;

	@Value("${afcs.email.username}")
	private String userName; 

	@Value("${afcs.email.password}")
	private String pswd;

	@Value("${mail.smtp.host}")
	private String smptHost;

	@Value("${mail.smtp.starttls.enable}")
	private String smtpStarttlsEnable;

	@Value("${mail.smtp.port}")
	private String smptPort;
	

	@Override
	@Async
	public void saveMail(String toAddress, String subject, String messageBody, String emailEventType) {
		EmailData emailData = new EmailData();
		emailData.setEmailAddress(toAddress);
		emailData.setSubject(subject);
		emailData.setEventType(emailEventType);
		emailData.setEmailBody(messageBody.getBytes());
		emailData.setStatus(EmailStatus.PENDING);
		emailServiceDAO.saveEmail(emailData);
	}

	@Override
	public synchronized void sendMail() {
		try {
			EmailData emailData = new EmailData();
			emailData.setStatus(EmailStatus.PENDING);
			List<EmailMaster> emailDatas = emailServiceDAO.getEmailList(emailData);
			if (!StringUtil.isListNullNEmpty(emailDatas)) {
				EmailData data = null;
				for (EmailMaster emailDataDB : emailDatas) {
					emailDataDB.setStatus(EmailStatus.INPROGRESS.name());
					emailDataDB = emailServiceDAO.updateEmail(emailDataDB);
					data = new EmailData();
					data.setEmailId(emailDataDB.getEmailId());
					data.setEventType(emailDataDB.getEventType());
					data.setSubject(emailDataDB.getSubject());
					data.setEmailBody(emailDataDB.getEmailBody());
					data.setEmailAddress(emailDataDB.getEmailAddress());
					data.setScheduledDate(emailDataDB.getScheduledDate());
					data.setCreatedDate(emailDataDB.getCreatedDate());
					data.setUpdatedDate(emailDataDB.getUpdatedDate());
					
					EmailProperties emailProperties = new EmailProperties();
					emailProperties.setFromAddress(fromAddress);
					emailProperties.setPswd(pswd);
					emailProperties.setSmptHost(smptHost);
					emailProperties.setSmptPort(smptPort);
					emailProperties.setSmtpStarttlsEnable(smtpStarttlsEnable);
					emailProperties.setUserName(userName);
					sendEmail(data, emailProperties);
				}
			}
		} catch (Exception e) {
			logger.error("EmailServiceImpl class :: sendMail method :: Exception ",e);
		}
	}

	@Async
	public void sendEmail(EmailData emailData, EmailProperties emailProperties) {
		try {
			emailData.setStatus(EmailStatus.FAILURE);
			if (!StringUtil.isNullEmpty(emailData.getEmailAddress()) && null != emailData.getEmailBody()) {
				// Sending an Email

				boolean isSuccess = ((EmailServiceClient) EmailThreadLocal.get()).sendMail(emailData.getEmailAddress(),
						emailData.getSubject(), new String(emailData.getEmailBody()), emailProperties);
				// Updating email status
				if (isSuccess) {
					emailData.setStatus(EmailStatus.SENT);
				} 
			}
		} catch (Exception e) {
			logger.error("EmailServiceImpl class :: sendEmail method :: Exception ",e);
		}
		EmailMaster email = emailServiceDAO.getEmailData(emailData);
		if (!StringUtil.isNull(email)) {
			email.setStatus(emailData.getStatus().name());
			email.setEventType(emailData.getEventType());
			email.setEmailAddress(emailData.getEmailAddress());
			emailServiceDAO.updateEmail(email);
		}
	}
}
