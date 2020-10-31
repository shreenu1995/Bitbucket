package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.PaymentGateway;
import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;

public interface PgOnboardingManagementDao {

	public boolean createPg(PaymentGateway paymentGateway);

	public PgResponse searchPaygate(PgRequest pgRequest);
	
	public PaymentGateway deletePaygate(PgRequest pgRequest);
	
	public PaymentGateway findByPgName(String pgName);
}
