/**
 * 
 */
package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.BatchSummary;

/**
 * @author pradeep
 *
 */
public interface IBatchSummaryDao {
	
	/**
	 * 
	 * @param batchSummary
	 * @return
	 */
	int saveBatchSummaryData(BatchSummary batchSummary);
}
