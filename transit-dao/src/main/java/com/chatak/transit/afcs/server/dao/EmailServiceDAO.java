package com.chatak.transit.afcs.server.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.model.EmailMaster;
import com.chatak.transit.afcs.server.pojo.EmailData;

@Service("emailServiceDAO")
public interface EmailServiceDAO {

	/**
	 * DAO Method to Save Email content in Database
	 * 
	 * @param emailData
	 * @return
	 */
	public boolean saveEmail(EmailData email);

	List<EmailMaster> getEmailList(EmailData emailData);
	
	EmailMaster updateEmail(EmailMaster email);

	EmailMaster getEmailData(EmailData emailData);

}
