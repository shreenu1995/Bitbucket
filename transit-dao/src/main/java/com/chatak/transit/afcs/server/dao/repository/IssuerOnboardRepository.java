package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.Issuer;

public interface IssuerOnboardRepository extends JpaRepository<Issuer, Long> {

	Issuer findByIssuerName(String issuerName);
	
}
