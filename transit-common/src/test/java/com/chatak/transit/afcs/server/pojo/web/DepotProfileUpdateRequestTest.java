package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DepotProfileUpdateRequestTest {

	DepotProfileUpdateRequest depotProfileUpdateRequest = new DepotProfileUpdateRequest();

	@Before
	public void setUp() {
		depotProfileUpdateRequest.setDepotId(TestConstants.LONG_ONE);
	}

	@Test
	public void testDepotProfileUpdateRequest() {
		Assert.assertEquals(Long.valueOf(TestConstants.LONG_ONE), depotProfileUpdateRequest.getDepotId());
	}
}
