package com.chatak.transit.afcs.server.service;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface SetupSoftwareMaintenanceService {

	SetupSoftwareRegistrationResponse setupSoftwareRegistrationMaintenance(SetupSoftwareRegistrationRequest request,
			SetupSoftwareRegistrationResponse response);

	SetupSoftwareSearchResponse setupSoftwareSearchResponse(SetupSoftwareSearchRequest request);

	WebResponse updateSoftwareMaintenance(SetupSoftwareUpdateRequest request, HttpServletResponse httpResponse,
			WebResponse response);

	SetupSoftwareUpdateResponse updateSoftwareStatus(SetupSoftwareRegistrationRequest request,
			HttpServletResponse httpResponse);

	SetupSoftwareSearchResponse getSoftwareData(SetupSoftwareSearchRequest request);

	SetupSoftwareListDataResponse getInheritList(Long ptoId);

}
