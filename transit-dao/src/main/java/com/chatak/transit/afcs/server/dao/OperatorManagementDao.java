package com.chatak.transit.afcs.server.dao;

import java.util.List;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;

public interface OperatorManagementDao {
	
	boolean saveOperatorDetails(Operator operator);
	
	boolean validateOperatorRegistrationRequest(OperatorRegistrationRequest request);
	
	OperatorResponse searchOperators(OperatorSearchRequest request);

	boolean validateOperatorStatusUpdate(@Valid OperatorStatusChangeRequest request);

	Operator updateOperatorStatus(OperatorStatusChangeRequest request);

	boolean updateOperatorProfile(OperatorUpdateRequest request);
	
	List<Operator> getAllOperators(Long ptoId);
	
	Operator findByOperatorId(Long id);
	
}
