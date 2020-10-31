/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.controller.HttValidation;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.TerminalSetupDao;
import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.dao.model.DeviceOnboardMaster;
import com.chatak.transit.afcs.server.dao.model.DeviceSetupManagement;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceModel;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceOnboardRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpResponse;
import com.chatak.transit.afcs.server.pojo.TransitTerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TransitTerminalSetUpResponse;
import com.chatak.transit.afcs.server.service.TerminalSetUpService;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class TerminalSetupServiceImpl extends HttValidation implements TerminalSetUpService {

	Logger logger = LoggerFactory.getLogger(TerminalSetupServiceImpl.class);

	private static final String SETUP_DATA_LENGTH = "Setup Data Length is: ";

	@Autowired
	TicketTxnDataRepository ticketRepository;

	@Autowired
	CustomErrorResolution customErrorResolution;

	@Autowired
	TerminalSetupDao terminalSetupDao;

	@Autowired
	DeviceOnboardRepository deviceOnboardRepository;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository; 
	
	@Autowired
	DeviceModelRepository deviceModelRepository; 

	@PersistenceContext
	private EntityManager em;

	@Override
	public String getTerminalSetupRequest(TerminalSetUpRequest request, HttpServletResponse httpResponse)
			throws PosException, IOException, ParseException {
		TerminalSetUpResponse response = new TerminalSetUpResponse();
		boolean isValidTimeStamp = isTimeStampValid(request.getDateTime());
	   	if (validateTerminalSetupRequest(request) && isValidTimeStamp) {
			DeviceSetupManagement deviceSetupManagement = new DeviceSetupManagement();
			deviceSetupManagement.setDeviceModel(request.getDeviceModelNumber());
			deviceSetupManagement.setDeviceOemId(request.getDeviceOemId());
			deviceSetupManagement.setGenerationDatetime(Timestamp.valueOf(request.getDateTime()));
			deviceSetupManagement.setProcessDateAndtime(Timestamp.from(Instant.now()));
			DeviceModel deviceModel1 = deviceModelRepository.findByDeviceModelName(request.getDeviceModelNumber());
			deviceSetupManagement.setDeviceId(deviceModel1.getDeviceId());
			List<DeviceOnboardMaster> deviceOnboardMaster1 =  deviceOnboardRepository.findByDeviceModelIdAndStatusNotLike(deviceModel1.getDeviceId(), Status.TERMINATED.getValue());
			deviceSetupManagement.setPtoId(deviceOnboardMaster1.get(0).getPtoId());
			DeviceModel deviceModel = terminalSetupDao.getTerminalSetupRequest(deviceSetupManagement);
			DeviceModel model = deviceModelRepository.findByDeviceModelName(request.getDeviceModelNumber());
			List<DeviceOnboardMaster> deviceOnboardMaster =  deviceOnboardRepository.findByDeviceModelIdAndStatusNotLike(model.getDeviceId(), Status.TERMINATED.getValue());
			PtoMaster ptoMasterName = ptoMasterRepository.findByPtoMasterId(deviceOnboardMaster.get(0).getPtoId());
			List<PtoMaster> ptoMaster =  ptoMasterRepository.findByPtoNameAndStatusLike(ptoMasterName.getPtoName(),Status.ACTIVE.getValue());
			
			if (deviceModel != null) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setDeviceId(String.valueOf(deviceModel.getDeviceId()));
				try {
					response.setPtoOperationId(String.valueOf(ptoMaster.get(0).getPtoMasterId()));
					response.setPtoName(ptoMaster.get(0).getPtoName());
				} catch (Exception e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			    response.setChecksum("");
			    if (ticketRepository.getCountByDeviceId(deviceModel.getDeviceId()) == 0) {
					response.setReserverd(String.valueOf(1));
				} else {
					response.setReserverd(
							String.valueOf(ticketRepository.getMaxShiftBatchNo(deviceModel.getDeviceId())
									+ ServerConstants.COUNT));

				}

			} else {
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				response.setDeviceId("");
				response.setPtoOperationId("");
				response.setChecksum("");
				response.setReserverd("");
			}
	   	}
		 else {
			terminalSetupErrors(request, response,isValidTimeStamp);
			response.setDeviceId("");
			response.setPtoOperationId("");
			response.setReserverd("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			logger.debug("Data Validation Failed.");
		}
		return response.toString();
	}
	
	@Override
	public boolean validateTerminalSetupRequest(TerminalSetUpRequest request) throws PosException, ParseException {
		JPAQuery query = new JPAQuery(em);
		return query.from(QDeviceModel.deviceModel)
				.where(QDeviceModel.deviceModel.deviceModelName.eq(request.getDeviceModelNumber()),
						QDeviceModel.deviceModel.deviceIMEINumber.eq(request.getDeviceOemId()))
				.exists();
	}

	@Override
	public boolean validateSetupDataLength(String data) {
		logger.debug("Inside validateSetupDataLength() function ");
		logger.debug(SETUP_DATA_LENGTH, (data.length() == ServerConstants.SETUP_DATA_LENGTH));
		return (data.length() == ServerConstants.SETUP_DATA_LENGTH);
	}

	@Override
	public void terminalSetupErrors(TerminalSetUpRequest request, TerminalSetUpResponse response,
			boolean isValidTimeStamp) {
		if (!customErrorResolution.isValidDeviceModelNumber(request.getDeviceModelNumber())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorMsg());
		} else if (!isValidTimeStamp) {
			response.setStatusCode(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorMsg());
		}
	}

    @Override
    public TransitTerminalSetUpResponse getTransitTerminalSetupRequest(
        TransitTerminalSetUpRequest request, HttpServletResponse httpResponse) {
      TransitTerminalSetUpResponse response = new TransitTerminalSetUpResponse();
      if(deviceModelRepository.existsByDeviceIMEINumberAndStatusNotLike(request.getDeviceSerialNumber(), Status.TERMINATED.getValue())){
        if(deviceModelRepository.existsByDeviceModelNameAndStatusNotLike(request.getDeviceModelNumber(), Status.TERMINATED.getValue())){
          try {
            boolean isValidTimeStamp = isTimeStampValid(request.getCurrentDateTime());
            if (validateTransitTerminalSetupRequest(request) && isValidTimeStamp) {
              DeviceSetupManagement deviceSetupManagement = new DeviceSetupManagement();
              deviceSetupManagement.setDeviceModel(request.getDeviceModelNumber());
              deviceSetupManagement.setDeviceOemId(request.getDeviceSerialNumber());
              deviceSetupManagement
                  .setGenerationDatetime(Timestamp.valueOf(request.getCurrentDateTime()));
              deviceSetupManagement.setProcessDateAndtime(Timestamp.from(Instant.now()));
              DeviceModel deviceModel = terminalSetupDao.getTerminalSetupRequest(deviceSetupManagement);
              if (deviceModel != null) {
              List<DeviceOnboardMaster> deviceOnboardMaster = deviceOnboardRepository.findByDeviceModelIdAndStatusNotLike(deviceModel.getDeviceId(), Status.TERMINATED.getValue());
              PtoMaster ptoMaster =
                  ptoMasterRepository.findByPtoMasterId(deviceOnboardMaster.get(0).getPtoId());
                response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
                response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
                response.setDeviceId(String.valueOf(deviceModel.getDeviceId()));
                response.setPtoId(String.valueOf(ptoMaster.getPtoMasterId()));
                response.setPtoName(ptoMaster.getPtoName());
                setShiftBatchNo(response, deviceModel);
              } else {
                httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(),
                    response.toString());
                response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
                response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
              }
            } else {
              transitTerminalSetupErrors(request, response, isValidTimeStamp);
              httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
              logger.debug("Data Validation Failed.");
            }
          } catch (Exception e) {
            logger.error(
                "ERROR :: TerminalSetupServiceImpl class :: getTransitTerminalSetupRequest method :: exception",
                e);
          }
        }else{
          response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorCode());
          response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorMsg());
        }
      }else{
        response.setStatusCode(CustomErrorCodes.DEVICEOEMID_NOT_EXIST.getCustomErrorCode());
        response.setStatusMessage(CustomErrorCodes.DEVICEOEMID_NOT_EXIST.getCustomErrorMsg());
      }
      response.setRequestID(request.getRequestID());
      return response;
    }

	private void setShiftBatchNo(TransitTerminalSetUpResponse response, DeviceModel deviceModel) {
		if (ticketRepository.getCountByDeviceId(deviceModel.getDeviceId()) == 0) {
		  response.setShiftBatchNumber(String.valueOf(1));
		} else {
		  response.setShiftBatchNumber(String.valueOf(
		      ticketRepository.getMaxShiftBatchNo(deviceModel.getDeviceId())
		          + ServerConstants.COUNT));
		}
	}

    @Override
    public boolean validateTransitTerminalSetupRequest(TransitTerminalSetUpRequest request)
        throws PosException, ParseException {
      JPAQuery query = new JPAQuery(em);
      return query.from(QDeviceModel.deviceModel)
          .where(QDeviceModel.deviceModel.deviceModelName.eq(request.getDeviceModelNumber()),
              QDeviceModel.deviceModel.deviceIMEINumber.eq(request.getDeviceSerialNumber()))
          .exists();
    }

    @Override
    public void transitTerminalSetupErrors(TransitTerminalSetUpRequest request,
        TransitTerminalSetUpResponse response, boolean isValidTimeStamp) {
      if (!customErrorResolution.isValidDeviceModelNumber(request.getDeviceModelNumber())) {
        response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorCode());
        response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorMsg());
      } else if (!isValidTimeStamp) {
        response.setStatusCode(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorCode());
        response.setStatusMessage(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorMsg());
      }
    }
  }
