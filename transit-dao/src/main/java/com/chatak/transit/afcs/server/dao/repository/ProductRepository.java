package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.ProductManagement;

public interface ProductRepository extends JpaRepository<ProductManagement, Integer>{
	
	@Transactional
	@Modifying
	@Query("UPDATE ProductManagement c SET c.status = :status, c.reason = :reason WHERE c.id = :id")
	int updateStatus(@Param("status") String status, @Param("id") int i, @Param("reason") String reason);

	ProductManagement findByProductId(Long productId);

	boolean existsByProductId(Long productId);
}
