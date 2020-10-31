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
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.SetupSoftwareVersionDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QSetupSoftwareMaintenance;
import com.chatak.transit.afcs.server.dao.model.SetupSoftwareMaintenance;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.SetupSoftwareVersionRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class SetupSoftwareVersionDaoImpl implements SetupSoftwareVersionDao {

	@Autowired
	SetupSoftwareVersionRepository setupSoftwareVersionRepository;

	@PersistenceContext
	EntityManager em;

	@PersistenceContext
	EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(FeeManagementDaoImpl.class);

	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Override
	public void save(SetupSoftwareMaintenance setupSoftwareMaintenance) {
		setupSoftwareVersionRepository.save(setupSoftwareMaintenance);		
	}
	
	@Override
	public SetupSoftwareSearchResponse getSoftwareVersionList(SetupSoftwareSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);
		List<SetupSoftwareMaintenance> softwareMaintenanceList = null;
		if(request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			softwareMaintenanceList = query.from(QSetupSoftwareMaintenance.setupSoftwareMaintenance)
					.where(QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.ne(Status.TERMINATED.getValue()),
							isPtoId(request.getPtoId()),
							isSoftwareId(request.getSoftwareId()),
							isOrganizationId(request.getOrganizationId()),
							isVersionNumber(request.getVersionNumber()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QSetupSoftwareMaintenance.setupSoftwareMaintenance.softwareId.desc())
					.list(QSetupSoftwareMaintenance.setupSoftwareMaintenance);
		} else if(request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			softwareMaintenanceList = query.from(QSetupSoftwareMaintenance.setupSoftwareMaintenance)
					.where(QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.ne(Status.TERMINATED.getValue()),
							QSetupSoftwareMaintenance.setupSoftwareMaintenance.organizationId.eq(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isSoftwareId(request.getSoftwareId()),
							isOrganizationId(request.getOrganizationId()), isVersionNumber(request.getVersionNumber()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QSetupSoftwareMaintenance.setupSoftwareMaintenance.softwareId.desc())
					.list(QSetupSoftwareMaintenance.setupSoftwareMaintenance);
		} else {
			softwareMaintenanceList = query.from(QSetupSoftwareMaintenance.setupSoftwareMaintenance)
					.where(QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.ne(Status.TERMINATED.getValue()),
							isPtoId(request.getPtoId()),
							isSoftwareId(request.getSoftwareId()),
							isOrganizationId(request.getOrganizationId()), isVersionNumber(request.getVersionNumber()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QSetupSoftwareMaintenance.setupSoftwareMaintenance.softwareId.desc())
					.list(QSetupSoftwareMaintenance.setupSoftwareMaintenance);
		}
		List<SetupSoftwareSearchRequest> setupSoftwareList = new ArrayList<>();
		for (SetupSoftwareMaintenance search : softwareMaintenanceList) {
			SetupSoftwareSearchRequest setupSoftwareSearchRequest = new SetupSoftwareSearchRequest();
			setupSoftwareSearchRequest.setSoftwareId(search.getSoftwareId());
			setupSoftwareSearchRequest.setVersionNumber(search.getVersionNumber());
			setupSoftwareSearchRequest.setInherit(search.getInheritt());
			setupSoftwareSearchRequest.setPtoId(search.getPtoId());
			setupSoftwareSearchRequest.setOrganizationId(search.getOrganizationId());
			setupSoftwareSearchRequest.setDeliveryDate(DateUtil.toDateStringFormat(search.getDeliveryDate()));
			setupSoftwareSearchRequest.setApplyDate(DateUtil.toDateStringFormat(search.getApplyDate()));
			setupSoftwareSearchRequest.setStatus(search.getStatus());
			setupSoftwareSearchRequest.setDescription(search.getDescription());
			setupSoftwareSearchRequest.setFullVersion(search.getFullVersion());
			setupSoftwareSearchRequest.setOrganizationId(search.getOrganizationId());
			if (!StringUtil.isNull(search.getOrganizationId())) {
				Long organnizationId = search.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository
							.findByOrgId(organnizationId);
					setupSoftwareSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: SetupSoftwareVersionDaoImpl class :: getSoftwareVersionList method :: exception", e);
				}
			}
			if (!StringUtil.isNull(search.getPtoId())) {
				Long ptoId = search.getPtoId();
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					setupSoftwareSearchRequest.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: ProductDaoImpl class :: searchProduct method :: exception", e);
				}
			}
			setupSoftwareList.add(setupSoftwareSearchRequest);
		}

		SetupSoftwareSearchResponse response = new SetupSoftwareSearchResponse();
		response.setSetupSoftwareSearchList(setupSoftwareList);
		response.setTotalRecords(totalUserRecords(request));
		return response;
	}

	private int totalUserRecords(SetupSoftwareSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QSetupSoftwareMaintenance.setupSoftwareMaintenance)
					.where(QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.ne(Status.TERMINATED.getValue()),
							isPtoId(request.getPtoId()),
							isSoftwareId(request.getSoftwareId()),
							isOrganizationId(request.getOrganizationId()), isVersionNumber(request.getVersionNumber()),
							isStatus(request.getStatus()))
					.orderBy().count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QSetupSoftwareMaintenance.setupSoftwareMaintenance)
					.where(QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.ne(Status.TERMINATED.getValue()),
							QSetupSoftwareMaintenance.setupSoftwareMaintenance.organizationId
									.eq(request.getOrganizationId()),
									isPtoId(request.getPtoId()),
									isSoftwareId(request.getSoftwareId()),
									isOrganizationId(request.getOrganizationId()), isVersionNumber(request.getVersionNumber()),
							isStatus(request.getStatus()))
					.orderBy().count();
		} else if (request.getUserType().equals(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QSetupSoftwareMaintenance.setupSoftwareMaintenance)
					.where(QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.ne(Status.TERMINATED.getValue()),
							isPtoId(request.getPtoId()),
							isSoftwareId(request.getSoftwareId()),
							isOrganizationId(request.getOrganizationId()), isVersionNumber(request.getVersionNumber()),
							isStatus(request.getStatus()))
					.orderBy().count();
		}
		return count != null ? count.intValue() : 0;
	}
	
	private BooleanExpression isSoftwareId(Long softwareId) {
		return !StringUtil.isNull(softwareId) ? QSetupSoftwareMaintenance.setupSoftwareMaintenance.softwareId.eq(softwareId) : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QSetupSoftwareMaintenance.setupSoftwareMaintenance.ptoId.eq(ptoId) : null;
	}
	
	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId)
				? QSetupSoftwareMaintenance.setupSoftwareMaintenance.organizationId.eq(organizationId):null;
	}

	private BooleanExpression isVersionNumber(String versionNumber) {
		return !StringUtil.isNullEmpty(versionNumber) ? QSetupSoftwareMaintenance.setupSoftwareMaintenance.versionNumber
				.toUpperCase().like("%" + versionNumber.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QSetupSoftwareMaintenance.setupSoftwareMaintenance.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	@Transactional
	@Override
	public boolean updateSoftwareMaintenance(SetupSoftwareUpdateRequest request) {
		QSetupSoftwareMaintenance softwareMaintenance = QSetupSoftwareMaintenance.setupSoftwareMaintenance;
		long noOfRowUserCrd = new JPAUpdateClause(em, softwareMaintenance)
				.where(softwareMaintenance.softwareId.eq(request.getSoftwareId()))
				.set(softwareMaintenance.updatedTime, Timestamp.from(Instant.now())).execute();

		QSetupSoftwareMaintenance updateSetupSoftware = QSetupSoftwareMaintenance.setupSoftwareMaintenance;
		long noOfRowUserPro = new JPAUpdateClause(em, updateSetupSoftware)
				.where(updateSetupSoftware.softwareId.eq(request.getSoftwareId()))
				.set(updateSetupSoftware.versionNumber, request.getVersionNumber())
				.set(updateSetupSoftware.inheritt, request.getInherit())
				.set(updateSetupSoftware.ptoId, request.getPtoId())
				.set(updateSetupSoftware.fullVersion, request.getFullVersion())
				.set(updateSetupSoftware.deliveryDate, DateUtil.convertStringToTimestamp(request.getDeliveryDate()))
				.set(updateSetupSoftware.applyDate, DateUtil.convertStringToTimestamp(request.getApplyDate()))
				.set(updateSetupSoftware.organizationId, request.getOrganizationId())
				.set(updateSetupSoftware.description, request.getDescription()).execute();
		return noOfRowUserCrd == 1l && noOfRowUserPro == 1l;
	}

	@Override
	public boolean updateSetupSoftwareStatus(SetupSoftwareMaintenance setupSoftwareMaintenanceTable) {
		return (ServerConstants.COUNT == setupSoftwareVersionRepository.updateStatus(
				setupSoftwareMaintenanceTable.getStatus(), setupSoftwareMaintenanceTable.getSoftwareId()));
	}

	@Override
	public SetupSoftwareSearchResponse getSoftwareDataById(SetupSoftwareSearchRequest request) {
		SetupSoftwareSearchResponse response = new SetupSoftwareSearchResponse();
		SetupSoftwareMaintenance software = setupSoftwareVersionRepository.findBySoftwareId(request.getSoftwareId());
		List<SetupSoftwareSearchRequest> setupSoftwareSearchList = new ArrayList<>();
		SetupSoftwareSearchRequest softwareSearchRequest = new SetupSoftwareSearchRequest();
		softwareSearchRequest.setApplyDate(DateUtil.toDateStringFormat(software.getApplyDate()));
		softwareSearchRequest.setDeliveryDate(DateUtil.toDateStringFormat(software.getDeliveryDate()));
		softwareSearchRequest.setDescription(software.getDescription());
		softwareSearchRequest.setInherit(software.getInheritt());
		softwareSearchRequest.setSoftwareId(software.getSoftwareId());
		softwareSearchRequest.setPtoId(software.getSoftwareId());
		softwareSearchRequest.setStatus(software.getStatus());
		softwareSearchRequest.setVersionNumber(software.getVersionNumber());
		softwareSearchRequest.setFullVersion(software.getFullVersion());
		softwareSearchRequest.setOrganizationId(software.getOrganizationId());
		setupSoftwareSearchList.add(softwareSearchRequest);
		response.setSetupSoftwareSearchList(setupSoftwareSearchList);
		return response;
	}

	@Override
	public SetupSoftwareListDataResponse getListInherit(Long ptoId) {
		List<SetupSoftwareMaintenance> listOfSoftwareMaintenance = null;

		if (ptoId != null) {
			listOfSoftwareMaintenance = setupSoftwareVersionRepository.findByPtoId(ptoId);
		} else {
			listOfSoftwareMaintenance = setupSoftwareVersionRepository.findAll();
		}
		SetupSoftwareListDataResponse listOfSoftwareDataResponse = new SetupSoftwareListDataResponse();
		List<SetupSoftwareRegistrationRequest> listOfSoftwarelistData = new ArrayList<>();
		for (SetupSoftwareMaintenance setupSoftwareMaintence : listOfSoftwareMaintenance) {
			SetupSoftwareRegistrationRequest setupSoftwareList = new SetupSoftwareRegistrationRequest();
			setupSoftwareList.setInherit(setupSoftwareMaintence.getInheritt());
			setupSoftwareList.setPtoId(setupSoftwareMaintence.getPtoId());
			setupSoftwareList.setVersionNumber(setupSoftwareMaintence.getVersionNumber());
			listOfSoftwarelistData.add(setupSoftwareList);
		}
		listOfSoftwareDataResponse.setListOfInheritAndPto(listOfSoftwarelistData);
		listOfSoftwareDataResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		listOfSoftwareDataResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return listOfSoftwareDataResponse;
	}

}
