package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class ChangePasswordRequestTest {

	ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();

	@Before
	public void setUp() {
		changePasswordRequest.setCurrentPassword(TestConstants.PTO_ID);
		changePasswordRequest.setNewPassword(TestConstants.PTO_ID);
		changePasswordRequest.setUserId(TestConstants.USER_ID);
	}

	@Test
	public void testChangePasswordRequest() {
		Assert.assertEquals(TestConstants.PTO_ID, changePasswordRequest.getCurrentPassword());
		Assert.assertEquals(TestConstants.PTO_ID, changePasswordRequest.getNewPassword());
		Assert.assertEquals(TestConstants.USER_ID, changePasswordRequest.getUserId());
	}

}
