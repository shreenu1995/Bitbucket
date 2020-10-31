package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chatak.transit.afcs.server.dao.model.StageMaster;

public interface StageNameRepository extends JpaRepository<StageMaster, String> {
	
	@Query("select p.stageName from StageMaster p ")
	public List<String> findAllStageName();
	
	@Query("select p.stageName from StageMaster p where  p.stageId = :stageId")
	public String findStageNameById(@Param("stageId") Long stageId);

}
