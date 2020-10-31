package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.AFCSCommonResponse;
import com.chatak.transit.afcs.server.pojo.TransitLocationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;

public interface DeviceTrackMgmtService {

	DeviceTrackingResponse searchDeviceTracking(DeviceTrackingRequest request);

	AFCSCommonResponse updateLocation(TransitLocationUpdateRequest locationUpdateRequest);
}
