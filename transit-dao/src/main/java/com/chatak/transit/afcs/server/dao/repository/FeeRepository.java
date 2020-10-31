package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.FeeMaster;

public interface FeeRepository extends JpaRepository<FeeMaster, Long> {
	
	FeeMaster findByFeeId(long feeId);
	
	boolean existsByFeeId(long feeId);
}
