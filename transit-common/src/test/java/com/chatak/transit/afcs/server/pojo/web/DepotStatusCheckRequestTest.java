package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DepotStatusCheckRequestTest {
	
	DepotStatusCheckRequest depotStatusCheckRequest = new DepotStatusCheckRequest();
	
	@Before
	public void setup() {
		depotStatusCheckRequest.setDepotId(TestConstants.ID);
	}
	
	@Test
	public void testDepotStatusCheckRequest() {
		Assert.assertEquals(TestConstants.ID, depotStatusCheckRequest.getDepotId());
	}
}
