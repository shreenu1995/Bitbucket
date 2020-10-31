package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.TransactionReportGenerationDao;
import com.chatak.transit.afcs.server.dao.model.QTicketsTxnData;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.repository.OperatorManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.TicketTxnDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class TransactionReportGenerationDaoImpl implements TransactionReportGenerationDao {

	@Autowired
	TicketTxnDataRepository ticketTransactionRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;
	
	@Autowired
	PtoMasterRepository ptoRepo;
	
	@Autowired
	OperatorManagementRepository operatorRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<TicketsTxnData> getReportTxnId(String ticketTxnData, int pageIndex) {
		return ticketTransactionRepository
				.getTicketReportTxnType(ticketTxnData, PageRequest.of(pageIndex, Constants.SIZE)).getContent();
	}

	@Override
	public List<TicketsTxnData> getReportDateTime(Timestamp startDate, Timestamp endDate, int pageIndex) {
		return ticketTransactionRepository
				.getTicketReportDate(startDate, endDate, PageRequest.of(pageIndex, Constants.SIZE)).getContent();
	}

	@Override
	public TransactionReportGenerationResponse getReportAll(String txnId, String startDate, String endDate,
			int pageIndex, String organizationId, Long ptoId) {
		Integer pageIndexNo = pageIndex;
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(entityManager);
		List<TicketsTxnData> listOfTxnData = query.from(QTicketsTxnData.ticketsTxnData)
				.where(isTxnId(txnId), isPtoId(ptoId),
						isFromDate(startDate), isToDate(endDate))
				.offset(fromIndex).limit(10).orderBy(QTicketsTxnData.ticketsTxnData.id.desc())
				.list(QTicketsTxnData.ticketsTxnData);
		TransactionReportGenerationResponse response = new TransactionReportGenerationResponse();
		List<TicketTxnDataResponse> transactionResultList = new ArrayList<>();
		for (TicketsTxnData ticketTxnData : listOfTxnData) {
			TicketTxnDataResponse ticketDataResponse = new TicketTxnDataResponse();
			ticketDataResponse.setId(ticketTxnData.getId());
			ticketDataResponse.setTicketNumber(ticketTxnData.getTicketNumber());
			ticketDataResponse.setTransactionId(ticketTxnData.getTransactionId());
			ticketDataResponse.setTicketDateTime(ticketTxnData.getTicketDateTime());
			ticketDataResponse.setTicketFareAmount(ticketTxnData.getTicketFareAmount() / 10);
			ticketDataResponse.setTicketFareOptionalPositiveAmount(ticketTxnData.getTicketFareOptionalPositiveAmount());
			ticketDataResponse.setTicketFareOptionalNegativeAmount(ticketTxnData.getTicketFareOptionalNegativeAmount());
			ticketDataResponse.setTicketOperationDate(ticketTxnData.getTicketOperationDate());
			ticketDataResponse.setTicketPaymentMode(ticketTxnData.getTicketPaymentMode());
			ticketDataResponse.setTicketOriginStationCode(ticketTxnData.getTicketOriginStationCode());
			ticketDataResponse.setTicketDestStationCode(ticketTxnData.getTicketDestStationCode());
			ticketDataResponse.setTicketPassengerCount(ticketTxnData.getTicketPassengerCount());
			ticketDataResponse.setPtoId(ticketTxnData.getPtoId());
			ticketDataResponse.setDeviceId(ticketTxnData.getDeviceId());
			ticketDataResponse.setConductorEmpId(ticketTxnData.getConductorEmpId());
			ticketDataResponse.setDriverEmpId(ticketTxnData.getDriverEmpId());
			ticketDataResponse.setShiftCode(ticketTxnData.getShiftCode());
			ticketDataResponse.setShiftBatchNumber(ticketTxnData.getShiftBatchNumber());
			ticketDataResponse.setTripNumber(ticketTxnData.getTripNumber());
			ticketDataResponse.setRouteCode(ticketTxnData.getRouteCode());
			ticketDataResponse.setTransportID(ticketTxnData.getTransportID());
			ticketDataResponse.setCurrentStopId(ticketTxnData.getCurrentStopId());
			ticketDataResponse.setCardNumber(ticketTxnData.getCardNumber());
			ticketDataResponse.setCardBalance(ticketTxnData.getCardBalance());
			ticketDataResponse.setCardExpiryDate(ticketTxnData.getCardExpiryDate());
			ticketDataResponse.setPaymentTxnUniqueId(ticketTxnData.getPaymentTxnUniqueId());
			ticketDataResponse.setPaymentTxnTerminalId(ticketTxnData.getPaymentTxnTerminalId());
			ticketDataResponse.setPassTypeCode(ticketTxnData.getPassTypeCode());
			ticketDataResponse.setChecksum(ticketTxnData.getChecksum());
			transactionResultList.add(ticketDataResponse);
		}
		response.setNoOfRecords(getTotalTicketsTxnRows(txnId, startDate, endDate, ptoId));
		response.setTxnResponse(transactionResultList);
		return response;
	}
	
	private BooleanExpression isFromDate(String fromDate) {
		Timestamp toTimeStamp = !StringUtil.isNullEmpty(fromDate)
				? DateUtil.toTimestamp(fromDate, Constants.REPORT_DATE_FORMAT)
				: null;
		return (!StringUtil.isNullEmpty(fromDate))
				? QTicketsTxnData.ticketsTxnData.ticketDateTime.goe(toTimeStamp)
				: null;
	}
	
	private BooleanExpression isToDate(String toDate) {
		Timestamp toTimeStamp = !StringUtil.isNullEmpty(toDate)
				? DateUtil.toTimestamp(toDate, Constants.REPORT_DATE_FORMAT)
				: null;

		return (!StringUtil.isNullEmpty(toDate))
				? QTicketsTxnData.ticketsTxnData.ticketDateTime.loe(toTimeStamp)
				: null;
	}

	private int getTotalTicketsTxnRows(String txnId, String startDate, String endDate, 
			Long ptoId) {
		JPAQuery query = new JPAQuery(entityManager);
		Long count = query
				.from(QTicketsTxnData.ticketsTxnData).where(isTxnId(txnId), isFromDate(startDate),
						isToDate(endDate),isPtoId(ptoId))
				.orderBy(QTicketsTxnData.ticketsTxnData.id.desc()).count();
		return count != null ? count.intValue() : 0;
	}

	

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QTicketsTxnData.ticketsTxnData.ptoId.eq(ptoId)
				: null;
	}
	
	private BooleanExpression isTxnId(String txnId) {
		return !StringUtil.isNullEmpty(txnId) ? QTicketsTxnData.ticketsTxnData.transactionId.eq(txnId) : null;
	}

	@Override
	public List<TicketsTxnData> getReportAllPart(String txnId, Timestamp startDate, Timestamp endDate, int pageIndex) {
		if (txnId == null && startDate == null && endDate == null) {
			return ticketTransactionRepository.findAll(PageRequest.of(pageIndex, Constants.SIZE)).getContent();
		} else if (txnId == null && startDate != null && endDate == null) {
			return ticketTransactionRepository
					.getTransactionReportAfterStartDate(startDate, PageRequest.of(pageIndex, Constants.SIZE))
					.getContent();
		} else if (txnId == null && startDate == null) {
			return ticketTransactionRepository
					.getTransactionReportBeforeEndDate(endDate, PageRequest.of(pageIndex, Constants.SIZE)).getContent();
		}
		return Collections.emptyList();
	}

	@Override
	public boolean validateRequest(TransactionReportGenerationRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId());
	}

	
	@Override
	public List<TicketsTxnData> getReportByPtoIdAndTransactionId(Long ptoId, String transactioId) {
		return ticketTransactionRepository.findByPtoIdAndTransactionId(ptoId, transactioId);
	}
	
	@Override
	public List<TicketsTxnData> getReportByPtoIdAndOrganizationId(Long ptoId, String organizationId) {
		return ticketTransactionRepository.findByPtoIdAndTransactionId(ptoId, organizationId);
	}
	
}
