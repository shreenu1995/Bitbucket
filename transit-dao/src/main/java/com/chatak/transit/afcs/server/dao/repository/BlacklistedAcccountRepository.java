package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.BlacklistedAccount;

public interface BlacklistedAcccountRepository extends JpaRepository<BlacklistedAccount, Long> {
   
	public boolean existsByIssuerName(String issuerName);
}
