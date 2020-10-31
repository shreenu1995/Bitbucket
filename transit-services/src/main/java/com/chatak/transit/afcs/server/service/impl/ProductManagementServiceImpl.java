package com.chatak.transit.afcs.server.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.ProductDao;
import com.chatak.transit.afcs.server.dao.model.ProductManagement;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.ProductManagementService;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class ProductManagementServiceImpl implements ProductManagementService {

	@Autowired
	ProductDao productDao;

	@Autowired
	CustomErrorResolution dataValidation;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public ProductRegistrationResponse productRegistration(ProductRegistrationRequest productRegistrationRequest,
			ProductRegistrationResponse productRegistrationResponse) {
		ProductManagement productManagement = new ProductManagement();
		productManagement.setProductName(productRegistrationRequest.getProductName());
		productManagement.setProductType(productRegistrationRequest.getProductType());
		productManagement.setOrganizationId(productRegistrationRequest.getOrganizationId());
		productManagement.setPtoId(productRegistrationRequest.getPtoId());
		productManagement.setTicketAndPassType(productRegistrationRequest.getTicketAndPassType());
		productManagement.setAmount(productRegistrationRequest.getAmount());
		productManagement.setDiscount(productRegistrationRequest.getDiscount());
		productManagement.setValidFrom(DateUtil.convertStringToTimestamp(productRegistrationRequest.getValidFrom()));
		productManagement.setValidTo(DateUtil.convertStringToTimestamp(productRegistrationRequest.getValidTo()));
		productManagement.setStatus(Status.ACTIVE.getValue());
		productManagement.setReason(productRegistrationRequest.getReason());
		productDao.saveProduct(productManagement);
		productRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		productRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return productRegistrationResponse;
	}

	@Override
	public ProductSearchResponse searchProduct(ProductSearchRequest request, ProductSearchResponse response) {

		response = productDao.searchProduct(request, response);

		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		ProductSearchResponse productResp = new ProductSearchResponse();
		productResp.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		productResp.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		return productResp;

	}

	@Override
	public ProductSearchResponse updateProductStatus(ProductStatusChangeRequest request, WebResponse response) {
		ProductSearchResponse productSearchResponse = new ProductSearchResponse();

		if (!StringUtil.isNull(request)) {
			ProductManagement productManagement = productDao.updateProductStatus(request);
			productSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			productSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			productSearchResponse.setProductName(productManagement.getProductName());
			return productSearchResponse;
		} else {
			productSearchResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			productSearchResponse.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return productSearchResponse;
		}

	}
	
	@Override
	public void errorProductStatusUpdate(ProductStatusChangeRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.validateProductId(request.getProductId())) {
			response.setStatusCode(CustomErrorCodes.PRODUCTID_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PRODUCTID_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse updateProductProfile(ProductUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) {
		if (productDao.updateProductProfile(request)) {
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

	
}
