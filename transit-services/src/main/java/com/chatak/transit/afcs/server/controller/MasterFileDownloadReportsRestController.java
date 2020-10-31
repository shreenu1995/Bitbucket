package com.chatak.transit.afcs.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;
import com.chatak.transit.afcs.server.service.MasterFileReportsService;

@RestController
@RequestMapping(value = "/masterFileDownloadReports/")
public class MasterFileDownloadReportsRestController {

	@Autowired
	private MasterFileReportsService masterFileReportsService;
	
	@PostMapping(value = "searchMasterFileReports")
	public MasterFileDownloadResponse searchMasterFileReports(@RequestBody MasterFileDownloadRequest masterFileDownloadRequest) {
		return masterFileReportsService.searchMasterFileReports(masterFileDownloadRequest);
	}
}
