package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.EmailMaster;
import com.chatak.transit.afcs.server.dao.repository.EmailServiceRepository;
import com.chatak.transit.afcs.server.enums.EmailStatus;
import com.chatak.transit.afcs.server.pojo.EmailData;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceDaoImplTest {

	@InjectMocks
	EmailServiceDaoImpl emailServiceDaoImpl;

	@Mock
	EmailServiceRepository emailRepository;

	@Mock
	EmailMaster email;

	@Mock
	EmailData emailData;

	@Test
	public void saveEmailTest() {
		emailData = new EmailData();
		emailData.setStatus(EmailStatus.PENDING);
		EmailMaster emailMaster = new EmailMaster();
		emailMaster.setEmailId(TestConstants.ID);
		emailMaster.setStatus(EmailStatus.PENDING.name());
		Mockito.when(emailRepository.saveAndFlush(email)).thenReturn(emailMaster);
		boolean response = emailServiceDaoImpl.saveEmail(emailData);
		Assert.assertEquals(true, response);
	}

	@Test
	public void saveEmailNullTest() {
		EmailMaster emailMaster = new EmailMaster();
		emailMaster.setStatus(EmailStatus.PENDING.name());
		emailMaster.setEmailId(TestConstants.ID);
		Mockito.when(emailRepository.saveAndFlush(email)).thenReturn(emailMaster);
		boolean response = emailServiceDaoImpl.saveEmail(null);
		Assert.assertFalse(response);
	}

	@Test
	public void getEmailListTest() {
		emailData = new EmailData();
		emailData.setStatus(EmailStatus.PENDING);
		EmailMaster emailMaster = new EmailMaster();
		emailMaster.setEmailId(TestConstants.ID);
		List<EmailMaster> list = new ArrayList<>();
		list.add(emailMaster);
		Mockito.when(emailRepository.findByStatus(Matchers.any(String.class))).thenReturn(list);
		List<EmailMaster> emailList = emailServiceDaoImpl.getEmailList(emailData);
		Assert.assertNotNull(emailList);
	}

	@Test
	public void getEmailData() {
		emailData = new EmailData();
		emailData.setEmailId(TestConstants.ID);
		EmailMaster master = new EmailMaster();
		master.setStatus(EmailStatus.SENT.name());
		Mockito.when(emailRepository.findByEmailId(Matchers.anyLong())).thenReturn(master);
		EmailMaster response = emailServiceDaoImpl.getEmailData(emailData);
		Assert.assertEquals(EmailStatus.SENT.name(), response.getStatus());
	}

	@Test
	public void updateEmailTest() {
		EmailMaster master = new EmailMaster();
		master.setStatus(EmailStatus.PENDING.name());
		master.setEmailAddress(TestConstants.ADDRESS);
		Mockito.when(emailRepository.save(email)).thenReturn(master);
		EmailMaster resonse = emailServiceDaoImpl.updateEmail(email);
		Assert.assertEquals(TestConstants.ADDRESS, resonse.getEmailAddress());
		Assert.assertEquals(EmailStatus.PENDING.name(), resonse.getStatus());
	}

	@Test
	public void updateEmailNullTest() {
		EmailMaster master = new EmailMaster();
		master.setStatus(EmailStatus.PENDING.name());
		master.setEmailAddress(TestConstants.ADDRESS);
		Mockito.when(emailRepository.save(email)).thenReturn(master);
		EmailMaster resonse = emailServiceDaoImpl.updateEmail(null);
		Assert.assertNull(resonse);
	}

}
