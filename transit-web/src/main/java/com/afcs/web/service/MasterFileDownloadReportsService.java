package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;

public interface MasterFileDownloadReportsService {

	public MasterFileDownloadResponse searchMasterFileReports(MasterFileDownloadRequest fileDownloadRequest) throws AFCSException;
}
