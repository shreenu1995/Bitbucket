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
import com.chatak.transit.afcs.server.dao.OperatorManagementDao;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QOperator;
import com.chatak.transit.afcs.server.dao.repository.OperatorManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class OperatorManagementDaoImpl implements OperatorManagementDao {

	@Autowired
	OperatorManagementRepository operatorManagementRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@PersistenceContext
	EntityManager em;

	private static final Logger logger = LoggerFactory.getLogger(DepotManagementDaoImpl.class);

	@Override
	public List<Operator> getAllOperators(Long ptoId) {
		return operatorManagementRepository.findByPtoIdAndStatusNotLike(ptoId,
				Status.TERMINATED.getValue());
	}

	@Override
	public boolean saveOperatorDetails(Operator operator) {
		return (operatorManagementRepository.save(operator) != null);

	}

	@Override
	public boolean validateOperatorRegistrationRequest(OperatorRegistrationRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId()) && !operatorManagementRepository
				.existsByOperatorNameAndStatusNotLike(request.getOperatorName(), Status.TERMINATED.getValue());
	}

	@Override
	public OperatorResponse searchOperators(OperatorSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<Operator> operatorList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			operatorList = query.from(QOperator.operator)
					.where(isOperatorId(request.getOperatorId()),
							QOperator.operator.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoName(request.getPtoId()),
							isStatus(request.getStatus()), isOperatorName(request.getOperatorName()),
							isOperatorUserId(request.getOperatorUserId()),
							isOperatorContactNumber(request.getOperatorContactNumber()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QOperator.operator.operatorId.desc())
					.list(QOperator.operator);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			operatorList = query.from(QOperator.operator)
					.where(isOperatorId(request.getOperatorId()),
							QOperator.operator.status.ne(Status.TERMINATED.getValue()),
							QOperator.operator.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoName(request.getPtoId()),
							isStatus(request.getStatus()), isOperatorName(request.getOperatorName()),
							isOperatorContactNumber(request.getOperatorContactNumber()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QOperator.operator.operatorId.desc())
					.list(QOperator.operator);
		} else {
			operatorList = query.from(QOperator.operator)
					.where(isOperatorId(request.getOperatorId()),
							QOperator.operator.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoName(request.getPtoId()),
							isStatus(request.getStatus()), isOperatorName(request.getOperatorName()),
							isOperatorContactNumber(request.getOperatorContactNumber()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QOperator.operator.operatorId.desc())
					.list(QOperator.operator);
		}
		OperatorResponse operatorResponse = new OperatorResponse();
		List<OperatorSearchRequest> operatorListResponse = new ArrayList<>();
		for (Operator operator : operatorList) {
			OperatorSearchRequest operatorSearchRequest = new OperatorSearchRequest();
			operatorSearchRequest.setOperatorId(operator.getOperatorId());
			operatorSearchRequest.setPtoId(operator.getPtoId());
			operatorSearchRequest.setOrganizationId(operator.getOrganizationId());
			operatorSearchRequest.setOperatorName(operator.getOperatorName());
			operatorSearchRequest.setOperatorContactNumber(operator.getOperatorContactNumber());
			operatorSearchRequest.setStatus(operator.getStatus());
			operatorSearchRequest.setOperatorUserId(operator.getOperatorUserId());
			if (!StringUtil.isNull(operator.getPtoId())) {
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(operator.getPtoId());
					operatorSearchRequest.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
				operatorListResponse.add(operatorSearchRequest);
			}
		}
		operatorResponse.setListOfOperators(operatorListResponse);
		operatorResponse.setTotalRecords(getTotalRecords(request));
		return operatorResponse;

	}

	private BooleanExpression isOperatorUserId(String operatorUserId) {
		return !StringUtil.isNullEmpty(operatorUserId)
				? QOperator.operator.operatorUserId.like("%" + operatorUserId.replace("*", "") + "%")
				: null;
	}

	private int getTotalRecords(OperatorSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QOperator.operator)
					.where(isOperatorId(request.getOperatorId()),
							QOperator.operator.status.ne(Status.TERMINATED.getValue()), isPtoName(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isStatus(request.getStatus()),
							isOperatorName(request.getOperatorName()),
							isOperatorContactNumber(request.getOperatorContactNumber()))
					.orderBy(QOperator.operator.operatorId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QOperator.operator)
					.where(isOperatorId(request.getOperatorId()),
							QOperator.operator.status.ne(Status.TERMINATED.getValue()),
							QOperator.operator.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoName(request.getPtoId()),
							isStatus(request.getStatus()), isOperatorName(request.getOperatorName()),
							isOperatorContactNumber(request.getOperatorContactNumber()))
					.orderBy(QOperator.operator.operatorId.desc()).count();
		} else {
			count = query.from(QOperator.operator)
					.where(isOperatorId(request.getOperatorId()),
							QOperator.operator.status.ne(Status.TERMINATED.getValue()), isPtoName(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isStatus(request.getStatus()),
							isOperatorName(request.getOperatorName()),
							isOperatorContactNumber(request.getOperatorContactNumber()))
					.orderBy(QOperator.operator.operatorId.desc()).count();
		}

		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QOperator.operator.organizationId.eq(organizationId) : null;
	}

	private BooleanExpression isOperatorId(Long operatorId) {
		return operatorId != null ? QOperator.operator.operatorId.eq(operatorId) : null;
	}

	private BooleanExpression isPtoName(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QOperator.operator.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isOperatorName(String operatorName) {
		return !StringUtil.isNullEmpty(operatorName)
				? QOperator.operator.operatorName.like("%" + operatorName.replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isOperatorContactNumber(String operatorContactNumber) {
		return !StringUtil.isNullEmpty(operatorContactNumber)
				? QOperator.operator.operatorContactNumber.like("%" + operatorContactNumber.replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QOperator.operator.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	@Override
	public boolean validateOperatorStatusUpdate(OperatorStatusChangeRequest request) {
		return operatorManagementRepository.existsByOperatorId(request.getOperatorId());
	}

	@Transactional
	@Override
	public boolean updateOperatorProfile(OperatorUpdateRequest request) {
		QOperator operator = QOperator.operator;
		long noOfRowOperator = new JPAUpdateClause(em, operator).where(operator.operatorId.eq(request.getOperatorId()))
				.set(operator.operatorContactNumber, request.getOperatorContactNumber())
				.set(operator.organizationId, Long.valueOf(request.getOrganizationId()))
				.set(operator.operatorName, request.getOperatorName()).set(operator.ptoId, request.getPtoId())
				.set(operator.operatorUserId, request.getOperatorUserId()).execute();

		return noOfRowOperator == 1l;
	}

	@Override
	public Operator updateOperatorStatus(OperatorStatusChangeRequest request) {
		Operator operator = operatorManagementRepository.findByOperatorId(request.getOperatorId());
		operator.setStatus(request.getStatus());
		operator.setReason(request.getReason());
		return operatorManagementRepository.save(operator);
	}

	@Override
	public Operator findByOperatorId(Long id) {
		return operatorManagementRepository.findByOperatorId(id);
	}

}
