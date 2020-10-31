package com.chatak.transit.afcs.server.dao;

import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;

@Repository
public interface MasterFileReportsServiceDao {

	public MasterFileDownloadResponse searchMasterFileReports(MasterFileDownloadRequest masterFileDownloadRequest);
}
