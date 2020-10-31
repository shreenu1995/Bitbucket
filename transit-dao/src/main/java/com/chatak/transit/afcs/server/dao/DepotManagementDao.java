/**
 * @author Girmiti SOftware
 *
 */
package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.DepotMaster;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;

public interface DepotManagementDao {

	public DepotMaster saveDepotRegistration(DepotMaster depotMaster); 

	public boolean validateGetDepotListView(DepotListViewRequest request);

	public List<DepotMaster> getDepotListView(DepotMaster depotMaster);

	boolean validateDepotProfileUpdateRequest(DepotProfileUpdateRequest request);

	boolean updateDepotProfile(DepotProfileUpdateRequest request);

	boolean validateDepotStatusUpdateRequest(DepotStatusUpdateRequest request);

	DepotMaster updateDepotStatus(DepotStatusUpdateRequest request);

	boolean checkDepotStatus(DepotStatusCheckRequest request);

	boolean validateDepotStatusCheck(DepotStatusCheckRequest request);
	
	public boolean validateDepotName(DepotRegistrationRequest request);

	public DepotSearchResponse searchdepot(DepotSearchRequest request);

}
