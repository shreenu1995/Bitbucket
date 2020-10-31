package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.BlacklistedAccountDao;
import com.chatak.transit.afcs.server.dao.model.BlacklistedAccount;
import com.chatak.transit.afcs.server.dao.model.QBlacklistedAccount;
import com.chatak.transit.afcs.server.dao.repository.BlacklistedAcccountRepository;
import com.chatak.transit.afcs.server.dao.repository.IssuerOnboardRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class BlacklistedAccountDaoImpl implements BlacklistedAccountDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@Autowired
	IssuerOnboardRepository issuerRepository;

	@Autowired
	BlacklistedAcccountRepository blacklistedAcccountRepository;

	@Override
	public BlacklistedAccountResponse getblackAccountList(BlacklistedAccountRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<BlacklistedAccount> blacklistedAccountList = null;
		blacklistedAccountList = query.from(QBlacklistedAccount.blacklistedAccount)
				.where(isIssuerName(request.getIssuerName())).offset(fromIndex).limit(Constants.SIZE)
				.orderBy(QBlacklistedAccount.blacklistedAccount.blacklistedId.desc())
				.list(QBlacklistedAccount.blacklistedAccount);

		List<BlacklistedAccountRequest> searchBlacklistedAccountList = new ArrayList<>();
		for (BlacklistedAccount blacklistedAccount : blacklistedAccountList) {
			BlacklistedAccountRequest blacklistedAccountRequest = new BlacklistedAccountRequest();
			blacklistedAccountRequest.setIssuerId(blacklistedAccount.getIssuerId());
			blacklistedAccountRequest.setIssuerName(blacklistedAccount.getIssuerName());
			blacklistedAccountRequest.setPtoName(blacklistedAccount.getPto());
			blacklistedAccountRequest.setAccount(blacklistedAccount.getAccount());
			blacklistedAccountRequest.setReason(blacklistedAccount.getReason());
			searchBlacklistedAccountList.add(blacklistedAccountRequest);

		}
		BlacklistedAccountResponse response = new BlacklistedAccountResponse();
		response.setBlacklistedAccountList(searchBlacklistedAccountList);
		response.setNoOfrecords(totalBlackListRecords(request));

		return response;

	}

	private int totalBlackListRecords(BlacklistedAccountRequest request) {
		Long count = new JPAQuery(em).from(QBlacklistedAccount.blacklistedAccount)
				.where(isIssuerName(request.getIssuerName()))
				.orderBy(QBlacklistedAccount.blacklistedAccount.blacklistedId.desc()).count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isIssuerName(String issuerName) {
		return !StringUtil.isNullEmpty(issuerName) ? QBlacklistedAccount.blacklistedAccount.issuerName.eq(issuerName) : null;
	}

	@Override
	public boolean saveBlacklistedDetails(BlacklistedAccount request) {
		return blacklistedAcccountRepository.save(request) != null;
	}

	@Override
	public boolean validateIssuerName(BlacklistedAccountRequest request) {
		return !blacklistedAcccountRepository.existsByIssuerName(request.getIssuerName());
	}
	
}
