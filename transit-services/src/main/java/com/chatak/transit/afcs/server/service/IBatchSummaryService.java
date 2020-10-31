/**
 * 
 */
package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.AFCSCommonResponse;
import com.chatak.transit.afcs.server.pojo.BatchUploadRequest;

/**
 * @author pradeep
 *
 */
public interface IBatchSummaryService {

	/**
	 * 
	 * @param batchUploadRequest
	 * @return
	 */
	AFCSCommonResponse saveBatchSummaryDetails(BatchUploadRequest batchUploadRequest);
}
