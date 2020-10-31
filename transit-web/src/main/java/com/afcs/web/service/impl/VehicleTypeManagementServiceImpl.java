package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.VehicleTypeManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class VehicleTypeManagementServiceImpl implements VehicleTypeManagementService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public WebResponse saveVehicleTypeRegistration(VehicleTypeRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/saveVehicleType");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleTypeSearchResponse vehicleTypeSearch(VehicleTypeSearchRequest request) throws AFCSException {
		VehicleTypeSearchResponse response = jsonUtil.postRequest(request, VehicleTypeSearchResponse.class,
				"online/searchVehicleType");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public WebResponse updateVehicleTypeProfile(VehicleTypeStatusUpdate request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request,WebResponse.class, "/online/vehicleTypeProfileUpdate");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateVehicleTypeStatus(VehicleTypeStatusUpdate request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "/online/vehicleTypeStatusUpdate");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
