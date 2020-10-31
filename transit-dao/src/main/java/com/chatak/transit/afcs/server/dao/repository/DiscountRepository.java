package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.DiscountMaster;

public interface DiscountRepository extends JpaRepository<DiscountMaster, Integer> {

	boolean existsByDiscountId(Long discountId);
	
	@Transactional
	@Modifying
	@Query("UPDATE DiscountMaster c SET c.status = :status, c.reason = :reason WHERE c.discountId = :discountId")
	int updateStatus(@Param("status") String status, @Param("discountId") int discountId, @Param("reason") String reason);
	
	DiscountMaster findByDiscountId(Long discountId);

}
