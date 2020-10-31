package com.chatak.transit.afcs.server.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.chatak.transit.afcs.server.dao.FinancialTransactionDao;
import com.chatak.transit.afcs.server.dao.model.FinancialTransactionData;
import com.chatak.transit.afcs.server.dao.repository.DeviceOnboardRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.FinancialTransactionRepository;
import com.chatak.transit.afcs.server.dao.repository.OperatorManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;

@Repository
public class FinancialTransactionDaoImpl implements FinancialTransactionDao {

	private static Logger logger = LoggerFactory.getLogger(FinancialTransactionDaoImpl.class);
	
	private static final String EQUIP_SERIAL_NO = "DeviceSerialNo : ";
    
	@Autowired
	DeviceTypeMasterRepository deviceTypeMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Autowired
	FinancialTransactionRepository financialRepository;
	
	@Autowired
	OperatorManagementRepository operatorManagementRepository;

	@Autowired
	DeviceOnboardRepository deviceOnboardRepository;
	
	@Override
	public boolean saveFinancialTxnData(FinancialTransactionData financialTxnData) {

		logger.debug("inside saveFinancialTxnData( )");

		return (null != financialRepository.save(financialTxnData));

	}

	@Override
	public boolean validateFinancialTxnRequest(FinancialTxnDataRequest request) {
		logger.info(EQUIP_SERIAL_NO, request.getDeviceSerialNo());
		if (operatorManagementRepository.existsByOperatorUserId(request.getUserId())
				&& deviceOnboardRepository.existsByDeviceModelId(Long.valueOf(request.getDeviceId()))
				&& ptoOperationMasterRepository.existsByPtoMasterId(Long.valueOf(request.getPtoOperationId()))
				&& deviceOnboardRepository.existsByPtoId(Long.valueOf(request.getPtoOperationId()))) {
			return true;
		}
		return false;
	}

}
