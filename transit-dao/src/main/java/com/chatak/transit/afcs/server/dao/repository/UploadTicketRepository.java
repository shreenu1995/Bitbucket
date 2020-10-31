package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;

public interface UploadTicketRepository extends JpaRepository<TicketsTxnData, Long> {

}
