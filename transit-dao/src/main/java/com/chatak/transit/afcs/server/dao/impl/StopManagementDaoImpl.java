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
import com.chatak.transit.afcs.server.dao.StopManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QStageMaster;
import com.chatak.transit.afcs.server.dao.model.QStopProfile;
import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.model.StageMaster;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.RouteManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.StageManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.StageNameRepository;
import com.chatak.transit.afcs.server.dao.repository.StopMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.StopProfileRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class StopManagementDaoImpl implements StopManagementDao {
	@Autowired
	StopMasterRepository stopMasterRepository;

	
	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Autowired
	StopProfileRepository stopProfileRepository;

	@Autowired
	StageNameRepository stageNameRepository;

	@Autowired
	StageManagementRepository stageManagementRepository;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	RouteManagementRepository routeManagementRepository;
	
	@PersistenceContext
	EntityManager em;

	private static final Logger logger = LoggerFactory.getLogger(StopManagementDaoImpl.class);

	
	@Override
	public boolean validateStopRegistrationRequest(StopRegistrationRequest stopRegistrationRequest) {

		return userCredentialRepository.existsByEmail(stopRegistrationRequest.getUserId());

	}

	@Override
	public Long saveStop(StopProfile stopProfile) {
		stopProfile = stopMasterRepository.save(stopProfile);
		return stopProfile.getStopId();
	}

	@Override
	public Long saveStopToStopProfile(StopProfile stopProfile) {
		stopProfile = stopProfileRepository.save(stopProfile);
		return stopProfile.getStopId();
	}

	@Override
	public StopSearchResponse searchStop(StopSearchRequest request) {

		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;

		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<StopProfile> stopList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			stopList = query.from(QStopProfile.stopProfile)
					.where(QStopProfile.stopProfile.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isStageId(request.getStageId()),
							isStopName(request.getStopName()), 
							isStatus(request.getStatus()),
							isStopId(request.getStopId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QStopProfile.stopProfile.stopId.desc())
					.list(QStopProfile.stopProfile);

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			stopList = query.from(QStopProfile.stopProfile)
					.where(QStopProfile.stopProfile.status.ne(Status.TERMINATED.getValue()),
							QStopProfile.stopProfile.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isStageId(request.getStageId()),
							isStopName(request.getStopName()), 
							isStatus(request.getStatus()),
							isStopId(request.getStopId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QStopProfile.stopProfile.stopId.desc())
					.list(QStopProfile.stopProfile);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			stopList = query.from(QStopProfile.stopProfile)
					.where(QStopProfile.stopProfile.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isStageId(request.getStageId()),
							isStopName(request.getStopName()), 
							isStatus(request.getStatus()),
							isStopId(request.getStopId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QStopProfile.stopProfile.stopId.desc())
					.list(QStopProfile.stopProfile);
		}

		List<StopSearchRequest> stopData = new ArrayList<>();
		for (StopProfile stop : stopList) {
			StopSearchRequest stopSearchRequest = new StopSearchRequest();
			stopSearchRequest.setStopName(stop.getStopName());
			stopSearchRequest.setPtoId(stop.getPtoId());
			stopSearchRequest.setRouteId(stop.getRouteId());
			stopSearchRequest.setStageId(stop.getStageId());
			stopSearchRequest.setStopId(stop.getStopId());
			stopSearchRequest.setStatus(stop.getStatus());
			stopSearchRequest.setOrganizationId(stop.getOrganizationId());
			
			if (!StringUtil.isNull(stopSearchRequest.getOrganizationId())) {
				Long organizationId = stopSearchRequest.getOrganizationId();
				getOrgDetails(stopSearchRequest, organizationId);
			}
			if (!StringUtil.isNull(stopSearchRequest.getPtoId())) {
				Long ptoId = stopSearchRequest.getPtoId();
				getPtoDetail(stopSearchRequest, ptoId);
			}
			if (!StringUtil.isNull(stopSearchRequest.getRouteId())) {
				Long routeId = stopSearchRequest.getRouteId();
				getRouteDetail(stopSearchRequest, routeId);
			}
			if (!StringUtil.isNull(stopSearchRequest.getStageId())) {
				Long stageId = stopSearchRequest.getStageId();
				getStageDetail(stopSearchRequest, stageId);
			}
			stopData.add(stopSearchRequest);
		}

		StopSearchResponse response = new StopSearchResponse();
		response.setStopSearchList(stopData);
		response.setNoOfRecords(totalNoOfRecords(request));

		return response;
	}

	private void getStageDetail(StopSearchRequest stopSearchRequest, Long stageId) {
		try {
			StageMaster stageMaster = stageManagementRepository.findByStageId(stageId);
			stopSearchRequest.setStageName(stageMaster.getStageName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: RouteManagementDaoImpl class :: getStageDetail method :: exception", e);
		}
	}

	private void getRouteDetail(StopSearchRequest stopSearchRequest, Long routeId) {
		try {
			RouteMaster routeMaster = routeManagementRepository.findByRouteId(routeId);
			stopSearchRequest.setRouteName(routeMaster.getRouteName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: RouteManagementDaoImpl class :: getRouteDetail method :: exception", e);
		}
	}

	private void getPtoDetail(StopSearchRequest stopSearchRequest, Long ptoId) {
		try {
			PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
			stopSearchRequest.setPtoName(ptoMaster.getPtoName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: RouteManagementDaoImpl class :: getPtoDetail method :: exception", e);
		}
	}

	private void getOrgDetails(StopSearchRequest stopSearchRequest, Long organizationId) {
		try {
			OrganizationMaster organizationMaster = organizationMasterRepository
					.findByOrgId(organizationId);
			stopSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: RouteManagementDaoImpl class :: getOrgDetails method :: exception", e);
		}
	}

	private int totalNoOfRecords(StopSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QStopProfile.stopProfile)
					.where(QStopProfile.stopProfile.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isStageId(request.getStageId()),
							isStopName(request.getStopName()), 
							isStatus(request.getStatus()),
							isStopId(request.getStopId()))
					.orderBy(QStopProfile.stopProfile.stopId.desc()).count();

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QStopProfile.stopProfile)
					.where(QStopProfile.stopProfile.status.ne(Status.TERMINATED.getValue()),
							QStopProfile.stopProfile.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isStageId(request.getStageId()),
							isStopName(request.getStopName()), 
							isStatus(request.getStatus()),
							isStopId(request.getStopId()))
					.orderBy(QStopProfile.stopProfile.stopId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QStopProfile.stopProfile)
					.where(QStopProfile.stopProfile.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isStageId(request.getStageId()),
							isStopName(request.getStopName()), 
							isStatus(request.getStatus()),
							isStopId(request.getStopId()))
					.orderBy(QStopProfile.stopProfile.stopId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QStopProfile.stopProfile.organizationId.eq(organizationId)
				: null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QStopProfile.stopProfile.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isStopId(Long stopId) {
		return !StringUtil.isNull(stopId) ? QStopProfile.stopProfile.stopId.eq(stopId) : null;
	}

	private BooleanExpression isStopName(String stopName) {
		return !StringUtil.isNullEmpty(stopName) ? QStopProfile.stopProfile.stopName.toUpperCase()
				.like("%" + stopName.toUpperCase().replace("*", "") + "%") : null;

	}
	private BooleanExpression isStageId(Long stageId) {
		return !StringUtil.isNull(stageId) ? QStopProfile.stopProfile.stageId.eq(stageId)
				: null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QStopProfile.stopProfile.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	@Transactional
	@Override
	public boolean updateStop(StopUpdateRequest request) {

		QStopProfile stopMaster = QStopProfile.stopProfile;
		long noOfRows = new JPAUpdateClause(em, stopMaster).where(stopMaster.stopId.eq(request.getStopId()))
				.set(stopMaster.ptoId, request.getPtoId())
				.set(stopMaster.routeId, request.getRouteId())
				.set(stopMaster.stageId, request.getStageId())
				.set(stopMaster.stopName, request.getStopName())
				.set(stopMaster.organizationId, request.getOrganizationId()).execute();
		return noOfRows == 1;
	}

	@Override
	public List<StageSearchRequest> getStageNameList() {
		JPAQuery query = new JPAQuery(em);
		List<StageSearchRequest> stageList = new ArrayList<>();
		List<StageMaster> stageMasterList = query.from(QStageMaster.stageMaster)
				.where(QStageMaster.stageMaster.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QStageMaster.stageMaster.stageId.desc()).list(QStageMaster.stageMaster);
		for (StageMaster stageProfileList : stageMasterList) {
			StageSearchRequest stageSearchRequest = new StageSearchRequest();
			stageSearchRequest.setStageName(stageProfileList.getStageName());
			stageSearchRequest.setStageId(stageProfileList.getStageId());
			stageList.add(stageSearchRequest);
		}
		return stageList;
		
	}
	
	@Override
	public List<StopSearchRequest> getStopNameList() {
		JPAQuery query = new JPAQuery(em);
		List<StopSearchRequest> stopList = new ArrayList<>();
		List<StopProfile> stopMasterList = query.from(QStopProfile.stopProfile)
				.where(QStopProfile.stopProfile.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QStopProfile.stopProfile.stopId.desc()).list(QStopProfile.stopProfile);
		for (StopProfile stopProfileList : stopMasterList) {
			StopSearchRequest stopSearchRequest = new StopSearchRequest();
			stopSearchRequest.setStopName(stopProfileList.getStopName());
			stopSearchRequest.setStopId(stopProfileList.getStopId());
			stopList.add(stopSearchRequest);
		}
		return stopList;
	}
	
	@Override
	public StopProfile updateStopStatus(StopUpdateRequest request) {
		StopProfile stopProfile = stopProfileRepository.findByStopId(request.getStopId());
		stopProfile.setStatus(request.getStatus());
		stopProfile.setReason(request.getReason());
		return stopProfileRepository.save(stopProfile);
	}

	@Override
	public List<StopProfile> getAllStopsByPtoIdFromTerminal(Long ptoId, Long routeId) {
		return stopMasterRepository.findByPtoIdAndRouteIdAndStatusNotLike(ptoId, routeId, Status.TERMINATED.getValue());
	}

	@Override
	public List<StopProfile> getAllStopsByPtoId(Long ptoId) {
		return stopProfileRepository.findByPtoIdAndStatusNotLike(ptoId, Status.TERMINATED.getValue());
	}

	@Override
	public StopProfile validateStopCode(String stopCode) {
		return stopProfileRepository.findByStopId(Long.valueOf(stopCode));
	}

	@Override
	public boolean validateStopId(String stopCode) {
		return stopProfileRepository.existsByStopId(Long.valueOf(stopCode));
	}

	@Override
	public StopSearchResponse getStageByRouteId(StopSearchRequest request) {
		List<StopSearchRequest> stopList = new ArrayList<>();
		List<StageMaster> stageList = stageManagementRepository.findByRouteId(request.getRouteId());
		StopSearchResponse response = new StopSearchResponse();
		for(StageMaster stageMaster : stageList) {
			StopSearchRequest searchRequest = new StopSearchRequest();
			searchRequest.setStageId(stageMaster.getStageId());
			searchRequest.setStageName(stageMaster.getStageName());
			stopList.add(searchRequest);
		}
		response.setStopSearchList(stopList);
		return response;
	}

}
