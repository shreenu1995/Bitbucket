package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.DeviceOnboardMaster;

public interface DeviceOnboardRepository extends JpaRepository<DeviceOnboardMaster, Long> {

	boolean existsByDeviceOnboardId(Long deviceOnboardId);
	
	@Transactional
	@Modifying
	@Query("UPDATE DeviceOnboardMaster c SET c.status = :status, c.reason = :reason WHERE c.deviceOnboardId = :deviceOnboardId")
	Long updateStatus(@Param("status") String status, @Param("deviceOnboardId") Long deviceOnboardId,
			@Param("reason") String reason);
	
	boolean existsByDeviceModelIdAndStatusNotLike(Long deviceModelId, String status);

	List<DeviceOnboardMaster> findByDeviceModelIdAndStatusNotLike(Long deviceModelId, String status);
	
	DeviceOnboardMaster findByDeviceOnboardId(Long deviceOnboardId);

	boolean existsByPtoIdAndDeviceModelId(Long ptoId, Long deviceModelId);

	boolean existsByPtoId(Long ptoId);

	boolean existsByDeviceModelId(Long deviceModelId);

}
