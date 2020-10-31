package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface OpsManifestManagementService {

	OpsManifestRegistrationResponse opsmanifestRegistration(
			OpsManifestRegistrationRequest opsManifestRegistrationRequest) throws AFCSException;

	OpsManifestSearchResponse searchOpsManifest(OpsManifestSearchRequest opsManifestSearchRequest) throws AFCSException;

	WebResponse updateOpsManifestStatus(OpsManifestStatusChangeRequest request) throws AFCSException;

	WebResponse updateOpsManifest(OpsManifestUpdateRequest request) throws AFCSException;

	String deviceOnboardIdExistCheck(Long deviceNo);

}
