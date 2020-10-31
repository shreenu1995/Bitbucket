package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;

public interface MasterFileReportsService {
	
	public MasterFileDownloadResponse searchMasterFileReports(MasterFileDownloadRequest masterFileDownloadRequest);

}
