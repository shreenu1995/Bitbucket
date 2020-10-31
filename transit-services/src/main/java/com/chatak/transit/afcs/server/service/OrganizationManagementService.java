package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface OrganizationManagementService {

	OrganizationRegistrationResponse createOrganization(OrganizationRegistrationRequest request, HttpServletResponse httpResponse,
			OrganizationRegistrationResponse response) throws IOException;

	WebResponse updateOrganization(OrganizationUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	OrganizationSearchResponse updateOrganizationStatus(OrganizationStatusUpdateRequest request, WebResponse response);

	OrganizationSearchResponse searchOrganization(OrganizationSearchRequest request);

	OrganizationMaster getOrganizationIdByOrganizationName(OrganizationSearchRequest request);

	OrganizationSearchResponse getOrganizationList(OrganizationSearchRequest request);

	OrganizationSearchResponse getOrganizationListWithStatusNotTerminated(OrganizationSearchRequest request);

	OrganizationSearchResponse getOrganizationListByOrgId(OrganizationSearchRequest request);
}
