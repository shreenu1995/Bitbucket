package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.MasterFileReportsServiceDao;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;

@Repository
public class MasterFileReportsServiceDaoImpl implements MasterFileReportsServiceDao {

	@Override
	public MasterFileDownloadResponse searchMasterFileReports(MasterFileDownloadRequest masterFileDownloadRequest) {
		MasterFileDownloadResponse downloadResponse = new MasterFileDownloadResponse();
		List<MasterFileDownloadRequest> downloadRequestlist = new ArrayList<>();
		for (int i = 0; i <= 9; i++) {
			MasterFileDownloadRequest downloadRequest = new MasterFileDownloadRequest();
			downloadRequest.setPtoName("PTO" + i);
			downloadRequest.setFileName("FileName" + i);
			downloadRequest.setFileVersion(i + ":" + "0" + ":" + i);
			downloadRequest.setDeviceSerial(i + "12" + i + "34");
			downloadRequest.setTime("1" + i + ":" + i + "0" + ":" + "0" + i);
			downloadRequest.setLocation("Location" + i);
			downloadRequestlist.add(downloadRequest);
		}
		downloadResponse.setTotalNoOfRecords(10);
		downloadResponse.setListOfAfceMasterFileReport(downloadRequestlist);
		return downloadResponse;
	}

}
