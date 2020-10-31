/**
 * 
 */
package com.chatak.transit.afcs.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.DeviceModelMangementDao;
import com.chatak.transit.afcs.server.dao.IBatchSummaryDao;
import com.chatak.transit.afcs.server.dao.OperatorManagementDao;
import com.chatak.transit.afcs.server.dao.model.BatchSummary;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.AFCSCommonResponse;
import com.chatak.transit.afcs.server.pojo.BatchUploadRequest;
import com.chatak.transit.afcs.server.service.IBatchSummaryService;

/**
 * @author pradeep
 *
 */
@Service
public class BatchSummaryServiceImpl implements IBatchSummaryService {

	private static Logger logger = LoggerFactory.getLogger(BatchSummaryServiceImpl.class);

	@Autowired
	IBatchSummaryDao batchSummaryDao;

	@Autowired
	OperatorManagementDao operatorManagementDao;

	@Autowired
	DeviceModelMangementDao deviceModelMangementDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chatak.transit.afcs.server.service.IBatchSummary#saveBatchSummaryDetails(
	 * com.chatak.transit.afcs.server.pojo.BatchUploadRequest)
	 */
	@Override
	public AFCSCommonResponse saveBatchSummaryDetails(BatchUploadRequest batchUploadRequest) {
		int status = 0;
		AFCSCommonResponse response = new AFCSCommonResponse();
		try {
			Operator op = operatorManagementDao.findByOperatorId(Long.parseLong(batchUploadRequest.getOperatorId()));
			if (op != null) {
				boolean isDeviceExists = deviceModelMangementDao
						.findDeviceByDeviceId(Long.parseLong(batchUploadRequest.getDeviceId()));
				if (isDeviceExists) {
					BatchSummary bs = new BatchSummary();
					bs.setOperator(op);
					bs.setDeviceId(Integer.parseInt(batchUploadRequest.getDeviceId()));
					bs.setCardPaymentCount(
							Integer.parseInt(batchUploadRequest.getBatchSummary().getCardPaymentCount()));
					bs.setCashPaymentCount(
							Integer.parseInt(batchUploadRequest.getBatchSummary().getCashPaymentCount()));
					bs.setTotalCount(Integer.parseInt(batchUploadRequest.getBatchSummary().getTotalCount()));
					bs.setCardAmount(Integer.parseInt(batchUploadRequest.getBatchSummary().getCardAmount()));
					bs.setCashAmount(Integer.parseInt(batchUploadRequest.getBatchSummary().getCashAmount()));
					bs.setTotalAmount(Integer.parseInt(batchUploadRequest.getBatchSummary().getTotalAmount()));
					bs.setBatchNumber(Integer.parseInt(batchUploadRequest.getBatchSummary().getBatchNumber()));
					bs.setShiftCode(Integer.parseInt(batchUploadRequest.getBatchSummary().getShiftCode()));
					status = batchSummaryDao.saveBatchSummaryData(bs);
					if (status > 0) {
						response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
						response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
					} else {
						response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
						response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
					}
				} else {
					response.setStatusCode(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorMsg());
				}
			} else {
				response.setStatusCode(CustomErrorCodes.OPERATOR_ID_NOT_EXIST.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.OPERATOR_ID_NOT_EXIST.getCustomErrorMsg());
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
