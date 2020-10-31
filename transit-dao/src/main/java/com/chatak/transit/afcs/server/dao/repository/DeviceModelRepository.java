package com.chatak.transit.afcs.server.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.DeviceModel;

public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {

	boolean existsByDeviceModelNameAndStatusNotLike(String modelName, String status);
	
	boolean existsByDeviceIMEINumberAndStatusNotLike(String deviceIMEINumber, String status);

	@Transactional
	@Modifying
	@Query("UPDATE DeviceModel c SET c.status = :status, c.reason = :reason, c.updatedBy = :updatedBy, c.updatedTime = :updatedTime WHERE c.deviceId = :deviceId")
	int updateStatus(@Param("status") String status, @Param("deviceId") Integer deviceId,
			@Param("updatedBy") String updatedBy, @Param("updatedTime") Timestamp updatedTime,
			@Param("reason") String reason);

	boolean existsByDeviceId(Long string);

	DeviceModel findByDeviceId(Long deviceModelId);

	List<DeviceModel> findByDeviceManufacturerIdAndStatusLike(Long deviceManufacturer, String status);

	DeviceModel findByDeviceIMEINumber(String deviceimeinumber);
	
	DeviceModel findByDeviceModelName(String deviceModel);

}
