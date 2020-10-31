package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;

public interface BulkUploadRepository extends JpaRepository<BulkUploadDetails, Long>{
	
	 public List<BulkUploadDetails> findByPtoId(Long ptoId);
	 
	 List<BulkUploadDetails> findByStartStopCode(String stopId);

}
