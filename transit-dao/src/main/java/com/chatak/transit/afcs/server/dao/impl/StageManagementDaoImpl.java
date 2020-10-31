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
import com.chatak.transit.afcs.server.dao.StageManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QRouteMaster;
import com.chatak.transit.afcs.server.dao.model.QStageMaster;
import com.chatak.transit.afcs.server.dao.model.QStopProfile;
import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.model.StageMaster;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.RouteManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.RouteNameRepository;
import com.chatak.transit.afcs.server.dao.repository.StageManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.StageRepository;
import com.chatak.transit.afcs.server.dao.repository.StopMappingRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class StageManagementDaoImpl implements StageManagementDao {
	
	private static final Logger logger = LoggerFactory.getLogger(StageManagementDaoImpl.class);

	@Autowired
	StageRepository stageRepository;

	@Autowired
	RouteNameRepository routeNameRepository;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	StageManagementRepository stageManagementRepository;

	@Autowired
	StopMappingRepository stopMappingRepository;

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@Autowired
	PtoManagementDao ptoManagementDao;

	@Autowired
	RouteManagementRepository routeManagementRepository;
	@Override
	public boolean validateStageRegistrationRequest(StageRegistrationRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId());
	}

	@Override
	public StageResponse searchStage(StageSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<StageMaster> stageList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			stageList = query.from(QStageMaster.stageMaster)
					.where(QStageMaster.stageMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isStageName(request.getStageName()), isStatus(request.getStatus()),
							isStageId(request.getStageId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QStageMaster.stageMaster.stageId.desc())
					.list(QStageMaster.stageMaster);

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			stageList = query.from(QStageMaster.stageMaster)
					.where(QStageMaster.stageMaster.status.ne(Status.TERMINATED.getValue()),
							QStageMaster.stageMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isStageName(request.getStageName()), isStatus(request.getStatus()),
							isStageId(request.getStageId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QStageMaster.stageMaster.stageId.desc())
					.list(QStageMaster.stageMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			stageList = query.from(QStageMaster.stageMaster)
					.where(QStageMaster.stageMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isStageName(request.getStageName()), isStatus(request.getStatus()),
							isStageId(request.getStageId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QStageMaster.stageMaster.stageId.desc())
					.list(QStageMaster.stageMaster);
		}
		StageResponse stageResponse = new StageResponse();
		List<StageSearchRequest> stagelistresponse = new ArrayList<>();
		for (StageMaster stage : stageList) {
			StageSearchRequest stageSearchRequest = new StageSearchRequest();
			stageSearchRequest.setStageId(stage.getStageId());
			stageSearchRequest.setPtoId(stage.getPtoId());
			stageSearchRequest.setRouteId(stage.getRouteId());
			stageSearchRequest.setStatus(stage.getStatus());
			stageSearchRequest.setStageName(stage.getStageName());
			stageSearchRequest.setOrganizationId(stage.getOrganizationId());
			if (!StringUtil.isNull(stageSearchRequest.getOrganizationId())) {
				Long organizationId = stageSearchRequest.getOrganizationId();
				getOrgDetails(stageSearchRequest, organizationId);
			}
			if (!StringUtil.isNull(stageSearchRequest.getPtoId())) {
				Long ptoId = stageSearchRequest.getPtoId();
				getPtoDetails(stageSearchRequest, ptoId);
			}
			if (!StringUtil.isNull(stageSearchRequest.getRouteId())) {
				Long routeId = stageSearchRequest.getRouteId();
				getRouteDetails(stageSearchRequest, routeId);
			}
			stagelistresponse.add(stageSearchRequest);
		}

		stageResponse.setListOfStages(stagelistresponse);
		stageResponse.setNoOfRecords(getTotalRecords(request));
		return stageResponse;
	}

	private void getRouteDetails(StageSearchRequest stageSearchRequest, Long routeId) {
		try {
			RouteMaster routeMaster = routeManagementRepository.findByRouteId(routeId);
			stageSearchRequest.setRouteName(routeMaster.getRouteName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: StageManagementDaoImpl class :: getRouteDetails method :: exception", e);
		}
	}

	private void getPtoDetails(StageSearchRequest stageSearchRequest, Long ptoId) {
		try {
			PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
			stageSearchRequest.setPtoName(ptoMaster.getPtoName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: StageManagementDaoImpl class :: getPtoDetails method :: exception", e);
		}
	}

	private void getOrgDetails(StageSearchRequest stageSearchRequest, Long organizationId) {
		try {
			OrganizationMaster organizationMaster = organizationMasterRepository
					.findByOrgId(organizationId);
			stageSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
		} catch (NullPointerException e) {
			logger.error("ERROR :: StageManagementDaoImpl class :: getOrgDetails method :: exception", e);
		}
	}

	private int getTotalRecords(StageSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QStageMaster.stageMaster)
					.where(QStageMaster.stageMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isStageName(request.getStageName()), isStatus(request.getStatus()),
							isStageId(request.getStageId()))
					.orderBy(QStageMaster.stageMaster.stageId.desc()).count();

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QStageMaster.stageMaster)
					.where(QStageMaster.stageMaster.status.ne(Status.TERMINATED.getValue()),
							QStageMaster.stageMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isStageName(request.getStageName()), isStatus(request.getStatus()),
							isStageId(request.getStageId()))
					.orderBy(QStageMaster.stageMaster.stageId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QStageMaster.stageMaster)
					.where(QStageMaster.stageMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isStageName(request.getStageName()), isStatus(request.getStatus()),
							isStageId(request.getStageId()))
					.orderBy(QStageMaster.stageMaster.stageId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QStageMaster.stageMaster.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QStageMaster.stageMaster.organizationId.eq(organizationId)
				: null;
	}
	
	private BooleanExpression isStageId(Long stageId) {
		return !StringUtil.isNull(stageId) ? QStageMaster.stageMaster.stageId.eq(stageId)
				: null;
	}

	private BooleanExpression isStageName(String stageName) {
		return !StringUtil.isNullEmpty(stageName)
				? QStageMaster.stageMaster.stageName.like("%" + stageName.replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QStageMaster.stageMaster.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	@Override
	public List<RouteSearchRequest> getRouteNameList() {
		JPAQuery query = new JPAQuery(em);
		List<RouteSearchRequest> routeList = new ArrayList<>();
		List<RouteMaster> routeMasterList = query.from(QRouteMaster.routeMaster)
				.where(QRouteMaster.routeMaster.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QRouteMaster.routeMaster.routeId.desc()).list(QRouteMaster.routeMaster);
		for (RouteMaster routeProfileList : routeMasterList) {
			RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
			routeSearchRequest.setRouteName(routeProfileList.getRouteName());
			routeSearchRequest.setRouteId(routeProfileList.getRouteId());
			routeList.add(routeSearchRequest);
		}
		return routeList;
	}
	
	@Transactional
	@Override
	public boolean stageProfileUpdate(StageRegistrationRequest request) {
		QStageMaster stageMaster = QStageMaster.stageMaster;
		long noOfRowStage = new JPAUpdateClause(em, stageMaster).where(stageMaster.stageId.eq(request.getStageId()))
				.set(stageMaster.organizationId, request.getOrganizationId())
				.set(stageMaster.ptoId, request.getPtoId())
				.set(stageMaster.stageName, request.getStageName()).execute();
		List<StopRegistrationRequest> listOfData = request.getDataFieldList();
		if (StringUtil.isListNotNullNEmpty(listOfData)) {
			List<StopRegistrationRequest> addStops = request.getDataFieldList();
			int size = stopMappingRepository.findByStageId(request.getStageId()).size();
			int count = addStops.size();
			List<StopRegistrationRequest> listOfStops = new ArrayList<>(addStops.subList(0, size));
			long noOfStops = 0;

			for (StopRegistrationRequest add : listOfStops) {
				QStopProfile stopMapping = QStopProfile.stopProfile;
				noOfStops += new JPAUpdateClause(em, stopMapping)
						.where(stopMapping.stageId.eq(request.getStageId()), stopMapping.stopId.eq(add.getStopId()))
						.set(stopMapping.organizationId, request.getOrganizationId())
						.set(stopMapping.ptoId, request.getPtoId())
						.set(stopMapping.routeId, request.getRouteId())
						.set(stopMapping.stopName, add.getStopName())
						.set(stopMapping.stopSequenceNumber, add.getStopSequenceNumber())
						.set(stopMapping.distance, add.getDistance())
						.execute();
			}

			List<StopRegistrationRequest> listOfStopsAfter = new ArrayList<>(addStops.subList(size, count));
			List<StopProfile> listOfStopMapping = new ArrayList<>();

			for (StopRegistrationRequest add : listOfStopsAfter) {
				StopProfile stopMapping = new StopProfile();
				stopMapping.setStageId(request.getStageId());
				stopMapping.setStopSequenceNumber(add.getStopSequenceNumber());
				stopMapping.setDistance(add.getDistance());
				stopMapping.setStatus(Status.ACTIVE.getValue());
				stopMapping.setOrganizationId(request.getOrganizationId());
				stopMapping.setPtoId(request.getPtoId());
				stopMapping.setStopName(add.getStopName());
				stopMapping.setRouteId(request.getRouteId());
				if (!StringUtil.isNull(add.getOrganizationId())) {
					getOrgDetail(add, stopMapping);
				}
				if (!StringUtil.isNull(add.getPtoId())) {
					getPtoDetail(add, stopMapping);
				}
				listOfStopMapping.add(stopMapping);
			}
			stopMappingRepository.saveAll(listOfStopMapping);

			if ((noOfRowStage == 1l && noOfStops >= 1l)) {
				return true;
			}
		}
		return noOfRowStage == 1l;
	}

	private void getPtoDetail(StopRegistrationRequest add, StopProfile stopMapping) {
		try {
			PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(add.getPtoId());
			stopMapping.setPtoId(ptoMaster.getPtoMasterId());
		} catch (NullPointerException e) {
			logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
		}
	}

	private void getOrgDetail(StopRegistrationRequest add, StopProfile stopMapping) {
		try {
			OrganizationMaster organizationMaster = organizationMasterRepository
					.findByOrgId(add.getOrganizationId());
			stopMapping.setOrganizationId(organizationMaster.getOrgId());
		} catch (NullPointerException e) {
			logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
		}
	}

	@Override
	public boolean validateStageStatusUpdate(StageStatusUpdateRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& stageRepository.existsByStageId(request.getStageId());
	}

	@Override
	public StageMaster saveStage(StageMaster stageMaster) {
		return stageManagementRepository.save(stageMaster);
	}

	@Override
	public void saveStopByStage(StopProfile map) {
		stopMappingRepository.save(map);
		
	}

	@Override
	public StageResponse getStageViewList(StageSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<Tuple> listOfStop = query.from(QStopProfile.stopProfile, QStageMaster.stageMaster)
				.where(isStageId(request.getStageId()),
						isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
						isStopSequenceNumber(request.getStopSequenceNumber()), isStageName(request.getStageName()),
						isDistance(request.getDistance()), isStatus(request.getStatus()))
				.offset(fromIndex).limit(Constants.SIZE).orderBy(QStageMaster.stageMaster.stageId.asc())
				.list(QStageMaster.stageMaster.stageId, QStageMaster.stageMaster.organizationId,
						QStageMaster.stageMaster.ptoId, QStageMaster.stageMaster.routeId,
						QStageMaster.stageMaster.stageName, QStageMaster.stageMaster.status,
						QStopProfile.stopProfile.stopSequenceNumber, QStopProfile.stopProfile.stopId,
						QStopProfile.stopProfile.distance);

		StageResponse response = new StageResponse();
		List<StageSearchRequest> stageViewResponselist = new ArrayList<>();
		List<StopRegistrationRequest> listAddStops = new ArrayList<>();
		for (Tuple tup : listOfStop) {
			StopRegistrationRequest addStopAll = new StopRegistrationRequest();
			addStopAll.setStopId(tup.get(QStopProfile.stopProfile.stopId));
			addStopAll.setStopSequenceNumber(tup.get(QStopProfile.stopProfile.stopSequenceNumber));
			addStopAll.setDistance(tup.get(QStopProfile.stopProfile.distance));
			listAddStops.add(addStopAll);
		}
		for (Tuple tuple : listOfStop) {
			StageSearchRequest stageViewRequest = new StageSearchRequest();
			stageViewRequest.setStageId(tuple.get(QStageMaster.stageMaster.stageId));
			stageViewRequest.setOrganizationId(tuple.get(QStageMaster.stageMaster.organizationId));
			stageViewRequest.setPtoId(tuple.get(QStageMaster.stageMaster.ptoId));
			stageViewRequest.setStageName(tuple.get(QStageMaster.stageMaster.stageName));
			stageViewRequest.setStatus(tuple.get(QStageMaster.stageMaster.status));
			stageViewRequest.setStopSequenceNumber(tuple.get(QStopProfile.stopProfile.stopSequenceNumber));
			stageViewRequest.setDistance(tuple.get(QStopProfile.stopProfile.distance));
			stageViewResponselist.add(stageViewRequest);
		}
		response.setListOfStages(stageViewResponselist);
		return response;
	}

	private BooleanExpression isStopSequenceNumber(int stopSequenceNumber) {
		return !StringUtil.isNull(stopSequenceNumber)
				? QStopProfile.stopProfile.stopSequenceNumber.eq(stopSequenceNumber) : null;

	}

	private BooleanExpression isDistance(double distance) {
		return !StringUtil.isNull(distance) ? QStopProfile.stopProfile.distance.eq(distance) : null;

	}

	private BooleanExpression isStageIdOnStops(Long stageId) {
		return !StringUtil.isNull(stageId) ? QStopProfile.stopProfile.stageId.eq(stageId) : null;
	}

	@Override
	public StageResponse getViewStop(StageSearchRequest request) {
		StageResponse stageResponse = new StageResponse();
		JPAQuery query = new JPAQuery(em);

		List<StageMaster> listOfStage = query.from(QStageMaster.stageMaster).where(isStageId(request.getStageId()))
				.orderBy(QStageMaster.stageMaster.stageId.desc()).list(QStageMaster.stageMaster);
		StageMaster stageMaster = listOfStage.get(0);

		List<StopProfile> listOfStop = query.from(QStopProfile.stopProfile)
				.where(isStageIdOnStops(request.getStageId())).orderBy(QStopProfile.stopProfile.stageId.desc())
				.list(QStopProfile.stopProfile);

		List<StopRegistrationRequest> listOfStopsOnStage = new ArrayList<>();
		for (StopProfile stopMapping : listOfStop) {
			StopRegistrationRequest addStops = new StopRegistrationRequest();
			addStops.setStopSequenceNumber(stopMapping.getStopSequenceNumber());
			addStops.setDistance(stopMapping.getDistance());
			addStops.setStopId(stopMapping.getStopId());
			addStops.setStopName(stopMapping.getStopName());
			listOfStopsOnStage.add(addStops);
		}
		List<StageSearchRequest> listOfStages = new ArrayList<>();
		StageSearchRequest stageRequest = new StageSearchRequest();
		RouteMaster routeMaster = routeManagementRepository.findByRouteId(listOfStage.get(0).getRouteId());
		stageRequest.setDataFieldList(listOfStopsOnStage);
		stageRequest.setOrganizationId(stageMaster.getOrganizationId());
		stageRequest.setPtoId(stageMaster.getPtoId());
		stageRequest.setStageId(stageMaster.getStageId());
		stageRequest.setStageName(stageMaster.getStageName());
		stageRequest.setRouteName(routeMaster.getRouteName());
		stageRequest.setRouteId(stageMaster.getRouteId());
		listOfStages.add(stageRequest);
		stageResponse.setListOfStages(listOfStages);
		return stageResponse;

	}

	@Override
	public boolean deleteStop(StageRegistrationRequest request) {
		stopMappingRepository.deleteByStopId(request.getStopId());
		return true;
	}

	@Override
	public StageMaster updateStageStatus(StageStatusUpdateRequest request) {
		StageMaster stageMaster = stageRepository.findByStageId(request.getStageId());
		stageMaster.setStatus(request.getStatus());
		stageMaster.setReason(request.getReason());
		return stageRepository.save(stageMaster);
	}
	
	@Override
	public RouteSearchResponse getRouteByPto(RouteSearchRequest routeSearchRequest) {
		List<RouteSearchRequest> routeList = new ArrayList<>();
		RouteSearchResponse response = new RouteSearchResponse();
		List<RouteMaster> listRouteMaster = routeNameRepository.findByPtoIdAndStatusLike(routeSearchRequest.getPtoId(), Status.ACTIVE.getValue());
		
		for(RouteMaster routeMaster : listRouteMaster) {
			routeSearchRequest = new RouteSearchRequest();
			routeSearchRequest.setRouteId(routeMaster.getRouteId());
			routeSearchRequest.setRouteName(routeMaster.getRouteName());
			routeList.add(routeSearchRequest);
		}
		response.setListORoutes(routeList);
		return response;
	}

}
