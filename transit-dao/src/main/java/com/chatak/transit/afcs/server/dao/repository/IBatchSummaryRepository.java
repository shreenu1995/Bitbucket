/**
 * 
 */
package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.BatchSummary;

/**
 * @author pradeep
 *
 */
public interface IBatchSummaryRepository extends JpaRepository<BatchSummary, Long> {

}
