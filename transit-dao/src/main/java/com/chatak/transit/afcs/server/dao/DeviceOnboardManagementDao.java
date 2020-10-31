package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.DeviceOnboardMaster;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;

public interface DeviceOnboardManagementDao {

	void saveDeviceOnboardingRegistrations(DeviceOnboardMaster deviceOnboardMaster);

	DeviceOnboardListSearchResponse getDeviceOnboardSearchList(DeviceOnboardSearchRequest request);

	boolean updateDeviceOnboardProfile(DeviceOnboardProfileUpdateRequest request);

	boolean validateDeviceOnboardStatusUpdate(DeviceOnboardProfileUpdateRequest request);

	DeviceOnboardMaster updateDeviceOnboardStatusUpdate(DeviceOnboardProfileUpdateRequest request);

	List<DeviceTypeSearchRequest> getDeviceTypeName();

	List<DeviceManufacturerSearchRequest> getDeviceManuName();

	List<DeviceModelRequest> getDeviceModelName();

	boolean validateDeviceModel(Long deviceModel);

}
