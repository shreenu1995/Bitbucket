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
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.OpsManifestManagementDao;
import com.chatak.transit.afcs.server.dao.model.DepotMaster;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.dao.model.OpsManifest;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QOpsManifest;
import com.chatak.transit.afcs.server.dao.repository.DepotMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.OperatorManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.OpsManifestRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class OpsManifestManagementDaoImpl implements OpsManifestManagementDao {

	@Autowired
	OpsManifestRepository opsManifestRepository;

	@PersistenceContext
	EntityManager em;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;
	
	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;
	
	@Autowired
	DepotMasterRepository depotMasterRepository;
	
	@Autowired
	OperatorManagementRepository operatorManagementRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(OpsManifestManagementDaoImpl.class);

	@Override
	public void saveOpsManifest(OpsManifest opsManifest) {
		opsManifestRepository.save(opsManifest);

	}

	@Override
	public OpsManifestSearchResponse getOpsManifestList(OpsManifestSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<OpsManifest> opsManifetList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			opsManifetList = query.from(QOpsManifest.opsManifest)
					.where(QOpsManifest.opsManifest.status.ne(Status.TERMINATED.getValue()),
							isStatus(request.getStatus()), isPtoId(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isOpsManifestId(request.getOpsManifestId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QOpsManifest.opsManifest.opsManifestId.desc())
					.list(QOpsManifest.opsManifest);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			opsManifetList = query.from(QOpsManifest.opsManifest)
					.where(QOpsManifest.opsManifest.status.ne(Status.TERMINATED.getValue()),
							QOpsManifest.opsManifest.organizationId.eq(request.getOrganizationId()),
							isStatus(request.getStatus()), isPtoId(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isOpsManifestId(request.getOpsManifestId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QOpsManifest.opsManifest.opsManifestId.desc())
					.list(QOpsManifest.opsManifest);
		} else {
			opsManifetList = query.from(QOpsManifest.opsManifest)
					.where(QOpsManifest.opsManifest.status.ne(Status.TERMINATED.getValue()),
							isStatus(request.getStatus()), isPtoId(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isOpsManifestId(request.getOpsManifestId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QOpsManifest.opsManifest.opsManifestId.desc())
					.list(QOpsManifest.opsManifest);
		}
		List<OpsManifestSearchRequest> searchOpsManifestList = new ArrayList<>();
		for (OpsManifest opsManifest : opsManifetList) {	
			OpsManifestSearchRequest opsManifestSearchRequest = new OpsManifestSearchRequest();
			opsManifestSearchRequest.setDepotId(opsManifest.getDepotId());
			opsManifestSearchRequest.setDeviceNo(opsManifest.getDeviceNo());
			opsManifestSearchRequest.setOperatorId(opsManifest.getOperatorId());
			opsManifestSearchRequest.setOpsManifestId(opsManifest.getOpsManifestId());
			opsManifestSearchRequest.setOrganizationId(opsManifest.getOrganizationId());
			opsManifestSearchRequest.setPtoId(opsManifest.getPtoId());
			opsManifestSearchRequest.setDate(DateUtil.toDateStringFormat((opsManifest.getDate())));
			opsManifestSearchRequest.setStatus(opsManifest.getStatus());
			if(!StringUtil.isNull(opsManifest.getOrganizationId())) {
				Long organnizationId=opsManifest.getOrganizationId();
				try {
					OrganizationMaster organizationMaster=organizationMasterRepository.findByOrgId(organnizationId);
					opsManifestSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: OpsManifestManagementDaoImpl class :: getOpsManifestList method :: exception", e);
				}
				
			}
			if (!StringUtil.isNull(opsManifest.getPtoId())) {
				Long ptoId = opsManifest.getPtoId();
				try {
					PtoMaster ptoMaster = ptoOperationMasterRepository.findByPtoMasterId(ptoId);
					opsManifestSearchRequest.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			if (!StringUtil.isNull(opsManifest.getDepotId())) {
				try {
					DepotMaster depotMaster = depotMasterRepository.findByDepotId(opsManifest.getDepotId());
					opsManifestSearchRequest.setDepotName(depotMaster.getDepotName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			if (!StringUtil.isNull(opsManifest.getOperatorId())) {
				try {
					Operator operator = operatorManagementRepository.findByOperatorId(opsManifest.getOperatorId());
					opsManifestSearchRequest.setOperatorName(operator.getOperatorName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			searchOpsManifestList.add(opsManifestSearchRequest);
		}
		OpsManifestSearchResponse response = new OpsManifestSearchResponse();
		response.setOpsManifestList(searchOpsManifestList);
		response.setTotalRecords(totalOpsManifestRecords(request));
		return response;
	}

	private int totalOpsManifestRecords(OpsManifestSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QOpsManifest.opsManifest)
					.where(QOpsManifest.opsManifest.status.ne(Status.TERMINATED.getValue()),
							isStatus(request.getStatus()), isPtoId(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isOpsManifestId(request.getOpsManifestId()))
					.orderBy(QOpsManifest.opsManifest.opsManifestId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QOpsManifest.opsManifest)
					.where(QOpsManifest.opsManifest.status.ne(Status.TERMINATED.getValue()),
							QOpsManifest.opsManifest.organizationId.eq(request.getOrganizationId()),
							isStatus(request.getStatus()), isPtoId(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isOpsManifestId(request.getOpsManifestId()))
					.orderBy(QOpsManifest.opsManifest.opsManifestId.desc()).count();
		} else {
			count = query.from(QOpsManifest.opsManifest)
					.where(QOpsManifest.opsManifest.status.ne(Status.TERMINATED.getValue()),
							isStatus(request.getStatus()), isPtoId(request.getPtoId()),
							isOrganizationId(request.getOrganizationId()), isOpsManifestId(request.getOpsManifestId()))
					.orderBy(QOpsManifest.opsManifest.opsManifestId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QOpsManifest.opsManifest.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QOpsManifest.opsManifest.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isOpsManifestId(Long opsManifestId) {
		return !StringUtil.isNull(opsManifestId) ? QOpsManifest.opsManifest.opsManifestId.eq(opsManifestId): null;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QOpsManifest.opsManifest.organizationId.eq(organizationId): null;
	}

	@Override
	public boolean validateOpsManifestStatusUpdate(OpsManifestStatusChangeRequest request) {
		return opsManifestRepository.existsByOpsManifestId(request.getOpsManifestId());
	}

	@Override
	public boolean updateOpsManifestStatus(OpsManifestStatusChangeRequest request) {
		return ServerConstants.COUNT == opsManifestRepository.updateStatus(request.getStatus(), request.getOpsManifestId());

	}

	@Transactional
	@Override
	public boolean updateOpsManifestProfile(OpsManifestUpdateRequest request) {
		QOpsManifest opsManifest = QOpsManifest.opsManifest;
		long noOfRowOpsManifest = new JPAUpdateClause(em, opsManifest)
				.where(opsManifest.opsManifestId.eq(request.getOpsManifestId()))
				.set(opsManifest.date, DateUtil.convertStringToTimestamp(request.getDate()))
				.set(opsManifest.depotCode, request.getDepotId())
				.set(opsManifest.depotId, request.getDepotId())
				.set(opsManifest.organizationId, request.getOrganizationId())
				.set(opsManifest.operatorId, request.getOperatorId())
				.set(opsManifest.ptoId, request.getPtoId())
				.set(opsManifest.opsManifestId, request.getOpsManifestId())
				.set(opsManifest.deviceNo, request.getDeviceNo()).execute();
		return noOfRowOpsManifest == 1l;
	}

	@Override
	public boolean validateExistingDeviceNumber(OpsManifestRegistrationRequest opsManifestRegistrationRequest) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QOpsManifest.opsManifest)
				.where(QOpsManifest.opsManifest.deviceNo.eq(opsManifestRegistrationRequest.getDeviceNo())).exists();
	}

}
