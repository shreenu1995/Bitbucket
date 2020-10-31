package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.FeeManagementDao;
import com.chatak.transit.afcs.server.dao.model.FeeMaster;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QFeeMaster;
import com.chatak.transit.afcs.server.dao.repository.FeeRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class FeeManagementDaoImpl implements FeeManagementDao {

	@Autowired
	FeeRepository feeRepository;

	@Autowired
	OrganizationMasterRepository ptoRepository;

	@PersistenceContext
	EntityManager em;

	@PersistenceContext
	EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(FeeManagementDaoImpl.class);

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Override
	public FeeMaster saveFee(FeeMaster feeMaster) {
		return feeRepository.save(feeMaster);

	}

	@Override
	public FeeSearchResponse getFeeList(FeeSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<FeeMaster> feeList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			feeList = query.from(QFeeMaster.feeMaster)
					.where(QFeeMaster.feeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isFeeId(request.getFeeId()), isStatus(request.getStatus()), isFeeName(request.getFeeName()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QFeeMaster.feeMaster.feeId.desc())
					.list(QFeeMaster.feeMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			feeList = query.from(QFeeMaster.feeMaster)
					.where(QFeeMaster.feeMaster.status.ne(Status.TERMINATED.getValue()),
							QFeeMaster.feeMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isFeeId(request.getFeeId()), isStatus(request.getStatus()), isFeeName(request.getFeeName()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QFeeMaster.feeMaster.feeId.desc())
					.list(QFeeMaster.feeMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			feeList = query.from(QFeeMaster.feeMaster)
					.where(QFeeMaster.feeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isFeeId(request.getFeeId()), isStatus(request.getStatus()), isFeeName(request.getFeeName()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QFeeMaster.feeMaster.feeId.desc())
					.list(QFeeMaster.feeMaster);
		}

		List<FeeSearchRequest> searchFeeList = new ArrayList<>();
		for (FeeMaster feeMaster : feeList) {
			FeeSearchRequest feeSearchRequest = new FeeSearchRequest();
			feeSearchRequest.setOrganizationId(feeMaster.getOrganizationId());
			feeSearchRequest.setPtoId(feeMaster.getPtoId());
			feeSearchRequest.setFeeName(feeMaster.getFeeName());
			feeSearchRequest.setFeeId((feeMaster.getFeeId()));
			feeSearchRequest.setStatus(feeMaster.getStatus());
			feeSearchRequest.setPtoShareType(feeMaster.getPtoShareType());
			feeSearchRequest.setPtoshareValue(feeMaster.getPtoShareValue());
			feeSearchRequest.setPtoFeeType(feeMaster.getPtoFeeType());
			feeSearchRequest.setPtoFeeValue(feeMaster.getPtoFeeValue());
			feeSearchRequest.setOrgShareType(feeMaster.getOrgShareType());
			feeSearchRequest.setOrgShareValue(feeMaster.getOrgShareValue());
			feeSearchRequest.setOrgFeeType(feeMaster.getOrgFeeType());
			feeSearchRequest.setOrgFeeValue(feeMaster.getOrgFeeValue());
			if (!StringUtil.isNull(feeMaster.getOrganizationId())) {
				Long organnizationId = feeMaster.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository.findByOrgId(organnizationId);
					feeSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: FeeManagementDaoImpl class :: getFeeList method :: exception", e);
				}
			}
			if (!StringUtil.isNull(feeMaster.getPtoId())) {
				Long ptoId = feeMaster.getPtoId();
				try {
					PtoMaster organizationMaster = ptoOperationMasterRepository.findByPtoMasterId(ptoId);
					feeSearchRequest.setPtoName(organizationMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			searchFeeList.add(feeSearchRequest);
		}
		FeeSearchResponse response = new FeeSearchResponse();
		response.setFeeList(searchFeeList);
		response.setTotalRecords(totalFeeRecords(request));
		return response;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QFeeMaster.feeMaster.organizationId.eq(organizationId) : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QFeeMaster.feeMaster.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isFeeId(Long feeId) {
		return !StringUtil.isNull(feeId) ? QFeeMaster.feeMaster.feeId.eq(feeId) : null;
	}

	private int totalFeeRecords(FeeSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QFeeMaster.feeMaster)
					.where(QFeeMaster.feeMaster.status.ne(Status.TERMINATED.getValue()), isPtoId(request.getPtoId()),
							isFeeName(request.getFeeName()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()))
					.orderBy(QFeeMaster.feeMaster.feeId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QFeeMaster.feeMaster)
					.where(QFeeMaster.feeMaster.status.ne(Status.TERMINATED.getValue()),
							QFeeMaster.feeMaster.organizationId.eq(request.getOrganizationId()),
							isPtoId(request.getPtoId()), isFeeName(request.getFeeName()),
							isOrganizationId(request.getOrganizationId()), isStatus(request.getStatus()))
					.orderBy(QFeeMaster.feeMaster.feeId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QFeeMaster.feeMaster)
					.where(QFeeMaster.feeMaster.status.ne(Status.TERMINATED.getValue()), isPtoId(request.getPtoId()),
							isFeeName(request.getFeeName()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()))
					.orderBy(QFeeMaster.feeMaster.feeId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QFeeMaster.feeMaster.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isFeeName(String feeName) {
		return !StringUtil.isNullEmpty(feeName)
				? QFeeMaster.feeMaster.feeName.toUpperCase().like("%" + feeName.toUpperCase().replace("*", "") + "%")
				: null;
	}

	@Override
	public boolean validateFeeStatusUpdate(FeeUpdateRequest request) {
		return feeRepository.existsByFeeId(request.getFeeId());
	}

	@Transactional
	@Override
	public boolean updateFeeProfileUpdate(FeeUpdateRequest request) {
		QFeeMaster feeMaster = QFeeMaster.feeMaster;
		long noOfRowUserCrd = new JPAUpdateClause(em, feeMaster).where(feeMaster.feeId.eq(request.getFeeId()))
				.set(feeMaster.feeName, request.getFeeName()).set(feeMaster.organizationId, request.getOrganizationId())
				.set(feeMaster.ptoId, request.getPtoId()).set(feeMaster.ptoShareType, request.getPtoShareType())
				.set(feeMaster.ptoShareValue, request.getPtoShareValue())
				.set(feeMaster.ptoFeeType, request.getPtoFeeType()).set(feeMaster.ptoFeeValue, request.getPtoFeeValue())
				.set(feeMaster.orgShareType, request.getOrgShareType())
				.set(feeMaster.orgShareValue, request.getOrgShareValue())
				.set(feeMaster.orgFeeType, request.getOrgFeeType()).set(feeMaster.orgFeeValue, request.getOrgFeeValue())
				.execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public List<String> getOrganisationList() {
		return ptoRepository.findAllOrganizationName();
	}

	@Override
	public FeeMaster updateFeeStatus(FeeRegistrationRequest request) {
		FeeMaster feeMaster = feeRepository.findByFeeId(request.getFeeId());
		feeMaster.setStatus(request.getStatus());
		feeMaster.setReason(request.getReason());
		return feeRepository.save(feeMaster);
	}

}
