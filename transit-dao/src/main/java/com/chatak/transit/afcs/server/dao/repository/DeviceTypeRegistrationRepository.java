package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;

public interface DeviceTypeRegistrationRepository extends JpaRepository<DeviceTypeMaster, Long> {

	boolean existsByDeviceTypeName(String deviceTypeName);

	boolean existsByDeviceTypeId(Long deviceTypeId);

	List<DeviceTypeMaster> findByDeviceTypeId(Long deviceTypeId);
	
	DeviceTypeMaster findByDeviceTypeIdAndStatusLike(Long deviceTypeId, String status);

}
