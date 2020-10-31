package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DepotListViewRequestTest {

	DepotListViewRequest depotListViewRequest = new DepotListViewRequest();

	@Before
	public void setUp() {
		depotListViewRequest.setUserId(TestConstants.USER_ID);
	}

	@Test
	public void testdepotListViewRequest() {
		Assert.assertEquals(TestConstants.USER_ID, depotListViewRequest.getUserId());
	}
}
