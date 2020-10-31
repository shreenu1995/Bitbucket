package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;

public interface IssuerOnboardingService {

	public IssuerResponse createIssuer(IssuerRequest issuerRequest);
	
	public IssuerResponse searchIssuer(IssuerRequest issuerRequest);
	
	public IssuerResponse deleteIssuer(IssuerRequest issuerRequest);
	
	public IssuerResponse getAllIssuers();
	
	public IssuerResponse getIssuerById(IssuerRequest issuerRequest);
}
