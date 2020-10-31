package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.DeviceTracking;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;

public interface DeviceTrackingDao {

	public DeviceTrackingResponse searchDeviceTracking(DeviceTrackingRequest request);
	
	public void saveDeviceTrackingdetails(DeviceTracking deviceTracking);
	
	public DeviceTracking updateLocation(DeviceTracking deviceTracking);
}
