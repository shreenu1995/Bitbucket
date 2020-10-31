package com.chatak.transit.afcs.server.dao;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.DeviceManufacturerMaster;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;

public interface DeviceManufacturerManagementDao {

	boolean validateDeviceManufacturerRegistrationRequest(@Valid DeviceManufacturerRegistrationRequest request);

	Long saveDeviceManufacturerRegistrationDetails(DeviceManufacturerMaster deviceManufacturerMaster);

	DeviceManufacturerListSearchResponse searchEquipementManufacturer(
			DeviceManufacturerSearchRequest request);

	DeviceTypeListViewResponse getDeviceTypeList(DeviceTypeListViewRequest request);

	boolean validateDeviceManufacturerProfileUpdate(DeviceManufacturerProfileUpdateRequest request);

	boolean updateDeviceManufacturerProfile(@Valid DeviceManufacturerProfileUpdateRequest request);

	boolean validateDeviceManufacturerStatusUpdate(DeviceManufacturerStatusUpdateRequest request);

	boolean updateDeviceManufacturerStatus(DeviceManufacturerMaster deviceManufacturerMaster);

	DeviceManufacturerListSearchResponse getDeviceManufacturerList(DeviceManufacturerListViewRequest request);
}
