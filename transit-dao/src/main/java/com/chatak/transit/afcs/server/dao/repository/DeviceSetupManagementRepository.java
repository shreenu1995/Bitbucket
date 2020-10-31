package com.chatak.transit.afcs.server.dao.repository;

import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chatak.transit.afcs.server.dao.model.DeviceSetupManagement;

public interface DeviceSetupManagementRepository extends JpaRepository<DeviceSetupManagement, Long> {
	@Query("SELECT e FROM DeviceSetupManagement e WHERE e.deviceOemId = :deviceOemId and e.deviceModel= :deviceModel and e.generationDatetime BETWEEN :startDate AND :endDate")
	Page<DeviceSetupManagement> getTerminalSetupReport(@Param("deviceOemId") String deviceOemId,
			@Param("deviceModel") String deviceModel, @Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM DeviceSetupManagement e WHERE e.deviceOemId = :deviceOemId and e.deviceModel= :deviceModel")
	Page<DeviceSetupManagement> getTerminalSetupReportForAll(@Param("deviceOemId") String deviceOemId,
			@Param("deviceModel") String deviceModel, Pageable pageable);

	@Query("SELECT e FROM DeviceSetupManagement e WHERE  e.generationDatetime BETWEEN :startDate AND :endDate")
	Page<DeviceSetupManagement> getTerminalSetupReportDate(@Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM DeviceSetupManagement e WHERE e.generationDatetime >= :startDate")
	Page<DeviceSetupManagement> getTransactionReportAfterStartDate(@Param("startDate") Timestamp startDate,
			Pageable pageable);

	@Query("SELECT e FROM DeviceSetupManagement e WHERE e.generationDatetime <= :endDate")
	Page<DeviceSetupManagement> getTransactionReportBeforeEndDate(@Param("endDate") Timestamp endDate,
			Pageable pageable);

	@Query("SELECT e FROM DeviceSetupManagement e WHERE e.deviceOemId = :deviceOemId ")
	Page<DeviceSetupManagement> getTerminalSetupReportDeviceOemId(@Param("deviceOemId") String deviceOemId,
			Pageable pageable);

	@Query("SELECT e FROM DeviceSetupManagement e WHERE  e.deviceModel= :deviceModel")
	Page<DeviceSetupManagement> getTerminalSetupReportDeviceModel(@Param("deviceModel") String deviceModel,
			Pageable pageable);

	boolean existsByDeviceOemId(String deviceOemId);

	boolean existsByDeviceModel(String deviceModel);

}