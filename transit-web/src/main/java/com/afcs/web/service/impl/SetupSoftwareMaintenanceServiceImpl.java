package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.afcs.web.service.SetupSoftwareMaintenanceService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class SetupSoftwareMaintenanceServiceImpl implements SetupSoftwareMaintenanceService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public SetupSoftwareRegistrationResponse setupSoftwareRegistration(
			SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest) throws AFCSException {
		SetupSoftwareRegistrationResponse response = jsonUtil.postRequest(setupSoftwareRegistrationRequest,
				SetupSoftwareRegistrationResponse.class,
				"setupsoftwaremaintenance/setupSoftwareMaintenanceRegistration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public SetupSoftwareSearchResponse searchSoftwareMaintenance(SetupSoftwareSearchRequest setupSoftwareSearchRequest)
			throws AFCSException {
		SetupSoftwareSearchResponse response = jsonUtil.postRequest(setupSoftwareSearchRequest,
				SetupSoftwareSearchResponse.class, "setupsoftwaremaintenance/setupSoftwareMaintenanceSearch");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateSoftwareMaintenance(SetupSoftwareUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"setupsoftwaremaintenance/updateSetupSoftwareMaintenance");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public SetupSoftwareSearchResponse getSoftwareEditViewData(SetupSoftwareSearchRequest request)
			throws AFCSException {
		SetupSoftwareSearchResponse response = jsonUtil.postRequest(request, SetupSoftwareSearchResponse.class,
				"setupsoftwaremaintenance/getSoftwareData");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateSoftwareStatus(SetupSoftwareRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"setupsoftwaremaintenance/updateSetupSoftwareStatus");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public SetupSoftwareListDataResponse getSoftwareListDataResponse(
			SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest) throws AFCSException {
		SetupSoftwareListDataResponse listOfDataResponse = jsonUtil.postRequest(setupSoftwareRegistrationRequest,
				SetupSoftwareListDataResponse.class, "setupsoftwaremaintenance/getListInherit");
		if (StringUtil.isNull(listOfDataResponse)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfDataResponse;
	}

	@Override
	public SetupSoftwareListDataResponse getVersionNumberByPtoName(
			SetupSoftwareSearchRequest setupSoftwareSearchRequest) throws AFCSException {
		SetupSoftwareListDataResponse response = jsonUtil.postRequest(setupSoftwareSearchRequest,
				SetupSoftwareListDataResponse.class, "setupsoftwaremaintenance/getListInherit");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
}
