package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.DeviceManufacturerManagementService;
import com.afcs.web.util.JsonUtil;
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
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeData;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceManufacturerManagementServiceImpl implements DeviceManufacturerManagementService {
	
	@Autowired
	JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public DeviceManufacturerRegistrationResponse deviceManufacturerRegistration(
			DeviceManufacturerRegistrationRequest request) {
		return jsonUtil.postRequest(request, DeviceManufacturerRegistrationResponse.class, "online/device/deviceManufacturerRegistration");
	}

	@Override
	public DeviceManufacturerListSearchResponse deviceManufacturerSearch(DeviceManufacturerSearchRequest request)
			throws AFCSException {
		DeviceManufacturerListSearchResponse response = jsonUtil.postRequest(request, DeviceManufacturerListSearchResponse.class,
				"online/device/searchDeviceManufacturer");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;		
	}

	@Override
	public DeviceTypeListViewResponse deviceTypeList(DeviceTypeListViewRequest deviceTypeListViewRequest) throws AFCSException {
		DeviceTypeListViewResponse response = jsonUtil.postRequest(deviceTypeListViewRequest, DeviceTypeListViewResponse.class, "online/device/deviceTypeList");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;	}

	@Override
	public WebResponse updateDeviceManufacturer(DeviceManufacturerProfileUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"online/device/deviceManufacturerProfileUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateDeviceManufacturereStatus(DeviceManufacturerStatusUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/device/deviceManufacturerStatusUpdate");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public DeviceModelResponse deviceTypeList(DeviceModelRequest deviceModelRequest) throws AFCSException {
		DeviceModelResponse response = jsonUtil.postRequest(deviceModelRequest, DeviceModelResponse.class, "online/device/deviceTypeList");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;	}

	@Override
	public DeviceManuFacturerListViewResponse geteEuipmentManufacturerListForEquipmetType(DeviceManufacturerSearchData deviceManufacturerSearchData) throws AFCSException {
		      DeviceTypeData request = new DeviceTypeData();
		      request.setDeviceTypeId(deviceManufacturerSearchData.getDeviceTypeId());
		      DeviceManuFacturerListViewResponse response = jsonUtil.postRequest(deviceManufacturerSearchData, DeviceManuFacturerListViewResponse.class, "online/device/deviceManufacturerList");
		      if(StringUtil.isNull(response)) {
					throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
				}
		      return response;	
	}

}
