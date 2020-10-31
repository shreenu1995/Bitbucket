package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DepotRegistrationResponseTest {

	DepotRegistrationResponse depotRegistrationResponse = new DepotRegistrationResponse();

	@Before
	public void setUp() {
		depotRegistrationResponse.setDepotId(TestConstants.ADMIN_USER_ID);
	}

	@Test
	public void testDepotRegistrationResponse() {
		Assert.assertEquals(TestConstants.ADMIN_USER_ID, depotRegistrationResponse.getDepotId());
	}
}
