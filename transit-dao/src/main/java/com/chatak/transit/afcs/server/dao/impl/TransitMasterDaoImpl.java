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
import com.chatak.transit.afcs.server.dao.TransitMasterDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QTransitMasterMaintenance;
import com.chatak.transit.afcs.server.dao.model.TransitMasterMaintenance;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.TransitMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class TransitMasterDaoImpl implements TransitMasterDao {

	private static final Logger logger = LoggerFactory.getLogger(TransitMasterDaoImpl.class);

	@Autowired
	TransitMasterRepository transitMasterRepository;

	@PersistenceContext
	EntityManager em;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@Override
	public void savetransitMaster(TransitMasterMaintenance setupTransitMasterMaintenance) {

		transitMasterRepository.save(setupTransitMasterMaintenance);
	}

	@Override
	public TransitMasterSearchResponse getTransitMaster(TransitMasterSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<TransitMasterMaintenance> transitMasterMaintenanceList = null;

		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			transitMasterMaintenanceList = query.from(QTransitMasterMaintenance.transitMasterMaintenance)
					.where(QTransitMasterMaintenance.transitMasterMaintenance.status.ne(Status.TERMINATED.getValue()),
							isTransitMasterId(request.getTransitMasterId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isVersionNumber(request.getVersionNumber()), isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.desc())
					.list(QTransitMasterMaintenance.transitMasterMaintenance);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			transitMasterMaintenanceList = query.from(QTransitMasterMaintenance.transitMasterMaintenance)
					.where(QTransitMasterMaintenance.transitMasterMaintenance.status.ne(Status.TERMINATED.getValue()),
							QTransitMasterMaintenance.transitMasterMaintenance.organizationId
									.eq(request.getOrganizationId()),
							isTransitMasterId(request.getTransitMasterId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isVersionNumber(request.getVersionNumber()), isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.desc())
					.list(QTransitMasterMaintenance.transitMasterMaintenance);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			transitMasterMaintenanceList = query.from(QTransitMasterMaintenance.transitMasterMaintenance)
					.where(QTransitMasterMaintenance.transitMasterMaintenance.status.ne(Status.TERMINATED.getValue()),
							isTransitMasterId(request.getTransitMasterId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isVersionNumber(request.getVersionNumber()), isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.desc())
					.list(QTransitMasterMaintenance.transitMasterMaintenance);
		}

		List<TransitMasterSearchRequest> transitMasterSearchList = new ArrayList<>();

		for (TransitMasterMaintenance search : transitMasterMaintenanceList) {
			TransitMasterSearchRequest transitMasterSearchRequest = new TransitMasterSearchRequest();
			transitMasterSearchRequest.setTransitMasterId(search.getTransitMasterId());
			transitMasterSearchRequest.setOrganizationId(search.getOrganizationId());
			transitMasterSearchRequest.setPtoId(search.getPtoId());
			transitMasterSearchRequest.setVersionNumber(search.getVersionNumber());
			transitMasterSearchRequest.setFullVersion(search.getFullVersion());
			transitMasterSearchRequest.setInherit(search.getInheritt());
			transitMasterSearchRequest.setDeliveryDate(DateUtil.toDateStringFormat(search.getDeliveryDate()));
			transitMasterSearchRequest.setApplyDate(DateUtil.toDateStringFormat(search.getApplyDate()));
			transitMasterSearchRequest.setStatus(search.getStatus());
			transitMasterSearchRequest.setDescription(search.getDescription());
			transitMasterSearchRequest.setOrganizationId(search.getOrganizationId());
			if (!StringUtil.isNull(search.getOrganizationId())) {
				Long organizationId = search.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository.findByOrgId(organizationId);
					transitMasterSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: FeeManagementDaoImpl class :: getFeeList method :: exception", e);
				}
			}
			if (!StringUtil.isNull(search.getPtoId())) {
				Long ptoId = search.getPtoId();
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					transitMasterSearchRequest.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			transitMasterSearchList.add(transitMasterSearchRequest);
		}

		TransitMasterSearchResponse response = new TransitMasterSearchResponse();
		response.setTransitMasterSearchList(transitMasterSearchList);
		response.setTotalRecords(totalUserRecords(request));
		return response;
	}

	private int totalUserRecords(TransitMasterSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QTransitMasterMaintenance.transitMasterMaintenance)
					.where(QTransitMasterMaintenance.transitMasterMaintenance.status.ne(Status.TERMINATED.getValue()),
							isTransitMasterId(request.getTransitMasterId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isVersionNumber(request.getVersionNumber()), isStatus(request.getStatus()))

					.orderBy(QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QTransitMasterMaintenance.transitMasterMaintenance)
					.where(QTransitMasterMaintenance.transitMasterMaintenance.status.ne(Status.TERMINATED.getValue()),
							QTransitMasterMaintenance.transitMasterMaintenance.organizationId
									.eq(request.getOrganizationId()),
							isTransitMasterId(request.getTransitMasterId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isVersionNumber(request.getVersionNumber()), isStatus(request.getStatus()))
					.orderBy(QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QTransitMasterMaintenance.transitMasterMaintenance)
					.where(QTransitMasterMaintenance.transitMasterMaintenance.status.ne(Status.TERMINATED.getValue()),
							isTransitMasterId(request.getTransitMasterId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isVersionNumber(request.getVersionNumber()), isStatus(request.getStatus()))
					.orderBy(QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isTransitMasterId(Long isTransitMasterId) {
		return !StringUtil.isNull(isTransitMasterId)
				? QTransitMasterMaintenance.transitMasterMaintenance.transitMasterId.eq(isTransitMasterId) : null;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId)
				? QTransitMasterMaintenance.transitMasterMaintenance.organizationId.eq(organizationId) : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QTransitMasterMaintenance.transitMasterMaintenance.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isVersionNumber(String versionNumber) {
		return !StringUtil.isNullEmpty(versionNumber) ? QTransitMasterMaintenance.transitMasterMaintenance.versionNumber
				.toUpperCase().like("%" + versionNumber.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QTransitMasterMaintenance.transitMasterMaintenance.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	@Transactional
	@Override
	public boolean updateTransitMaster(TransitMasterUpdateRequest request) {
		QTransitMasterMaintenance transitMasterMaintenance = QTransitMasterMaintenance.transitMasterMaintenance;
		long noOfRowUserPro = new JPAUpdateClause(em, transitMasterMaintenance)
				.where(transitMasterMaintenance.transitMasterId.eq(request.getTransitMasterId()))
				.set(transitMasterMaintenance.versionNumber, request.getVersionNumber())
				.set(transitMasterMaintenance.inheritt, request.getInherit())
				.set(transitMasterMaintenance.ptoId, request.getPtoId())
				.set(transitMasterMaintenance.fullVersion, request.getFullVersion())
				.set(transitMasterMaintenance.deliveryDate,
						DateUtil.convertStringToTimestamp(request.getDeliveryDate()))
				.set(transitMasterMaintenance.applyDate, DateUtil.convertStringToTimestamp(request.getApplyDate()))
				.set(transitMasterMaintenance.organizationId, request.getOrganizationId())
				.set(transitMasterMaintenance.description, request.getDescription()).execute();

		return noOfRowUserPro == 1l;
	}

	@Override
	public boolean updateTransitMasterStatus(TransitMasterRegistrationRequest request) {
		if (!StringUtil.isNull(request)) {
			TransitMasterMaintenance transitMasterMaintenance = transitMasterRepository
					.findByTransitMasterId(request.getTransitMasterId());
			transitMasterMaintenance.setStatus(request.getStatus());
			transitMasterMaintenance.setReason(request.getReason());
			transitMasterRepository.save(transitMasterMaintenance);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TransitMasterSearchResponse searchTransitMasterData(TransitMasterSearchRequest request) {
		TransitMasterSearchResponse response = new TransitMasterSearchResponse();
		TransitMasterMaintenance transitMaster = transitMasterRepository
				.findByTransitMasterId(request.getTransitMasterId());
		List<TransitMasterSearchRequest> transitMasterSearchRequestList = new ArrayList<>();
		TransitMasterSearchRequest transitMasterSearch = new TransitMasterSearchRequest();
		transitMasterSearch.setApplyDate(DateUtil.toDateStringFormat(transitMaster.getApplyDate()));
		transitMasterSearch.setDeliveryDate(DateUtil.toDateStringFormat(transitMaster.getDeliveryDate()));
		transitMasterSearch.setDescription(transitMaster.getDescription());
		transitMasterSearch.setInherit(transitMaster.getInheritt());
		transitMasterSearch.setTransitMasterId(transitMaster.getTransitMasterId());
		transitMasterSearch.setPtoId(transitMaster.getPtoId());
		transitMasterSearch.setStatus(transitMaster.getStatus());
		transitMasterSearch.setVersionNumber(transitMaster.getVersionNumber());
		transitMasterSearch.setFullVersion(transitMaster.getFullVersion());
		transitMasterSearch.setOrganizationId(transitMaster.getOrganizationId());
		transitMasterSearchRequestList.add(transitMasterSearch);
		response.setTransitMasterSearchList(transitMasterSearchRequestList);
		return response;
	}

	@Override
	public TransitMasterListDataResponse getListInherit(Long ptoId) {
		List<TransitMasterMaintenance> listOfTransitMasterMaintenance = null;
		if (ptoId != null) {
			listOfTransitMasterMaintenance = transitMasterRepository.findByPtoId(ptoId);
		} else {
			listOfTransitMasterMaintenance = transitMasterRepository.findAll();
		}
		TransitMasterListDataResponse transitMasterListDataResponse = new TransitMasterListDataResponse();
		List<TransitMasterRegistrationRequest> listOfTransitMaster = new ArrayList<>();
		for (TransitMasterMaintenance transitMasterMaintenance : listOfTransitMasterMaintenance) {
			TransitMasterRegistrationRequest transitMasterListData = new TransitMasterRegistrationRequest();
			transitMasterListData.setInherit(transitMasterMaintenance.getInheritt());
			transitMasterListData.setPtoId(transitMasterMaintenance.getPtoId());
			transitMasterListData.setVersionNumber(transitMasterMaintenance.getVersionNumber());
			listOfTransitMaster.add(transitMasterListData);
		}
		transitMasterListDataResponse.setListOfInheritAndPto(listOfTransitMaster);
		transitMasterListDataResponse.setListOfInheritAndPto(listOfTransitMaster);
		transitMasterListDataResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		transitMasterListDataResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return transitMasterListDataResponse;
	}
}
