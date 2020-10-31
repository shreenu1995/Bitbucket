package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DiscountDao;
import com.chatak.transit.afcs.server.dao.model.DiscountMaster;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DiscountService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DiscountServiceImpl implements DiscountService{
	
	@Autowired
	DiscountDao discountDao;
	
	@Autowired
	CustomErrorResolution dataValidation;
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public WebResponse saveDiscountRegistration(DiscountRequest request, WebResponse response) {
		DiscountMaster discountMaster = new DiscountMaster();
		discountMaster.setOrganizationId(request.getOrganizationId());
		discountMaster.setPtoId(request.getPtoId());
		discountMaster.setStatus(Status.ACTIVE.getValue());
		discountMaster.setDiscount(request.getDiscount());
		discountMaster.setDiscountName(request.getDiscountName());
		discountMaster.setDiscountType(request.getDiscountType());
		discountMaster.setRouteStageStationDifference(request.getRouteStageStationDifference());
		discountDao.saveDiscountRegistration(discountMaster);
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return response;
	}

	@Override
	public DiscountListResponse searchDiscountList(DiscountRequest request, HttpServletResponse httpResponse) {
		DiscountListResponse response = discountDao.getDiscountList(request);
		if(!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public void errorDiscountStatusUpdate(DiscountStatusChangeRequest request, WebResponse response) {
		 if (!dataValidation.validateDiscountId(request.getDiscountId())) {
			response.setStatusCode(CustomErrorCodes.PRODUCTID_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PRODUCTID_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse updateDiscountProfile(DiscountRequest request, WebResponse response,
			HttpServletResponse httpResponse) {
		if (discountDao.updateDiscountProfile(request)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;
		}
	}
	
	@Override
	public DiscountListResponse updateDiscountStatus(@Valid DiscountStatusChangeRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException {
		DiscountListResponse discountListResponse = new DiscountListResponse();

		if (!StringUtil.isNull(request)) {
			DiscountMaster discountMaster = discountDao.updateDiscountStatus(request);
			discountListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			discountListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			discountListResponse.setDiscountName(discountMaster.getDiscountName());
			return discountListResponse;
		} else {
			discountListResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			discountListResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return discountListResponse;
		}

	}


}
