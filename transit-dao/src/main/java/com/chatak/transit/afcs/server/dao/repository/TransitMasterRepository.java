package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.TransitMasterMaintenance;

public interface TransitMasterRepository extends JpaRepository<TransitMasterMaintenance, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE TransitMasterMaintenance c SET c.status = :status WHERE c.transitMasterId = :transitMasterId")
	Long updateStatus(@Param("status") String status, @Param("transitMasterId") Long transitMasterId);

	TransitMasterMaintenance findByTransitMasterId(Long transitMasterId);

	List<TransitMasterMaintenance> findByPtoId(Long ptoId);

}
