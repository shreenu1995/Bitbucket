package com.chatak.transit.afcs.server.dao;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.DiscountMaster;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;

public interface DiscountDao {

	void saveDiscountRegistration(DiscountMaster discountMaster);

	DiscountListResponse getDiscountList(DiscountRequest request);

	boolean validateDiscountStatusUpdate(@Valid DiscountStatusChangeRequest request);

	DiscountMaster updateDiscountStatus(@Valid DiscountStatusChangeRequest request);

	boolean updateDiscountProfile(DiscountRequest request);

}
