package com.chatak.transit.afcs.server.dao;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.OpsManifest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;

public interface OpsManifestManagementDao {

	void saveOpsManifest(OpsManifest opsManifest);

	OpsManifestSearchResponse getOpsManifestList(OpsManifestSearchRequest request);

	boolean validateOpsManifestStatusUpdate(@Valid OpsManifestStatusChangeRequest request);

	boolean updateOpsManifestStatus(@Valid OpsManifestStatusChangeRequest request);

	boolean updateOpsManifestProfile(OpsManifestUpdateRequest request);

	boolean validateExistingDeviceNumber(OpsManifestRegistrationRequest opsManifestRegistrationRequest);

}
