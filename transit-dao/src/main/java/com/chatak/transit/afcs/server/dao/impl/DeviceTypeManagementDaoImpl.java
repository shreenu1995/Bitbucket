package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.DeviceTypeManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DeviceTypeManagementDaoImpl implements DeviceTypeManagementDao {

	@Autowired
	DeviceTypeRegistrationRepository deviceTypeRegistrationRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Autowired
	DeviceModelRepository equipementModelRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Long saveDeviceTypeRegistrationDetails(DeviceTypeMaster request) {
		DeviceTypeMaster deviceTypeMaster = deviceTypeRegistrationRepository.save(request);
		return deviceTypeMaster.getDeviceTypeId();
	}

	@Override
	public boolean validateDeviceTypeRegistrationRequest(DeviceTypeRegistrationRequest request) {
		return !deviceTypeRegistrationRepository.existsByDeviceTypeName(request.getDeviceTypeName());

	}

	@Override
	public DeviceTypeSearchResponse searchDeviceType(DeviceTypeSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);

		List<DeviceTypeMaster> deviceTypeList = query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.status.eq(Status.ACTIVE.getValue()),isDeviceTypeId(request.getDeviceTypeId()), isDeviceTypeName(request.getDeviceTypeName()),
						 isDescription(request.getDescription()),isStatus(request.getStatus()))
				.offset(fromIndex).limit(Constants.SIZE).orderBy(QDeviceTypeMaster.deviceTypeMaster.deviceTypeId.desc())
				.list(QDeviceTypeMaster.deviceTypeMaster);

		List<DeviceTypeSearchRequest> eqpmentTypeList = new ArrayList<>();
		for (DeviceTypeMaster deviceTypeMaster : deviceTypeList) {
			DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
			deviceTypeSearchRequest.setDeviceTypeId(deviceTypeMaster.getDeviceTypeId());
			deviceTypeSearchRequest.setDescription(deviceTypeMaster.getDescription());
			deviceTypeSearchRequest.setDeviceTypeName(deviceTypeMaster.getDeviceTypeName());
			deviceTypeSearchRequest.setStatus(deviceTypeMaster.getStatus());
			eqpmentTypeList.add(deviceTypeSearchRequest);
		}
		DeviceTypeSearchResponse response = new DeviceTypeSearchResponse();
		response.setListDeviceType(eqpmentTypeList);
		response.setTotalRecords(totalDeviceTypeRecords(request));
		return response;
	}
	
	private BooleanExpression isDeviceTypeId(Long deviceTypeId) {
		return !StringUtil.isNull(deviceTypeId) ? QDeviceTypeMaster.deviceTypeMaster.deviceTypeId.eq(deviceTypeId) : null;
	}

	private int totalDeviceTypeRecords(DeviceTypeSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.status.ne(Status.TERMINATED.getValue()),isDeviceTypeId(request.getDeviceTypeId()), isDeviceTypeName(request.getDeviceTypeName()),
						 isDescription(request.getDescription()),isStatus(request.getStatus()))
				.orderBy().count();
		return count != null ? count.intValue() : 0;
	}
	
	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QDeviceTypeMaster.deviceTypeMaster.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%")
	            : null;
	}

	private BooleanExpression isDeviceTypeName(String deviceTypeName) {
		return !StringUtil.isNullEmpty(deviceTypeName) ? QDeviceTypeMaster.deviceTypeMaster.deviceTypeName
				.toUpperCase().like("%" + deviceTypeName.toUpperCase().replace("*", "") + "%") : null;
	}


	private BooleanExpression isDescription(String description) {
		return !StringUtil.isNullEmpty(description) ? QDeviceTypeMaster.deviceTypeMaster.description.toUpperCase()
				.like("%" + description.toUpperCase().replace("*", "") + "%") : null;
	}

	@Transactional
	@Override
	public boolean updateDeviceTypeProfile(DeviceTypeProfileUpdateRequest request) {
		QDeviceTypeMaster deviceType = QDeviceTypeMaster.deviceTypeMaster;
		long noOfRowUserCrd = new JPAUpdateClause(em, deviceType)
				.where(deviceType.deviceTypeId.eq(request.getDeviceTypeId()))
				.set(deviceType.deviceTypeName, request.getDeviceTypeName())
				.set(deviceType.description, request.getDescription()).execute();
		return noOfRowUserCrd == 1l;
	}
	
	@Override
	public boolean validateDeviceTypeProfileUpdate(DeviceTypeProfileUpdateRequest request) {
				return deviceTypeRegistrationRepository.existsByDeviceTypeId(request.getDeviceTypeId());
	}
	
	@Transactional
	@Override
	public boolean updateDeviceTypeStatus(DeviceTypeStatusUpdateRequest request) {
		QDeviceTypeMaster deviceType = QDeviceTypeMaster.deviceTypeMaster;
		long noOfRowUserCrd = new JPAUpdateClause(em, deviceType)
				.where(deviceType.deviceTypeId.eq(request.getDeviceTypeId()))
				.set(deviceType.status, request.getStatus()).execute();
		return noOfRowUserCrd == 1l;
	}
	
	@Override
	public boolean validateDeviceTypeStatusUpdate(DeviceTypeStatusUpdateRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& deviceTypeRegistrationRepository.existsByDeviceTypeId(request.getDeviceTypeId());
	}

	@Override
	public boolean validateDeviceTypeListView(DeviceTypeListViewRequest request) {
		return deviceTypeRegistrationRepository.existsByDeviceTypeId(request.getDeviceTypeId());
	}
	
	@Override
	public List<DeviceTypeMaster> getDeviceTypeListView(DeviceTypeListViewRequest request) {
		return deviceTypeRegistrationRepository.findByDeviceTypeId(request.getDeviceTypeId());
	}
	
}