package com.chatak.transit.afcs.server.service;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface ProductManagementService {

	ProductRegistrationResponse productRegistration(ProductRegistrationRequest productRegistrationRequest,
			ProductRegistrationResponse productRegistrationResponse);

ProductSearchResponse searchProduct(ProductSearchRequest request, ProductSearchResponse response);


	ProductSearchResponse updateProductStatus(ProductStatusChangeRequest request, WebResponse response);
	void errorProductStatusUpdate(ProductStatusChangeRequest request, WebResponse response);


	WebResponse updateProductProfile(ProductUpdateRequest request, WebResponse response, HttpServletResponse httpResponse);


}
