/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DepotManagementService {

	DepotRegistrationResponse saveDepotRegistrationrequest(DepotRegistrationRequest request,
			HttpServletResponse httpResponse, DepotRegistrationResponse response) throws IOException;

	DepotListViewResponse getDepotListViewRequest(DepotListViewRequest request, DepotListViewResponse response,
			HttpServletResponse httpServletResponse, BindingResult bindingResult) throws IOException;

	void validateDepotRegistrationErrors(DepotRegistrationRequest request, DepotRegistrationResponse response);

	WebResponse updateDepotProfile(DepotProfileUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	void validateDepotProfileUpdate(DepotProfileUpdateRequest request, WebResponse response);

	DepotSearchResponse updateDepotStatus(DepotStatusUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	void errorDepotStatusCheck(DepotStatusCheckRequest request, WebResponse response);

	WebResponse checkDepotStatus(DepotStatusCheckRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	DepotSearchResponse searchDepot(DepotSearchRequest request);

	void errorDepotStatusUpdate(DepotStatusUpdateRequest request, WebResponse response);

}
