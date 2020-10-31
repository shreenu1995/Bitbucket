package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.PgOnboardingManagementDao;
import com.chatak.transit.afcs.server.dao.model.PaymentGateway;
import com.chatak.transit.afcs.server.dao.model.QPaymentGateway;
import com.chatak.transit.afcs.server.dao.model.QPtoMaster;
import com.chatak.transit.afcs.server.dao.repository.PgOnboardRepository;
import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class PgOnboardingManagementDaoImpl implements PgOnboardingManagementDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	private PgOnboardRepository pgOnboardRepository;

	@Override
	public boolean createPg(PaymentGateway paymentGateway) {
		return pgOnboardRepository.save(paymentGateway) != null;
	}

	@Override
	public PgResponse searchPaygate(PgRequest pgRequest) {
		Integer pageIndexNo = pgRequest.getPageSize();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		Long ptoMasterId = null;
		if (!pgRequest.getPtoMasterId().equals("")) {
			ptoMasterId = Long.parseLong(pgRequest.getPtoMasterId());
		}
		JPAQuery query = new JPAQuery(em);

		List<Tuple> issuerList = query.from(QPaymentGateway.paymentGateway, QPtoMaster.ptoMaster)
				.where(QPaymentGateway.paymentGateway.ptoMasterId.eq(QPtoMaster.ptoMaster.ptoMasterId),
						isPgName(pgRequest.getPgName()), isPtoMasterId(ptoMasterId))
				.offset(fromIndex).limit(Constants.SIZE).orderBy(QPaymentGateway.paymentGateway.pgName.asc())
				.list(QPaymentGateway.paymentGateway.id, QPaymentGateway.paymentGateway.pgName,
						QPaymentGateway.paymentGateway.serviceUrl, QPtoMaster.ptoMaster.ptoName,
						QPtoMaster.ptoMaster.ptoMasterId);

		List<PgRequest> pgRequestList = new ArrayList<>();
		for (Tuple tuple : issuerList) {
			PgRequest request = new PgRequest();
			request.setPgId(tuple.get(QPaymentGateway.paymentGateway.id));
			request.setPgName(tuple.get(QPaymentGateway.paymentGateway.pgName));
			request.setPtoMasterId(tuple.get(QPtoMaster.ptoMaster.ptoMasterId).toString());
			request.setPtoName(tuple.get(QPtoMaster.ptoMaster.ptoName));
			request.setServiceUrl(tuple.get(QPaymentGateway.paymentGateway.serviceUrl));
			pgRequestList.add(request);
		}
		PgResponse pgResponse = new PgResponse();
		pgResponse.setListOfAfcsPg(pgRequestList);
		pgResponse.setTotalNoOfRecords(totalUserRecords(pgRequest));
		return pgResponse;
	}

	private int totalUserRecords(PgRequest pgRequest) {
		Long ptoMasterId = null;
		if (!pgRequest.getPtoMasterId().equals("")) {
			ptoMasterId = Long.parseLong(pgRequest.getPtoMasterId());
		}
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QPaymentGateway.paymentGateway, QPtoMaster.ptoMaster)
				.where(QPaymentGateway.paymentGateway.ptoMasterId.eq(QPtoMaster.ptoMaster.ptoMasterId),
						isPgName(pgRequest.getPgName()), isPtoMasterId(ptoMasterId))
				.orderBy(QPaymentGateway.paymentGateway.pgName.asc()).count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isPgName(String pgName) {
		return !StringUtil.isNullEmpty(pgName) ? QPaymentGateway.paymentGateway.pgName.toUpperCase()
				.like("%" + pgName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isPtoMasterId(Long ptoMasterId) {
		return !StringUtil.isNull(ptoMasterId) ? QPaymentGateway.paymentGateway.ptoMasterId.eq(ptoMasterId) : null;
	}

	@Override
	public PaymentGateway deletePaygate(PgRequest pgRequest) {
		return pgOnboardRepository.findById(pgRequest.getPgId()).get();
	}

	@Override
	public PaymentGateway findByPgName(String pgName) {
		return pgOnboardRepository.findByPgName(pgName);
	}
}
