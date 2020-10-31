package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.Issuer;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;

public interface IssuerOnboardingManagementDao {

	boolean createIssuer(Issuer issuer);
	
	public IssuerResponse searchIssuer(IssuerRequest issuerRequest);
	
	public Issuer deleteIssuer(IssuerRequest issuerRequest);
	
	public Issuer findByIssuerName(String issuerName);
	
	public List<Issuer> getAllIssuer();
	
}
