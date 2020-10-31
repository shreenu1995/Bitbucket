package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface OperatorManagementService {
	
	WebResponse createOperator(OperatorRegistrationRequest request) throws AFCSException;
	
	OperatorResponse searchOperator(OperatorSearchRequest request) throws AFCSException;

	WebResponse updateOperatorProfile(OperatorUpdateRequest request) throws AFCSException;

	OperatorResponse updateOperatorStatus(OperatorStatusChangeRequest request) throws AFCSException;
}
