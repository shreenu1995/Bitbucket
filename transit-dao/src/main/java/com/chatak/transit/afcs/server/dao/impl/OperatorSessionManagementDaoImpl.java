package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.OperatorSessionManagementDao;
import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;
import com.chatak.transit.afcs.server.dao.model.QAdminSessionManagement;
import com.chatak.transit.afcs.server.dao.repository.AdminSessionManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceOnboardRepository;
import com.chatak.transit.afcs.server.dao.repository.OperatorManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.AdminSessionRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportResponse;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class OperatorSessionManagementDaoImpl implements OperatorSessionManagementDao {

	Logger logger = LoggerFactory.getLogger(OperatorSessionManagementDaoImpl.class);

	@Autowired
	DeviceModelRepository deviceModelRepository;

	@Autowired
	DeviceOnboardRepository deviceOnboardRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Autowired
	AdminSessionManagementRepository adminSessionManagementRepostory;
	
	@Autowired
	OperatorManagementRepository operatorManagementRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean saveAdminSessionRequest(AdminSessionManagement adminSessionManagement) {
		String info = "saveAdminSessionRequest called from Dao Model";
		logger.debug(info);
		return (null != adminSessionManagementRepostory.save(adminSessionManagement));
	}

	@Override
	public boolean validateAdminSessionRequest(AdminSessionRequest request) throws PosException {
		return operatorManagementRepository.existsByOperatorId(Long.valueOf(request.getEmpId()))
				&& operatorManagementRepository.existsByPtoIdAndOperatorId(request.getPtoId(),
						Long.valueOf(request.getEmpId()))
				&& deviceModelRepository.existsByDeviceId(Long.valueOf(request.getDeviceId()))
				&& deviceOnboardRepository.existsByPtoIdAndDeviceModelId(request.getPtoId(),
						Long.valueOf(request.getDeviceId()));

	}

	public boolean validateFileDownloadRequest(FileDownloadRequest request, FileDownloadResponse response) {
		return operatorManagementRepository.existsByPtoId(Long.valueOf(request.getPtoOperationId()))
				&& deviceModelRepository.existsByDeviceId(Long.valueOf(request.getDeviceId()));
	}
	
	public boolean validateTransitFileDownloadRequest(TransitFileDownloadRequest request, TransitFileDownloadResponse response) {
		return operatorManagementRepository.existsByPtoId(Long.valueOf(request.getPtoId()))
				&& deviceModelRepository.existsByDeviceId(Long.valueOf(request.getDeviceId()));
	}

	@Override
	public OperatorSessionReportGenerationResponse generateOperatorSessionReport(
			OperatorSessionReportGenerationRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<AdminSessionManagement> listOfTxnData = query.from(QAdminSessionManagement.adminSessionManagement)
				.where(isTxnId(request.getTransactionId()), isFromDate(request.getGenerationDateStart()),
						isToDate(request.getGenerationDateEnd()))
				.offset(fromIndex).limit(10).orderBy(QAdminSessionManagement.adminSessionManagement.id.desc())
				.list(QAdminSessionManagement.adminSessionManagement);
		OperatorSessionReportGenerationResponse response = new OperatorSessionReportGenerationResponse();
		List<OperatorSessionReportResponse> listOfOperatorSessionResponse = new ArrayList<>();
		for (AdminSessionManagement adminSessionManagement : listOfTxnData) {
			OperatorSessionReportResponse operatorSessionReportResponse = new OperatorSessionReportResponse();
			operatorSessionReportResponse.setGenerateDateAndTime(adminSessionManagement.getGenerateDateAndTime());
			operatorSessionReportResponse.setDeviceId(adminSessionManagement.getDeviceId());
			operatorSessionReportResponse.setTransactionId(adminSessionManagement.getTransactionId());
			operatorSessionReportResponse.setPtoId(adminSessionManagement.getPtoId());
			operatorSessionReportResponse.setUserId(adminSessionManagement.getUserId());
			listOfOperatorSessionResponse.add(operatorSessionReportResponse);
		}
		response.setTotalNoOfRecords(getTotalOperatorCount(request.getTransactionId(), request.getGenerationDateStart(), request.getGenerationDateEnd()));
		response.setListOfOperatorSessionResponse(listOfOperatorSessionResponse);
		return response;
	}
	
	private BooleanExpression isFromDate(String fromDate) {
		Timestamp toTimeStamp = !StringUtil.isNullEmpty(fromDate)
				? DateUtil.toTimestamp(fromDate, Constants.REPORT_DATE_FORMAT)
				: null;
		return (!StringUtil.isNullEmpty(fromDate))
				? QAdminSessionManagement.adminSessionManagement.generateDateAndTime.goe(toTimeStamp)
				: null;
	}
	
	private BooleanExpression isToDate(String toDate) {
		Timestamp toTimeStamp = !StringUtil.isNullEmpty(toDate)
				? DateUtil.toTimestamp(toDate, Constants.REPORT_DATE_FORMAT)
				: null;

		return (!StringUtil.isNullEmpty(toDate))
				? QAdminSessionManagement.adminSessionManagement.processDateAndtime.loe(toTimeStamp)
				: null;
	}

	private int getTotalOperatorCount(String txnId, String startDate, String endDate) {
		JPAQuery query = new JPAQuery(entityManager);
		Long count = query.from(QAdminSessionManagement.adminSessionManagement)
				.where(isTxnId(txnId), isFromDate(startDate), isToDate(endDate))
				.orderBy(QAdminSessionManagement.adminSessionManagement.id.desc()).count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isTxnId(String transactionId) {
		return !StringUtil.isNullEmpty(transactionId)
				? QAdminSessionManagement.adminSessionManagement.transactionId.eq(transactionId)
				: null;
	}
}
