package com.chatak.transit.afcs.server.dao;

import java.util.List;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;

public interface PtoManagementDao {

	public PtoMaster savePtoRegistration(PtoMaster ptoOperationMaster);

	boolean validatePtoRegistrationRequest(PtoRegistrationRequest request);
	
	List<PtoMaster> getPtoList(PtoListRequest ptoOperationListRequest);

	PtoMaster updatePtoOperationStatus(PtoStatusUpdateRequest request);

	boolean validatePtoOperationStatusUpdate(PtoStatusUpdateRequest request);

	boolean validatePtoOPerationStatusCheck(PtoStatusCheckRequest request);

	public PtoSearchResponse searchPto(PtoSearchRequest request);

	boolean validatePtoOperationProfileUpdate(PtoUpdateRequest request);

	boolean updatePtoMaster(PtoUpdateRequest request);

	List<PtoMaster> getPtoByOrganizationId(PtoListRequest ptoOperationListRequest);

	public List<PtoMaster> getPtoListWithStatusNotTerminated(@Valid PtoListRequest ptoListRequest);

	List<PtoMaster> getPtoByPtoName(String ptoName);

	public List<PtoMaster> getPtoListByOrganizationId(@Valid PtoListRequest ptoListRequest);

	public List<PtoMaster> getActivePtoListByOrganizationId(@Valid PtoListRequest ptoListRequest);

	public List<PtoMaster> getActivePtoList(@Valid PtoListRequest ptoListRequest);
	
	List<PtoMaster> getPtoByPtoMasterId(PtoListRequest ptoListRequest);

	List<PtoMaster> getPtoByPtoMasterIdFromTerminal(PtoListRequest ptoListRequest);

	List<PtoMaster> getPtoListByPtoMasterId(PtoListRequest ptoListRequest);

	List<PtoMaster> getPtoDataByPtoMasterId(PtoListRequest ptoListRequest);

	PtoMaster getPtoByPtoMasterId(Long ptoMasterId);

}
