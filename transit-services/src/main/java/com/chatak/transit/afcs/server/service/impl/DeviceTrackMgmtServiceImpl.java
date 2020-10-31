package com.chatak.transit.afcs.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.DeviceModelMangementDao;
import com.chatak.transit.afcs.server.dao.DeviceTrackingDao;
import com.chatak.transit.afcs.server.dao.model.DeviceTracking;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.AFCSCommonResponse;
import com.chatak.transit.afcs.server.pojo.TransitLocationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;
import com.chatak.transit.afcs.server.service.DeviceTrackMgmtService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DeviceTrackMgmtServiceImpl extends ServerConstants implements DeviceTrackMgmtService {

	@Autowired
	DeviceTrackingDao deviceTrackingDao;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@Autowired
	private DeviceModelMangementDao deviceModelMangementDao;
	
	@Override
	public DeviceTrackingResponse searchDeviceTracking(DeviceTrackingRequest request) {
		DeviceTrackingResponse response = deviceTrackingDao.searchDeviceTracking(request);
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
	public AFCSCommonResponse updateLocation(TransitLocationUpdateRequest locationUpdateRequest) {
		DeviceTracking deviceTracking = new DeviceTracking();
		AFCSCommonResponse response = new AFCSCommonResponse();
		if (!StringUtil.isNull(locationUpdateRequest.getDeviceId())
				&& deviceModelMangementDao.findDeviceByDeviceId(Long.parseLong(locationUpdateRequest.getDeviceId()))) {
			deviceTracking.setDeviceSerial(Long.parseLong(locationUpdateRequest.getDeviceId()));
			if (!StringUtil.isNull(locationUpdateRequest.getPtoId())
					&& ptoMasterRepository.existsByPtoMasterId(Long.valueOf(locationUpdateRequest.getPtoId()))) {
				String ptoId = locationUpdateRequest.getPtoId();
				PtoMaster ptoList = ptoMasterRepository.findByPtoMasterId(Long.parseLong(ptoId));
				deviceTracking.setPtoId(ptoList.getPtoMasterId());
				deviceTracking.setLatitude(locationUpdateRequest.getLatitude());
				deviceTracking.setLongitude(locationUpdateRequest.getLongitude());
				deviceTracking.setStatus("On Track");
				deviceTracking = deviceTrackingDao.updateLocation(deviceTracking);
				if (!StringUtil.isNull(deviceTracking)) {
					response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				} else {
					response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
				}
			} else {
				response.setStatusCode(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorMsg());
			}
		} else {
			response.setStatusCode(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorMsg());
		}
		return response;
	}
}
