package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface DiscountManagementService {

	DiscountResponse discountRegistration(DiscountRequest request) throws AFCSException;

	DiscountListResponse discountSearch(DiscountRequest request) throws AFCSException;

	DiscountListResponse updateDiscountStatus(DiscountStatusChangeRequest request) throws AFCSException;

	WebResponse updateDiscount(DiscountRequest request) throws AFCSException;

}
