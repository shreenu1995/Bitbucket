package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceManuFacturerListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchData;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceManufacturerManagementService {

	DeviceManufacturerRegistrationResponse deviceManufacturerRegistration(
			DeviceManufacturerRegistrationRequest request);

	DeviceManufacturerListSearchResponse deviceManufacturerSearch(DeviceManufacturerSearchRequest request)throws AFCSException;

	DeviceTypeListViewResponse deviceTypeList(DeviceTypeListViewRequest deviceTypeListViewRequest)
			throws AFCSException;

	WebResponse updateDeviceManufacturer(DeviceManufacturerProfileUpdateRequest request) throws AFCSException;

	WebResponse updateDeviceManufacturereStatus(DeviceManufacturerStatusUpdateRequest request) throws AFCSException;

	DeviceModelResponse deviceTypeList(DeviceModelRequest deviceModelRequest)throws AFCSException;

	DeviceManuFacturerListViewResponse geteEuipmentManufacturerListForEquipmetType(DeviceManufacturerSearchData deviceManufacturerSearchData) throws AFCSException;

}
