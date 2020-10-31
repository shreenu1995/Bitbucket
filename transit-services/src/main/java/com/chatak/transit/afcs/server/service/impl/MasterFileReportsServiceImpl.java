package com.chatak.transit.afcs.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.MasterFileReportsServiceDao;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;
import com.chatak.transit.afcs.server.service.MasterFileReportsService;

@Service
public class MasterFileReportsServiceImpl implements MasterFileReportsService {

	@Autowired
	private MasterFileReportsServiceDao masterFileReportsServiceDao;

	@Override
	public MasterFileDownloadResponse searchMasterFileReports(MasterFileDownloadRequest masterFileDownloadRequest) {
		MasterFileDownloadResponse response = masterFileReportsServiceDao
				.searchMasterFileReports(masterFileDownloadRequest);
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return response;
	}

}
