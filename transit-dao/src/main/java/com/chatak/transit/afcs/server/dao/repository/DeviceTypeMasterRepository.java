package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;

public interface DeviceTypeMasterRepository extends JpaRepository<DeviceTypeMaster, Long> {

	boolean existsByDeviceTypeName(String deviceTypeName);

}
