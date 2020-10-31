package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.IssuerOnboardingManagementDao;
import com.chatak.transit.afcs.server.dao.model.Issuer;
import com.chatak.transit.afcs.server.dao.model.QIssuer;
import com.chatak.transit.afcs.server.dao.model.QPtoMaster;
import com.chatak.transit.afcs.server.dao.repository.IssuerOnboardRepository;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class IssuerOnboardingManagementDaoImpl implements IssuerOnboardingManagementDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	private IssuerOnboardRepository issuerOnboardRepository;

	@Override
	public boolean createIssuer(Issuer issuer) {
		return issuerOnboardRepository.save(issuer) != null;
	}

	@Override
	public IssuerResponse searchIssuer(IssuerRequest issuerRequest) {
		Integer pageIndexNo = issuerRequest.getPageSize();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		Long ptoMasterId = null;
		if (!issuerRequest.getPtoMasterId().equals("")) {
			ptoMasterId = Long.parseLong(issuerRequest.getPtoMasterId());
		}
		JPAQuery query = new JPAQuery(em);

		List<Tuple> issuerList = query.from(QIssuer.issuer, QPtoMaster.ptoMaster)
				.where(QIssuer.issuer.ptoMaterId.eq(QPtoMaster.ptoMaster.ptoMasterId),
						isIssuerName(issuerRequest.getIssuerName()), isPtoMasterId(ptoMasterId))
				.offset(fromIndex).limit(Constants.SIZE).orderBy(QIssuer.issuer.id.desc())
				.list(QIssuer.issuer.id, QIssuer.issuer.issuerName, QIssuer.issuer.serviceUrl,
						QPtoMaster.ptoMaster.ptoName, QPtoMaster.ptoMaster.ptoMasterId);

		List<IssuerRequest> issuerRequestList = new ArrayList<>();
		for (Tuple tuple : issuerList) {
			IssuerRequest request = new IssuerRequest();
			request.setIssuerId(tuple.get(QIssuer.issuer.id));
			request.setIssuerName(tuple.get(QIssuer.issuer.issuerName));
			request.setPtoMasterId(tuple.get(QPtoMaster.ptoMaster.ptoMasterId).toString());
			request.setPtoName(tuple.get(QPtoMaster.ptoMaster.ptoName));
			request.setServiceUrl(tuple.get(QIssuer.issuer.serviceUrl));
			issuerRequestList.add(request);
		}
		IssuerResponse issuerResponse = new IssuerResponse();
		issuerResponse.setListOfAfcsIssuer(issuerRequestList);
		issuerResponse.setTotalNoOfRecords(totalUserRecords(issuerRequest));
		return issuerResponse;
	}

	private int totalUserRecords(IssuerRequest issuerRequest) {
		Long ptoMasterId = null;
		if (!issuerRequest.getPtoMasterId().equals("")) {
			ptoMasterId = Long.parseLong(issuerRequest.getPtoMasterId());
		}
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QIssuer.issuer, QPtoMaster.ptoMaster)
				.where(QIssuer.issuer.ptoMaterId.eq(QPtoMaster.ptoMaster.ptoMasterId),
						isIssuerName(issuerRequest.getIssuerName()), isPtoMasterId(ptoMasterId))
				.orderBy(QIssuer.issuer.issuerName.asc()).count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isIssuerName(String issuerName) {
		return !StringUtil.isNullEmpty(issuerName)
				? QIssuer.issuer.issuerName.toUpperCase().like("%" + issuerName.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isPtoMasterId(Long ptoMasterId) {
		return !StringUtil.isNull(ptoMasterId) ? QIssuer.issuer.ptoMaterId.eq(ptoMasterId) : null;
	}

	@Override
	public Issuer deleteIssuer(IssuerRequest issuerRequest) {
		return issuerOnboardRepository.findById(issuerRequest.getIssuerId()).get();
	}

	@Override
	public Issuer findByIssuerName(String issuerName) {
		return issuerOnboardRepository.findByIssuerName(issuerName);
	}
	
	@Override
	public List<Issuer> getAllIssuer(){
		return issuerOnboardRepository.findAll();
	}

}
