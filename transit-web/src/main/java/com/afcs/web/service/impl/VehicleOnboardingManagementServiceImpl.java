package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.VehicleOnboardingManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class VehicleOnboardingManagementServiceImpl implements VehicleOnboardingManagementService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public WebResponse saveVehicleOnboardRegistration(VehicleOnboardingRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/saveVehicleOnBoarding");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleModelListResponse getVehicleModelByManufacturerName(VehicleModelSearchRequest vehicleModelSearchRequest)
			throws AFCSException {
		VehicleModelListResponse response = jsonUtil.postRequest(vehicleModelSearchRequest,
				VehicleModelListResponse.class, "online/getVehicleModelByManufacturerName");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleOnboardingSearchResponse vehicleOnboardSearch(VehicleOnboardingSearchRequest request)
			throws AFCSException {
		VehicleOnboardingSearchResponse response = jsonUtil.postRequest(request, VehicleOnboardingSearchResponse.class,
				"online/searchVehicleOnboarding");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateVehicleOnboard(VehicleOnboardProfileUpdate request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"online/vehicleOnboardProfileUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleOnboardingResponse updateVehicleOnboardStatus(VehicleOnboardStatusUpdate request) throws AFCSException {
		VehicleOnboardingResponse response = jsonUtil.postRequest(request, VehicleOnboardingResponse.class,
				"online/vehicleOnboardStatusUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
