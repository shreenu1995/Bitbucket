package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.OpsManifest;

public interface OpsManifestRepository extends JpaRepository<OpsManifest, Long> {

	boolean existsByOpsManifestId(Long opsManifestId);

	@Transactional
	@Modifying
	@Query("UPDATE OpsManifest c SET c.status = :status WHERE c.opsManifestId = :opsManifestId")
	int updateStatus(@Param("status") String status, @Param("opsManifestId") Long opsManifestId);

}
