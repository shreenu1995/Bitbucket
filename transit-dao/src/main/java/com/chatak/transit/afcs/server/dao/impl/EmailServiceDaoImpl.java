/**
 * 
 */
package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.EmailServiceDAO;
import com.chatak.transit.afcs.server.dao.model.EmailMaster;
import com.chatak.transit.afcs.server.dao.repository.EmailServiceRepository;
import com.chatak.transit.afcs.server.pojo.EmailData;

@Repository("emailServiceDAO")
public class EmailServiceDaoImpl implements EmailServiceDAO {

	Logger logger = LoggerFactory.getLogger(EmailServiceDaoImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	EmailServiceRepository emailRepository;

	/**
	 * Method to Save Email content in Database
	 * 
	 * @param emailData
	 * @return
	 */
	public boolean saveEmail(EmailData emailData) {
		try {
			logger.info("EmailServiceDAOImpl :: saveEmail method :: enter");
			EmailMaster email = new EmailMaster();
			Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
			email.setEventType(emailData.getEventType());
			email.setSubject(emailData.getSubject());
			email.setEmailBody(emailData.getEmailBody());
			email.setEmailAddress(emailData.getEmailAddress());
			email.setStatus(emailData.getStatus().name());
			email.setScheduledDate(emailData.getScheduledDate());
			email.setCreatedDate(currentTime);
			email.setUpdatedDate(currentTime);
			emailRepository.saveAndFlush(email);
			return true;
		} catch (Exception e) {
			logger.error("EmailServiceDAOImpl :: saveEmail method", e);
		}
		logger.info("EmailServiceDAOImpl :: saveEmail method :: exit ");
		return false;
	}

	@Override
	public List<EmailMaster> getEmailList(EmailData emailData) {
		return emailRepository.findByStatus(emailData.getStatus().name());
	}
	
	@Override
	public EmailMaster getEmailData(EmailData emailData) {
		return emailRepository.findByEmailId(emailData.getEmailId());
	}

	@Override
	public EmailMaster updateEmail(EmailMaster email) {
		try {
			email.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
			return emailRepository.save(email);
		} catch (Exception e) {
			logger.error("EmailServiceDAOImpl :: updateEmail method", e);
		}
		return null;
	}

}
