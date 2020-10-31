package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;
import com.chatak.transit.afcs.server.dao.model.FareMaster;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareStatusUpdate;

public interface FareManagementDao {

	boolean validateFareRegistrationRequest(FareRegistrationRequest request);

	Long saveFareRegistrationDetails(FareMaster request);

	FareSearchResponse getFareList(FareSearchRequest request);

	boolean validateFare(FareStatusUpdate fareStatusUpdate);

	FareMaster updateFareStatus(FareStatusUpdate request);

	boolean updateFare(FareStatusUpdate fareUpdate);
	
	boolean saveBulkUpload(BulkUploadDetails request);
	
	List<BulkUploadDetails> getAllBulkFares(Long ptoId);
	
	List<BulkUploadDetails> getFareByStopId(String stopId);

}
