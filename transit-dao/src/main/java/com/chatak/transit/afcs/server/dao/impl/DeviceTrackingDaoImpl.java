package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.DeviceTrackingDao;
import com.chatak.transit.afcs.server.dao.model.DeviceTracking;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceTracking;
import com.chatak.transit.afcs.server.dao.repository.DeviceTrackingRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DeviceTrackingDaoImpl implements DeviceTrackingDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	PtoMasterRepository ptoRepo;
	
	@Autowired
	DeviceTrackingRepository deviceTrackingRepository;
	
	@Override
	public DeviceTrackingResponse searchDeviceTracking(DeviceTrackingRequest request) {
		Integer pageIndexNo = request.getIndexPage();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(entityManager);
		
		QDeviceTracking deviceTracking = QDeviceTracking.deviceTracking;
		
		List<DeviceTracking> listOfDeviceTracking = query.from(deviceTracking)
				.where(isPtoId(request.getPtoId()), isRouteId(request.getRouteId()),
						isDeviceId(request.getDeviceSerialNo()))
				.offset(fromIndex).limit(10).orderBy(deviceTracking.deviceSerial.desc()).list(deviceTracking);
		DeviceTrackingResponse response = new DeviceTrackingResponse();
		List<DeviceTrackingRequest> deviceTrackingRequestList = new ArrayList<>();
		for (DeviceTracking tuple : listOfDeviceTracking) {
			DeviceTrackingRequest devRequest = new DeviceTrackingRequest();
			PtoMaster ptoName = ptoRepo.findByPtoMasterId(tuple.getPtoId()!=null?tuple.getPtoId():null);
			devRequest.setPtoName(!StringUtil.isNull(ptoName) ? ptoName.getPtoName() : "N/A");
			devRequest.setDeviceSerialNo(tuple.getDeviceSerial());
			devRequest.setStatus(tuple.getStatus());
			devRequest.setLatitude(tuple.getLatitude());
			devRequest.setLongitude(tuple.getLongitude());
			deviceTrackingRequestList.add(devRequest);
		}
		response.setNoOfRecords(
				getDeviceTrackingRecordTotal(request.getPtoId(), request.getRouteId(), request.getDeviceSerialNo()));
		response.setDeviceTrackingRequest(deviceTrackingRequestList);
		return response;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QDeviceTracking.deviceTracking.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isRouteId(String routeId) {
		return !StringUtil.isNullEmpty(routeId) ? QDeviceTracking.deviceTracking.routeId.eq(routeId) : null;
	}
	
	private BooleanExpression isDeviceId(Long deviceId) {
		return (deviceId != null) ? QDeviceTracking.deviceTracking.deviceSerial.eq(deviceId) : null;
	}

	private int getDeviceTrackingRecordTotal(Long ptoId, String routeId, Long deviceId) {

		JPAQuery query = new JPAQuery(entityManager);
		Long count = query
				.from(QDeviceTracking.deviceTracking).where(isPtoId(ptoId), isRouteId(routeId),
						isDeviceId(deviceId))
				.orderBy(QDeviceTracking.deviceTracking.deviceSerial.desc()).count();
		return count != null ? count.intValue() : 0;
	}

	@Override
	public void saveDeviceTrackingdetails(DeviceTracking deviceTracking) {
		DeviceTracking deviceTrack =  deviceTrackingRepository.findByDeviceSerialAndPtoIdAndRouteId(deviceTracking.getDeviceSerial(), deviceTracking.getPtoId(), deviceTracking.getRouteId());
		if(deviceTrack!=null){
			deviceTracking.setDeviceTrackingId(deviceTrack.getDeviceTrackingId());
		}
		deviceTrackingRepository.save(deviceTracking);
	}
	
	@Override
	public DeviceTracking updateLocation(DeviceTracking deviceTracking) {
		DeviceTracking deviceTrack = deviceTrackingRepository
				.findByDeviceSerialAndPtoId(deviceTracking.getDeviceSerial(), deviceTracking.getPtoId());
		if (deviceTrack != null) {
			deviceTracking.setDeviceTrackingId(deviceTrack.getDeviceTrackingId());
		}
		return deviceTrackingRepository.save(deviceTracking);
	}
}
