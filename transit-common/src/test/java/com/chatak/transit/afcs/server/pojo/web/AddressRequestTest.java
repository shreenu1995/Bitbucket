package com.chatak.transit.afcs.server.pojo.web;

import org.junit.Assert;
import org.junit.Test;

import com.chatak.transit.afcs.server.commonConstants.TestConstants;

public class AddressRequestTest {

	@Test
	public void testAddressRequest() {
		AddressRequest addressRequest = new AddressRequest();
		addressRequest.setAddress(TestConstants.ADDRESS);
		addressRequest.setCity(TestConstants.CITY);
		addressRequest.setCountry(TestConstants.ADDRESS);
		addressRequest.setDistrict(TestConstants.ADDRESS);
		addressRequest.setEmail(TestConstants.EMAIL);
		addressRequest.setMobile(TestConstants.MODEl_NUMBER);
		addressRequest.setPhoneNumber(TestConstants.MODEl_NUMBER);
		addressRequest.setPincode(TestConstants.PTO_ID);
		addressRequest.setState(TestConstants.CITY);
		Assert.assertEquals(TestConstants.ADDRESS, addressRequest.getAddress());
		Assert.assertEquals(TestConstants.CITY, addressRequest.getCity());
		Assert.assertEquals(TestConstants.ADDRESS, addressRequest.getCountry());
		Assert.assertEquals(TestConstants.ADDRESS, addressRequest.getDistrict());
		Assert.assertEquals(TestConstants.EMAIL, addressRequest.getEmail());
		Assert.assertEquals(TestConstants.MODEl_NUMBER, addressRequest.getMobile());
		Assert.assertEquals(TestConstants.MODEl_NUMBER, addressRequest.getPhoneNumber());
		Assert.assertEquals(TestConstants.PTO_ID, addressRequest.getPincode());
		Assert.assertEquals(TestConstants.CITY, addressRequest.getState());
	}
}
