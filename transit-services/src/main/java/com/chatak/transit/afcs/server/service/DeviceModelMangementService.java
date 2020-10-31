package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DeviceModelMangementService {

	DeviceModelResponse saveDeviceModel(DeviceModelRequest request, DeviceModelResponse response,
			HttpServletResponse httpResponse) throws IOException;

	DeviceModelListSearchResponse searchDeviceModel(DeviceModelSearchRequest request,
			HttpServletResponse httpResponse);

	WebResponse deviceModelProfileUpdate(DeviceModelProfileUpdateRequest request, WebResponse webResponse,
			HttpServletResponse httpResponse) throws IOException;

	DeviceModelListSearchResponse updateDeviceModelStatus(DeviceModelStatusUpdateRequest request, WebResponse response);

	DeviceModelListResponse deviceModelList(DeviceModelSearchRequest request);

}
