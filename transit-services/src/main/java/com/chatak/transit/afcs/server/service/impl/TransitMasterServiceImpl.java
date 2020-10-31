package com.chatak.transit.afcs.server.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.TransitMasterDao;
import com.chatak.transit.afcs.server.dao.impl.TransitMasterDaoImpl;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.TransitMasterMaintenance;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TransitMasterService;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class TransitMasterServiceImpl implements TransitMasterService {

	private static final Logger logger = LoggerFactory.getLogger(TransitMasterDaoImpl.class);

	@Autowired
	TransitMasterDao transitMasterDao;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Override
	public TransitMasterRegistrationResponse transitMasterRegistrationResponse(TransitMasterRegistrationRequest request,
			TransitMasterRegistrationResponse response) {

		TransitMasterRegistrationResponse transitMasterRegistrationResponse = new TransitMasterRegistrationResponse();
		TransitMasterMaintenance setupTransitMasterMaintenance = new TransitMasterMaintenance();
		setupTransitMasterMaintenance.setDeliveryDate(DateUtil.convertStringToTimestamp(request.getDeliveryDate()));
		setupTransitMasterMaintenance.setApplyDate(DateUtil.convertStringToTimestamp(request.getApplyDate()));
		setupTransitMasterMaintenance.setDescription(request.getDescription());
		setupTransitMasterMaintenance.setFullVersion(request.getFullVersion());
		setupTransitMasterMaintenance.setInheritt(request.getInherit());
		setupTransitMasterMaintenance.setPtoId(request.getPtoId());
		setupTransitMasterMaintenance.setVersionNumber(request.getVersionNumber());
		setupTransitMasterMaintenance.setStatus(Status.ACTIVE.getValue());
		setupTransitMasterMaintenance.setOrganizationId(request.getOrganizationId());
		if (!StringUtil.isNull(request.getOrganizationId())) {
			try {
				OrganizationMaster organizationMaster = organizationMasterRepository
						.findByOrgId(request.getOrganizationId());
				request.setOrganizationName(organizationMaster.getOrganizationName());
			} catch (NullPointerException e) {
				logger.error("ERROR :: FeeManagementDaoImpl class :: getFeeList method :: exception", e);
			}
		}
		transitMasterDao.savetransitMaster(setupTransitMasterMaintenance);
		if (!StringUtil.isNull(setupTransitMasterMaintenance)) {
			transitMasterRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			transitMasterRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		}
		return transitMasterRegistrationResponse;
	}

	@Override
	public TransitMasterSearchResponse transitMasterSearchResponse(TransitMasterSearchRequest request) {
		TransitMasterSearchResponse response = transitMasterDao.getTransitMaster(request);
		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public WebResponse updateTransitMaster(TransitMasterUpdateRequest request, HttpServletResponse httpResponse,
			WebResponse response) {

		if (transitMasterDao.updateTransitMaster(request)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());

			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());

			return response;
		}
	}

	@Override
	public WebResponse updateTransitMasterStatus(TransitMasterRegistrationRequest request, WebResponse response) {

		if (transitMasterDao.updateTransitMasterStatus(request)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public TransitMasterSearchResponse searchTransitMasterData(TransitMasterSearchRequest request) {
		TransitMasterSearchResponse response = transitMasterDao.searchTransitMasterData(request);
		if (StringUtil.isListNotNullNEmpty(response.getTransitMasterSearchList())) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		}

		return response;

	}

	@Override
	public TransitMasterListDataResponse getInheritList(Long ptoId) {
		return transitMasterDao.getListInherit(ptoId);
	}

}
