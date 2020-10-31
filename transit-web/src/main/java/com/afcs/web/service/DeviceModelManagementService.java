package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceModelManagementService {

	DeviceModelResponse deviceModelRegistration(DeviceModelRequest request) throws AFCSException;

	DeviceModelListSearchResponse searchDeviceModel(DeviceModelSearchRequest request) throws AFCSException;
	
	WebResponse updateDeviceModel(DeviceModelProfileUpdateRequest request) throws AFCSException;

	DeviceModelListSearchResponse updateDeviceModelStatus(DeviceModelStatusUpdateRequest request) throws AFCSException;

}
