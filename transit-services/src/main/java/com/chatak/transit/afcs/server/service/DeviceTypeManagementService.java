package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceTypeManagementService {

	DeviceTypeRegistrationResponse deviceTypeRegistration(DeviceTypeRegistrationRequest request,
			DeviceTypeRegistrationResponse response, HttpServletResponse httpResponse) throws IOException;
	
	void validateDeviceTypeRegistrationErrors(DeviceTypeRegistrationRequest request,
			DeviceTypeRegistrationResponse response);
	
	DeviceTypeSearchResponse deviceTypeSearch(@Valid DeviceTypeSearchRequest request);
	
	WebResponse deviceTypeProfileUpdate(@Valid DeviceTypeProfileUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;
	
	DeviceTypeListViewResponse deviceTypeListView(DeviceTypeListViewRequest request,
			DeviceTypeListViewResponse response, HttpServletResponse httpResponse) throws IOException;

	WebResponse deviceTypeStatusuUpdate(DeviceTypeStatusUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse);

}
