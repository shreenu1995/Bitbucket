package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.DeviceTrackingService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceTrackingServiceImpl implements DeviceTrackingService{

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;
	
	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";
	
	@Override
	public DeviceTrackingResponse searchDeviceTracking(DeviceTrackingRequest request) throws AFCSException {
		DeviceTrackingResponse response = jsonUtil.postRequest(request, DeviceTrackingResponse.class, "deviceTracking/searchDeviceTracking");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}

		return response;
	}

}
