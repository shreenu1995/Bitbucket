package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DepotRegistrationRequestTest extends AddressRequestTest {

	DepotRegistrationRequest depotRegistrationRequest = new DepotRegistrationRequest();

	@Before
	public void setUp() {
		depotRegistrationRequest.setDepotName(TestConstants.DEPOT_NAME);
		depotRegistrationRequest.setUserId(TestConstants.USER_ID);
	}

	@Test
	public void testDepotRegistrationRequest() {
		Assert.assertEquals(TestConstants.DEPOT_NAME, depotRegistrationRequest.getDepotName());
		Assert.assertEquals(TestConstants.USER_ID, depotRegistrationRequest.getUserId());
	}
}
