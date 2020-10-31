/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.model.DepotMaster;

public interface DepotMasterRepository extends JpaRepository<DepotMaster, Long> {

	boolean existsByDepotId(long depotId);

	@Transactional
	@Modifying
	@Query("UPDATE DepotMaster c SET c.status = :status, c.reason = :reason WHERE c.depotId = :depotId")
	int updateStatus(@Param("status") String status, @Param("depotId") int depotId, @Param("reason") String reason);

	boolean existsByDepotIdAndStatus(Long depotId, boolean status);
	
	DepotMaster findByDepotId(long depotId);

	boolean existsByDepotNameAndStatusNotLike(String depotName, String status);
}
