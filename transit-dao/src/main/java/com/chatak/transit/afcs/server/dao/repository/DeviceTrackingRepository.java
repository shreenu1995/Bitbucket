package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.DeviceTracking;

public interface DeviceTrackingRepository extends JpaRepository<DeviceTracking, Long> {

	DeviceTracking findByDeviceSerialAndPtoIdAndRouteId(Long deviceSerial,Long ptoId,String routeId);
	
	DeviceTracking findByDeviceSerialAndPtoId(Long deviceSerial,Long ptoId);
}
