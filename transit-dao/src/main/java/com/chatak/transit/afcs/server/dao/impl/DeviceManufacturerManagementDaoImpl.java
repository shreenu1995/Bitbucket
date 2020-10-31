package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.DeviceManufacturerManagementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceManufacturerMaster;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceManufacturerMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.repository.DeviceManufacturerRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DeviceManufacturerManagementDaoImpl implements DeviceManufacturerManagementDao {

	@Autowired
	DeviceManufacturerRegistrationRepository deviceManufacturerRegistrationRepository;

	@Autowired
	DeviceTypeRegistrationRepository deviceTypeRegistrationRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Override
	public boolean validateDeviceManufacturerRegistrationRequest(DeviceManufacturerRegistrationRequest request) {
		return !deviceManufacturerRegistrationRepository
				.existsByDeviceManufacturer(request.getDeviceManufacturer());
	}

	@Override
	public Long saveDeviceManufacturerRegistrationDetails(DeviceManufacturerMaster request) {
		DeviceManufacturerMaster deviceManufacturerMaster = deviceManufacturerRegistrationRepository
				.save(request);
		return deviceManufacturerMaster.getDeviceManufacturerId();
	}

	@Override
	public DeviceManufacturerListSearchResponse searchEquipementManufacturer(
			DeviceManufacturerSearchRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<DeviceManufacturerMaster> deviceManufacturerList = query
				.from(QDeviceManufacturerMaster.deviceManufacturerMaster)
				.where(QDeviceManufacturerMaster.deviceManufacturerMaster.status.eq(Status.ACTIVE.getValue()),
						isDeviceTypeId(request.getDeviceTypeId()),
						isDeviceManufacturerCode(request.getDeviceManufacturerCode()),
						isDeviceManufacturerName(request.getDeviceManufacturer()),
						isDeviceTypeId(request.getDeviceTypeId()), isStatus(request.getStatus()))
				.offset(fromIndex).limit(Constants.SIZE)
				.orderBy(QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturerId.desc())
				.list(QDeviceManufacturerMaster.deviceManufacturerMaster);

		DeviceManufacturerListSearchResponse deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();

		List<DeviceManufacturerSearchResponse> deviceManufacturerList1 = new ArrayList<>();

		for (DeviceManufacturerMaster deviceManufacturerMaster : deviceManufacturerList) {
			if (!StringUtil.isNull(deviceManufacturerMaster)) {
				DeviceManufacturerSearchResponse deviceManufacturerSearchResponse = new DeviceManufacturerSearchResponse();
				DeviceTypeMaster deviceTypeMaster = deviceTypeRegistrationRepository
						.findByDeviceTypeIdAndStatusLike(deviceManufacturerMaster.getDeviceTypeId(), Status.ACTIVE.getValue());
				deviceManufacturerSearchResponse
						.setDeviceManufacturer(deviceManufacturerMaster.getDeviceManufacturer());
				deviceManufacturerSearchResponse.setDeviceTypeName(deviceTypeMaster.getDeviceTypeName());
				deviceManufacturerSearchResponse.setDeviceTypeId(deviceManufacturerMaster.getDeviceTypeId());
				deviceManufacturerSearchResponse.setDeviceManufacturerId(deviceManufacturerMaster.getDeviceManufacturerId());
				deviceManufacturerSearchResponse.setDescription(deviceManufacturerMaster.getDescription());
				deviceManufacturerSearchResponse.setStatus(deviceManufacturerMaster.getStatus());
				deviceManufacturerList1.add(deviceManufacturerSearchResponse);
			}
		}
		deviceManufacturerListSearchResponse.setTotalRecords(getTotalNoOfRows(request));
		deviceManufacturerListSearchResponse.setListDeviceManuFacturer(deviceManufacturerList1);
		return deviceManufacturerListSearchResponse;
	}

	private int getTotalNoOfRows(DeviceManufacturerSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QDeviceManufacturerMaster.deviceManufacturerMaster)
				.where(QDeviceManufacturerMaster.deviceManufacturerMaster.status.eq(Status.ACTIVE.getValue()),
						isDeviceTypeId(request.getDeviceTypeId()),
						isDeviceManufacturerCode(request.getDeviceManufacturerCode()),
						isDeviceManufacturerName(request.getDeviceManufacturer()),
						isStatus(request.getStatus()))
				.orderBy(QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturerId.desc())
				.count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QDeviceManufacturerMaster.deviceManufacturerMaster.status
				.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isDeviceManufacturerCode(Long deviceManufacturerId) {
		return !StringUtil.isNull(deviceManufacturerId) ? QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturerId.eq(deviceManufacturerId) : null;
	}

	private BooleanExpression isDeviceTypeId(Long deviceTypeId) {
		return !StringUtil.isNull(deviceTypeId) ? QDeviceManufacturerMaster.deviceManufacturerMaster.deviceTypeId.eq(deviceTypeId) : null;
	}
	
	private BooleanExpression isDeviceManufacturerName(String deviceManufacturerName) {
		return !StringUtil.isNullEmpty(deviceManufacturerName)
				? QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturer.toUpperCase()
						.like("%" + deviceManufacturerName.toUpperCase().replace("*", "") + "%")
				: null;
	}

	@Override
	public DeviceTypeListViewResponse getDeviceTypeList(DeviceTypeListViewRequest request) {
		JPAQuery query = new JPAQuery(em);
		List<DeviceTypeListViewRequest> deviceTypeListView = new ArrayList<>();
		DeviceTypeListViewResponse deviceTypeListViewResponse = new DeviceTypeListViewResponse();
		List<DeviceTypeMaster> deviceTypeMasterList = query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.status.eq(Status.ACTIVE.getValue()))
				.orderBy(QDeviceTypeMaster.deviceTypeMaster.deviceTypeName.asc())
				.list(QDeviceTypeMaster.deviceTypeMaster);
		for (DeviceTypeMaster deviceTypeMaster : deviceTypeMasterList) {
			DeviceTypeListViewRequest deviceTypeListViewRequest = new DeviceTypeListViewRequest();
			deviceTypeListViewRequest.setDeviceTypeId(deviceTypeMaster.getDeviceTypeId());
			deviceTypeListViewRequest.setDeviceTypeName(deviceTypeMaster.getDeviceTypeName());
			deviceTypeListView.add(deviceTypeListViewRequest);
		}
		deviceTypeListViewResponse.setDeviceTypeListView(deviceTypeListView);
		deviceTypeListViewResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		deviceTypeListViewResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return deviceTypeListViewResponse;
	}

	@Override
	public DeviceManufacturerListSearchResponse getDeviceManufacturerList(
			DeviceManufacturerListViewRequest request) {
		DeviceManufacturerListSearchResponse response = new DeviceManufacturerListSearchResponse();
		response.getListDeviceManuFacturer();
		List<DeviceManufacturerSearchResponse> listOfManufacturer = new ArrayList<>();
		List<DeviceManufacturerMaster> listOfDeviceManufacturer = deviceManufacturerRegistrationRepository
				.findByDeviceTypeId(request.getDeviceTypeId());
		for (DeviceManufacturerMaster deviceManufacturerMaster : listOfDeviceManufacturer) {
			DeviceManufacturerSearchResponse deviceResponse = new DeviceManufacturerSearchResponse();
			deviceResponse.setDeviceManufacturer(deviceManufacturerMaster.getDeviceManufacturer());
			deviceResponse.setDeviceManufacturerId(deviceManufacturerMaster.getDeviceManufacturerId());
			listOfManufacturer.add(deviceResponse);

		}
		response.setListDeviceManuFacturer(listOfManufacturer);
		return response;

	}

	@Override
	public boolean validateDeviceManufacturerProfileUpdate(DeviceManufacturerProfileUpdateRequest request) {
		return deviceManufacturerRegistrationRepository
				.existsByDeviceManufacturerId(request.getDeviceManufacturerCode());
	}

	@Transactional
	@Override
	public boolean updateDeviceManufacturerProfile(DeviceManufacturerProfileUpdateRequest request) {
		Timestamp timeStamp = Timestamp.from(Instant.now());
		QDeviceManufacturerMaster deviceManufacturer = QDeviceManufacturerMaster.deviceManufacturerMaster;
		long noOfRowUserCrd = new JPAUpdateClause(em, deviceManufacturer)
				.where(deviceManufacturer.deviceManufacturerId
						.eq(request.getDeviceManufacturerCode()))
				.set(deviceManufacturer.deviceManufacturer, request.getDeviceManufacturer())
				.set(deviceManufacturer.deviceTypeId, request.getDeviceTypeId())
				.set(deviceManufacturer.updatedBy, request.getUserId())
				.set(deviceManufacturer.updatedTime, timeStamp)
				.set(deviceManufacturer.description, request.getDescription()).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateDeviceManufacturerStatusUpdate(DeviceManufacturerStatusUpdateRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& deviceManufacturerRegistrationRepository
						.existsByDeviceManufacturerId(request.getDeviceManufacturerCode());
	}

	@Override
	public boolean updateDeviceManufacturerStatus(DeviceManufacturerMaster deviceManufacturerMaster) {
		return (ServerConstants.COUNT == deviceManufacturerRegistrationRepository.updateStatus(
				deviceManufacturerMaster.getStatus(), deviceManufacturerMaster.getDeviceManufacturerId(),
				deviceManufacturerMaster.getUpdatedBy(), deviceManufacturerMaster.getUpdatedTime()));
	}

}