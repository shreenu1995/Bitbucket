/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;

public interface TicketTxnDataRepository extends JpaRepository<TicketsTxnData, Long> {
	@Query("SELECT e FROM TicketsTxnData e WHERE e.transactionId = :transactionId and e.ticketDateTime BETWEEN :startDate AND :endDate")
	Page<TicketsTxnData> getTicketReport(@Param("transactionId") String transactionId,
			@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM TicketsTxnData e WHERE e.ticketDateTime BETWEEN :startDate AND :endDate")
	Page<TicketsTxnData> getTicketReportDate(@Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT e FROM TicketsTxnData e WHERE e.transactionId = :transactionId ")
	Page<TicketsTxnData> getTicketReportTxnType(@Param("transactionId") String transactionId, Pageable pageable);

	@Query("SELECT e FROM TicketsTxnData e WHERE e.ticketDateTime >= :startDate")
	Page<TicketsTxnData> getTransactionReportAfterStartDate(@Param("startDate") Timestamp startDate, Pageable pageable);

	@Query("SELECT e FROM TicketsTxnData e WHERE e.ticketDateTime <= :endDate")
	Page<TicketsTxnData> getTransactionReportBeforeEndDate(@Param("endDate") Timestamp endDate, Pageable pageable);

	@Query("SELECT SUM(u.ticketFareAmount) from TicketsTxnData u where u.transactionId = :transactionId and u.shiftBatchNumber = :shiftBatchNumber")
	public Double getTotalAmount(@Param("transactionId") String transactionId,
			@Param("shiftBatchNumber") int shiftBatchNumber);

	@Query("select count(u.transactionId) from TicketsTxnData u where u.transactionId = :transactionId and u.shiftBatchNumber = :shiftBatchNumber")
	public int countTicket(@Param("transactionId") String transactionId,
			@Param("shiftBatchNumber") int shiftBatchNumber);

	@Query("SELECT max(tt.shiftBatchNumber) FROM TicketsTxnData tt where tt.deviceId = :deviceId ")
	int getMaxShiftBatchNo(@Param("deviceId") Long deviceId);
	
	@Query("SELECT count(data.deviceId) FROM TicketsTxnData data where data.deviceId = :deviceId ")
	public int getCountByDeviceId(@Param("deviceId") Long deviceId);
	
	public List<TicketsTxnData> findByPtoIdAndTransactionId(Long ptoId, String transactioId);
	
}
