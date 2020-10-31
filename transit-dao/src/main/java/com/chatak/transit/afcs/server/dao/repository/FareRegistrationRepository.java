package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.FareMaster;

public interface FareRegistrationRepository extends JpaRepository<FareMaster, Long> {

	boolean existsByFareName(String fareName);

	boolean existsByFareId(Long fareId);
	
	FareMaster findByFareId(Long fareId);
}
