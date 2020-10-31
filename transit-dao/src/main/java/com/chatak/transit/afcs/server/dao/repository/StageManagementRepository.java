package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.StageMaster;

public interface StageManagementRepository extends JpaRepository<StageMaster, Long> {

	public boolean existsByStageName(String stageName);

	public List<StageMaster> findByStageId(String stageId);

	public StageMaster findByStageId(Long stageId);
	
	public List<StageMaster> findByRouteId(Long routeId);

}
