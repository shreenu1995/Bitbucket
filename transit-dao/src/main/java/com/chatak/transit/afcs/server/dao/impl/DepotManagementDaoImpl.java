package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
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
import com.chatak.transit.afcs.server.dao.DepotManagementDao;
import com.chatak.transit.afcs.server.dao.model.DepotMaster;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QDepotMaster;
import com.chatak.transit.afcs.server.dao.repository.DepotMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DepotManagementDaoImpl implements DepotManagementDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	DepotMasterRepository depotMasterRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	private static final Logger logger = LoggerFactory.getLogger(DepotManagementDaoImpl.class);

	@Override
	public DepotMaster saveDepotRegistration(DepotMaster depotMaster) {
		return depotMasterRepository.save(depotMaster);
	}

	@Override
	public boolean validateDepotName(DepotRegistrationRequest request) {
		return !depotMasterRepository.existsByDepotNameAndStatusNotLike(request.getDepotName(),
				Status.TERMINATED.getValue());
	}

	@Override
	public boolean validateGetDepotListView(DepotListViewRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId());
	}

	@Override
	public List<DepotMaster> getDepotListView(DepotMaster depotMaster) {
		return depotMasterRepository.findAll();
	}

	@Override
	public boolean validateDepotProfileUpdateRequest(DepotProfileUpdateRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& depotMasterRepository.existsByDepotId(request.getDepotId());
	}

	@Transactional
	@Override
	public boolean updateDepotProfile(DepotProfileUpdateRequest request) {
		QDepotMaster depotMaster = QDepotMaster.depotMaster;
		long noOfRowDepotMaster = new JPAUpdateClause(em, depotMaster)
				.where(depotMaster.depotId.eq(request.getDepotId())).set(depotMaster.depotName, request.getDepotName())
				.set(depotMaster.organizationId, request.getOrganizationId())
				.set(depotMaster.ptoId, request.getPtoId()).set(depotMaster.depotMobile, request.getMobile())
				.set(depotMaster.depotShortName, request.getDepotShortName())
				.set(depotMaster.depotIncharge, request.getDepotIncharge())
				.set(depotMaster.updatedDateTime, Timestamp.from(Instant.now())).execute();
			return noOfRowDepotMaster == 1l;
	}

	@Override
	public boolean validateDepotStatusUpdateRequest(DepotStatusUpdateRequest request) {
		return depotMasterRepository.existsByDepotId(request.getDepotId());
	}

	@Override
	public boolean validateDepotStatusCheck(DepotStatusCheckRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& ptoOperationMasterRepository.existsByPtoMasterId(request.getPtoMasterId())
				&& depotMasterRepository.existsByDepotId(request.getDepotId());
	}

	@Override
	public boolean checkDepotStatus(DepotStatusCheckRequest request) {
		return depotMasterRepository.existsByDepotIdAndStatus(request.getDepotId(), true);
	}

	@Override
	public DepotSearchResponse searchdepot(DepotSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<DepotMaster> depotList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			depotList = query.from(QDepotMaster.depotMaster)
					.where(QDepotMaster.depotMaster.status.ne(Status.TERMINATED.getValue()),
							isDepotId(request.getDepotId()), isDepotName(request.getDepotName()),
							isPtoId(request.getPtoId()), isStatus(request.getStatus()),
							isOrganizationId(request.getOrganizationId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QDepotMaster.depotMaster.depotId.desc())
					.list(QDepotMaster.depotMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			depotList = query.from(QDepotMaster.depotMaster)
					.where(QDepotMaster.depotMaster.status.ne(Status.TERMINATED.getValue()),
							QDepotMaster.depotMaster.organizationId.eq(request.getOrganizationId()),
							isDepotId(request.getDepotId()), isDepotName(request.getDepotName()),
							isPtoId(request.getPtoId()), isStatus(request.getStatus()),
							isOrganizationId(request.getOrganizationId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QDepotMaster.depotMaster.depotId.desc())
					.list(QDepotMaster.depotMaster);
		} else {
			depotList = query.from(QDepotMaster.depotMaster)
					.where(QDepotMaster.depotMaster.status.ne(Status.TERMINATED.getValue()),
							isDepotId(request.getDepotId()), isDepotName(request.getDepotName()),
							isPtoId(request.getPtoId()), isStatus(request.getStatus()),
							isOrganizationId(request.getOrganizationId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QDepotMaster.depotMaster.depotId.desc())
					.list(QDepotMaster.depotMaster);
		}
		List<DepotSearchRequest> depotListResponse = new ArrayList<>();
		DepotSearchRequest searchData = null;
		for (DepotMaster depotMaster : depotList) {
			searchData = new DepotSearchRequest();
			searchData.setDepotId(depotMaster.getDepotId());
			searchData.setDepotName(depotMaster.getDepotName());
			searchData.setOrganizationId(depotMaster.getOrganizationId());
			searchData.setPtoId(depotMaster.getPtoId());
			searchData.setDepotShortName(depotMaster.getDepotShortName());
			searchData.setDepotIncharge(depotMaster.getDepotIncharge());
			searchData.setMobile(depotMaster.getDepotMobile());
			searchData.setStatus(depotMaster.getStatus());
			if (!StringUtil.isNull(depotMaster.getOrganizationId())) {
				Long organizationId = depotMaster.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository
							.findByOrgId(organizationId);
					searchData.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			if (!StringUtil.isNull(depotMaster.getPtoId())) {
				Long ptoId = depotMaster.getPtoId();
				try {
					PtoMaster organizationMaster = ptoOperationMasterRepository.findByPtoMasterId(ptoId);
					searchData.setPtoName(organizationMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			depotListResponse.add(searchData);
		}
		DepotSearchResponse depotResponse = new DepotSearchResponse();
		depotResponse.setDepotListResponse(depotListResponse);
		depotResponse.setNoOfRecords(getTotalNoOfRecords(request));
		return depotResponse;
	}

	private int getTotalNoOfRecords(DepotSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QDepotMaster.depotMaster)
					.where(QDepotMaster.depotMaster.status.ne(Status.TERMINATED.getValue()),
							isDepotId(request.getDepotId()), isDepotName(request.getDepotName()),
							isPtoId(request.getPtoId()), isStatus(request.getStatus()),
							isOrganizationId(request.getOrganizationId()))
					.orderBy(QDepotMaster.depotMaster.depotId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QDepotMaster.depotMaster)
					.where(QDepotMaster.depotMaster.status.ne(Status.TERMINATED.getValue()),
							QDepotMaster.depotMaster.organizationId.eq(request.getOrganizationId()),
							isDepotId(request.getDepotId()), isDepotName(request.getDepotName()),
							isPtoId(request.getPtoId()), isStatus(request.getStatus()),
							isOrganizationId(request.getOrganizationId()))
					.orderBy(QDepotMaster.depotMaster.depotId.desc()).count();
		} else {
			count = query.from(QDepotMaster.depotMaster)
					.where(QDepotMaster.depotMaster.status.ne(Status.TERMINATED.getValue()),
							isDepotId(request.getDepotId()), isDepotName(request.getDepotName()),
							isPtoId(request.getPtoId()), isStatus(request.getStatus()),
							isOrganizationId(request.getOrganizationId()))
					.orderBy(QDepotMaster.depotMaster.depotId.desc()).count();
		}

		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isDepotId(Long depotId) {
		return !StringUtil.isNull(depotId) ? QDepotMaster.depotMaster.depotId.eq(depotId) : null;
	}

	private BooleanExpression isDepotName(String depotName) {
		return !StringUtil.isNullEmpty(depotName) ? QDepotMaster.depotMaster.depotName.toUpperCase()
				.like("%" + depotName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QDepotMaster.depotMaster.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QDepotMaster.depotMaster.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QDepotMaster.depotMaster.organizationId.eq(organizationId) : null;
	}

	@Override
	public DepotMaster updateDepotStatus(DepotStatusUpdateRequest request) {
		DepotMaster depotMaster = depotMasterRepository.findByDepotId(request.getDepotId());
		depotMaster.setStatus(request.getStatus());
		depotMaster.setReason(request.getReason());
		return depotMasterRepository.save(depotMaster);
	}

}
