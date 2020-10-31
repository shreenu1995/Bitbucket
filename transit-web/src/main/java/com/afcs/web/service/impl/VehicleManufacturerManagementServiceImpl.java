package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.VehicleManufacturerManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelList;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeList;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class VehicleManufacturerManagementServiceImpl implements VehicleManufacturerManagementService {
	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public WebResponse saveVehicleManufacturerRegistration(VehicleManufacturerRegistrationRequest request)
			throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/saveVehicleManufacturer");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleTypeList getVehicleType() throws AFCSException {
		VehicleTypeList listOfVehicleName = jsonUtil.postRequest(VehicleTypeList.class, "online/getVehicleType");
		if (StringUtil.isNull(listOfVehicleName)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfVehicleName;
	}

	@Override
	public VehicleManufacturerListResponse getVehicleManuf() throws AFCSException {
		VehicleManufacturerListResponse listOfVehicleManuf = jsonUtil.postRequest(VehicleManufacturerListResponse.class,
				"online/getVehicleManufName");
		if (StringUtil.isNull(listOfVehicleManuf)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfVehicleManuf;
	}
	
	@Override
	public VehicleModelList getVehicleModel() throws AFCSException {
		VehicleModelList listOfVehicleModel = jsonUtil.postRequest(VehicleModelList.class,
				"online/getVehicleModel");
		if (StringUtil.isNull(listOfVehicleModel)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfVehicleModel;
	}
	
	@Override
	public VehicleManufacturerSearchResponse vehicleManufSearch(VehicleManufacturerSearchRequest request)
			throws AFCSException {
		VehicleManufacturerSearchResponse response = jsonUtil.postRequest(request,
				VehicleManufacturerSearchResponse.class, "online/searchVehicleManufacturer");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateVehicleManufactureStatus(VehicleManufacturerStatusUpdate request)
			throws AFCSException {
		WebResponse response = jsonUtil
				.postRequest(request, WebResponse.class, "online/vehicleManufactureStatusUpdate");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}

		return response;
	}

	@Override
	public WebResponse updateVehicleManufactureProfile(VehicleManufacturerProfileUpdate request) throws AFCSException {
		WebResponse response = jsonUtil
				.postRequest(request, WebResponse.class, "/online/vehicleManufacturerProfileUpdate");
		
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		
		return response;
	}

	@Override
	public VehicleOnboardingSearchResponse getOrganizationList(
			VehicleOnboardingSearchRequest vehicleOnboardingSearchRequest) throws AFCSException {
		VehicleOnboardingSearchResponse response = jsonUtil.postRequest(vehicleOnboardingSearchRequest, VehicleOnboardingSearchResponse.class, "online/getAllOrganizations");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
