package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.TerminalSetupReportGenerationDao;
import com.chatak.transit.afcs.server.dao.model.DeviceSetupManagement;
import com.chatak.transit.afcs.server.dao.model.QDeviceSetupManagement;
import com.chatak.transit.afcs.server.dao.repository.DeviceSetupManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class TerminalSetupReportGenerationDaoImpl implements TerminalSetupReportGenerationDao {

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	DeviceSetupManagementRepository deviceSetupManagementRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean validationTerminalSetUpReport(TerminalsetupReportGenerationRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& ptoOperationMasterRepository.existsByPtoMasterId(request.getPtoId());
	}

	@Override
	public TerminalSetupReportGenerationResponse getReportAll(TerminalsetupReportGenerationRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<DeviceSetupManagement> listOfTemData = query.from(QDeviceSetupManagement.deviceSetupManagement)
				.where(isPtoId(request.getPtoId()), isStartDate(request.getGenerationDateStart()), isEndDate(request.getGenerationDateEnd()), isOemId(request.getEquimentOemId()), isModelName(request.getEquimentModelNumber())).offset(fromIndex).limit(10)
				.orderBy(QDeviceSetupManagement.deviceSetupManagement.id.desc()).list(QDeviceSetupManagement.deviceSetupManagement);
		TerminalSetupReportGenerationResponse response = new TerminalSetupReportGenerationResponse();
		List<TerminalsetupReportResponse> listTerminalsetupReportResponse = new ArrayList<>();
		for (DeviceSetupManagement terminalSetupTableData : listOfTemData) {
			TerminalsetupReportResponse terminalSetupResponse = new TerminalsetupReportResponse();
			terminalSetupResponse.setEqipmentOemId(terminalSetupTableData.getDeviceOemId());
			terminalSetupResponse.setDeviceId((terminalSetupTableData.getDeviceId()).toString());
			terminalSetupResponse.setDeviceModel(terminalSetupTableData.getDeviceModel());
			terminalSetupResponse.setGenerationDateTime(terminalSetupTableData.getGenerationDatetime());
			terminalSetupResponse.setPtoId(terminalSetupTableData.getPtoId());
			listTerminalsetupReportResponse.add(terminalSetupResponse);
		}
		response.setNoOfRecords(getTotalTerminalSetUpRows(request));
		response.setListOfterminalSetupResponse(listTerminalsetupReportResponse);
		return response;
	}
	
	private int getTotalTerminalSetUpRows(TerminalsetupReportGenerationRequest request) {
		 JPAQuery query = new JPAQuery(entityManager);
	     Long count = query.from(QDeviceSetupManagement.deviceSetupManagement)
	         .where(isPtoId(request.getPtoId()), isStartDate(request.getGenerationDateStart()), isEndDate(request.getGenerationDateEnd()), isOemId(request.getEquimentOemId()), isModelName(request.getEquimentModelNumber()))
	         .orderBy(QDeviceSetupManagement.deviceSetupManagement.id.desc()).count();
	     return count != null ? count.intValue() : 0;
		}
	
	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QDeviceSetupManagement.deviceSetupManagement.ptoId.eq(ptoId) : null;
	}
	
	private BooleanExpression isOemId(String deviceOemId) {
		return !StringUtil.isNullEmpty(deviceOemId) ? QDeviceSetupManagement.deviceSetupManagement.deviceOemId.eq(deviceOemId) : null;
	}
	
	private BooleanExpression isModelName(String deviceModel) {
		return !StringUtil.isNullEmpty(deviceModel) ? QDeviceSetupManagement.deviceSetupManagement.deviceModel.eq(deviceModel) : null;
	}
	
	private BooleanExpression isStartDate(Timestamp startDate) {
		return (startDate != null) ? QDeviceSetupManagement.deviceSetupManagement.generationDatetime.goe(startDate) : null;
	}
	
	private BooleanExpression isEndDate(Timestamp endDate) {
		return (endDate != null) ? QDeviceSetupManagement.deviceSetupManagement.generationDatetime.goe(endDate) : null;
	}
}
