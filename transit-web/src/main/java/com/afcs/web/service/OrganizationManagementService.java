package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface OrganizationManagementService {

	OrganizationRegistrationResponse createOrganization(OrganizationRegistrationRequest request) throws AFCSException;

	OrganizationSearchResponse searchOrganization(OrganizationSearchRequest request) throws AFCSException;

	WebResponse updateOrganization(OrganizationUpdateRequest request) throws AFCSException;

	OrganizationSearchResponse updateOrganizationStatus(OrganizationStatusUpdateRequest request) throws AFCSException;

	OrganizationResponse getOrganizationIdByOrganizationName(OrganizationSearchRequest ptoSearchRequest,
			OrganizationResponse response) throws AFCSException;

	OrganizationSearchResponse getOrganizationList(OrganizationSearchRequest searchPtoRequest) throws AFCSException;

	OrganizationSearchResponse getOrganizationListWithStatusNotTerminated(
			OrganizationSearchRequest organizationSearchRequest) throws AFCSException;

	OrganizationSearchResponse getOrganizationListByOrgId(OrganizationSearchRequest organizationSearchRequest) throws AFCSException;

}
