package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface ProductManagementService {

	WebResponse saveProductRegistration(ProductRegistrationRequest request) throws AFCSException;

	ProductSearchResponse searchProduct(ProductSearchRequest request) throws AFCSException;

	ProductSearchResponse updateProductStatus(ProductStatusChangeRequest request) throws AFCSException;

	WebResponse updateProduct(ProductUpdateRequest request) throws AFCSException;

}
