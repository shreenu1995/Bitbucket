package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.DiscountManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DiscountManagementServiceImpl implements DiscountManagementService{

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;
	
	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public DiscountResponse discountRegistration(DiscountRequest request) throws AFCSException {
		DiscountResponse response = jsonUtil.postRequest(request, DiscountResponse.class,
				"/online/discount/registration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;	
		}

	@Override
	public DiscountListResponse discountSearch(DiscountRequest request) throws AFCSException {
		DiscountListResponse response = jsonUtil.postRequest(request,
				DiscountListResponse.class, "/online/discount/searchDiscount");
		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public DiscountListResponse updateDiscountStatus(DiscountStatusChangeRequest request) throws AFCSException {
		DiscountListResponse response = jsonUtil.postRequest(request, DiscountListResponse.class, "/online/discount/updateDiscountStatus");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateDiscount(DiscountRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "/online/discount/updateDiscountProfile");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
