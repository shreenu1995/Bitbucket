package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;

public interface DeviceTrackingService {

	DeviceTrackingResponse searchDeviceTracking(DeviceTrackingRequest request) throws AFCSException;
	
}
