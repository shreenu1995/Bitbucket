package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.Operator;

public interface OperatorManagementRepository extends JpaRepository<Operator, Long> {

	public boolean existsByOperatorId(Long operatorId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Operator c SET c.status = :status, c.reason = :reason WHERE c.operatorId = :operatorId")
	int updateStatus(@Param("status") String status, @Param("operatorId") Long operatorId, @Param("reason") String reason);

	public boolean existsByOperatorNameAndStatusNotLike(String operatorName, String value);
	
	List<Operator> findByStatusNotLike(String status);
	
	Operator findByOperatorId(Long operatorId);

	public boolean existsByOperatorUserId(String userId);
	
	public List<Operator> findByPtoIdAndStatusNotLike(Long ptoId, String status);

	public boolean existsByPtoId(Long ptoId);

	public boolean existsByPtoIdAndOperatorId(Long ptoId, Long operatorId);

}
