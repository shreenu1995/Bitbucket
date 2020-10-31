package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class DeviceDetailsTest {
	
	DeviceDetails deviceDetails = new DeviceDetails();
	
	@Before
	public void setUp() {
		deviceDetails.setId(TestConstants.ID);
		deviceDetails.setDeviceId(TestConstants.DEVICE_ID);
		deviceDetails.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		deviceDetails.setModel(TestConstants.MODEL_NAME);
		deviceDetails.setDescription(TestConstants.DESCRIPTION);
		deviceDetails.setAssignedSoftwareVersion(TestConstants.SOFTWARE_VERSION);
		deviceDetails.setAdditionalSpecialData(TestConstants.SOFTWARE_VERSION);
		deviceDetails.setAssignedMasterVersion(TestConstants.SOFTWARE_VERSION);
		deviceDetails.setCreatedBy(TestConstants.CREATEDBY);
		deviceDetails.setUpdatedBy(TestConstants.UPDATED_BY);
	}
	
	@Test
	public void testDeviceDetails() {
		Assert.assertEquals(TestConstants.ID, deviceDetails.getId());
		Assert.assertEquals(TestConstants.DEVICE_ID, deviceDetails.getDeviceId());
		Assert.assertEquals(TestConstants.PTO_OPERATION_ID, deviceDetails.getPtoOperationId());
		Assert.assertEquals(TestConstants.MODEL_NAME, deviceDetails.getModel());
		Assert.assertEquals(TestConstants.DESCRIPTION, deviceDetails.getDescription());
		Assert.assertEquals(TestConstants.SOFTWARE_VERSION, deviceDetails.getAssignedSoftwareVersion());
		Assert.assertEquals(TestConstants.SOFTWARE_VERSION, deviceDetails.getAdditionalSpecialData());
		Assert.assertEquals(TestConstants.SOFTWARE_VERSION, deviceDetails.getAssignedMasterVersion());
		Assert.assertEquals(TestConstants.CREATEDBY, deviceDetails.getCreatedBy());
		Assert.assertEquals(TestConstants.UPDATED_BY, deviceDetails.getUpdatedBy());
	}
}
