package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.FeeMaster;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;

public interface FeeManagementDao {
	
	FeeMaster saveFee(FeeMaster feeMaster);

	FeeSearchResponse getFeeList(FeeSearchRequest request);
	
	boolean validateFeeStatusUpdate(FeeUpdateRequest request);
	
	boolean updateFeeProfileUpdate(FeeUpdateRequest request);
	
	List<String> getOrganisationList();
	
	FeeMaster updateFeeStatus(FeeRegistrationRequest request);

}