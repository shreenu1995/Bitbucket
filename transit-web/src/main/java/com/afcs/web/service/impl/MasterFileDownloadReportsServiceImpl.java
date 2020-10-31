package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.MasterFileDownloadReportsService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class MasterFileDownloadReportsServiceImpl implements MasterFileDownloadReportsService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public MasterFileDownloadResponse searchMasterFileReports(MasterFileDownloadRequest fileDownloadRequest)
			throws AFCSException {
		MasterFileDownloadResponse response = jsonUtil.postRequest(fileDownloadRequest,
				MasterFileDownloadResponse.class, "masterFileDownloadReports/searchMasterFileReports");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
