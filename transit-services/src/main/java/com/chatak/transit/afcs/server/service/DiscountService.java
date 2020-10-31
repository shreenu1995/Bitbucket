package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DiscountService {

	WebResponse saveDiscountRegistration(DiscountRequest request, WebResponse response);

	DiscountListResponse searchDiscountList(DiscountRequest request, HttpServletResponse httpResponse);

	DiscountListResponse updateDiscountStatus(@Valid DiscountStatusChangeRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	WebResponse updateDiscountProfile(DiscountRequest request, WebResponse response, HttpServletResponse httpResponse);

	void errorDiscountStatusUpdate(DiscountStatusChangeRequest request, WebResponse response);

}
