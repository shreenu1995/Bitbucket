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
import com.chatak.transit.afcs.server.dao.DeviceOnboardManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceManufacturerMaster;
import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.dao.model.DeviceOnboardMaster;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceManufacturerMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceModel;
import com.chatak.transit.afcs.server.dao.model.QDeviceOnboardMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.repository.DeviceManufacturerRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceOnboardRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DeviceOnboardManagementDaoImpl implements DeviceOnboardManagementDao {

	@Autowired
	DeviceOnboardRepository deviceOnboardRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	@Autowired
	DeviceTypeRegistrationRepository deviceTypeRepository;
	
	@Autowired
	DeviceManufacturerRegistrationRepository deviceManufacturerRepository;
	
	@Autowired
	DeviceModelRepository deviceModelRepository;

	@PersistenceContext
	private EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(FeeManagementDaoImpl.class);

	@Override
	public void saveDeviceOnboardingRegistrations(DeviceOnboardMaster deviceOnboardMaster) {
		deviceOnboardRepository.save(deviceOnboardMaster);
	}

	@Override
	public DeviceOnboardListSearchResponse getDeviceOnboardSearchList(DeviceOnboardSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<DeviceOnboardMaster> deviceOnboardMasterList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			deviceOnboardMasterList = query.from(QDeviceOnboardMaster.deviceOnboardMaster)
					.where(QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isDeviceOnboarId(request.getDeviceOnboardId()),
							isDeviceTypeId(request.getDeviceTypeId()),
							isDeviceModelId(request.getDeviceModelId()),
							isPtoId(request.getPtoId()),
							isDeviceManufacturerId(request.getDeviceManufacturerCode()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.desc())
					.list(QDeviceOnboardMaster.deviceOnboardMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			deviceOnboardMasterList = query.from(QDeviceOnboardMaster.deviceOnboardMaster)
					.where(QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue()),
							QDeviceOnboardMaster.deviceOnboardMaster.organizationId.eq(request.getOrganizationId()),
							isDeviceOnboarId(request.getDeviceOnboardId()),
							isDeviceTypeId(request.getDeviceTypeId()),
							isDeviceModelId(request.getDeviceModelId()),
							isPtoId(request.getPtoId()),
							isDeviceManufacturerId(request.getDeviceManufacturerCode()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.desc())
					.list(QDeviceOnboardMaster.deviceOnboardMaster);
		} else {
			deviceOnboardMasterList = query.from(QDeviceOnboardMaster.deviceOnboardMaster)
					.where(QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue()),
							isDeviceOnboarId(request.getDeviceOnboardId()),
							isDeviceManufacturerId(request.getDeviceManufacturerCode()),
							isDeviceModelId(request.getDeviceModelId()),
							isDeviceTypeId(request.getDeviceTypeId()),
							isPtoId(request.getPtoId()),
							isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE)
					.orderBy(QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.desc())
					.list(QDeviceOnboardMaster.deviceOnboardMaster);
		}
		List<DeviceOnboardSearchResponse> listDeviceOnboardSearchResponse = new ArrayList<>();
		DeviceTypeMaster deviceTypeMaster=new DeviceTypeMaster();
		DeviceManufacturerMaster deviceManufacturerMaster=new DeviceManufacturerMaster();
		DeviceModel deviceModel=new DeviceModel();
		for (DeviceOnboardMaster deviceOnboardMaster : deviceOnboardMasterList) {

			if(!StringUtil.isNull(deviceOnboardMaster.getDeviceTypeId())) {
			deviceTypeMaster = deviceTypeRepository.findByDeviceTypeIdAndStatusLike(deviceOnboardMaster.getDeviceTypeId(), Status.ACTIVE.getValue());
			}
			if(!StringUtil.isNull(deviceOnboardMaster.getDeviceManufacturerId())) {
				 deviceManufacturerMaster = deviceManufacturerRepository.findByDeviceManufacturerId(deviceOnboardMaster.getDeviceManufacturerId());
			}
			if(!StringUtil.isNull(deviceOnboardMaster.getDeviceModelId())) {
				 deviceModel = deviceModelRepository.findByDeviceId(deviceOnboardMaster.getDeviceModelId());			}
			
			setDeviceOnboardData(listDeviceOnboardSearchResponse, deviceTypeMaster, deviceManufacturerMaster,
					deviceModel, deviceOnboardMaster);

		}
		DeviceOnboardListSearchResponse deviceOnboardListSearchResponse = new DeviceOnboardListSearchResponse();
		deviceOnboardListSearchResponse.setTotalRecords(getTotalNoOfRows(request));
		deviceOnboardListSearchResponse.setListDeviceOnboard(listDeviceOnboardSearchResponse);
		return deviceOnboardListSearchResponse;
	}

	private void setDeviceOnboardData(List<DeviceOnboardSearchResponse> listDeviceOnboardSearchResponse,
			DeviceTypeMaster deviceTypeMaster, DeviceManufacturerMaster deviceManufacturerMaster,
			DeviceModel deviceModel, DeviceOnboardMaster deviceOnboardMaster) {
		DeviceOnboardSearchResponse deviceOnboardSearchResponse = new DeviceOnboardSearchResponse();
		deviceOnboardSearchResponse.setOrganizationId(deviceOnboardMaster.getOrganizationId());
		deviceOnboardSearchResponse.setPtoId(deviceOnboardMaster.getPtoId());
		deviceOnboardSearchResponse.setDeviceManufacturerId(deviceOnboardMaster.getDeviceManufacturerId());
		if (!StringUtil.isNull(deviceManufacturerMaster.getDeviceManufacturer())) {
			deviceOnboardSearchResponse.setDeviceManufacturer(deviceManufacturerMaster.getDeviceManufacturer());
		}
		deviceOnboardSearchResponse.setDeviceTypeId(deviceOnboardMaster.getDeviceTypeId());
		if (!StringUtil.isNull(deviceTypeMaster.getDeviceTypeName())) {
			deviceOnboardSearchResponse.setDeviceTypeName(deviceTypeMaster.getDeviceTypeName());
		}
		deviceOnboardSearchResponse.setDeviceModelId(deviceOnboardMaster.getDeviceModelId());
		if (!StringUtil.isNull(deviceModel.getDeviceModelName())) {
			deviceOnboardSearchResponse.setDeviceModel(deviceModel.getDeviceModelName());
		}
		deviceOnboardSearchResponse.setDeviceOnboardId(String.valueOf(deviceOnboardMaster.getDeviceOnboardId()));
		deviceOnboardSearchResponse.setStatus(deviceOnboardMaster.getStatus());
		if (!StringUtil.isNull(deviceOnboardMaster.getOrganizationId())) {
			try {
				OrganizationMaster organizationMaster = organizationMasterRepository
						.findByOrgId(deviceOnboardMaster.getOrganizationId());
				deviceOnboardSearchResponse.setOrganizationName(organizationMaster.getOrganizationName());
			} catch (NullPointerException e) {
				logger.error("ERROR :: DeviceOnboardManagementDaoImpl class :: getDeviceOnboardSearchList method :: exception", e);
			}
		}
		if (!StringUtil.isNull(deviceOnboardMaster.getPtoId())) {
			try {
				PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(deviceOnboardMaster.getPtoId());
				deviceOnboardSearchResponse.setPtoName(ptoMaster.getPtoName());
			} catch (NullPointerException e) {
				logger.error("ERROR :: DepotManagementDaoImpl class :: searchdepot method :: exception", e);
			}
		}
		listDeviceOnboardSearchResponse.add(deviceOnboardSearchResponse);
	}

	private int getTotalNoOfRows(DeviceOnboardSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QDeviceOnboardMaster.deviceOnboardMaster)
					.where(QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isDeviceOnboarId(request.getDeviceOnboardId()),
							isDeviceManufacturerId(request.getDeviceManufacturerCode()),
							isDeviceModelId(request.getDeviceModelId()),
							isDeviceTypeId(request.getDeviceTypeId()),
							isPtoId(request.getPtoId()),
							isStatus(request.getStatus()))
					.orderBy(QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QDeviceOnboardMaster.deviceOnboardMaster)
					.where(QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue()),
							QDeviceOnboardMaster.deviceOnboardMaster.organizationId.eq(request.getOrganizationId()),
							isDeviceOnboarId(request.getDeviceOnboardId()),
							isDeviceManufacturerId(request.getDeviceManufacturerCode()),
							isDeviceModelId(request.getDeviceModelId()),
							isDeviceTypeId(request.getDeviceTypeId()),
							isPtoId(request.getPtoId()),
							isStatus(request.getStatus()))
					.orderBy(QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.desc()).count();
		} else{
			count = query.from(QDeviceOnboardMaster.deviceOnboardMaster)
					.where(QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue()),
							isDeviceOnboarId(request.getDeviceOnboardId()),
							isDeviceManufacturerId(request.getDeviceManufacturerCode()),
							isDeviceModelId(request.getDeviceModelId()),
							isDeviceTypeId(request.getDeviceTypeId()),
							isPtoId(request.getPtoId()),
							isStatus(request.getStatus()))
					.orderBy(QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QDeviceOnboardMaster.deviceOnboardMaster.organizationId.eq(organizationId) : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QDeviceOnboardMaster.deviceOnboardMaster.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isDeviceOnboarId(Long deviceOnboarId) {
		return !StringUtil.isNull(deviceOnboarId) ? QDeviceOnboardMaster.deviceOnboardMaster.deviceOnboardId.eq(deviceOnboarId) : null;
	}

	private BooleanExpression isDeviceManufacturerId(Long deviceManufacturerId) {
		return !StringUtil.isNull(deviceManufacturerId) ? QDeviceOnboardMaster.deviceOnboardMaster.deviceManufacturerId.eq(deviceManufacturerId) : null;
	}

	private BooleanExpression isDeviceModelId(Long deviceModelId) {
		return !StringUtil.isNull(deviceModelId) ? QDeviceOnboardMaster.deviceOnboardMaster.deviceModelId.eq(deviceModelId) : null;
	}

	private BooleanExpression isDeviceTypeId(Long deviceTypeId) {
		return !StringUtil.isNull(deviceTypeId) ? QDeviceOnboardMaster.deviceOnboardMaster.deviceTypeId.eq(deviceTypeId) : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QDeviceOnboardMaster.deviceOnboardMaster.ptoId.eq(ptoId) : null;
	}

	@Transactional
	@Override
	public boolean updateDeviceOnboardProfile(DeviceOnboardProfileUpdateRequest request) {
		QDeviceOnboardMaster model = QDeviceOnboardMaster.deviceOnboardMaster;
		long noOfRowUserCrd = new JPAUpdateClause(em, model)
				.where(model.deviceOnboardId.eq(request.getDeviceOnboardId()))
				.set(model.ptoId, request.getPtoId())
				.set(model.organizationId, request.getOrganizationId())
				.set(model.deviceModelId, request.getDeviceModelId())
				.set(model.deviceManufacturerId, request.getDeviceManufacturerCode())
				.set(model.deviceTypeId, request.getDeviceTypeId()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateDeviceOnboardStatusUpdate(DeviceOnboardProfileUpdateRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& deviceOnboardRepository.existsByDeviceOnboardId(request.getDeviceOnboardId());
	}


	@Override
	public List<DeviceTypeSearchRequest> getDeviceTypeName() {
		JPAQuery query = new JPAQuery(em);
		List<DeviceTypeSearchRequest> deviceTypeList = new ArrayList<>();
		List<DeviceTypeMaster> deviceTypeProfileList = query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QDeviceTypeMaster.deviceTypeMaster.deviceTypeId.desc())
				.list(QDeviceTypeMaster.deviceTypeMaster);
		for (DeviceTypeMaster deviceTypeMaster : deviceTypeProfileList) {
			DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
			deviceTypeSearchRequest.setDeviceTypeName(deviceTypeMaster.getDeviceTypeName());
			deviceTypeSearchRequest.setDeviceTypeId(deviceTypeMaster.getDeviceTypeId());
			deviceTypeList.add(deviceTypeSearchRequest);
		}
		
		return deviceTypeList;
	}

	@Override
	public List<DeviceManufacturerSearchRequest> getDeviceManuName() {
		JPAQuery query = new JPAQuery(em);
		List<DeviceManufacturerSearchRequest> deviceManuList = new ArrayList<>();
		List<DeviceManufacturerMaster> deviceManuProfileList = query
				.from(QDeviceManufacturerMaster.deviceManufacturerMaster)
				.where(QDeviceManufacturerMaster.deviceManufacturerMaster.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturerId.desc())
				.list(QDeviceManufacturerMaster.deviceManufacturerMaster);
		for (DeviceManufacturerMaster deviceManufacturerMaster : deviceManuProfileList) {
			DeviceManufacturerSearchRequest deviceManufacturerSearchRequest = new DeviceManufacturerSearchRequest();
			deviceManufacturerSearchRequest.setDeviceManufacturer(deviceManufacturerMaster.getDeviceManufacturer());
			deviceManufacturerSearchRequest.setDeviceManufacturerCode(deviceManufacturerMaster.getDeviceManufacturerId());
			deviceManuList.add(deviceManufacturerSearchRequest);
			
		}
		return deviceManuList;
	}

	@Override
	public List<DeviceModelRequest> getDeviceModelName() {
		JPAQuery query = new JPAQuery(em);
		List<DeviceModelRequest> deviceModelList = new ArrayList<>();
		List<DeviceModel> deviceModelProfileList = query.from(QDeviceModel.deviceModel)
				.where(QDeviceModel.deviceModel.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QDeviceModel.deviceModel.deviceId.desc()).list(QDeviceModel.deviceModel);
		for (DeviceModel deviceModel : deviceModelProfileList) {
			DeviceModelRequest deviceModelRequest = new DeviceModelRequest();
			deviceModelRequest.setDeviceModel(deviceModel.getDeviceModelName());
			deviceModelRequest.setDeviceModelId(deviceModel.getDeviceId());
			deviceModelList.add(deviceModelRequest);
		}
		return deviceModelList;
	}

	@Override
	public boolean validateDeviceModel(Long deviceModelId) {
		return !deviceOnboardRepository.existsByDeviceModelIdAndStatusNotLike(deviceModelId,Status.TERMINATED.getValue());
	}

	@Override
	public DeviceOnboardMaster updateDeviceOnboardStatusUpdate(DeviceOnboardProfileUpdateRequest request) {
			DeviceOnboardMaster deviceOnboardMaster = deviceOnboardRepository.findByDeviceOnboardId(request.getDeviceOnboardId());
			deviceOnboardMaster.setStatus(request.getStatus());
			deviceOnboardMaster.setReason(request.getReason());
			deviceOnboardRepository.save(deviceOnboardMaster);
			return deviceOnboardMaster;
	}
}
