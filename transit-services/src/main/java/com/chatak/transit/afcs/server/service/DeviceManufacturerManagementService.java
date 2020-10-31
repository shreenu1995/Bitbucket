package com.chatak.transit.afcs.server.service;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceManufacturerManagementService {

	public DeviceManufacturerRegistrationResponse deviceManufacturerRegistration(
			@Valid DeviceManufacturerRegistrationRequest request, DeviceManufacturerRegistrationResponse response,
			HttpServletResponse httpResponse) ;

	public DeviceManufacturerListSearchResponse searchDeviceManufacturer(
			@Valid DeviceManufacturerSearchRequest request);

	public DeviceTypeListViewResponse deviceTypeList(DeviceTypeListViewRequest request);

	public WebResponse deviceManufacturerProfileUpdate(@Valid DeviceManufacturerProfileUpdateRequest request,
			WebResponse response, HttpServletResponse httpResponse);

	public WebResponse deviceManufacturerStatusuUpdate(@Valid DeviceManufacturerStatusUpdateRequest request,
			WebResponse response, HttpServletResponse httpResponse);

	public DeviceManufacturerListSearchResponse deviceManufacturerList(DeviceManufacturerListViewRequest request);
}
