package com.chatak.transit.afcs.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.IssuerOnboardingManagementDao;
import com.chatak.transit.afcs.server.dao.RegionManagementServiceDao;
import com.chatak.transit.afcs.server.dao.model.City;
import com.chatak.transit.afcs.server.dao.model.Issuer;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;
import com.chatak.transit.afcs.server.service.IssuerOnboardingService;

@Service
public class IssuerOnboardingServiceImpl implements IssuerOnboardingService {

	Logger logger = LoggerFactory.getLogger(IssuerOnboardingServiceImpl.class);

	@PersistenceContext
	EntityManager em;

	@Autowired
	private RegionManagementServiceDao regionManagementServiceDao;

	@Autowired
	private IssuerOnboardingManagementDao issuerOnboardingManagementDao;

	@Override
	public IssuerResponse createIssuer(IssuerRequest issuerRequest) {
		logger.info("Entering:: IssuerOnboardingServiceImpl:: createIssuer method");
		IssuerResponse response = new IssuerResponse();
		State state = regionManagementServiceDao.findByStateName(issuerRequest.getState());
		City city = regionManagementServiceDao.findByCityName(issuerRequest.getCity());
		Issuer issuer = new Issuer();
		issuer.setIssuerName(issuerRequest.getIssuerName());
		issuer.setPtoMaterId(Long.parseLong(issuerRequest.getPtoMasterId()));
		issuer.setServiceUrl(issuerRequest.getServiceUrl());
		if (!issuerRequest.getCountryId().isEmpty()) {
			issuer.setCountryId(Integer.parseInt(issuerRequest.getCountryId()));
		}
		if (state != null) {
			issuer.setStateId(state.getId());
		}

		if (city != null) {
			issuer.setCityId(city.getId());
		}
		Issuer existIssuer = issuerOnboardingManagementDao.findByIssuerName(issuerRequest.getIssuerName());
		if (existIssuer != null) {
			response.setStatusCode(CustomErrorCodes.ISSUER_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.ISSUER_EXIST.getCustomErrorMsg());
			return response;
		}
		boolean saveResponse = issuerOnboardingManagementDao.createIssuer(issuer);
		if (saveResponse) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public IssuerResponse searchIssuer(IssuerRequest issuerRequest) {
		logger.info("Entering:: IssuerOnboardingServiceImpl:: searchIssuer method");
		IssuerResponse issuerResponse = issuerOnboardingManagementDao.searchIssuer(issuerRequest);
		issuerResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		issuerResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return issuerResponse;
	}

	@Override
	public IssuerResponse deleteIssuer(IssuerRequest issuerRequest) {
		logger.info("Entering:: IssuerOnboardingServiceImpl:: deleteIssuer method");
		IssuerResponse issuerResponse = new IssuerResponse();
		Issuer issuer = issuerOnboardingManagementDao.deleteIssuer(issuerRequest);
		issuerResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		issuerResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		issuerResponse.setIssuerName(issuer.getIssuerName());
		return issuerResponse;
	}

	@Override
	public IssuerResponse getAllIssuers() {
		logger.info("Entering:: IssuerOnboardingServiceImpl:: getAllIssuer method");
		IssuerResponse issuerResponse = new IssuerResponse();
		List<Issuer> issuerList = issuerOnboardingManagementDao.getAllIssuer();
		List<IssuerRequest> issuerRequestList = new ArrayList<>();
		for(Issuer issuer : issuerList) {
			IssuerRequest request = new IssuerRequest();
			request.setIssuerName(issuer.getIssuerName());
			issuerRequestList.add(request);
		}
		issuerResponse.setListOfAfcsIssuer(issuerRequestList);
		issuerResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		issuerResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return issuerResponse;
	}
	
	@Override
	
	public IssuerResponse getIssuerById(IssuerRequest issuerRequest) {
		logger.info("Entering:: IssuerOnboardingServiceImpl:: getIssuerId method");
		Issuer existIssuer = issuerOnboardingManagementDao.findByIssuerName(issuerRequest.getIssuerName());
		IssuerResponse issuerResponse = new IssuerResponse();
		issuerRequest.setIssuerId(existIssuer.getId());
		List<IssuerRequest> issuerRequestList = new ArrayList<>();
		issuerRequestList.add(issuerRequest);
		issuerResponse.setListOfAfcsIssuer(issuerRequestList);
		return issuerResponse;
	}
}
