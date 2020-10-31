package com.chatak.transit.afcs.email.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.chatak.transit.afcs.server.service.EmailService;

@Configuration
@EnableScheduling
public class EmailSchedulerTask {

	@Autowired
	private EmailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerTask.class);

	/**
	 * Method to process the Email sending
	 */
	@Scheduled(cron = "${afcs.scheduler.email.push.reminder.cron}")
	public void processSendEmailTask() {
		logger.info("EmailSchedulerTask :: processSendEmailTask method:: enter");
		emailService.sendMail();
		logger.info("EmailSchedulerTask :: processSendEmailTask method:: exit");
	}
}
