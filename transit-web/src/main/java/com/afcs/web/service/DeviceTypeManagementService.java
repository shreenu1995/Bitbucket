package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceTypeManagementService {

	DeviceTypeRegistrationResponse saveDeviceType(DeviceTypeRegistrationRequest request);

	DeviceTypeSearchResponse searchDeviceType(DeviceTypeSearchRequest request) throws AFCSException;

	WebResponse updateDeviceType(DeviceTypeProfileUpdateRequest request) throws AFCSException;

	WebResponse updateDeviceTypeStatus(DeviceTypeStatusUpdateRequest request)throws AFCSException;

}
