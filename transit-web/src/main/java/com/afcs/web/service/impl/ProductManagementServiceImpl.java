package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.afcs.web.service.ProductManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class ProductManagementServiceImpl implements ProductManagementService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public WebResponse saveProductRegistration(ProductRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/productRegistration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public ProductSearchResponse searchProduct(ProductSearchRequest request) throws AFCSException {
		ProductSearchResponse response = jsonUtil.postRequest(request, ProductSearchResponse.class,
				"online/searchProduct");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public ProductSearchResponse updateProductStatus(ProductStatusChangeRequest request) throws AFCSException {
		ProductSearchResponse response = jsonUtil.postRequest(request, ProductSearchResponse.class, "online/updateProductStatus");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateProduct(ProductUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/updateProductProfile");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
