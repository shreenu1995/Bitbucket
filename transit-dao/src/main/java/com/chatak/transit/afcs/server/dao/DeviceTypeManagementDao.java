package com.chatak.transit.afcs.server.dao;

import java.util.List;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;

public interface DeviceTypeManagementDao {

	boolean validateDeviceTypeRegistrationRequest(DeviceTypeRegistrationRequest request);
	
	Long saveDeviceTypeRegistrationDetails(DeviceTypeMaster request);

	boolean updateDeviceTypeProfile(@Valid DeviceTypeProfileUpdateRequest request);
	
	List<DeviceTypeMaster> getDeviceTypeListView(DeviceTypeListViewRequest request);
	
	boolean validateDeviceTypeStatusUpdate(@Valid DeviceTypeStatusUpdateRequest request);
	
	boolean updateDeviceTypeStatus(@Valid DeviceTypeStatusUpdateRequest request);

	boolean validateDeviceTypeProfileUpdate(DeviceTypeProfileUpdateRequest request);

	DeviceTypeSearchResponse searchDeviceType(@Valid DeviceTypeSearchRequest request);

	boolean validateDeviceTypeListView(DeviceTypeListViewRequest request);

}