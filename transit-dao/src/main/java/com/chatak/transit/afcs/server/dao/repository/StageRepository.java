package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.StageMaster;

public interface StageRepository extends JpaRepository<StageMaster, Long> {

	boolean existsByStageId(Long stageId);

	@Transactional
	@Modifying
	@Query("UPDATE StageMaster c SET c.status = :status, c.reason = :reason WHERE c.stageId = :stageId")
	int updateStatus(@Param("status") String status, @Param("stageId") int i, @Param("reason") String reason);
	
	StageMaster findByStageId(Long stageId);

}
