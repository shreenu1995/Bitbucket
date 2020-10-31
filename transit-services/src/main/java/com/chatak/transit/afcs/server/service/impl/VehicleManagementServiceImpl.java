package com.chatak.transit.afcs.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.dao.VehicleManagementDao;
import com.chatak.transit.afcs.server.dao.model.VehicleManufacturer;
import com.chatak.transit.afcs.server.dao.model.VehicleModel;
import com.chatak.transit.afcs.server.dao.model.VehicleOnboarding;
import com.chatak.transit.afcs.server.dao.model.VehicleTypeProfile;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
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
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.VehicleManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class VehicleManagementServiceImpl implements VehicleManagementService {
	
	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	VehicleManagementDao vehicleManagementDao;

	@Autowired
	OrganizationManagementDao organizationManagementDao;
	
	@Autowired
	OrganizationMasterRepository organizationMasterRepository;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository; 
	
	@Override
	public WebResponse saveVehicleTypeRegistration(VehicleTypeRegistrationRequest request, WebResponse response) {
		VehicleTypeProfile vehicleType = new VehicleTypeProfile();
		vehicleType.setVehicleType(request.getVehicleType());
		vehicleType.setDescription(request.getDescription());
		vehicleType.setStatus(Status.ACTIVE.getValue());
		if (vehicleManagementDao.validateVehicleTypeRegistration(request) && vehicleManagementDao.saveVehicleTypeRegistration(vehicleType)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setReservedResponse("");
				return response;
		}
		
		validateVehicleTypeRegistrationErrors(request,response);
		response.setReservedResponse("");
		return response;
	}

	private void validateVehicleTypeRegistrationErrors(VehicleTypeRegistrationRequest request, WebResponse response) {
		if(dataValidation.validateVehicleTypeName(request.getVehicleType())) {
			response.setStatusCode(CustomErrorCodes.INVALID_VEHICLE_TYPE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_VEHICLE_TYPE.getCustomErrorMsg());
		}
		
	}

	@Override
	public VehicleTypeSearchResponse searchVehicleType(VehicleTypeSearchRequest request,
			VehicleTypeSearchResponse response) {
		response = vehicleManagementDao.searchVehicleType(request, response);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return null;
	}

	// Vehicle Manufacturer
	@Override
	public WebResponse saveVehicleManufacturer(VehicleManufacturerRegistrationRequest request, WebResponse response) {

		VehicleManufacturer vehicleManufacturer = new VehicleManufacturer();
		vehicleManufacturer.setVehicleTypeId(request.getVehicleTypeId());
		vehicleManufacturer.setVehicleManufacturerName(request.getVehicleManufacturerName());
		vehicleManufacturer.setDescription(request.getDescription());
		vehicleManufacturer.setStatus(Status.ACTIVE.getValue());
		if (vehicleManagementDao.validateVehicleManufacturerName(request) && vehicleManagementDao.saveVehicleManufacturer(vehicleManufacturer)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setReservedResponse("");
				return response;
		}
		validateVehicleManufRegistrationErrors(request, response);
		response.setReservedResponse("");
		return response;
	}


	private void validateVehicleManufRegistrationErrors(VehicleManufacturerRegistrationRequest request,
			WebResponse response) {
		if(dataValidation.validateVehicleManufacturerName(request.getVehicleManufacturerName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_VEHICLE_MANUFACTURER.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_VEHICLE_MANUFACTURER.getCustomErrorMsg());
		}
	}

	@Override
	public VehicleManufacturerSearchResponse searchVehicleManuf(VehicleManufacturerSearchRequest request,
			VehicleManufacturerSearchResponse response) {
		response = vehicleManagementDao.searchVehicleManuf(request, response);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return null;
	}

	// Vehicle Model

	@Override
	public WebResponse saveVehicleModelRegistration(VehicleModelRegistrationRequest request, WebResponse response) {
		if (vehicleManagementDao.validateVehicleModelName(request)) {
			VehicleModel vehicleModel = new VehicleModel();
			vehicleModel.setVehicleTypeId(request.getVehicleTypeId());
			vehicleModel.setVehicleManufacturerId(request.getVehicleManufacturerId());
			vehicleModel.setVehicleModelName(request.getVehicleModelName());
			vehicleModel.setVehicleChassisNumber(request.getVehicleChassisNumber());
			vehicleModel.setVehicleEngineNumber(request.getVehicleEngineNumber());
			vehicleModel.setVehicleRegistrationNumber(request.getVehicleRegistrationNumber());
			vehicleModel.setDescription(request.getDescription());
			vehicleModel.setStatus(Status.ACTIVE.getValue());
			if (vehicleManagementDao.saveVehicleModelRegistration(vehicleModel)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setReservedResponse("");
				return response;
			}
		}
		
		validateVehicleModelRegistrationErrors(request, response);
		response.setReservedResponse("");
		return response;
	}

	private void validateVehicleModelRegistrationErrors(VehicleModelRegistrationRequest request, WebResponse response) {
		if(dataValidation.validateVehicleModelName(request.getVehicleModelName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_VEHICLE_MODEL.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_VEHICLE_MODEL.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public VehicleModelSearchResponse searchVehicleModel(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response) {
		response = vehicleManagementDao.searchVehicleModal(request, response);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return null;
	}

	// Vehicle Onboarding
	@Override
	public WebResponse saveVehicleOnBoarding(VehicleOnboardingRequest request, WebResponse response) {
		VehicleOnboarding vehicleOnboarding = new VehicleOnboarding();
		vehicleOnboarding.setVehicleManufacturerId(request.getVehicleManufacturerId());
		vehicleOnboarding.setVehicleModelId(request.getVehicleModelId());
		vehicleOnboarding.setVehicleTypeId(request.getVehicleTypeId());
		vehicleOnboarding.setStatus(Status.ACTIVE.getValue());
		vehicleOnboarding.setOrganizationId(request.getOrgId());
		vehicleOnboarding.setPtoId(request.getPtoId());
		if (vehicleManagementDao.saveVehicleOnBoarding(vehicleOnboarding)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setReservedResponse("");
			return response;
		}
		response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		response.setReservedResponse("");
		return response;
	}

	@Override
	public VehicleOnboardingSearchResponse searchVehicleOnboarding(VehicleOnboardingSearchRequest request,
			VehicleOnboardingSearchResponse response) {
		response = vehicleManagementDao.searchVehicleOnboarding(request, response);
		if (response != null) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return null;
	}

	// Find AllVehicleType

	@Override
	public List<VehicleTypeSearchRequest> getVehicleType() {
		return vehicleManagementDao.getVehicleTypeList();
	}

	// Find AllVehicleManufacturer
	@Override
	public List<VehicleManufacturerResponse> getVehicleManufacturerName() {
		return vehicleManagementDao.getManufacturerName();
	}

	// Find All Vehicle Model Name

	@Override
	public List<VehicleModelResponse> getModelName() {
		return vehicleManagementDao.getModelName();
	}

	@Override
	public WebResponse updateProfileVehicleType(VehicleTypeStatusUpdate vehicleTypeStatusUpdate, WebResponse response) {
		if (vehicleManagementDao.validateVehicleTypeProfile(vehicleTypeStatusUpdate)) {
			if (vehicleManagementDao.updateVehicleTypeProfile(vehicleTypeStatusUpdate)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public WebResponse updatStatusVehicleType(VehicleTypeStatusUpdate vehicleTypeStatusUpdate, WebResponse response) {
		if (vehicleManagementDao.validateVehicleTypeProfile(vehicleTypeStatusUpdate)) {
			if (vehicleManagementDao.updateVehicleTypeStatus(vehicleTypeStatusUpdate)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public WebResponse updateStatusVehicleManufacturer(VehicleManufacturerStatusUpdate manufacturerStatusUpdate,
			WebResponse response) {
		if (vehicleManagementDao.validateVehicleManufacturerStatus(manufacturerStatusUpdate)) {
			if (vehicleManagementDao.updateVehicleManufacturerStatus(manufacturerStatusUpdate)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public VehicleModelListResponse searchVehicleManufacturerListByVehicleType(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response) {
		VehicleModelListResponse vehicleModelListResponse = vehicleManagementDao.getVehicleManufacturerList(request);
		if (!StringUtil.isNull(vehicleModelListResponse)) {
			vehicleModelListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			vehicleModelListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			vehicleModelListResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			vehicleModelListResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return vehicleModelListResponse;
	}

	@Override
	public VehicleModelListResponse searchVehicleModelByManufacturerName(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response) {
		VehicleModelListResponse vehicleModelListResponse = vehicleManagementDao.getVehicleModelList(request);
		if (!StringUtil.isNull(vehicleModelListResponse)) {
			vehicleModelListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			vehicleModelListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			vehicleModelListResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			vehicleModelListResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return vehicleModelListResponse;
	}
	
	@Override
	public WebResponse updateVehicleModelProfile(VehicleModelProfileUpdate vehicleModelProfileUpdate,
			WebResponse response) {
		if (vehicleManagementDao.validateVehicleModelProfile(vehicleModelProfileUpdate)) {
			if (vehicleManagementDao.updateVehicleModelProfile(vehicleModelProfileUpdate)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public WebResponse updateVehicleMunufacturer(VehicleManufacturerProfileUpdate request, WebResponse response) {
		if (vehicleManagementDao.validateVehicleManufacturer(request)) {
			if (vehicleManagementDao.updateVehicleManufacturer(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public VehicleModelListResponse searchVehicleModelList(VehicleModelSearchRequest request) {
		VehicleModelListResponse response = vehicleManagementDao.getVehicleModelList(request);
		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public VehicleOnboardingResponse updateVehicleOnboardStatus(VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate,
			VehicleOnboardingResponse response) {
		if (vehicleManagementDao.validateVehicleOnboardProfile(vehicleOnboardStatusUpdate)) {
			if (!StringUtil.isNull(vehicleOnboardStatusUpdate)) {
				VehicleOnboarding vehicleOnboarding=vehicleManagementDao.updateVehicleOnboardStatus(vehicleOnboardStatusUpdate);
				response.setVehicleManufacturerId(vehicleOnboarding.getVehicleManufacturerId());
				response.setVehicleOnboardingId(vehicleOnboarding.getVehicleOnboardingId());
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public WebResponse updateVehicleOnboardProfile(VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate,
			WebResponse response) {
		if (vehicleManagementDao.validateVehicleOnboardProfile(vehicleOnboardProfileUpdate)) {
			if (vehicleManagementDao.updateVehicleOnboardProfile(vehicleOnboardProfileUpdate)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		}
		return response;
	}

	@Override
	public VehicleModelSearchResponse updatStatusVehicleModel(VehicleModelStatusUpdate vehicleModelStatusUpdate,
			WebResponse response) {
		VehicleModelSearchResponse modelSearchResponse = new VehicleModelSearchResponse();

		if (!StringUtil.isNull(vehicleModelStatusUpdate)) {
			VehicleModel vehicleModel = vehicleManagementDao.updateVehicleModelStatus(vehicleModelStatusUpdate);
			modelSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			modelSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			modelSearchResponse.setVehicleModelName(vehicleModel.getVehicleModelName());
			return modelSearchResponse;
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return modelSearchResponse;
		}
	}

	@Override
	public boolean engineNumberExistsCheck(VehicleModelRegistrationRequest request) {
		return vehicleManagementDao.validateEngineNumberInfo(request);

	}

	@Override
	public boolean chassisNumberExistsCheck(VehicleModelRegistrationRequest request) {
		return vehicleManagementDao.validateChassisNumberInfo(request);
	}

}
