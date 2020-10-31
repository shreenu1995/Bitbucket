package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.EmailMaster;

public interface EmailServiceRepository extends JpaRepository<EmailMaster, Long> {
	
	public List<EmailMaster> findByStatus(String status);
	
	public EmailMaster findByEmailId(Long emailId);
	
}
