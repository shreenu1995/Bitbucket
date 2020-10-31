package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.SetupSoftwareMaintenance;

public interface SetupSoftwareVersionRepository extends JpaRepository<SetupSoftwareMaintenance, Integer>{

	@Transactional
	@Modifying
	@Query("UPDATE SetupSoftwareMaintenance c SET c.status = :status WHERE c.softwareId = :softwareId")
	int updateStatus(@Param("status") String status,@Param("softwareId") Long softwareId);
	
	SetupSoftwareMaintenance findBySoftwareId(Long softwareId);
	
	List<SetupSoftwareMaintenance> findByPtoId(Long ptoId);

}
