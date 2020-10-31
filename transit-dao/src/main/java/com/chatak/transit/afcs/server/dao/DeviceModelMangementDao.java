package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;

public interface DeviceModelMangementDao {

	Long saveDeviceModelDetails(DeviceModel request);

	DeviceModelListSearchResponse getEquipementModelSearchList(DeviceModelSearchRequest request);

	boolean validateDeviceModel(DeviceModelRequest request);

	boolean validateDeviceModelProfileUpdate(DeviceModelProfileUpdateRequest request);

	boolean updateDeviceModelProfile(DeviceModelProfileUpdateRequest request);

	boolean validateDeviceModelStatusUpdate(DeviceModelStatusUpdateRequest request);

	DeviceModel updateDeviceModelStatusUpdate(DeviceModelStatusUpdateRequest request);

	DeviceModelListResponse getDeviceModelList(DeviceModelSearchRequest request);
	
	boolean findDeviceByDeviceId(Long deviceId);
}
