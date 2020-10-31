package com.chatak.transit.afcs.server.dao.repository;

import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;

public interface AdminSessionManagementRepository extends JpaRepository<AdminSessionManagement, Long> {
	@Query("SELECT e FROM AdminSessionManagement e WHERE e.transactionId = :transactionId and e.generateDateAndTime BETWEEN :startDate AND :endDate")
	Page<AdminSessionManagement> getOperatorSessionReport(@Param("transactionId") String transactionId,
			@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM AdminSessionManagement e WHERE e.generateDateAndTime BETWEEN :startDate AND :endDate")
	Page<AdminSessionManagement> getOperatorSessionReportDate(@Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM AdminSessionManagement e WHERE e.transactionId = :transactionId ")
	Page<AdminSessionManagement> getOperatorSessionTxnType(@Param("transactionId") String transactionId,
			Pageable pageable);

	@Query("SELECT e FROM AdminSessionManagement e WHERE e.generateDateAndTime >= :startDate")
	Page<AdminSessionManagement> getOperatorSessionReportAfterStartDate(@Param("startDate") Timestamp startDate,
			Pageable pageable);

	@Query("SELECT e FROM AdminSessionManagement e WHERE e.generateDateAndTime <= :endDate")
	Page<AdminSessionManagement> getOperatorSessionReportBeforeEndDate(@Param("endDate") Timestamp endDate,
			Pageable pageable);

}
