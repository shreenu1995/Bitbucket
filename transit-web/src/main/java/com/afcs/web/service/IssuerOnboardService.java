package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;

public interface IssuerOnboardService {

	public IssuerResponse createIssuer(IssuerRequest issuerRequest) throws AFCSException;
	
	public IssuerResponse searchIssuer(IssuerRequest issuerRequest) throws AFCSException;
	
	public IssuerResponse deleteIssuer(IssuerRequest issuerRequest) throws AFCSException;
	
	public IssuerResponse getAllIssuers()throws AFCSException;
	
	public IssuerResponse getIssuerById(IssuerRequest issuerRequest)throws AFCSException;
}
