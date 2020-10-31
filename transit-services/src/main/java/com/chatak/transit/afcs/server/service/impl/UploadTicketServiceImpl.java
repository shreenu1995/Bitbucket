package com.chatak.transit.afcs.server.service.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.DeviceModelMangementDao;
import com.chatak.transit.afcs.server.dao.OperatorManagementDao;
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.UploadTicketDao;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.model.TripDetailsData;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;
import com.chatak.transit.afcs.server.pojo.TransitTicketTransactionDataRequest;
import com.chatak.transit.afcs.server.service.UploadTicketToAfcsService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
@PropertySource("classpath:error.properties")
public class UploadTicketServiceImpl implements UploadTicketToAfcsService {

	Logger logger = LoggerFactory.getLogger(UploadTicketServiceImpl.class);

	@Autowired
	UploadTicketDao uploadTicketDao;

	@Autowired
	OperatorManagementDao operatorManagementDao;

	@Autowired
	DeviceModelMangementDao deviceModelMangementDao;

	@Autowired
	PtoManagementDao ptoManagementDao;

	@Override
	public TicketTransactionDataResponse saveUploadTicket(TransitTicketTransactionDataRequest request) {

		TicketsTxnData ticketTxnData = new TicketsTxnData();
		ticketTxnData.setTicketNumber(request.getTicketDetails().getTicketNumber());
		ticketTxnData.setTicketDateTime(Timestamp.valueOf(request.getCurrentDateTime()));
		ticketTxnData.setTicketFareAmount(Long.parseLong(request.getTicketDetails().getFare()));
		ticketTxnData.setTicketPassengerCount(request.getTicketDetails().getPassengerCount());
		ticketTxnData.setPtoId(Long.parseLong(request.getPtoId()));
		ticketTxnData.setDeviceId(Long.parseLong(request.getDeviceId()));
		ticketTxnData.setOperatorId(request.getOperatorId());

		ticketTxnData.setCardNumber(request.getCardInfo().getCardNumber());
		ticketTxnData.setCardExpiryDate(request.getCardInfo().getExpiry());
		ticketTxnData.setChecksum(request.getCheckSum());

		TicketTransactionDataResponse response = new TicketTransactionDataResponse();

		if (!StringUtil.isNull(Long.parseLong(request.getPtoId()))) {
			boolean status;
			status = ptoManagementDao.getPtoByPtoMasterId(Long.parseLong(request.getPtoId())) != null;
			if (status == false) {

				response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
				return response;
			}

		}
		if (!StringUtil.isNull(Long.parseLong(request.getOperatorId()))) {

			Operator op = operatorManagementDao.findByOperatorId(Long.parseLong(request.getOperatorId()));
			if (op == null) {

				response.setStatusCode(CustomErrorCodes.OPERATOR_ID_NOT_EXIST.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.OPERATOR_ID_NOT_EXIST.getCustomErrorMsg());
				return response;
			}

		}
		if (!StringUtil.isNull(Long.parseLong(request.getDeviceId()))) {
			boolean isDeviceExists = deviceModelMangementDao
					.findDeviceByDeviceId(Long.parseLong(request.getDeviceId()));
			if (!isDeviceExists) {
				response.setStatusCode(CustomErrorCodes.DEVICE_ID_PTO_OPERATION_ID.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.DEVICE_ID_PTO_OPERATION_ID.getCustomErrorMsg());
				return response;
			} else {
				TicketsTxnData ticketTxnDataOne = uploadTicketDao.saveUploadTicket(ticketTxnData);
				TripDetailsData tripDetailsData = new TripDetailsData();
				tripDetailsData.setOrginStop(request.getTripDetails().getOrginStop());
				tripDetailsData.setDestinationStop(request.getTripDetails().getDestinationStop());
				tripDetailsData.setRoutecode(request.getTripDetails().getRouteCode());
				tripDetailsData.setTripnumber(request.getTripDetails().getTripNumber());
				tripDetailsData.setTripDetailId(ticketTxnDataOne.getId());
				TripDetailsData tripDetailsDatao = uploadTicketDao.saveTripDetails(tripDetailsData);

				if (!StringUtil.isNull(tripDetailsDatao) && !StringUtil.isNull(ticketTxnDataOne)) {

					logger.info("Ticket Data Upload SuccessFully!!! ");
					response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());

				} else {
					response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());

					logger.info("Failed to save Ticket Data");

				}
				return response;
			}
		}
		return response;

	}
}
