package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.PaymentGateway;

public interface PgOnboardRepository extends JpaRepository<PaymentGateway, Long> {

	PaymentGateway findByPgName(String pgName);
	
}
