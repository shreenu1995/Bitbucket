package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.TransitMasterMaintenance;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;

public interface TransitMasterDao {

	void savetransitMaster(TransitMasterMaintenance setupTransitMasterMaintenance);

	TransitMasterSearchResponse getTransitMaster(TransitMasterSearchRequest request);

	boolean updateTransitMaster(TransitMasterUpdateRequest request);

	boolean updateTransitMasterStatus(TransitMasterRegistrationRequest request);

	TransitMasterSearchResponse searchTransitMasterData(TransitMasterSearchRequest request);

	TransitMasterListDataResponse getListInherit(Long ptoId);
}
