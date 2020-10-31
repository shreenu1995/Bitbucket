package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.SetupSoftwareMaintenance;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;

public interface SetupSoftwareVersionDao {

	SetupSoftwareSearchResponse getSoftwareVersionList(SetupSoftwareSearchRequest request);

	boolean updateSoftwareMaintenance(SetupSoftwareUpdateRequest request);

	boolean updateSetupSoftwareStatus(SetupSoftwareMaintenance setupSoftwareMaintenanceTable);

	SetupSoftwareSearchResponse getSoftwareDataById(SetupSoftwareSearchRequest request);

	SetupSoftwareListDataResponse getListInherit(Long ptoId);

	void save(SetupSoftwareMaintenance setupSoftwareMaintenance);

}
