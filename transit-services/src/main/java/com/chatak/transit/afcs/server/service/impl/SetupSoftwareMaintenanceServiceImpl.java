package com.chatak.transit.afcs.server.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.SetupSoftwareVersionDao;
import com.chatak.transit.afcs.server.dao.model.SetupSoftwareMaintenance;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.SetupSoftwareMaintenanceService;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class SetupSoftwareMaintenanceServiceImpl implements SetupSoftwareMaintenanceService {

	@Autowired
	SetupSoftwareVersionDao setupSoftwareVersionDao;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Override
	public SetupSoftwareRegistrationResponse setupSoftwareRegistrationMaintenance(
			SetupSoftwareRegistrationRequest request, SetupSoftwareRegistrationResponse response) {
		LocalDateTime now = LocalDateTime.now();
		SetupSoftwareMaintenance setupSoftwareMaintenance = new SetupSoftwareMaintenance();
		setupSoftwareMaintenance.setDeliveryDate(DateUtil.convertStringToTimestamp(request.getDeliveryDate()));
		setupSoftwareMaintenance.setApplyDate(DateUtil.convertStringToTimestamp(request.getApplyDate()));
		setupSoftwareMaintenance.setDescription(request.getDescription());
		setupSoftwareMaintenance.setFullVersion(request.getFullVersion());
		setupSoftwareMaintenance.setInheritt(request.getInherit());
		setupSoftwareMaintenance.setPtoId(request.getPtoId());
		setupSoftwareMaintenance.setVersionNumber(request.getVersionNumber());
		setupSoftwareMaintenance.setStatus(Status.ACTIVE.getValue());
		setupSoftwareMaintenance.setCreatedBy(request.getUserId());
		setupSoftwareMaintenance.setUpdatedBy(request.getUserId());
		setupSoftwareMaintenance.setCreatedTime(Timestamp.valueOf(now));
		setupSoftwareMaintenance.setUpdatedTime(Timestamp.valueOf(now));
		setupSoftwareMaintenance.setOrganizationId(request.getOrganizationId());
		setupSoftwareVersionDao.save(setupSoftwareMaintenance);
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return response;
	}

	@Override
	public SetupSoftwareSearchResponse setupSoftwareSearchResponse(SetupSoftwareSearchRequest request) {
		SetupSoftwareSearchResponse response = setupSoftwareVersionDao.getSoftwareVersionList(request);
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
	public WebResponse updateSoftwareMaintenance(SetupSoftwareUpdateRequest request, HttpServletResponse httpResponse,
			WebResponse response) {

		if (setupSoftwareVersionDao.updateSoftwareMaintenance(request)) {
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
	public SetupSoftwareSearchResponse getSoftwareData(SetupSoftwareSearchRequest request) {
		SetupSoftwareSearchResponse response = setupSoftwareVersionDao.getSoftwareDataById(request);
		if (StringUtil.isListNotNullNEmpty(response.getSetupSoftwareSearchList())) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public SetupSoftwareUpdateResponse updateSoftwareStatus(SetupSoftwareRegistrationRequest request,
			HttpServletResponse httpResponse) {
		SetupSoftwareUpdateResponse setupSoftwareUpdateResponse = new SetupSoftwareUpdateResponse();
		SetupSoftwareMaintenance setupSoftwareMaintenanceTable = new SetupSoftwareMaintenance();
		setupSoftwareMaintenanceTable.setSoftwareId(request.getSoftwareId());
		setupSoftwareMaintenanceTable.setStatus((request.getStatus()));
		if (setupSoftwareVersionDao.updateSetupSoftwareStatus(setupSoftwareMaintenanceTable)) {
			setupSoftwareUpdateResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			setupSoftwareUpdateResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return setupSoftwareUpdateResponse;
		}
		setupSoftwareUpdateResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		setupSoftwareUpdateResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		return setupSoftwareUpdateResponse;
	}

	@Override
	public SetupSoftwareListDataResponse getInheritList(Long ptoId) {
		return setupSoftwareVersionDao.getListInherit(ptoId);
	}

}
