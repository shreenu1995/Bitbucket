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
import com.chatak.transit.afcs.server.dao.RouteManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QRouteMaster;
import com.chatak.transit.afcs.server.dao.model.QStageMaster;
import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.model.StageMaster;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.RouteManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.StageMappingRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.AddStages;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class RouteManagementDaoImpl implements RouteManagementDao {

	@Autowired
	RouteManagementRepository routeManagementRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	StageMappingRepository stageMappingRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@PersistenceContext
	private EntityManager em;

	
	private static final Logger logger = LoggerFactory.getLogger(RouteManagementDaoImpl.class);

	@Override
	public List<RouteMaster> getAllRoutes(Long ptoId) {
		return routeManagementRepository.findByPtoIdAndStatusNotLike(ptoId, Status.TERMINATED.getValue());
	}
	
	@Override
	public boolean validateRouteRegistrationRequest(RouteRegistrationRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId());
	}

	@Override
	public RouteSearchResponse getRouteSearchList(RouteSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<RouteMaster> listOfRouteMaster = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			listOfRouteMaster = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isRouteName(request.getRouteName()),
							isFromRoute(request.getFromRoute()),
							isToRoute(request.getToRoute()), 
							isStatus(request.getStatus()),
							isRouteId(request.getRouteId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QRouteMaster.routeMaster.routeId.desc())
					.list(QRouteMaster.routeMaster);

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			listOfRouteMaster = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							QRouteMaster.routeMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isRouteName(request.getRouteName()), isFromRoute(request.getFromRoute()),
							isToRoute(request.getToRoute()), isStatus(request.getStatus()),
							isRouteId(request.getRouteId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QRouteMaster.routeMaster.routeId.desc())
					.list(QRouteMaster.routeMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			listOfRouteMaster = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isRouteName(request.getRouteName()), isFromRoute(request.getFromRoute()),
							isToRoute(request.getToRoute()), isStatus(request.getStatus()),
							isRouteId(request.getRouteId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QRouteMaster.routeMaster.routeId.desc())
					.list(QRouteMaster.routeMaster);
		}

		RouteSearchResponse response = new RouteSearchResponse();
		List<RouteSearchRequest> routeSearchResponselist = new ArrayList<>();
		for (RouteMaster routeMasterTabledata : listOfRouteMaster) {
			RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
			routeSearchRequest.setRouteId(routeMasterTabledata.getRouteId());
			routeSearchRequest.setRouteName(routeMasterTabledata.getRouteName());
			routeSearchRequest.setPtoId(routeMasterTabledata.getPtoId());
			routeSearchRequest.setFromRoute(routeMasterTabledata.getFromRoute());
			routeSearchRequest.setToRoute(routeMasterTabledata.getToRoute());
			routeSearchRequest.setStatus(routeMasterTabledata.getStatus());
			routeSearchRequest.setOrganizationId(routeMasterTabledata.getOrganizationId());
			routeSearchRequest.setRouteCode(routeMasterTabledata.getRouteCode());
			if (!StringUtil.isNull(routeSearchRequest.getOrganizationId())) {
				Long organizationId = routeSearchRequest.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository
							.findByOrgId(organizationId);
					routeSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: RouteManagementDaoImpl class :: getRouteSearchList method :: exception", e);
				}
			}
			if (!StringUtil.isNull(routeSearchRequest.getPtoId())) {
				Long ptoId = routeSearchRequest.getPtoId();
				try {
					PtoMaster organizationMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					routeSearchRequest.setPtoName(organizationMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: RouteManagementDaoImpl class :: getRouteSearchList method :: exception", e);
				}
			}
			routeSearchResponselist.add(routeSearchRequest);
		}
		response.setTotalRecords(getTotalTicketsTxnRows(request));
		response.setListORoutes(routeSearchResponselist);
		return response;
	}

	private int getTotalTicketsTxnRows(RouteSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isRouteName(request.getRouteName()), isFromRoute(request.getFromRoute()),
							isToRoute(request.getToRoute()), isStatus(request.getStatus()),
							isRouteId(request.getRouteId()))
					.orderBy(QRouteMaster.routeMaster.routeId.desc()).count();

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							QRouteMaster.routeMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isRouteName(request.getRouteName()), isFromRoute(request.getFromRoute()),
							isToRoute(request.getToRoute()), isStatus(request.getStatus()),
							isRouteId(request.getRouteId()))
					.orderBy(QRouteMaster.routeMaster.routeId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isRouteName(request.getRouteName()), isFromRoute(request.getFromRoute()),
							isToRoute(request.getToRoute()), isStatus(request.getStatus()),
							isRouteId(request.getRouteId()))
					.orderBy(QRouteMaster.routeMaster.routeId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}


	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QRouteMaster.routeMaster.organizationId.eq(organizationId) : null;

	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QRouteMaster.routeMaster.ptoId.eq(ptoId) : null;

	}
	
	private BooleanExpression isRouteId(Long routeId) {
		return !StringUtil.isNull(routeId) ? QRouteMaster.routeMaster.routeId.eq(routeId) : null;

	}
	
	private BooleanExpression isStageRouteId(Long routeId) {
		return !StringUtil.isNull(routeId) ? QStageMaster.stageMaster.routeId.eq(routeId) : null;

	}

	private BooleanExpression isRouteName(String routeName) {
		return !StringUtil.isNullEmpty(routeName) ? QRouteMaster.routeMaster.routeName.toUpperCase()
				.like("%" + routeName.toUpperCase().replace("*", "") + "%") : null;
	}

	

	private BooleanExpression isFromRoute(String fromRoute) {
		return !StringUtil.isNull(fromRoute) ? QRouteMaster.routeMaster.fromRoute.toUpperCase()
				.like("%" + fromRoute.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isToRoute(String toRoute) {
		return !StringUtil.isNull(toRoute) ? QRouteMaster.routeMaster.toRoute.toUpperCase()
				.like("%" + toRoute.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QRouteMaster.routeMaster.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isStageSequenceNumber(int stageSequenceNumber) {
		return !StringUtil.isNull(stageSequenceNumber)
				? QStageMaster.stageMaster.stageSequenceNumber.eq(stageSequenceNumber) : null;

	}

	private BooleanExpression isStageName(String stageName) {
		return !StringUtil.isNull(stageName) ? QStageMaster.stageMaster.stageName.toUpperCase()
				.like("%" + stageName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isDistance(double distance) {
		return !StringUtil.isNull(distance) ? QStageMaster.stageMaster.distance.eq(distance) : null;

	}

	@Override
	public RouteSearchResponse getRouteViewList(RouteSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<Tuple> listOfStage = query.from(QStageMaster.stageMaster, QRouteMaster.routeMaster)
				.where(isRouteId(request.getRouteId()), 
						isRouteName(request.getRouteName()),
						isOrganizationId(request.getOrganizationId()),
						isPtoId(request.getPtoId()),
						isFromRoute(request.getFromRoute()),
						isToRoute(request.getToRoute()),
						isStageSequenceNumber(request.getStageSequenceNumber()),
						isStageName(request.getStageName()),
						isDistance(request.getDistance()), isStatus(request.getStatus()))
				.offset(fromIndex).limit(Constants.SIZE).orderBy(QRouteMaster.routeMaster.routeId.asc())
				.list(QRouteMaster.routeMaster.routeId, QRouteMaster.routeMaster.organizationId,
						QRouteMaster.routeMaster.ptoId, QRouteMaster.routeMaster.routeName,
						QRouteMaster.routeMaster.fromRoute, QRouteMaster.routeMaster.toRoute,
						QRouteMaster.routeMaster.routeCode,QRouteMaster.routeMaster.status,
						QStageMaster.stageMaster.stageSequenceNumber,QStageMaster.stageMaster.stageName,
						QStageMaster.stageMaster.distance);

		RouteSearchResponse response = new RouteSearchResponse();
		List<RouteSearchRequest> routeViewResponselist = new ArrayList<>();
		List<AddStages> listAddStages = new ArrayList<>();

		for (Tuple tup : listOfStage) {

			AddStages addStagesAll = new AddStages();
			addStagesAll.setStageName(tup.get(QStageMaster.stageMaster.stageName));
			addStagesAll.setStageSequenceNumber(tup.get(QStageMaster.stageMaster.stageSequenceNumber));
			addStagesAll.setDistance(tup.get(QStageMaster.stageMaster.distance));
			listAddStages.add(addStagesAll);
		}

		for (Tuple tuple : listOfStage) {
			RouteSearchRequest routeViewRequest = new RouteSearchRequest();
			routeViewRequest.setRouteId(tuple.get(QRouteMaster.routeMaster.routeId));
			routeViewRequest.setOrganizationId(tuple.get(QRouteMaster.routeMaster.organizationId));
			routeViewRequest.setPtoId(tuple.get(QRouteMaster.routeMaster.ptoId));
			routeViewRequest.setRouteName(tuple.get(QRouteMaster.routeMaster.routeName));
			routeViewRequest.setRouteCode(tuple.get(QRouteMaster.routeMaster.routeCode));
			routeViewRequest.setFromRoute(tuple.get(QRouteMaster.routeMaster.fromRoute));
			routeViewRequest.setToRoute(tuple.get(QRouteMaster.routeMaster.toRoute));
			routeViewRequest.setStatus(tuple.get(QRouteMaster.routeMaster.status));
			routeViewRequest.setStageSequenceNumber(tuple.get(QStageMaster.stageMaster.stageSequenceNumber));
			routeViewRequest.setStageName(tuple.get(QStageMaster.stageMaster.stageName));
			routeViewRequest.setDistance(tuple.get(QStageMaster.stageMaster.distance));
			routeViewResponselist.add(routeViewRequest);
		}
		response.setListORoutes(routeViewResponselist);
		return response;
	}

	@Override
	public RouteSearchResponse getViewStagesOnRole(RouteSearchRequest request) {
		RouteSearchResponse routeSearchResponse = new RouteSearchResponse();
		JPAQuery query = new JPAQuery(em);

		List<RouteMaster> listOfRoute = query.from(QRouteMaster.routeMaster).where(isRouteId(request.getRouteId()))
				.orderBy(QRouteMaster.routeMaster.routeId.desc()).list(QRouteMaster.routeMaster);
		RouteMaster routeMaster = listOfRoute.get(0);

		List<StageMaster> listOfStage = query.from(QStageMaster.stageMaster)
				.where(isStageRouteId(request.getRouteId())).orderBy(QStageMaster.stageMaster.routeId.desc())
				.list(QStageMaster.stageMaster);

		List<AddStages> listOfStagesOnRoute = new ArrayList<>();
		for (StageMaster stageMapping : listOfStage) {
			AddStages routeSearchRequest = new AddStages();

			routeSearchRequest.setStageName(stageMapping.getStageName());
			routeSearchRequest.setStageSequenceNumber(stageMapping.getStageSequenceNumber());
			routeSearchRequest.setDistance(stageMapping.getDistance());
			routeSearchRequest.setId(stageMapping.getStageId());
			listOfStagesOnRoute.add(routeSearchRequest);
		}
		List<RouteSearchRequest> listOfRouteD = new ArrayList<>();
		RouteSearchRequest routeRequest = new RouteSearchRequest();
		routeRequest.setDataFieldList(listOfStagesOnRoute);
		routeRequest.setFromRoute(routeMaster.getFromRoute());
		routeRequest.setToRoute(routeMaster.getToRoute());
		routeRequest.setOrganizationId(routeMaster.getOrganizationId());
		routeRequest.setPtoId(routeMaster.getPtoId());
		routeRequest.setRouteId(routeMaster.getRouteId());
		routeRequest.setRouteCode(routeMaster.getRouteCode());
		routeRequest.setRouteName(routeMaster.getRouteName());
		listOfRouteD.add(routeRequest);
		routeSearchResponse.setListORoutes(listOfRouteD);
		return routeSearchResponse;
	}

	@Transactional
	@Override
	public boolean isUpdateRouteProfile(RouteRegistrationRequest request) {
		QRouteMaster routeMaster = QRouteMaster.routeMaster;
		long noOfRows = new JPAUpdateClause(em, routeMaster).where(routeMaster.routeId.eq(request.getRouteId()))
				.set(routeMaster.organizationId, request.getOrganizationId())
				.set(routeMaster.routeCode, request.getRouteCode()).set(routeMaster.ptoId, request.getPtoId())
				.set(routeMaster.routeName, request.getRouteName()).set(routeMaster.fromRoute, request.getFromRoute())
				.set(routeMaster.toRoute, request.getToRoute()).execute();

		List<AddStages> addStages = new ArrayList<>();
		AddStages addStage = new AddStages();
		addStage.setDistance(request.getDistance());
		addStage.setId(request.getId());
		addStage.setStageSequenceNumber(request.getStageSequenceNumber());
		addStage.setStageName(request.getStageName());
		addStages.add(addStage);
		
		if (StringUtil.isListNotNullNEmpty(addStages)) {
			List<AddStages> listOfData = request.getDataFieldList();
			int size = stageMappingRepository.findByRouteId(request.getRouteId()).size();
			int count = listOfData.size();
			List<AddStages> listOfStages = new ArrayList<>(addStages.subList(0, size));
			long noOfStages = 0;

			for (AddStages add : listOfStages) {
				QStageMaster stageMapping = QStageMaster.stageMaster;
				noOfStages = new JPAUpdateClause(em, stageMapping)
						.where(stageMapping.routeId.eq(request.getRouteId()),
								stageMapping.stageId.eq(add.getId()))
						.set(stageMapping.organizationId, request.getOrganizationId())
						.set(stageMapping.ptoId, request.getPtoId())
						.set(stageMapping.routeId, request.getRouteId())
						.set(stageMapping.stageSequenceNumber, add.getStageSequenceNumber())
						.set(stageMapping.stageName, add.getStageName()).set(stageMapping.distance, add.getDistance())
						.execute();
			}

			List<AddStages> listOfStagesAfter = new ArrayList<>(listOfData.subList(size, count));
			List<StageMaster> listOfStageMapping = new ArrayList<>();

			for (AddStages add : listOfStagesAfter) {
				StageMaster stageMapping = new StageMaster();
				stageMapping.setRouteId(request.getRouteId());
				stageMapping.setStageName(add.getStageName());
				stageMapping.setStageSequenceNumber(add.getStageSequenceNumber());
				stageMapping.setDistance(add.getDistance());
				stageMapping.setStatus(Status.ACTIVE.getValue());
				stageMapping.setOrganizationId(request.getOrganizationId());
				stageMapping.setPtoId(request.getPtoId());
				stageMapping.setRouteId(request.getRouteId());
				listOfStageMapping.add(stageMapping);
			}
			stageMappingRepository.saveAll(listOfStageMapping);
			if ((noOfRows == 1l && noOfStages >= 1l)) {
				return true;
			}
		}
		return noOfRows == 1l;
	}

	@Override
	public boolean deleteStage(RouteRegistrationRequest request) {
		stageMappingRepository.deleteByStageId((int) request.getId());
		return true;
	}

	@Override
	public RouteMaster isUpdateRouteStatus(RouteStatusUpdateRequest request) {
		RouteMaster routeMaster = routeManagementRepository.findByRouteId(request.getRouteId());
		routeMaster.setStatus(request.getStatus());
		routeMaster.setReason(request.getReason());
		return routeManagementRepository.save(routeMaster);
	}
	
	@Override
	public RouteSearchResponse getRouteSearchListForPtoResult(RouteSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		List<RouteMaster> listOfRouteMaster = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			listOfRouteMaster = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()))
					.orderBy(QRouteMaster.routeMaster.routeId.desc())
					.list(QRouteMaster.routeMaster);

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			listOfRouteMaster = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							QRouteMaster.routeMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()))
					.orderBy(QRouteMaster.routeMaster.routeId.desc())
					.list(QRouteMaster.routeMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			listOfRouteMaster = query.from(QRouteMaster.routeMaster)
					.where(QRouteMaster.routeMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()))
					.orderBy(QRouteMaster.routeMaster.routeId.desc())
					.list(QRouteMaster.routeMaster);
		}

		RouteSearchResponse response = new RouteSearchResponse();
		List<RouteSearchRequest> routeSearchResponselist = new ArrayList<>();
		for (RouteMaster routeMasterTabledata : listOfRouteMaster) {
			RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
			routeSearchRequest.setRouteId(routeMasterTabledata.getRouteId());
			routeSearchRequest.setRouteName(routeMasterTabledata.getRouteName());
			routeSearchRequest.setOrganizationId(routeMasterTabledata.getOrganizationId());
			routeSearchRequest.setPtoId(routeMasterTabledata.getPtoId());
			routeSearchRequest.setFromRoute(routeMasterTabledata.getFromRoute());
			routeSearchRequest.setToRoute(routeMasterTabledata.getToRoute());
			routeSearchRequest.setStatus(routeMasterTabledata.getStatus());
			routeSearchRequest.setOrganizationId(routeMasterTabledata.getOrganizationId());
			routeSearchRequest.setRouteCode(routeMasterTabledata.getRouteCode());
			routeSearchResponselist.add(routeSearchRequest);
		}
		response.setTotalRecords(getTotalTicketsTxnRows(request));
		response.setListORoutes(routeSearchResponselist);
		return response;
	}
	
	@Override
	public RouteMaster validateRouteCode(BulkUploadRequest bulkUploadRequest) {
		return routeManagementRepository.findByRouteCodeAndPtoId(bulkUploadRequest.getRouteCode(), Long.valueOf(bulkUploadRequest.getPtoId()));
	}

	@Override
	public boolean validateRouteId(BulkUploadRequest bulkUploadRequest) {
		return routeManagementRepository.existsByRouteCodeAndPtoId(bulkUploadRequest.getRouteCode(), Long.valueOf(bulkUploadRequest.getPtoId()));
	}

}
