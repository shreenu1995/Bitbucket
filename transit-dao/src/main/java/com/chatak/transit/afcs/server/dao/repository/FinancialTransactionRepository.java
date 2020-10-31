package com.chatak.transit.afcs.server.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chatak.transit.afcs.server.dao.model.FinancialTransactionData;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransactionData, Long> {
	@Query("SELECT e FROM FinancialTransactionData e WHERE e.createdDateTime BETWEEN :startDate AND :endDate")
	Page<FinancialTransactionData> getRevenueReportDate(@Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM FinancialTransactionData e WHERE e.createdDateTime >= :startDate")
	Page<FinancialTransactionData> getRevenueReportAfterStartDate(@Param("startDate") Timestamp startDate,
			Pageable pageable);

	@Query("SELECT e FROM FinancialTransactionData e WHERE e.createdDateTime <= :endDate")
	Page<FinancialTransactionData> getRevenueReportBeforeEndDate(@Param("endDate") Timestamp endDate,
			Pageable pageable);

	Page<FinancialTransactionData> findByTripNo(String tripNo, Pageable pageable);

	Page<FinancialTransactionData> findByShiftBatchNo(int shiftBatchNo, Pageable pageable);

	List<FinancialTransactionData> findByTripNo(String tripNo);

	List<FinancialTransactionData> findByShiftBatchNo(int shiftBatchNo);

	@Query("SELECT max(tt.shiftBatchNo) FROM FinancialTransactionData tt where tt.deviceSerialNo = :deviceSerialNo")
	String getMaxShiftBatchNo(@Param("deviceSerialNo") String deviceSerialNo);

}
