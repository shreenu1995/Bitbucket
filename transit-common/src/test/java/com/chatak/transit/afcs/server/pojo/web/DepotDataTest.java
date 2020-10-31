package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DepotDataTest {

	DepotData depotData = new DepotData();

	@Before
	public void setUp() {
		depotData.setDepotId(TestConstants.ID);
		depotData.setDepotName(TestConstants.DEPOT_NAME);
	}

	@Test
	public void testDepotData() {
		Assert.assertEquals(TestConstants.ID, depotData.getDepotId());
		Assert.assertEquals(TestConstants.DEPOT_NAME, depotData.getDepotName());
	}
}
