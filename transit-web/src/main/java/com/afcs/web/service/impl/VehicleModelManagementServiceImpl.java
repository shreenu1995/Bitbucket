package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.VehicleModelManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class VehicleModelManagementServiceImpl implements VehicleModelManagementService {
	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public WebResponse saveVehicleModelRegistration(VehicleModelRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/saveVehicleModel");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleManufacturerListResponse getVehicleManufacturer() throws AFCSException {
		VehicleManufacturerListResponse listOfVehicleName = jsonUtil.postRequest(VehicleManufacturerListResponse.class,
				"online/getVehicleManufName");
		if (StringUtil.isNull(listOfVehicleName)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfVehicleName;
	}

	@Override
	public VehicleModelSearchResponse vehicleModelSearch(VehicleModelSearchRequest request) throws AFCSException {
		VehicleModelSearchResponse response = jsonUtil.postRequest(request, VehicleModelSearchResponse.class,
				"online/searchVehicleModel");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public VehicleModelListResponse getVehicleModelForManufacturerListForVehicleType(
			VehicleManufacturerSearchRequest vehicleManufacturerSearchRequest) throws AFCSException {
		VehicleModelListResponse response = jsonUtil.postRequest(vehicleManufacturerSearchRequest, VehicleModelListResponse.class,
				"online/vehicleManufacturerListByVehicleType");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateVehicleModel(VehicleModelProfileUpdate request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
					"online/vehicleModelProfileUpdate");
			if (StringUtil.isNull(response)) {
				throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
			}
			return response;
	}

	@Override
	public VehicleModelSearchResponse updateVehicleModelStatus(VehicleModelStatusUpdate request) throws AFCSException {
		VehicleModelSearchResponse response = jsonUtil.postRequest(request, VehicleModelSearchResponse.class,
				"online/vehicleModelStatusUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public String engineNumberExistsCheck(String name) {
		VehicleModelRegistrationRequest vehicleModelRegistrationRequest= new VehicleModelRegistrationRequest();
		vehicleModelRegistrationRequest.setVehicleEngineNumber(name);
		Response response = jsonUtil.postRequest(vehicleModelRegistrationRequest, Response.class, "online/engineNumberExistsCheck");
		return response.getResult();
	}

	@Override
	public String chassisNumberExistsCheck(String name) {
		VehicleModelRegistrationRequest vehicleModelRegistrationRequest= new VehicleModelRegistrationRequest();
		vehicleModelRegistrationRequest.setVehicleChassisNumber(name);
		Response response = jsonUtil.postRequest(vehicleModelRegistrationRequest, Response.class, "online/chassisNumberExistsCheck");
		return response.getResult();
	}

}
