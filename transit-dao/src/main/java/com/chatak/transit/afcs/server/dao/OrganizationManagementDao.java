package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;

public interface OrganizationManagementDao {

	public OrganizationMaster saveOrganization(OrganizationMaster ptoMaster);

	boolean validateOrganizationRegistration(OrganizationRegistrationRequest request);

	boolean validateOrganizationMasterUpdate(OrganizationUpdateRequest request);

	boolean updateOrganization(OrganizationUpdateRequest request);

	boolean validateOrganizationStatusUpdate(OrganizationStatusUpdateRequest request);

	OrganizationMaster updateOrganizationStatus(OrganizationStatusUpdateRequest request);

	boolean validatePtoStatusCheck(OrganizationStatusCheckRequest request);

	OrganizationSearchResponse searchOrganizationList(OrganizationSearchRequest request);

	public OrganizationMaster getOrganizationIdByOrganizationName(OrganizationSearchRequest request);

	public List<OrganizationMaster> getOrganizationList(OrganizationSearchRequest request);

	public List<OrganizationMaster> getOrganizationListWithStatusNotTerminated(OrganizationSearchRequest request);

	public List<OrganizationMaster> getOrganizationByOrgId(OrganizationSearchRequest request);

	public List<OrganizationMaster> getOrganizationListByOrgId(OrganizationSearchRequest request);

}
