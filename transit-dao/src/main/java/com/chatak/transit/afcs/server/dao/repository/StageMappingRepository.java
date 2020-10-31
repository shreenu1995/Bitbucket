package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.StageMaster;

public interface StageMappingRepository extends JpaRepository<StageMaster, Long> {

	List<StageMaster> findByRouteId(Long routeId);

	@Transactional
	void deleteByStageId(int stageId);
}
