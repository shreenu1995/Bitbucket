package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DevcieTypeList;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelList;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchData;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceOnboardService {

	DeviceOnboardingResponse saveDeviceOnboard(DeviceOnboardingRequest request) throws AFCSException;

	DeviceOnboardListSearchResponse searchDeviceOnboard(DeviceOnboardSearchRequest request) throws AFCSException;

	DeviceOnboardingResponse getDeviceTypeList(DeviceOnboardingRequest deviceOnboardingRequest) throws AFCSException;

	WebResponse updateDeviceOnboard(DeviceOnboardingRequest request) throws AFCSException;

	DeviceOnboardSearchResponse updateDeviceOnboardStatus(DeviceOnboardProfileUpdateRequest request) throws AFCSException;

	DeviceModelListResponse geteEuipmentModelListForManufacturer(DeviceModelSearchData deviceModelSearchData) throws AFCSException;

	DevcieTypeList getDeviceTypeList() throws AFCSException;

	DeviceManufacturerListResponse getDeviceManuf() throws AFCSException;

	DeviceModelList getDeviceModel() throws AFCSException;

}
