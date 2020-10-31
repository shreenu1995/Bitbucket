package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.TerminalSetupReportGenerationDao;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;
import com.chatak.transit.afcs.server.service.TerminalSetupReportGenerationService;

@Service
public class TerminalSetupReportGenerationServiceImpl implements TerminalSetupReportGenerationService {
	Logger logger = LoggerFactory.getLogger(TerminalSetupReportGenerationServiceImpl.class);

	@Autowired
	CustomErrorResolution dataValidation;

	@PersistenceContext
	EntityManager em;

	@Autowired
	TerminalSetupReportGenerationDao terminalReportGenerationDao;

	@Override
	public void checkTerminalSetupReportErrors(TerminalsetupReportGenerationRequest request,
			TerminalSetupReportGenerationResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.ptoIdValidation(request.getPtoId())) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.isValidDeviceModelNumber(request.getEquimentModelNumber())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorMsg());
		}
	}
	
	@Override
	public TerminalSetupReportGenerationResponse generateTerminalSetupReport(
			TerminalsetupReportGenerationRequest request, TerminalSetupReportGenerationResponse response,
			HttpServletResponse httpResponse) throws IOException {
		
		if (terminalReportGenerationDao.validationTerminalSetUpReport(request)) {
			request.getGenerationDateEnd();
			response = terminalReportGenerationDao.getReportAll(request);
			if (response != null) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			}
		}
		checkTerminalSetupReportErrors(request, response);
		return response;		
	}	
}
