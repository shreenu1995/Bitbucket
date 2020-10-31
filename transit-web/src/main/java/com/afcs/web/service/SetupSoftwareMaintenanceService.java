package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface SetupSoftwareMaintenanceService {

	SetupSoftwareRegistrationResponse setupSoftwareRegistration(
			SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest) throws AFCSException;

	SetupSoftwareSearchResponse searchSoftwareMaintenance(SetupSoftwareSearchRequest setupSoftwareSearchRequest)
			throws AFCSException;

	WebResponse updateSoftwareMaintenance(SetupSoftwareUpdateRequest request) throws AFCSException;

	WebResponse updateSoftwareStatus(SetupSoftwareRegistrationRequest request) throws AFCSException;

	SetupSoftwareSearchResponse getSoftwareEditViewData(SetupSoftwareSearchRequest request) throws AFCSException;

	SetupSoftwareListDataResponse getSoftwareListDataResponse(
			SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest) throws AFCSException;

	SetupSoftwareListDataResponse getVersionNumberByPtoName(
			SetupSoftwareSearchRequest setupSoftwareSearchRequest) throws AFCSException;

}
