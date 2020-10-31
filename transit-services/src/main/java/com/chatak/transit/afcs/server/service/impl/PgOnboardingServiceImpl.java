package com.chatak.transit.afcs.server.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.PgOnboardingManagementDao;
import com.chatak.transit.afcs.server.dao.RegionManagementServiceDao;
import com.chatak.transit.afcs.server.dao.model.City;
import com.chatak.transit.afcs.server.dao.model.PaymentGateway;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;
import com.chatak.transit.afcs.server.service.PgOnboardingService;

@Service
public class PgOnboardingServiceImpl implements PgOnboardingService {

	Logger logger = LoggerFactory.getLogger(PgOnboardingServiceImpl.class);
	
	@PersistenceContext
	EntityManager em;

	@Autowired
	private RegionManagementServiceDao regionManagementServiceDao;

	@Autowired
	private PgOnboardingManagementDao pgOnboardingManagementDao;

	@Override
	public PgResponse createPaygate(PgRequest pgRequest) {
		logger.info("Entering:: PgOnboardingServiceImpl:: createPaygate method");
		PgResponse response = new PgResponse();
		State state = regionManagementServiceDao.findByStateName(pgRequest.getState());
		City city = regionManagementServiceDao.findByCityName(pgRequest.getCity());
		PaymentGateway paymentGateway = new PaymentGateway();
		paymentGateway.setPgName(pgRequest.getPgName());
		paymentGateway.setPtoMasterId(Long.parseLong(pgRequest.getPtoMasterId()));
		paymentGateway.setServiceUrl(pgRequest.getServiceUrl());
		if (!pgRequest.getCountryId().isEmpty()) {
			paymentGateway.setCountryId(Integer.parseInt(pgRequest.getCountryId()));
		}
		if (state != null) {
			paymentGateway.setStateId(state.getId());
		}

		if (city != null) {
			paymentGateway.setCityId(city.getId());
		}
		PaymentGateway existPg = pgOnboardingManagementDao.findByPgName(pgRequest.getPgName());
		if (existPg != null) {
			response.setStatusCode(CustomErrorCodes.PG_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PG_EXIST.getCustomErrorMsg());
			return response;
		}
		boolean saveResponse = pgOnboardingManagementDao.createPg(paymentGateway);
		if (saveResponse) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public PgResponse searchPaygate(PgRequest pgRequest) {
		logger.info("Entering:: PgOnboardingServiceImpl:: searchPaygate method");
		PgResponse pgResponse = pgOnboardingManagementDao.searchPaygate(pgRequest);
		pgResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		pgResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return pgResponse;
	}

	@Override
	public PgResponse deletePaygate(PgRequest pgRequest) {
		logger.info("Entering:: PgOnboardingServiceImpl:: deletePaygate method");
		PgResponse pgResponse = new PgResponse();
		pgOnboardingManagementDao.deletePaygate(pgRequest);
		PaymentGateway paymentGateway = pgOnboardingManagementDao.deletePaygate(pgRequest);
		pgResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		pgResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		pgResponse.setPgName(paymentGateway.getPgName());
		return pgResponse;
	}

}
