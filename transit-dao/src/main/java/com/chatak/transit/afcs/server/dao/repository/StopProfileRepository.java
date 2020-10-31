package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.StopProfile;

public interface StopProfileRepository extends JpaRepository<StopProfile, Integer> {
	
	StopProfile findByStopId(Long stopId);
	
	boolean existsByStopNameIgnoreCase(String stopName);
	
	boolean existsByStopId(Long stopCode);
	
	List<StopProfile> findByPtoIdAndStatusNotLike(Long ptoId, String value);
}
