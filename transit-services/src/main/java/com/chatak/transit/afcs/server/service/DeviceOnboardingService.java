package com.chatak.transit.afcs.server.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceOnboardingService {


	WebResponse saveDeviceOnboarding(DeviceOnboardingRequest request, WebResponse response);

	DeviceOnboardListSearchResponse searchDeviceOnboarding(DeviceOnboardSearchRequest request,
			HttpServletResponse httpResponse);

	WebResponse updatDeviceOnboardProfile(DeviceOnboardProfileUpdateRequest request,
			WebResponse webResponse);
	
	DeviceOnboardSearchResponse updateDeviceOnBoardStatus(DeviceOnboardProfileUpdateRequest request);

	List<DeviceTypeSearchRequest> getDeviceTypeName();

	List<DeviceManufacturerSearchRequest> getDeviceManuName();

	List<DeviceModelRequest> getDeviceModelName();
}
