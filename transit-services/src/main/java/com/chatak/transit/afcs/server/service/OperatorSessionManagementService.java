/**
 * @author Girmiti Software
 */

package com.chatak.transit.afcs.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.AdminSessionRequest;
import com.chatak.transit.afcs.server.pojo.AdminSessionResponse;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;

public interface OperatorSessionManagementService {

	AdminSessionResponse saveAdminSessionRequest(AdminSessionRequest request, HttpServletResponse httpResponse,
			AdminSessionResponse response) throws PosException, JsonProcessingException;

	boolean validateAdminDataLength(String request);

	void adminSessionErrors(AdminSessionRequest request, AdminSessionResponse response);

	void softwareDownloadRequest(FileDownloadRequest request, HttpServletResponse httpResponse);

	void checkFileDownloadErrors(FileDownloadRequest request, FileDownloadResponse response);

	boolean validateFileDataLength(String data);

	String checkVersion(FileDownloadRequest request, HttpServletResponse httpResponse, FileDownloadResponse response);
	
	OperatorSessionReportGenerationResponse generateOperatorSessionReport(OperatorSessionReportGenerationRequest request);
	
	TransitFileDownloadResponse checkTransitVersion(TransitFileDownloadRequest request, HttpServletResponse httpResponse, TransitFileDownloadResponse response);
	
	TransitFileDownloadResponse downloadMasterData(TransitFileDownloadRequest request, HttpServletResponse httpResponse, HttpServletRequest httpRequest);

}
