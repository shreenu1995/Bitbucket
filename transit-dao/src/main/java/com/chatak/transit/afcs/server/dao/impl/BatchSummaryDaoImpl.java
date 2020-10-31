/**
 * 
 */
package com.chatak.transit.afcs.server.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.IBatchSummaryDao;
import com.chatak.transit.afcs.server.dao.model.BatchSummary;
import com.chatak.transit.afcs.server.dao.repository.IBatchSummaryRepository;
import com.chatak.transit.afcs.server.util.StringUtil;

/**
 * @author pradeep
 *
 */
@Repository
public class BatchSummaryDaoImpl implements IBatchSummaryDao {

	private static Logger logger = LoggerFactory.getLogger(BatchSummaryDaoImpl.class);

	@Autowired
	IBatchSummaryRepository batchSummaryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chatak.transit.afcs.server.dao.IBatchSummaryDao#saveBatchSummaryDetails(
	 * com.chatak.transit.afcs.server.dao.model.BatchSummary)
	 */
	@Override
	public int saveBatchSummaryData(BatchSummary batchSummary) {
		try {
			BatchSummary bs = batchSummaryRepository.save(batchSummary);
			if (!StringUtil.isNull(bs)) {
				return 1;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

}
