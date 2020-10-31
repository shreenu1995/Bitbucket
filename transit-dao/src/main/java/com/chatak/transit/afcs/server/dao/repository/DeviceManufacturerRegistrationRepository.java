package com.chatak.transit.afcs.server.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.DeviceManufacturerMaster;

public interface DeviceManufacturerRegistrationRepository extends JpaRepository<DeviceManufacturerMaster, Long> {

	boolean existsByDeviceManufacturer(String deviceManufacturer);

	boolean existsByDeviceManufacturerId(Long deviceManufacturerCode);

	@Transactional
	@Modifying
	@Query("UPDATE DeviceManufacturerMaster c SET c.status = :status, c.updatedBy = :updatedBy, c.updatedTime = :updatedTime WHERE c.deviceManufacturerId = :deviceManufacturerId")
	int updateStatus(@Param("status") String status, @Param("deviceManufacturerId") Long i,
			@Param("updatedBy") String updatedBy, @Param("updatedTime") Timestamp updatedTime);

	List<DeviceManufacturerMaster> findByDeviceTypeId(Long deviceTypeName);
	
	DeviceManufacturerMaster findByDeviceTypeId(Integer deviceTypeId);
	
	DeviceManufacturerMaster findByDeviceManufacturerId(Long deviceManufacturerId);
	
	DeviceManufacturerMaster findByDeviceManufacturer(String deviceManufacturerName);

}
