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
import com.chatak.transit.afcs.server.dao.PtoManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QPtoMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class PtoManagementDaoImpl implements PtoManagementDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	private static final Logger logger = LoggerFactory.getLogger(PtoManagementDaoImpl.class);

	@Override
	public PtoMaster savePtoRegistration(PtoMaster ptoMaster) {
		return ptoMasterRepository.save(ptoMaster);
	}

	@Override
	public boolean validatePtoRegistrationRequest(PtoRegistrationRequest request) {
		return organizationMasterRepository.existsByOrgId(request.getOrgId()) && !ptoMasterRepository
				.existsByPtoNameAndStatusNotLike(request.getPtoName(), Status.TERMINATED.getValue());
	}

	@Override
	public List<PtoMaster> getPtoList(PtoListRequest ptoOperationListRequest) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QPtoMaster.ptoMaster).where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()))
				.orderBy(QPtoMaster.ptoMaster.ptoName.asc()).list(QPtoMaster.ptoMaster);
	}

	@Override
	public List<PtoMaster> getActivePtoList(PtoListRequest ptoOperationListRequest) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QPtoMaster.ptoMaster)
				.where(QPtoMaster.ptoMaster.status.eq(Status.ACTIVE.getValue()),
						isPtoMasterId(ptoOperationListRequest.getPtoMasterId()),
						isOrgId(ptoOperationListRequest.getOrgId()))
				.orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc()).list(QPtoMaster.ptoMaster);
	}

	@Override
	public List<PtoMaster> getPtoByOrganizationId(PtoListRequest ptoOperationListRequest) {
		return ptoMasterRepository.findByOrgIdAndStatusNotLike(ptoOperationListRequest.getOrgId(),
				Status.TERMINATED.getValue());
	}

	@Override
	public boolean validatePtoOperationStatusUpdate(PtoStatusUpdateRequest request) {
		return ptoMasterRepository.existsByPtoMasterId(request.getPtoMasterId());
	}

	@Override
	public boolean validatePtoOPerationStatusCheck(PtoStatusCheckRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& ptoMasterRepository.existsByPtoMasterId(request.getPtoMasterId());
	}

	@Override
	public boolean validatePtoOperationProfileUpdate(PtoUpdateRequest request) {
		return ptoMasterRepository.existsByPtoMasterId(request.getPtoMasterId());
	}

	@Transactional
	@Override
	public boolean updatePtoMaster(PtoUpdateRequest request) {
		QPtoMaster ptoMaster = QPtoMaster.ptoMaster;
		long noOfRowPtoMaster = new JPAUpdateClause(em, ptoMaster)
				.where(ptoMaster.ptoMasterId.eq(request.getPtoMasterId())).set(ptoMaster.ptoName, request.getPtoName())
				.set(ptoMaster.orgId, request.getOrgId()).set(ptoMaster.city, request.getCity())
				.set(ptoMaster.contactPerson, request.getContactPerson()).set(ptoMaster.state, request.getState())
				.set(ptoMaster.ptoEmail, request.getPtoEmail()).set(ptoMaster.ptoMobile, request.getPtoMobile())
				.set(ptoMaster.ptoMasterId, request.getPtoMasterId()).set(ptoMaster.siteUrl, request.getSiteUrl())
				.execute();
		return noOfRowPtoMaster == 1l;
	}

	@Override
	public PtoSearchResponse searchPto(PtoSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;

		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<Tuple> ptoList = null;
		List<PtoSearchRequest> ptoData = new ArrayList<>();
		PtoSearchRequest ptoSearchRequest = null;

		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			ptoList = query.from(QPtoMaster.ptoMaster)
					.where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()),
							isPtoOperationName(request.getPtoName()), isStatus(request.getStatus()),
							isMobileNumber(request.getPtoMobile()), isEmail(request.getPtoEmail()),
							isPtoMasterId(request.getPtoMasterId()), isOrgId(request.getOrgId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc())
					.list(QPtoMaster.ptoMaster.city, QPtoMaster.ptoMaster.contactPerson, QPtoMaster.ptoMaster.ptoEmail,
							QPtoMaster.ptoMaster.ptoMobile, QPtoMaster.ptoMaster.ptoMasterId,
							QPtoMaster.ptoMaster.ptoMasterId, QPtoMaster.ptoMaster.ptoName,
							QPtoMaster.ptoMaster.siteUrl, QPtoMaster.ptoMaster.state, QPtoMaster.ptoMaster.orgId,
							QPtoMaster.ptoMaster.status);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			ptoList = query.from(QPtoMaster.ptoMaster)
					.where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()),
							QPtoMaster.ptoMaster.orgId.eq(request.getOrgId()), isPtoOperationName(request.getPtoName()),
							isStatus(request.getStatus()), isMobileNumber(request.getPtoMobile()),
							isEmail(request.getPtoEmail()), isPtoMasterId(request.getPtoMasterId()),
							isOrgId(request.getOrgId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc())
					.list(QPtoMaster.ptoMaster.city, QPtoMaster.ptoMaster.contactPerson, QPtoMaster.ptoMaster.ptoEmail,
							QPtoMaster.ptoMaster.ptoMobile, QPtoMaster.ptoMaster.ptoMasterId,
							QPtoMaster.ptoMaster.ptoName, QPtoMaster.ptoMaster.ptoMasterId,
							QPtoMaster.ptoMaster.siteUrl, QPtoMaster.ptoMaster.state, QPtoMaster.ptoMaster.orgId,
							QPtoMaster.ptoMaster.status);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			ptoList = query.from(QPtoMaster.ptoMaster)
					.where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()),
							QPtoMaster.ptoMaster.ptoMasterId.eq(request.getPtoMasterId()), 
							isPtoMasterId(request.getPtoMasterId()),
							isOrgId(request.getOrgId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc())
					.list(QPtoMaster.ptoMaster.city, QPtoMaster.ptoMaster.contactPerson, QPtoMaster.ptoMaster.ptoEmail,
							QPtoMaster.ptoMaster.ptoMobile, QPtoMaster.ptoMaster.ptoMasterId,
							QPtoMaster.ptoMaster.ptoName, QPtoMaster.ptoMaster.ptoMasterId,
							QPtoMaster.ptoMaster.siteUrl, QPtoMaster.ptoMaster.state, QPtoMaster.ptoMaster.orgId,
							QPtoMaster.ptoMaster.status);
		}

		for (Tuple tuple : ptoList) {
			ptoSearchRequest = new PtoSearchRequest();
			ptoSearchRequest.setCity(tuple.get(QPtoMaster.ptoMaster.city));
			ptoSearchRequest.setContactPerson(tuple.get(QPtoMaster.ptoMaster.contactPerson));
			ptoSearchRequest.setPtoEmail(tuple.get(QPtoMaster.ptoMaster.ptoEmail));
			ptoSearchRequest.setPtoMobile(tuple.get(QPtoMaster.ptoMaster.ptoMobile));
			ptoSearchRequest.setPtoMasterId(tuple.get(QPtoMaster.ptoMaster.ptoMasterId));
			ptoSearchRequest.setPtoName(tuple.get(QPtoMaster.ptoMaster.ptoName));
			ptoSearchRequest.setSiteUrl(tuple.get(QPtoMaster.ptoMaster.siteUrl));
			ptoSearchRequest.setState(tuple.get(QPtoMaster.ptoMaster.state));
			ptoSearchRequest.setPtoMasterId(tuple.get(QPtoMaster.ptoMaster.ptoMasterId));
			ptoSearchRequest.setOrgId(tuple.get(QPtoMaster.ptoMaster.orgId));
			if (!StringUtil.isNull(tuple.get(QPtoMaster.ptoMaster.orgId))) {
				Long orgId = tuple.get(QPtoMaster.ptoMaster.orgId);
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository.findByOrgId(orgId);
					ptoSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: PtoManagementDaoImpl class :: searchPto method :: exception", e);
				}
			}
			ptoSearchRequest.setStatus(tuple.get(QPtoMaster.ptoMaster.status));
			ptoData.add(ptoSearchRequest);
		}

		PtoSearchResponse response = new PtoSearchResponse();
		response.setPtosearchList(ptoData);
		response.setTotalRecords(totalNoOfRecords(request));

		return response;
	}

	private int totalNoOfRecords(PtoSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QPtoMaster.ptoMaster)
					.where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()),
							isPtoOperationName(request.getPtoName()), isStatus(request.getStatus()),
							isMobileNumber(request.getPtoMobile()), isEmail(request.getPtoEmail()),
							isPtoMasterId(request.getPtoMasterId()), isOrgId(request.getOrgId()))
					.orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc()).count();

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QPtoMaster.ptoMaster)
					.where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()),
							QPtoMaster.ptoMaster.orgId.eq(request.getOrgId()), isPtoOperationName(request.getPtoName()),
							isStatus(request.getStatus()), isMobileNumber(request.getPtoMobile()),
							isEmail(request.getPtoEmail()), isPtoMasterId(request.getPtoMasterId()),
							isOrgId(request.getOrgId()))
					.orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isPtoOperationName(String ptoOperationName) {
		return !StringUtil.isNullEmpty(ptoOperationName) ? QPtoMaster.ptoMaster.ptoName.toUpperCase()
				.like("%" + ptoOperationName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QPtoMaster.ptoMaster.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isMobileNumber(String mobileNumber) {
		return !StringUtil.isNullEmpty(mobileNumber) ? QPtoMaster.ptoMaster.ptoMobile.like(mobileNumber) : null;
	}

	private BooleanExpression isEmail(String email) {
		return !StringUtil.isNullEmpty(email)
				? QPtoMaster.ptoMaster.ptoEmail.toUpperCase().like("%" + email.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isPtoMasterId(Long ptoMasterId) {
		return !StringUtil.isNull(ptoMasterId) ? QPtoMaster.ptoMaster.ptoMasterId.eq(ptoMasterId) : null;
	}

	private BooleanExpression isOrgId(Long orgId) {
		return !StringUtil.isNull(orgId) ? QPtoMaster.ptoMaster.orgId.eq(orgId) : null;
	}

	@Override
	public List<PtoMaster> getPtoListWithStatusNotTerminated(PtoListRequest ptoListRequest) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QPtoMaster.ptoMaster).where(QPtoMaster.ptoMaster.status.ne(Status.TERMINATED.getValue()))
				.orderBy(QPtoMaster.ptoMaster.ptoMasterId.desc()).list(QPtoMaster.ptoMaster);
	}

	@Override
	public List<PtoMaster> getPtoByPtoMasterIdFromTerminal(PtoListRequest ptoListRequest) {
		return ptoMasterRepository.findByPtoMasterIdAndStatusNotLike(ptoListRequest.getPtoMasterId(),
				Status.TERMINATED.getValue());
	}

	@Override
	public List<PtoMaster> getPtoByPtoMasterId(PtoListRequest ptoListRequest) {
		return ptoMasterRepository.findByPtoMasterIdAndStatusNotLike(ptoListRequest.getPtoMasterId(),
				Status.TERMINATED.getValue());
	}

	@Override
	public PtoMaster updatePtoOperationStatus(PtoStatusUpdateRequest request) {
		PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(request.getPtoMasterId());
		ptoMaster.setStatus(request.getStatus());
		ptoMaster.setReason(request.getReason());
		return ptoMasterRepository.save(ptoMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PtoMaster> getPtoByPtoName(String ptoName) {
		return ptoMasterRepository.findByPtoNameAndStatusLike(ptoName, Status.ACTIVE.getValue());
	}

	@Override
	public List<PtoMaster> getPtoListByPtoMasterId(PtoListRequest ptoListRequest) {
		return ptoMasterRepository.findByPtoMasterIdAndStatusNotLike(ptoListRequest.getPtoMasterId(),
				Status.TERMINATED.getValue());
	}

	@Override
	public List<PtoMaster> getPtoListByOrganizationId(PtoListRequest ptoListRequest) {
		return ptoMasterRepository.findByOrgIdAndStatusNotLike(ptoListRequest.getOrgId(),
				Status.TERMINATED.getValue());

	}

	@Override
	public List<PtoMaster> getActivePtoListByOrganizationId(PtoListRequest ptoListRequest) {
		return ptoMasterRepository.findByOrgIdAndStatusLike(ptoListRequest.getOrgId(),
				Status.ACTIVE.getValue());

	}

	@Override
	public List<PtoMaster> getPtoDataByPtoMasterId(PtoListRequest ptoListRequest) {
		return ptoMasterRepository.findByPtoMasterIdAndStatusLike(ptoListRequest.getPtoMasterId(),
				Status.ACTIVE.getValue());
	}

	@Override
	public PtoMaster getPtoByPtoMasterId(Long ptoMasterId) {
		return ptoMasterRepository.findByPtoMasterId(ptoMasterId);
	}

}