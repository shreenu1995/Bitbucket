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
import com.chatak.transit.afcs.server.dao.FareManagementDao;
import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;
import com.chatak.transit.afcs.server.dao.model.FareMaster;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QFareMaster;
import com.chatak.transit.afcs.server.dao.repository.BulkUploadRepository;
import com.chatak.transit.afcs.server.dao.repository.FareRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareStatusUpdate;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class FareManagementDaoImpl implements FareManagementDao {

	@Autowired
	FareRegistrationRepository fareRegistrationRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;
	
	@Autowired
	BulkUploadRepository bulkUploadRepository; 

	@PersistenceContext
	EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(FeeManagementDaoImpl.class);

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Override
	public Long saveFareRegistrationDetails(FareMaster request) {
		FareMaster fareMaster = fareRegistrationRepository.save(request);
		return fareMaster.getFareId();
	}

	@Override
	public boolean validateFareRegistrationRequest(FareRegistrationRequest request) {
		return ptoOperationMasterRepository.existsByPtoMasterId(request.getPtoId())

				&& !fareRegistrationRepository.existsByFareName(request.getFareName());

	}

	@Override
	public FareSearchResponse getFareList(FareSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<FareMaster> fareList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			fareList = query.from(QFareMaster.fareMaster)
					.where(QFareMaster.fareMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isFareName(request.getFareName()), isStatus(request.getStatus()),
							isFareId(request.getFareId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QFareMaster.fareMaster.fareId.desc())
					.list(QFareMaster.fareMaster);

		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			fareList = query.from(QFareMaster.fareMaster)
					.where(QFareMaster.fareMaster.status.ne(Status.TERMINATED.getValue()),
							QFareMaster.fareMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isFareName(request.getFareName()), isStatus(request.getStatus()),
							isFareId(request.getFareId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QFareMaster.fareMaster.fareId.desc())
					.list(QFareMaster.fareMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			fareList = query.from(QFareMaster.fareMaster)
					.where(QFareMaster.fareMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), isPtoId(request.getPtoId()),
							isFareName(request.getFareName()), isStatus(request.getStatus()),
							isFareId(request.getFareId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QFareMaster.fareMaster.fareId.desc())
					.list(QFareMaster.fareMaster);
		}

		List<FareSearchRequest> searchFareList = new ArrayList<>();
		for (FareMaster fareMaster : fareList) {
			FareSearchRequest fareSearchRequest = new FareSearchRequest();
			fareSearchRequest.setFareId(String.valueOf(fareMaster.getFareId()));
			fareSearchRequest.setFareName(fareMaster.getFareName());
			fareSearchRequest.setFareType(fareMaster.getFareType());
			fareSearchRequest.setDifference(fareMaster.getDifference());
			fareSearchRequest.setFareAmount(fareMaster.getFareAmount());
			fareSearchRequest.setPtoId(fareMaster.getPtoId());
			fareSearchRequest.setOrganizationId(fareMaster.getOrganizationId());
			fareSearchRequest.setStatus(fareMaster.getStatus());
			fareSearchRequest.setOrganizationId(fareMaster.getOrganizationId());
			if (!StringUtil.isNull(fareMaster.getOrganizationId())) {
				Long organizationId = fareMaster.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository
							.findByOrgId(organizationId);
					fareSearchRequest.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: FeeManagementDaoImpl class :: getFareList method :: exception", e);
				}
			}
			if (!StringUtil.isNull(fareMaster.getPtoId())) {
				Long ptoId = fareMaster.getPtoId();
				try {
					PtoMaster organizationMaster = ptoOperationMasterRepository.findByPtoMasterId(ptoId);
					fareSearchRequest.setPtoName(organizationMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: FeeManagementDaoImpl class :: getFareList method :: exception", e);
				}
			}
			searchFareList.add(fareSearchRequest);
		}
		FareSearchResponse response = new FareSearchResponse();
		response.setFareList(searchFareList);
		response.setTotalRecords(totalFareRecords(request));
		return response;
	}

	private int totalFareRecords(FareSearchRequest request) {
		JPAQuery query = new JPAQuery(em);

		Long count = null;
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QFareMaster.fareMaster)
					.where(QFareMaster.fareMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()), isFareName(request.getFareName()),
							isStatus(request.getStatus()))
					.orderBy(QFareMaster.fareMaster.fareName.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QFareMaster.fareMaster)
					.where(QFareMaster.fareMaster.status.ne(Status.TERMINATED.getValue()),
							QFareMaster.fareMaster.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()), isFareName(request.getFareName()),
							isStatus(request.getStatus()))
					.orderBy(QFareMaster.fareMaster.fareName.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QFareMaster.fareMaster)
					.where(QFareMaster.fareMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()), isFareName(request.getFareName()),
							isStatus(request.getStatus()))
					.orderBy(QFareMaster.fareMaster.fareName.desc()).count();
		}

		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId)
				? QFareMaster.fareMaster.organizationId.eq(organizationId) : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId)
				? QFareMaster.fareMaster.ptoId.eq(ptoId)
				: null;
	}

	private BooleanExpression isFareName(String fareName) {
		return !StringUtil.isNullEmpty(fareName) ? QFareMaster.fareMaster.fareName.toUpperCase()
				.like("%" + fareName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isFareId(String fareId) {
		return !StringUtil.isNull(fareId) ? QFareMaster.fareMaster.fareId.like("%" + fareId.replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QFareMaster.fareMaster.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	@Override
	public boolean validateFare(FareStatusUpdate fareStatusUpdate) {

		return fareRegistrationRepository.existsByFareId(fareStatusUpdate.getFareId());
	}

	@Transactional
	@Override
	public boolean updateFare(FareStatusUpdate request) {
		QFareMaster updateFareMaster = QFareMaster.fareMaster;
		long noOfRowFare = new JPAUpdateClause(em, updateFareMaster)
				.where(updateFareMaster.fareId.eq(request.getFareId()))
				.set(updateFareMaster.ptoId, request.getPtoId())
				.set(updateFareMaster.fareName, request.getFareName())
				.set(updateFareMaster.fareType, request.getFareType())
				.set(updateFareMaster.difference, request.getDifference())
				.set(updateFareMaster.fareAmount, request.getFareAmount()).execute();
		return noOfRowFare == 1l;
	}

	@Override
	public FareMaster updateFareStatus(FareStatusUpdate request) {
		FareMaster fareMaster = fareRegistrationRepository.findByFareId(request.getFareId());
		fareMaster.setStatus(request.getStatus());
		fareMaster.setReason(request.getReason());
		return fareRegistrationRepository.save(fareMaster);
	}

	@Override
	public boolean saveBulkUpload(BulkUploadDetails request) {
		return bulkUploadRepository.save(request) != null;
	}

	@Override
	public List<BulkUploadDetails> getAllBulkFares(Long ptoId) {
		return bulkUploadRepository.findByPtoId(ptoId);
	}

	@Override
	public List<BulkUploadDetails> getFareByStopId(String stopId) {
		return bulkUploadRepository.findByStartStopCode(stopId);
	}

}
