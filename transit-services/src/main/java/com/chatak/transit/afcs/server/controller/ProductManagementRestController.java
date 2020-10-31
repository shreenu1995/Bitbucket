package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.ProductManagementService;

@RestController
@RequestMapping(value = "/online/")
public class ProductManagementRestController extends ServiceHelper {

	@Autowired
	private ProductManagementService productManagementService;

	@PostMapping(value = "productRegistration")
	public ProductRegistrationResponse cardRegistration(
			@RequestBody ProductRegistrationRequest productRegistrationRequest,
			ProductRegistrationResponse productRegistrationResponse) {
		return productManagementService.productRegistration(productRegistrationRequest, productRegistrationResponse);
	}

	@PostMapping(value = "searchProduct")
	public ProductSearchResponse searchCard(@RequestBody ProductSearchRequest request, ProductSearchResponse response) {
		return productManagementService.searchProduct(request, response);
	}
	
	@PostMapping(value = "updateProductStatus")
	public WebResponse updateProductStatus(@RequestBody ProductStatusChangeRequest request, BindingResult bindingResult,
			WebResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return productManagementService.updateProductStatus(request, response);
	}
	
	@PostMapping(value = "updateProductProfile")
	public WebResponse updateProductProfile(@RequestBody @Validated(value=OrderChecks.class) ProductUpdateRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return productManagementService.updateProductProfile(request, response, httpResponse);

	}
}
