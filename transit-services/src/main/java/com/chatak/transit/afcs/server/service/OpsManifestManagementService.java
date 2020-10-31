package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface OpsManifestManagementService {

	OpsManifestRegistrationResponse opsManifestRegistration(
			@Valid OpsManifestRegistrationRequest opsManifestRegistrationRequest, HttpServletResponse httpResponse,
			OpsManifestRegistrationResponse opsManifestRegistrationResponse);

	OpsManifestSearchResponse searchOpsManifest(OpsManifestSearchRequest request);

	WebResponse updateOpsManifestStatus(@Valid OpsManifestStatusChangeRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	WebResponse updateOpsManifestProfile(OpsManifestUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse);

	boolean deviceOnboardIdExistCheck(OpsManifestRegistrationRequest opsManifestRegistrationRequest);

}
