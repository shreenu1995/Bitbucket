package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class CommonUserParameterTest {

	CommonUserParameter commonUserParameter = new CommonUserParameter();

	@Before
	public void setUp() {
		commonUserParameter.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		commonUserParameter.setReservedResponse(TestConstants.ADMIN_SESSION_REQUEST);
		commonUserParameter.setStatusCode(TestConstants.STATUS_CODE);
		commonUserParameter.setStatusMessage(TestConstants.STATUS_MESSAGE);
		commonUserParameter.setUserId(TestConstants.USER_ID);
	}

	@Test
	public void testCommonUserParameter() {
		Assert.assertEquals(TestConstants.PTO_OPERATION_ID, commonUserParameter.getPtoOperationId());
		Assert.assertEquals(TestConstants.ADMIN_SESSION_REQUEST, commonUserParameter.getReservedResponse());
		Assert.assertEquals(TestConstants.STATUS_CODE, commonUserParameter.getStatusCode());
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, commonUserParameter.getStatusMessage());
		Assert.assertEquals(TestConstants.USER_ID, commonUserParameter.getUserId());

	}
}
