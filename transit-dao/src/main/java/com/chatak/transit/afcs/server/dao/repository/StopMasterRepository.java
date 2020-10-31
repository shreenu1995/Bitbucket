package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.StopProfile;

public interface StopMasterRepository extends JpaRepository<StopProfile,Long>{

	boolean existsByStopNameIgnoreCase(String stopName);
	
	List<StopProfile> findByPtoIdAndRouteIdAndStatusNotLike(Long ptoId, Long routeId, String value);

	List<StopProfile> findByPtoIdAndStatusNotLike(String string, String value);
	
	boolean existsByStopId(Integer stopCode);
	
	StopProfile findByStopId(Integer stopCode);

}
