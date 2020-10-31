package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.QOrganizationMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class OrganizationManagementDaoImpl implements OrganizationManagementDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Override
	public OrganizationMaster saveOrganization(OrganizationMaster organizationMaster) {
		return organizationMasterRepository.save(organizationMaster);
	}

	@Override
	public boolean validateOrganizationRegistration(OrganizationRegistrationRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
		&& !organizationMasterRepository.existsByOrganizationNameAndStatusNotLike(request.getOrganizationName(), Status.TERMINATED.getValue());
	}

	@Override
	public boolean validateOrganizationMasterUpdate(OrganizationUpdateRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& organizationMasterRepository.existsByOrgId(request.getOrgId());
	}

	@Transactional
	@Override
	public boolean updateOrganization(OrganizationUpdateRequest request) {
		QOrganizationMaster organizationMaster = QOrganizationMaster.organizationMaster;
		long noOfRowPtoMaster = new JPAUpdateClause(em, organizationMaster)
				.where(organizationMaster.orgId.eq(request.getOrgId()))
				.set(organizationMaster.organizationName, request.getOrganizationName())
				.set(organizationMaster.updatedBy, request.getUserId())
				.set(organizationMaster.state, request.getState())
				.set(organizationMaster.city, request.getCity())
				.set(organizationMaster.contactPerson, request.getContactPerson())
				.set(organizationMaster.siteUrl, request.getSiteUrl())
				.set(organizationMaster.organizationMobileNumber, request.getOrganizationMobile())
				.set(organizationMaster.organizationEmail, request.getOrganizationEmail())
				.set(organizationMaster.createdBy, request.getCreatedBy())
				.set(organizationMaster.createdDateTime, request.getCreatedDateTime())
				.set(organizationMaster.updatedDateTime, Timestamp.from(Instant.now())).execute();
		return noOfRowPtoMaster == 1l;
	}

	@Override
	public boolean validateOrganizationStatusUpdate(OrganizationStatusUpdateRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& organizationMasterRepository.existsByOrgId(request.getOrgId());
	}

	@Override
	public boolean validatePtoStatusCheck(OrganizationStatusCheckRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& organizationMasterRepository.existsByOrgId(request.getOrgId());
	}

	@Override
	public OrganizationSearchResponse searchOrganizationList(OrganizationSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<Tuple> ptoList = query
				.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.status.ne(Status.TERMINATED.getValue()),
						isPtoName(request.getOrganizationName()), isStatus(request.getStatus()),
						isOrgId(request.getOrgId()), isMobile(request.getOrganizationMobile()),
						isEmailId(request.getOrganizationEmail()))
				.offset(fromIndex).limit(Constants.SIZE).orderBy(QOrganizationMaster.organizationMaster.orgId.desc())
				.list(QOrganizationMaster.organizationMaster.organizationName,
						QOrganizationMaster.organizationMaster.contactPerson,
						QOrganizationMaster.organizationMaster.organizationMobileNumber,
						QOrganizationMaster.organizationMaster.organizationEmail,
						QOrganizationMaster.organizationMaster.siteUrl,
						QOrganizationMaster.organizationMaster.state,
						QOrganizationMaster.organizationMaster.city,
						QOrganizationMaster.organizationMaster.createdBy,
						QOrganizationMaster.organizationMaster.updatedBy,
						QOrganizationMaster.organizationMaster.createdDateTime,
						QOrganizationMaster.organizationMaster.updatedDateTime,
						QOrganizationMaster.organizationMaster.status,
						QOrganizationMaster.organizationMaster.orgId);
		List<OrganizationSearchRequest> searchPtoList = new ArrayList<>();
		OrganizationSearchRequest searchPtoRequest = null;
		for (Tuple tuple : ptoList) {
			searchPtoRequest = new OrganizationSearchRequest();
			searchPtoRequest.setOrganizationName(tuple.get(QOrganizationMaster.organizationMaster.organizationName));
			searchPtoRequest.setContactPerson(tuple.get(QOrganizationMaster.organizationMaster.contactPerson));
			searchPtoRequest
					.setOrganizationMobile(tuple.get(QOrganizationMaster.organizationMaster.organizationMobileNumber));
			searchPtoRequest
					.setOrganizationEmail(tuple.get(QOrganizationMaster.organizationMaster.organizationEmail));
			searchPtoRequest.setSiteUrl(tuple.get(QOrganizationMaster.organizationMaster.siteUrl));
			searchPtoRequest.setState(tuple.get(QOrganizationMaster.organizationMaster.state));
			searchPtoRequest.setCity(tuple.get(QOrganizationMaster.organizationMaster.city));
			searchPtoRequest.setCreatedBy(tuple.get(QOrganizationMaster.organizationMaster.createdBy));
			searchPtoRequest.setUpdatedBy(tuple.get(QOrganizationMaster.organizationMaster.updatedBy));
			searchPtoRequest.setCreatedDateTime(tuple.get(QOrganizationMaster.organizationMaster.createdDateTime));
			searchPtoRequest.setUpdatedDateTime(tuple.get(QOrganizationMaster.organizationMaster.updatedDateTime));
			searchPtoRequest.setStatus(tuple.get(QOrganizationMaster.organizationMaster.status));
			searchPtoRequest.setOrgId(tuple.get(QOrganizationMaster.organizationMaster.orgId));
			searchPtoList.add(searchPtoRequest);
		}

		OrganizationSearchResponse response = new OrganizationSearchResponse();
		response.setOrganizationList(searchPtoList);
		response.setTotalRecords(totalUserRecords(request));
		return response;
	}

	private int totalUserRecords(OrganizationSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.orgId
						.eq(QOrganizationMaster.organizationMaster.orgId),
						QOrganizationMaster.organizationMaster.status.ne(Status.TERMINATED.getValue()),
						isPtoName(request.getOrganizationName()), isStatus(request.getStatus()),
						isOrgId(request.getOrgId()), isMobile(request.getOrganizationMobile()),
						isEmailId(request.getOrganizationEmail()))
				.orderBy(QOrganizationMaster.organizationMaster.orgId.desc()).count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isPtoName(String userName) {
		return !StringUtil.isNullEmpty(userName) ? QOrganizationMaster.organizationMaster.organizationName
				.toUpperCase().like("%" + userName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isOrgId(Long orgId) {
		return !StringUtil.isNull(orgId) ? QOrganizationMaster.organizationMaster.orgId.eq(orgId) : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QOrganizationMaster.organizationMaster.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isMobile(String mobile) {
		return !StringUtil.isNullEmpty(mobile) ? QOrganizationMaster.organizationMaster.organizationMobileNumber.eq(mobile)
				: null;
	}

	private BooleanExpression isEmailId(String email) {
		if (email != null && !"".equals(email)) {
			String newOrganizationEmail = StringUtil.escapeUnderscoreQuery(email);
			return !StringUtil.isNullEmpty(email) ? QOrganizationMaster.organizationMaster.organizationEmail
					.toUpperCase().like("%" + newOrganizationEmail.toUpperCase().replace("*", "") + "%") : null;
		} else {
			return null;
		}
	}

	@Override
	public OrganizationMaster getOrganizationIdByOrganizationName(OrganizationSearchRequest request) {
		return organizationMasterRepository.findByOrganizationNameAndStatusNotLike(request.getOrganizationName(), Status.TERMINATED.getValue());
	}

	@Override
	public List<OrganizationMaster> getOrganizationList(OrganizationSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QOrganizationMaster.organizationMaster.orgId.asc())
				.list(QOrganizationMaster.organizationMaster);
	}

	@Override
	public List<OrganizationMaster> getOrganizationListWithStatusNotTerminated(OrganizationSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.status.ne(Status.TERMINATED.getValue()))
				.orderBy(QOrganizationMaster.organizationMaster.orgId.asc())
				.list(QOrganizationMaster.organizationMaster);
	}

	@Override
	public List<OrganizationMaster> getOrganizationByOrgId(OrganizationSearchRequest request) {
		return getOrgListByOrgIdCommonScript(request);
	}

	public OrganizationMaster updateOrganizationStatus(OrganizationStatusUpdateRequest request) {
		OrganizationMaster organizationMaster = organizationMasterRepository
				.findByOrgId(request.getOrgId());
		organizationMaster.setStatus(request.getStatus());
		organizationMaster.setReason(request.getReason());
		return organizationMasterRepository.save(organizationMaster);

	}

	@Override
	public List<OrganizationMaster> getOrganizationListByOrgId(OrganizationSearchRequest request) {
		return getOrgListByOrgIdCommonScript(request);
	}

	private List<OrganizationMaster> getOrgListByOrgIdCommonScript(OrganizationSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.orgId.eq(request.getOrgId()))
				.orderBy(QOrganizationMaster.organizationMaster.orgId.asc())
				.list(QOrganizationMaster.organizationMaster);
	}

}
