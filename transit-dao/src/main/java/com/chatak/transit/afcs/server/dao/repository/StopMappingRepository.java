package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.StopProfile;

public interface StopMappingRepository extends JpaRepository<StopProfile, Long>{
	
	List<StopProfile> findByStageId(Long stageId);
	
	@Transactional
	void deleteByStopId(Long stopId);

}
