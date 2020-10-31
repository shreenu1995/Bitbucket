/**
 * @author Girmiti Software
 */

package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.AdminSessionRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;

public interface OperatorSessionManagementDao {

	boolean saveAdminSessionRequest(AdminSessionManagement adminSessionManagement);

	boolean validateAdminSessionRequest(AdminSessionRequest request) throws PosException;

	boolean validateFileDownloadRequest(FileDownloadRequest request, FileDownloadResponse response);
	
	OperatorSessionReportGenerationResponse generateOperatorSessionReport(OperatorSessionReportGenerationRequest request);

	boolean validateTransitFileDownloadRequest(TransitFileDownloadRequest request,
			TransitFileDownloadResponse response);
}
