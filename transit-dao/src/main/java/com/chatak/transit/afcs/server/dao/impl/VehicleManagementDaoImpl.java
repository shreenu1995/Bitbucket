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
import com.chatak.transit.afcs.server.dao.VehicleManagementDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceOnboardMaster;
import com.chatak.transit.afcs.server.dao.model.QVehicleManufacturer;
import com.chatak.transit.afcs.server.dao.model.QVehicleModel;
import com.chatak.transit.afcs.server.dao.model.QVehicleOnboarding;
import com.chatak.transit.afcs.server.dao.model.QVehicleTypeProfile;
import com.chatak.transit.afcs.server.dao.model.VehicleManufacturer;
import com.chatak.transit.afcs.server.dao.model.VehicleModel;
import com.chatak.transit.afcs.server.dao.model.VehicleOnboarding;
import com.chatak.transit.afcs.server.dao.model.VehicleTypeProfile;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.dao.repository.VehicleManufacturerRepository;
import com.chatak.transit.afcs.server.dao.repository.VehicleModelRepository;
import com.chatak.transit.afcs.server.dao.repository.VehicleOnboardingRepository;
import com.chatak.transit.afcs.server.dao.repository.VehicleTypeRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class VehicleManagementDaoImpl implements VehicleManagementDao {

	@Autowired
	VehicleTypeRepository vehicleTypeRepository;

	@Autowired
	VehicleManufacturerRepository vehicleManufacturerRepository;

	@Autowired
	VehicleModelRepository vehicleModelRepository;

	@Autowired
	VehicleOnboardingRepository vehicleOnboardingRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;
	
	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@PersistenceContext
	EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleManagementDaoImpl.class);

	@Override
	public boolean saveVehicleTypeRegistration(VehicleTypeProfile request) {
		return vehicleTypeRepository.save(request) != null;

	}

	@Override
	public VehicleTypeSearchResponse searchVehicleType(VehicleTypeSearchRequest request,
			VehicleTypeSearchResponse response) {

		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		List<VehicleTypeSearchRequest> listOfVehicleType = new ArrayList<>();
		JPAQuery query = new JPAQuery(em);
		List<VehicleTypeProfile> listOfVehicleProfile = query.from(QVehicleTypeProfile.vehicleTypeProfile)
				.where(QVehicleTypeProfile.vehicleTypeProfile.status.eq(Status.ACTIVE.getValue()),
						isVehicleTypeName(request.getVehicleTypeName()),
						isVehicleTypeId(request.getVehicleTypeId()))
				.offset(fromIndex).limit(10).orderBy(QVehicleTypeProfile.vehicleTypeProfile.vehicleTypeId.desc())
				.list(QVehicleTypeProfile.vehicleTypeProfile);

		for (VehicleTypeProfile vehicleTypeProfile : listOfVehicleProfile) {
			VehicleTypeSearchRequest vehicleTypeResponse = new VehicleTypeSearchRequest();
			vehicleTypeResponse.setVehicleTypeId(vehicleTypeProfile.getVehicleTypeId());
			vehicleTypeResponse.setVehicleTypeName(vehicleTypeProfile.getVehicleType());
			vehicleTypeResponse.setDescription(vehicleTypeProfile.getDescription());
			vehicleTypeResponse.setStatus(vehicleTypeProfile.getStatus());
			listOfVehicleType.add(vehicleTypeResponse);
		}

		response.setListOfVehicleType(listOfVehicleType);
		response.setTotalNoOfRecords(getTotalVehicleTypeRows(request));
		return response;
	}

	private int getTotalVehicleTypeRows(VehicleTypeSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QVehicleTypeProfile.vehicleTypeProfile)
				.where(QVehicleTypeProfile.vehicleTypeProfile.status.eq(Status.ACTIVE.getValue()),
						isVehicleTypeName(request.getVehicleTypeName()), isVehicleTypeId(request.getVehicleTypeId()))
				.orderBy().count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isVehicleTypeId(Long vehicleTypeId) {
		return !StringUtil.isNull(vehicleTypeId) ? QVehicleTypeProfile.vehicleTypeProfile.vehicleTypeId.eq(vehicleTypeId) : null;
	}

	private BooleanExpression isVehicleTypeName(String vehicleTypeName) {
		return !StringUtil.isNullEmpty(vehicleTypeName) ? QVehicleTypeProfile.vehicleTypeProfile.vehicleType
				.toUpperCase().like("%" + vehicleTypeName.toUpperCase().replace("*", "") + "%") : null;
	}

	// Vehicle Manufacturer
	@Override
	public boolean saveVehicleManufacturer(VehicleManufacturer request) {
		return vehicleManufacturerRepository.save(request) != null;
	}

	@Override
	public VehicleManufacturerSearchResponse searchVehicleManuf(VehicleManufacturerSearchRequest request,
			VehicleManufacturerSearchResponse response) {

		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		List<VehicleManufacturerResponse> listOfVehicleManuf = new ArrayList<>();
		JPAQuery query = new JPAQuery(em);
		List<VehicleManufacturer> listOfVehicleManufResp = query.from(QVehicleManufacturer.vehicleManufacturer)
				.where(QVehicleManufacturer.vehicleManufacturer.status.eq(Status.ACTIVE.getValue()),
						isVehicleManufName(request.getVehicleManufacturerName()),
						isVehicleManufId(request.getVehicleManufId()),
						isVehicleTypeIdManu(request.getVehicleTypeId()))
				.offset(fromIndex).limit(10)
				.orderBy(QVehicleManufacturer.vehicleManufacturer.vehicleManufacturerId.desc())
				.list(QVehicleManufacturer.vehicleManufacturer);

		for (VehicleManufacturer vehicleManufRes : listOfVehicleManufResp) {
			if (!StringUtil.isNull(vehicleManufRes)) {
				VehicleTypeProfile vehicleTypeProfile = vehicleTypeRepository.findByVehicleTypeId(vehicleManufRes.getVehicleTypeId());
				VehicleManufacturerResponse vehicleResponse = new VehicleManufacturerResponse();
				vehicleResponse.setVehicleManufacturerId(vehicleManufRes.getVehicleManufacturerId());
				vehicleResponse.setVehicleTypeId(vehicleTypeProfile.getVehicleTypeId());
				vehicleResponse.setVehicleManufacturerName(vehicleManufRes.getVehicleManufacturerName());
				vehicleResponse.setVehicleType(vehicleTypeProfile.getVehicleType());
				vehicleResponse.setDescription(vehicleManufRes.getDescription());
				vehicleResponse.setStatus(vehicleManufRes.getStatus());
				listOfVehicleManuf.add(vehicleResponse);
			}
		}

		response.setListOfVehicleManuf(listOfVehicleManuf);
		response.setTotalNoOfCount(getTotalVehicleManufRows(request));
		return response;
	}
	
	private BooleanExpression isVehicleManufId(int vehicleManufId) {
		return vehicleManufId != 0
				? QVehicleManufacturer.vehicleManufacturer.vehicleManufacturerId.eq(vehicleManufId) : null;
	}

	private int getTotalVehicleManufRows(VehicleManufacturerSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QVehicleManufacturer.vehicleManufacturer)
				.where(QVehicleManufacturer.vehicleManufacturer.status.ne(Status.TERMINATED.getValue()),
						isVehicleManufId(request.getVehicleManufId()),
						isVehicleManufName(request.getVehicleManufacturerName()),
						isVehicleTypeIdManu(request.getVehicleTypeId()))
				.orderBy().count();
		return count != null ? count.intValue() : 0;
	}
	
	private BooleanExpression isVehicleTypeIdManu(Long vehicleTypeId) {
		return !StringUtil.isNull(vehicleTypeId) ? QVehicleManufacturer.vehicleManufacturer.vehicleTypeId.eq(vehicleTypeId) : null;
	}

	private BooleanExpression isVehicleManufName(String vehicleManufName) {
		return !StringUtil.isNullEmpty(vehicleManufName)
				? QVehicleManufacturer.vehicleManufacturer.vehicleManufacturerName.toUpperCase()
						.like("%" + vehicleManufName.toUpperCase().replace("*", "") + "%")
				: null;
	}

	// Vehicle Model

	@Override
	public boolean saveVehicleModelRegistration(VehicleModel request) {
		return vehicleModelRepository.save(request) != null;

	}

	@Override
	public VehicleModelSearchResponse searchVehicleModal(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response) {

		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		List<VehicleModelResponse> listOfVehicleManuf = new ArrayList<>();
		JPAQuery query = new JPAQuery(em);
		List<VehicleModel> listOfVehicleManufResp = query.from(QVehicleModel.vehicleModel)
				.where(QVehicleModel.vehicleModel.status.ne(Status.TERMINATED.getValue()),
						isVehicleModalCode(request.getVehicleModelId()),
						isVehicleTypeIdModel(request.getVehicleTypeId()),
						isVehicleManufacturerId(request.getVehicleManufacturerId()),
						isVehicleModelName(request.getVehicleModelName()),
				        isVehicleModelStatus(request.getStatus()))
				.offset(fromIndex).limit(10).orderBy(QVehicleModel.vehicleModel.vehicleModelId.desc())
				.list(QVehicleModel.vehicleModel);

		for (VehicleModel vehicleManufRes : listOfVehicleManufResp) {
			VehicleModelResponse vehicleModelResponse = new VehicleModelResponse();
			VehicleTypeProfile vehicleTypeProfile = vehicleTypeRepository.findByVehicleTypeId(vehicleManufRes.getVehicleTypeId());
			VehicleManufacturer vehicleManufacturer = vehicleManufacturerRepository.findByVehicleManufacturerId(vehicleManufRes.getVehicleManufacturerId());
			vehicleModelResponse.setVehicleManufacturerName(vehicleManufacturer.getVehicleManufacturerName());
			vehicleModelResponse.setVehicleType(vehicleTypeProfile.getVehicleType());
			vehicleModelResponse.setVehicleTypeId(vehicleManufRes.getVehicleTypeId());
			vehicleModelResponse.setVehicleManufacturerId(vehicleManufRes.getVehicleManufacturerId());
			vehicleModelResponse.setVehicleModelId(vehicleManufRes.getVehicleModelId());
			vehicleModelResponse.setVehicleModelName(vehicleManufRes.getVehicleModelName());
			vehicleModelResponse.setVehicleRegistrationNumber(vehicleManufRes.getVehicleRegistrationNumber());
			vehicleModelResponse.setVehicleChassisNumber(vehicleManufRes.getVehicleChassisNumber());
			vehicleModelResponse.setVehicleEngineNumber(vehicleManufRes.getVehicleEngineNumber());
			vehicleModelResponse.setDescription(vehicleManufRes.getDescription());
			vehicleModelResponse.setStatus(vehicleManufRes.getStatus());
			listOfVehicleManuf.add(vehicleModelResponse);
		}

		response.setListOfVehicleModel(listOfVehicleManuf);
		response.setTotalNoOfRecords(getTotalVehicleModelRows(request));
		return response;
	}

	private int getTotalVehicleModelRows(VehicleModelSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QVehicleModel.vehicleModel)
				.where(QVehicleModel.vehicleModel.status.ne(Status.TERMINATED.getValue()),
						isVehicleModalCode(request.getVehicleModelId()),
						isVehicleTypeIdModel(request.getVehicleTypeId()),
						isVehicleManufacturerId(request.getVehicleManufacturerId()),
						isVehicleModelName(request.getVehicleModelName()),
				        isVehicleModelStatus(request.getStatus()))
				.orderBy().count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isVehicleModalCode(String vehicleModelCode) {
		return !StringUtil.isNull(vehicleModelCode)
				? QVehicleModel.vehicleModel.vehicleModelId.like("%" + vehicleModelCode.replace("*", "") + "%") : null;
	}

	private BooleanExpression isVehicleModelName(String vehicleModelName) {
		return !StringUtil.isNullEmpty(vehicleModelName) ? QVehicleModel.vehicleModel.vehicleModelName.toUpperCase()
				.like("%" + vehicleModelName.toUpperCase().replace("*", "") + "%") : null;
	}
	
	private BooleanExpression isVehicleModelStatus(String status) {
		return !StringUtil.isNull(status)
				? QVehicleModel.vehicleModel.status.like("%" + status.replace("*", "") + "%") : null;
	}

	private BooleanExpression isVehicleTypeIdModel(Long vehicleTypeId) {
		return !StringUtil.isNull(vehicleTypeId) ? QVehicleModel.vehicleModel.vehicleTypeId
				.eq(vehicleTypeId) : null;
	}
	private BooleanExpression isVehicleManufacturerId(Integer vehicleManufacturerId) {
		return !StringUtil.isNull(vehicleManufacturerId)
				?QVehicleModel.vehicleModel.vehicleManufacturerId.eq(vehicleManufacturerId)
				: null;
	}
	
	// Vehicle OnBoarding

	@Override
	public boolean saveVehicleOnBoarding(VehicleOnboarding request) {
		return vehicleOnboardingRepository.save(request) != null;

	}

	@Override
	public VehicleOnboardingSearchResponse searchVehicleOnboarding(VehicleOnboardingSearchRequest request,
			VehicleOnboardingSearchResponse response) {

		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<VehicleOnboarding> listOfVehicleOnboardResp = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			listOfVehicleOnboardResp = query.from(QVehicleOnboarding.vehicleOnboarding)
					.where(QVehicleOnboarding.vehicleOnboarding.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrgId()),
							isPtoId(request.getPtoId()),
							isVehicleOnboardingId(request.getVehicleOnboardingId()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(10)
					.orderBy(QVehicleOnboarding.vehicleOnboarding.vehicleOnboardingId.desc())
					.list(QVehicleOnboarding.vehicleOnboarding);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			listOfVehicleOnboardResp = query.from(QVehicleOnboarding.vehicleOnboarding)
					.where(QVehicleOnboarding.vehicleOnboarding.status.ne(Status.TERMINATED.getValue()),
							QVehicleOnboarding.vehicleOnboarding.organizationId.eq(Long.parseLong(request.getOrganizationId())),
							isVehicleOnboardingId(request.getVehicleOnboardingId()),
							isPtoId(request.getPtoId()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(10)
					.orderBy(QVehicleOnboarding.vehicleOnboarding.vehicleOnboardingId.desc())
					.list(QVehicleOnboarding.vehicleOnboarding);
		} else {
			listOfVehicleOnboardResp = query.from(QVehicleOnboarding.vehicleOnboarding)
					.where(QVehicleOnboarding.vehicleOnboarding.status.ne(Status.TERMINATED.getValue()),
							QVehicleOnboarding.vehicleOnboarding.ptoId.eq(request.getPtoId()),
							isOrganizationId(request.getOrgId()),
							isPtoId(request.getPtoId()),
							isVehicleOnboardingId(request.getVehicleOnboardingId()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(10)
					.orderBy(QVehicleOnboarding.vehicleOnboarding.vehicleOnboardingId.desc())
					.list(QVehicleOnboarding.vehicleOnboarding);
		}
		List<VehicleOnboardingResponse> listOfVehicleOnboard = new ArrayList<>();
		for (VehicleOnboarding vehicleManufRes : listOfVehicleOnboardResp) {
			VehicleTypeProfile vehicleTypeProfile = vehicleTypeRepository.findByVehicleTypeId(vehicleManufRes.getVehicleTypeId());
			VehicleManufacturer vehicleManufacturer = vehicleManufacturerRepository.findByVehicleManufacturerId(vehicleManufRes.getVehicleManufacturerId());
			VehicleModel vehicleModel = vehicleModelRepository.findByVehicleModelId(vehicleManufRes.getVehicleModelId());
			VehicleOnboardingResponse onboardingResponse = new VehicleOnboardingResponse();
			onboardingResponse.setOrganizationId(vehicleManufRes.getOrganizationId());
			onboardingResponse.setVehicleManufacturerName(vehicleManufacturer.getVehicleManufacturerName());
			onboardingResponse.setVehicleModelName(vehicleModel.getVehicleModelName());
			onboardingResponse.setVehicleOnboardingId(vehicleManufRes.getVehicleOnboardingId());
			onboardingResponse.setVehicleType(vehicleTypeProfile.getVehicleType());
			onboardingResponse.setVehicleManufacturerId(vehicleManufRes.getVehicleManufacturerId());
			onboardingResponse.setVehicleModelId(vehicleManufRes.getVehicleModelId());
			onboardingResponse.setVehicleTypeId(vehicleManufRes.getVehicleTypeId());
			onboardingResponse.setPtoId(vehicleManufRes.getPtoId());
			onboardingResponse.setStatus(vehicleManufRes.getStatus());
			
			if (!StringUtil.isNull(vehicleManufRes.getOrganizationId())) {
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository
							.findByOrgId(vehicleManufRes.getOrganizationId());
					onboardingResponse.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			if (!StringUtil.isNull(vehicleManufRes.getPtoId())) {
				try {
					PtoMaster ptoMaster = ptoOperationMasterRepository.findByPtoMasterId(vehicleManufRes.getPtoId());
					onboardingResponse.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
				}
			}
			listOfVehicleOnboard.add(onboardingResponse);
		}

		response.setListOfOnboarding(listOfVehicleOnboard);
		response.setTotalNoOfCount(getTotalVehicleOnboardingRows(request));
		return response;
	}

	private int getTotalVehicleOnboardingRows(VehicleOnboardingSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QVehicleOnboarding.vehicleOnboarding)
					.where(QVehicleOnboarding.vehicleOnboarding.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrgId()),
							isVehicleOnboardingId(request.getVehicleOnboardingId()),
							isStatus(request.getStatus()))
					.orderBy().count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QVehicleOnboarding.vehicleOnboarding)
					.where(QVehicleOnboarding.vehicleOnboarding.status.ne(Status.TERMINATED.getValue()),
							QVehicleOnboarding.vehicleOnboarding.organizationId.eq(Long.valueOf(request.getOrganizationId())),
							isVehicleOnboardingId(request.getVehicleOnboardingId()),
							isStatus(request.getStatus()))
					.orderBy().count();
		} else {
			count = query.from(QVehicleOnboarding.vehicleOnboarding)
					.where(QVehicleOnboarding.vehicleOnboarding.status.ne(Status.TERMINATED.getValue()),
							QVehicleOnboarding.vehicleOnboarding.ptoId.eq(request.getPtoId()),
							isOrganizationId(request.getOrgId()),
							isVehicleOnboardingId(request.getVehicleOnboardingId()),
							isStatus(request.getStatus()))
					.orderBy().count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QVehicleOnboarding.vehicleOnboarding.ptoId.eq(ptoId) : null;
	}
	
	private BooleanExpression isOrganizationId(Long orgId) {
		return orgId != null ? QVehicleOnboarding.vehicleOnboarding.organizationId.eq(orgId) : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNull(status)
				? QVehicleOnboarding.vehicleOnboarding.status.like("%" + status.replace("*", "") + "%") : null;
	}

	private BooleanExpression isVehicleOnboardingId(Long vehicleOnboardingCode) {
		return vehicleOnboardingCode != null
				? QVehicleOnboarding.vehicleOnboarding.vehicleOnboardingId.eq(vehicleOnboardingCode) : null;
	}

	// Find AllVehicleType
	@Override
	public List<VehicleTypeSearchRequest> getVehicleTypeList() {
		JPAQuery query = new JPAQuery(em);
		List<VehicleTypeSearchRequest> vehicleList = new ArrayList<>();
		List<VehicleTypeProfile> vehicleTypeProfileList = query.from(QVehicleTypeProfile.vehicleTypeProfile)
				.where(QVehicleTypeProfile.vehicleTypeProfile.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QVehicleTypeProfile.vehicleTypeProfile.vehicleType.asc())
				.list(QVehicleTypeProfile.vehicleTypeProfile);
		for (VehicleTypeProfile vehicleTypeProfile : vehicleTypeProfileList) {
			VehicleTypeSearchRequest vehicleTypeResponse = new VehicleTypeSearchRequest();
			vehicleTypeResponse.setVehicleTypeName(vehicleTypeProfile.getVehicleType());
			vehicleTypeResponse.setVehicleTypeId(vehicleTypeProfile.getVehicleTypeId());
			vehicleList.add(vehicleTypeResponse);
		}
		return vehicleList;
	}
	
	@Override
	public List<VehicleManufacturerResponse> getManufacturerName() {
		JPAQuery query = new JPAQuery(em);
		List<VehicleManufacturer>  vehicleManufacturerList = query.from(QVehicleManufacturer.vehicleManufacturer)
				.where(QVehicleManufacturer.vehicleManufacturer.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QVehicleManufacturer.vehicleManufacturer.vehicleManufacturerName.asc())
				.list(QVehicleManufacturer.vehicleManufacturer);
		List<VehicleManufacturerResponse> vehicleManufacturerResponseList = new ArrayList<>();
		for(VehicleManufacturer vehicleManufacturer : vehicleManufacturerList) {
			VehicleManufacturerResponse vehicleManufacturerResponse = new VehicleManufacturerResponse();
			vehicleManufacturerResponse.setVehicleManufacturerName(vehicleManufacturer.getVehicleManufacturerName());
			vehicleManufacturerResponse.setVehicleManufacturerId(vehicleManufacturer.getVehicleManufacturerId());
			vehicleManufacturerResponseList.add(vehicleManufacturerResponse);
		}
		return vehicleManufacturerResponseList;
	}
	
	@Override
	public List<VehicleModelResponse> getModelName() {
		JPAQuery query = new JPAQuery(em);
		List<VehicleModel> vehicleModelResponse = query.from(QVehicleModel.vehicleModel)
				.where(QVehicleModel.vehicleModel.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QVehicleModel.vehicleModel.vehicleModelName.asc()).list(QVehicleModel.vehicleModel);
		List<VehicleModelResponse> vehicleModelResponseList = new ArrayList<>();
		for (VehicleModel vehicleModellist : vehicleModelResponse) {
			VehicleModelResponse vehicleModel = new VehicleModelResponse();
			vehicleModel.setVehicleModelName(vehicleModellist.getVehicleModelName());
			vehicleModel.setVehicleModelId(vehicleModellist.getVehicleModelId());
			vehicleModelResponseList.add(vehicleModel);
		}

		return vehicleModelResponseList;
	}

	@Override
	public boolean validateVehicleTypeProfile(VehicleTypeStatusUpdate request) {
		return vehicleTypeRepository.existsByvehicleTypeId(request.getVehicleTypeId());
	}

	@Transactional
	@Override
	public boolean updateVehicleTypeProfile(VehicleTypeStatusUpdate request) {
		QVehicleTypeProfile vehicleType = QVehicleTypeProfile.vehicleTypeProfile;
		long noOfRowUserCrd = new JPAUpdateClause(em, vehicleType)
				.where(vehicleType.vehicleTypeId.eq(request.getVehicleTypeId()))
				.set(vehicleType.vehicleType, request.getVehicleTypeName())
				.set(vehicleType.description, request.getDescription()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateVehicleTypeStatus(VehicleTypeStatusUpdate request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& vehicleTypeRepository.existsByvehicleTypeId(request.getVehicleTypeId());
	}

	@Transactional
	@Override
	public boolean updateVehicleTypeStatus(VehicleTypeStatusUpdate request) {
		QVehicleTypeProfile vehicleType = QVehicleTypeProfile.vehicleTypeProfile;
		long noOfRowUserCrd = new JPAUpdateClause(em, vehicleType)
				.where(vehicleType.vehicleTypeId.eq(request.getVehicleTypeId()))
				.set(vehicleType.status, request.getStatus()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateVehicleManufacturerStatus(VehicleManufacturerStatusUpdate request) {
		return userCredentialRepository.existsByEmail(request.getUserId()) && vehicleManufacturerRepository
				.existsByvehicleManufacturerId(request.getVehicleManufacturerId());
	}

	@Transactional
	@Override
	public boolean updateVehicleManufacturerStatus(VehicleManufacturerStatusUpdate request) {
		QVehicleManufacturer vehicleManufacturer = QVehicleManufacturer.vehicleManufacturer;
		long noOfRowUserCrd = new JPAUpdateClause(em, vehicleManufacturer)
				.where(vehicleManufacturer.vehicleManufacturerId
						.eq(request.getVehicleManufacturerId()))
				.set(vehicleManufacturer.status, request.getStatus()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public VehicleModelListResponse getVehicleManufacturerList(VehicleModelSearchRequest request) {
		VehicleModelListResponse response = new VehicleModelListResponse();
		response.getListofVehicleManufacturer();
		List<VehicleManufacturerResponse> listOfVehicleManufacturer = new ArrayList<>();
		VehicleTypeProfile vehicleTypeProfile = vehicleTypeRepository.findByVehicleTypeId(request.getVehicleTypeId());
		List<VehicleManufacturer> vehicleManufacturerList = vehicleManufacturerRepository
				.findByVehicleTypeId(vehicleTypeProfile.getVehicleTypeId());
		for (VehicleManufacturer vehicleManufacturer : vehicleManufacturerList) {
			VehicleManufacturerResponse vehicleManufacturerResponse = new VehicleManufacturerResponse();
			vehicleManufacturerResponse.setVehicleManufacturerName(vehicleManufacturer.getVehicleManufacturerName());
			vehicleManufacturerResponse.setVehicleManufacturerId(vehicleManufacturer.getVehicleManufacturerId());
			listOfVehicleManufacturer.add(vehicleManufacturerResponse);

		}
		response.setListofVehicleManufacturer(listOfVehicleManufacturer);
		return response;
	}

	@Override
	public boolean validateVehicleModelProfile(VehicleModelProfileUpdate vehicleModelProfileUpdate) {
		return vehicleModelRepository
				.existsByVehicleModelId(vehicleModelProfileUpdate.getVehicleModelId());
	}

	@Transactional
	@Override
	public boolean updateVehicleModelProfile(VehicleModelProfileUpdate vehicleModelProfileUpdate) {
		QVehicleModel vehicleModel = QVehicleModel.vehicleModel;
		VehicleTypeProfile vehicleTypeProfile = vehicleTypeRepository.findByVehicleTypeId(vehicleModelProfileUpdate.getVehicleTypeId());
		VehicleManufacturer vehicleManufacturer = vehicleManufacturerRepository.findByVehicleManufacturerId(vehicleModelProfileUpdate.getVehicleManufacturerId());
		long noOfRowUserCrd = new JPAUpdateClause(em, vehicleModel)
				.where(vehicleModel.vehicleModelId.eq(vehicleModelProfileUpdate.getVehicleModelId()))
				.set(vehicleModel.vehicleChassisNumber, vehicleModelProfileUpdate.getVehicleChassisNumber())
				.set(vehicleModel.vehicleEngineNumber, vehicleModelProfileUpdate.getVehicleEngineNumber())
				.set(vehicleModel.vehicleModelName, vehicleModelProfileUpdate.getVehicleModelName())
				.set(vehicleModel.vehicleRegistrationNumber, vehicleModelProfileUpdate.getVehicleRegistrationNumber())
				.set(vehicleModel.vehicleTypeId, vehicleTypeProfile.getVehicleTypeId())
				.set(vehicleModel.vehicleManufacturerId, vehicleManufacturer.getVehicleManufacturerId())
				.set(vehicleModel.description, vehicleModelProfileUpdate.getDescription()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateVehicleModelProfile(VehicleModelStatusUpdate vehicleModelStatusUpdate) {
		return userCredentialRepository.existsByEmail(vehicleModelStatusUpdate.getUserId()) && vehicleModelRepository
				.existsByVehicleModelId(vehicleModelStatusUpdate.getVehicleModelId());
	}

	@Override
	public boolean validateVehicleManufacturer(VehicleManufacturerProfileUpdate request) {
		return vehicleManufacturerRepository
				.existsByvehicleManufacturerId(request.getVehicleManufacturerId());
	}

	@Transactional
	@Override
	public boolean updateVehicleManufacturer(VehicleManufacturerProfileUpdate request) {
		QVehicleManufacturer vehicleManufacturer = QVehicleManufacturer.vehicleManufacturer;
		long noOfRowUserCrd = new JPAUpdateClause(em, vehicleManufacturer)
				.where(vehicleManufacturer.vehicleManufacturerId
						.eq(Integer.valueOf(request.getVehicleManufacturerId())))
				.set(vehicleManufacturer.vehicleTypeId, request.getVehicleTypeId())
				.set(vehicleManufacturer.vehicleManufacturerName, request.getVehicleManufacturerName())
				.set(vehicleManufacturer.description, request.getDescription()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public VehicleModelListResponse getVehicleModelList(VehicleModelSearchRequest request) {
		VehicleModelListResponse response = new VehicleModelListResponse();
		response.getListofModel();
		List<VehicleModelResponse> listOfModel = new ArrayList<>();
		List<VehicleModel> listOfVehicleModel = vehicleModelRepository
				.findByVehicleManufacturerIdAndStatusLike(request.getVehicleManufacturerId(),Status.ACTIVE.getValue());
		for (VehicleModel vehicleModel : listOfVehicleModel) {
			VehicleModelResponse vehicleModelResponse = new VehicleModelResponse();
			vehicleModelResponse.setVehicleModelName(vehicleModel.getVehicleModelName());
			vehicleModelResponse.setVehicleModelId(vehicleModel.getVehicleModelId());
			listOfModel.add(vehicleModelResponse);
		}
		response.setNoOfRecords(getTotalVehicleModelRows(request));
		response.setListofModel(listOfModel);
		return response;
	}

	@Transactional
	@Override
	public VehicleOnboarding updateVehicleOnboardStatus(VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate) {
		VehicleOnboarding vehicleOnboarding = vehicleOnboardingRepository.findByVehicleOnboardingId(vehicleOnboardStatusUpdate.getVehicleOnboardingId());
		vehicleOnboarding.setStatus(vehicleOnboardStatusUpdate.getStatus());
		vehicleOnboarding.setReason(vehicleOnboardStatusUpdate.getReason());
		return vehicleOnboardingRepository.save(vehicleOnboarding);
	}

	@Override
	public boolean validateVehicleOnboardProfile(VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate) {
		return userCredentialRepository.existsByEmail(vehicleOnboardStatusUpdate.getUserId())
				&& vehicleOnboardingRepository
						.existsByVehicleOnboardingId(vehicleOnboardStatusUpdate.getVehicleOnboardingId());
	}

	@Override
	public boolean validateVehicleOnboardProfile(VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate) {
		return userCredentialRepository.existsByEmail(vehicleOnboardProfileUpdate.getUserId())
				&& vehicleOnboardingRepository
						.existsByVehicleOnboardingId(vehicleOnboardProfileUpdate.getVehicleOnboardingId());
	}

	@Transactional
	@Override
	public boolean updateVehicleOnboardProfile(VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate) {
		QVehicleOnboarding vehicleOnboard = QVehicleOnboarding.vehicleOnboarding;
		long noOfRowUserCrd = new JPAUpdateClause(em, vehicleOnboard)
				.where(vehicleOnboard.vehicleOnboardingId.eq(vehicleOnboardProfileUpdate.getVehicleOnboardingId()))
				.set(vehicleOnboard.organizationId, vehicleOnboardProfileUpdate.getOrganizationId())
				.set(vehicleOnboard.vehicleManufacturerId, vehicleOnboardProfileUpdate.getVehicleManufacturerId())
				.set(vehicleOnboard.vehicleModelId, vehicleOnboardProfileUpdate.getVehicleModelId())
				.set(vehicleOnboard.vehicleTypeId, vehicleOnboardProfileUpdate.getVehicleTypeId())
				.set(vehicleOnboard.ptoId, vehicleOnboardProfileUpdate.getPtoId()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateVehicleTypeRegistration(VehicleTypeRegistrationRequest request) {
		return !vehicleTypeRepository.existsByVehicleType(request.getVehicleType());
	}

	@Override
	public boolean validateVehicleManufacturerName(VehicleManufacturerRegistrationRequest request) {
		return !vehicleManufacturerRepository.existsByVehicleManufacturerName(request.getVehicleManufacturerName());
	}

	@Override
	public boolean validateVehicleModelName(VehicleModelRegistrationRequest request) {
		return !vehicleModelRepository.existsByVehicleModelNameAndStatusNotLike(request.getVehicleModelName(), Status.TERMINATED.getValue());
	}

	@Override
	public VehicleModel updateVehicleModelStatus(VehicleModelStatusUpdate vehicleModelStatusUpdate) {
		VehicleModel vehicleModel = vehicleModelRepository
				.findByVehicleModelId(vehicleModelStatusUpdate.getVehicleModelId());
		vehicleModel.setStatus(vehicleModelStatusUpdate.getStatus());
		vehicleModel.setReason(vehicleModelStatusUpdate.getReason());
		return vehicleModelRepository.save(vehicleModel);
	}

	@Override
	public boolean validateEngineNumberInfo(VehicleModelRegistrationRequest request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QVehicleModel.vehicleModel)
				.where(QVehicleModel.vehicleModel.vehicleEngineNumber.eq(request.getVehicleEngineNumber()),
						isStatusNotTerminated(Status.TERMINATED.getValue()))
				.exists();
	}
	private BooleanExpression isStatusNotTerminated(String status) {
		return QVehicleModel.vehicleModel.status.toUpperCase()
				.notLike("%" + status.toUpperCase().replace("*", "") + "%");
	}

	@Override
	public boolean validateChassisNumberInfo(VehicleModelRegistrationRequest request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QVehicleModel.vehicleModel)
				.where(QVehicleModel.vehicleModel.vehicleChassisNumber.eq(request.getVehicleChassisNumber()),
						isStatusNotTerminated(Status.TERMINATED.getValue()))
				.exists();
	}
	
	@Override
	public VehicleTypeProfile getVehicleTypeId(String vehicleType) {
		return vehicleTypeRepository.findByVehicleType(vehicleType);
	}

	@Override
	public VehicleTypeProfile getVehicleTypeName(int vehicleTypeId) {
		return vehicleTypeRepository.findByVehicleTypeId(vehicleTypeId);
	}

	@Override
	public VehicleManufacturer getVehicleMaunfacturerId(String vehicleManufacturer) {
		return vehicleManufacturerRepository.findByVehicleManufacturerName(vehicleManufacturer);
	}
}