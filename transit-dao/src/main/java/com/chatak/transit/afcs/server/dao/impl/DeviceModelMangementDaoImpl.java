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
import com.chatak.transit.afcs.server.dao.DeviceModelMangementDao;
import com.chatak.transit.afcs.server.dao.model.DeviceManufacturerMaster;
import com.chatak.transit.afcs.server.dao.model.DeviceModel;
import com.chatak.transit.afcs.server.dao.model.DeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceModel;
import com.chatak.transit.afcs.server.dao.repository.DeviceManufacturerRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeRegistrationRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DeviceModelMangementDaoImpl implements DeviceModelMangementDao {

	@Autowired
	DeviceTypeRegistrationRepository deviceTypeRegistrationRepository;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialRepository;

	@Autowired
	DeviceModelRepository equipementModelRepository;

	@Autowired
	DeviceManufacturerRegistrationRepository deviceManufacturerRegistrationRepository;

	@PersistenceContext
	private EntityManager em;

	public Long saveDeviceModelDetails(DeviceModel request) {
		DeviceModel response = equipementModelRepository.save(request);
		return response.getDeviceId();
	}

	@Override
	public DeviceModelListSearchResponse getEquipementModelSearchList(DeviceModelSearchRequest request) {
		Integer pageIndexNo = request.getIndexPage();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<DeviceModel> listOfDeviceModel = query.from(QDeviceModel.deviceModel)
				.where(QDeviceModel.deviceModel.status.ne(Status.TERMINATED.getValue()),
						isDeviceId(request.getDeviceId()), isDeviceModel(request.getDeviceModel()),
						isDeviceTypeId(request.getDeviceTypeId()),
						isDeviceManufacturerId(request.getDeviceManufacturerCode()), isStatus(request.getStatus()))
				.offset(fromIndex).limit(10).orderBy(QDeviceModel.deviceModel.deviceId.desc())
				.list(QDeviceModel.deviceModel);

		DeviceModelListSearchResponse response = new DeviceModelListSearchResponse();
		List<DeviceModelSearchResponse> deviceModelSearchResponselist = new ArrayList<>();

		for (DeviceModel deviceModelTableData : listOfDeviceModel) {
			DeviceTypeMaster deviceTypeMaster = deviceTypeRegistrationRepository.findByDeviceTypeIdAndStatusLike(deviceModelTableData.getDeviceTypeId(), Status.ACTIVE.getValue());
			DeviceManufacturerMaster deviceManufacturerMaster = deviceManufacturerRegistrationRepository.findByDeviceManufacturerId(deviceModelTableData.getDeviceManufacturerId());
			DeviceModelSearchResponse modelSearchDataResponse = new DeviceModelSearchResponse();
			modelSearchDataResponse.setDeviceModel(deviceModelTableData.getDeviceModelName());
			modelSearchDataResponse.setDeviceManufacturer(deviceManufacturerMaster.getDeviceManufacturer());
			modelSearchDataResponse.setDeviceTypeName(deviceTypeMaster.getDeviceTypeName());
			modelSearchDataResponse.setDescription(deviceModelTableData.getDescription());
			modelSearchDataResponse.setDeviceTypeId(deviceModelTableData.getDeviceTypeId());
			modelSearchDataResponse.setDeviceManufacturerId(deviceModelTableData.getDeviceManufacturerId());
			modelSearchDataResponse.setDeviceId(deviceModelTableData.getDeviceId());
			modelSearchDataResponse.setDeviceIMEINumber(deviceModelTableData.getDeviceIMEINumber());
			modelSearchDataResponse.setStatus(deviceModelTableData.getStatus());
			deviceModelSearchResponselist.add(modelSearchDataResponse);
		}
		response.setNoOfRecords(getTotalTicketsTxnRows(request));
		response.setListDeviceModelSearchResponse(deviceModelSearchResponselist);
		return response;
	}

	private int getTotalTicketsTxnRows(DeviceModelSearchRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = query.from(QDeviceModel.deviceModel)
				.where(QDeviceModel.deviceModel.status.ne(Status.TERMINATED.getValue()),
						isDeviceId(request.getDeviceId()), isDeviceModel(request.getDeviceModel()),
						isDeviceTypeId(request.getDeviceTypeId()),
						isDeviceManufacturerId(request.getDeviceManufacturerCode()), isStatus(request.getStatus()))
				.orderBy(QDeviceModel.deviceModel.deviceId.desc()).count();
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status)
				? QDeviceModel.deviceModel.status.toUpperCase().like("%" + status.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isDeviceId(String deviceId) {
		return !StringUtil.isNull(deviceId)
				? QDeviceModel.deviceModel.deviceId.like("%" + deviceId.replace("*", "") + "%") : null;
	}

	private BooleanExpression isDeviceModel(String deviceModel) {
		return !StringUtil.isNullEmpty(deviceModel) ? QDeviceModel.deviceModel.deviceModelName.toUpperCase()
				.like("%" + deviceModel.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isDeviceTypeId(Long deviceTypeId) {
		return !StringUtil.isNull(deviceTypeId) ? QDeviceModel.deviceModel.deviceTypeId.eq(deviceTypeId) : null;
	}

	private BooleanExpression isDeviceManufacturerId(Long deviceManufacturerId) {
		return !StringUtil.isNull(deviceManufacturerId) ? QDeviceModel.deviceModel.deviceManufacturerId.eq(deviceManufacturerId) : null;

	}

	public boolean validateDeviceModel(DeviceModelRequest request) {
		return deviceTypeRegistrationRepository.existsByDeviceTypeId(request.getDeviceTypeId())
				&& deviceManufacturerRegistrationRepository.existsByDeviceManufacturerId(request.getDeviceManufacturerCode())
		        && !equipementModelRepository.existsByDeviceIMEINumberAndStatusNotLike(request.getDeviceIMEINumber(), "Terminated");
	}

	@Override
	public boolean validateDeviceModelProfileUpdate(DeviceModelProfileUpdateRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& equipementModelRepository.existsByDeviceId(request.getDeviceId())
				&& deviceTypeRegistrationRepository.existsByDeviceTypeId(request.getDeviceTypeId());
	}

	@Transactional
	@Override
	public boolean updateDeviceModelProfile(DeviceModelProfileUpdateRequest request) {
		QDeviceModel model = QDeviceModel.deviceModel;
		long noOfRowUserCrd = new JPAUpdateClause(em, model)
				.where(model.deviceId.eq(request.getDeviceId()))
				.set(model.deviceModelName, request.getDeviceModel())
				.set(model.deviceIMEINumber, request.getDeviceIMEINumber())
				.set(model.description, request.getDescription())
				.set(model.deviceManufacturerId, request.getDeviceManufacturerCode())
				.set(model.deviceTypeId, request.getDeviceTypeId())
				.set(model.updatedTime, Timestamp.from(Instant.now())).set(model.updatedBy, request.getUserId())
				.execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateDeviceModelStatusUpdate(DeviceModelStatusUpdateRequest request) {
		return userCredentialRepository.existsByEmail(request.getUserId())
				&& equipementModelRepository.existsByDeviceId(request.getDeviceId());
	}

	@Override
	public DeviceModelListResponse getDeviceModelList(DeviceModelSearchRequest request) {
		DeviceModelListResponse response = new DeviceModelListResponse();
		response.getListDeviceModel();
		List<DeviceModelSearchResponse> listOfModel = new ArrayList<>();
		List<DeviceModel> listOfDeviceModel = equipementModelRepository
				.findByDeviceManufacturerIdAndStatusLike(request.getDeviceManufacturerCode(), Status.ACTIVE.getValue());
		for (DeviceModel deviceModel : listOfDeviceModel) {
			DeviceModelSearchResponse deviceModelSearchResponse = new DeviceModelSearchResponse();
			deviceModelSearchResponse.setDeviceId(deviceModel.getDeviceId());
			deviceModelSearchResponse.setDeviceModel(deviceModel.getDeviceModelName());
			listOfModel.add(deviceModelSearchResponse);
		}
		response.setTotalRecords(getTotalTicketsTxnRows(request));
		response.setListDeviceModel(listOfModel);
		return response;
	}

	@Override
	public DeviceModel updateDeviceModelStatusUpdate(DeviceModelStatusUpdateRequest request) {
		DeviceModel model = equipementModelRepository.findByDeviceId(request.getDeviceId());
		model.setStatus(request.getStatus());
		model.setReason(request.getReason());
		return equipementModelRepository.save(model);
	}

	@Override
	public boolean findDeviceByDeviceId(Long deviceId) {
		return equipementModelRepository.existsByDeviceId(deviceId);
	}
}
