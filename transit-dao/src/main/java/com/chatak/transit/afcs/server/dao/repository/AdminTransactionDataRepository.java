package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatak.transit.afcs.server.dao.model.AdminTransactionData;

public interface AdminTransactionDataRepository extends JpaRepository<AdminTransactionData, Long> {

}