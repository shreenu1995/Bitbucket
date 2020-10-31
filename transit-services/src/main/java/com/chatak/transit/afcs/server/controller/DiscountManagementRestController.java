package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DiscountService;

@RestController
@RequestMapping(value = "/online/discount/")
public class DiscountManagementRestController extends ServiceHelper {

	@Autowired
	DiscountService discountService;

	@PostMapping(value = "registration")
	public WebResponse discountRegistration(@RequestBody DiscountRequest request, WebResponse response) {
		return discountService.saveDiscountRegistration(request, response);
	}

	@PostMapping(value = "searchDiscount")
	public DiscountListResponse searchDiscountList(@RequestBody DiscountRequest request, BindingResult bindingResult,
			DiscountListResponse response, HttpServletResponse httpResponse) {
		return discountService.searchDiscountList(request, httpResponse);
	}

	@PostMapping(value = "updateDiscountStatus")
	public WebResponse updateProductStatus(@Valid @RequestBody DiscountStatusChangeRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return discountService.updateDiscountStatus(request, response, httpResponse);
	}

	@PostMapping(value = "updateDiscountProfile")
	public WebResponse updateDiscountProfile(@RequestBody @Validated(value = OrderChecks.class) DiscountRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return discountService.updateDiscountProfile(request, response, httpResponse);

	}
}
