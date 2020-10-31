package com.chatak.transit.afcs.server.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface OperatorManagementService {

	WebResponse operatorRegister(OperatorRegistrationRequest request);

	void validateOperatorRegistrationErrors(OperatorRegistrationRequest request, WebResponse response);

	OperatorResponse searchOperators(OperatorSearchRequest request);

	OperatorResponse updateOperatorStatus(OperatorStatusChangeRequest request, WebResponse response);

	WebResponse updateOperatorProfile(OperatorUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse);

	List<Operator> getAllOperator(Long ptoId);
}
