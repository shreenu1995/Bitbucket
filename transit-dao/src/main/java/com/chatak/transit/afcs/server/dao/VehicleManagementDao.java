package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.VehicleManufacturer;
import com.chatak.transit.afcs.server.dao.model.VehicleModel;
import com.chatak.transit.afcs.server.dao.model.VehicleOnboarding;
import com.chatak.transit.afcs.server.dao.model.VehicleTypeProfile;
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
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;

public interface VehicleManagementDao {
	
	boolean validateVehicleTypeRegistration(VehicleTypeRegistrationRequest request);

	boolean saveVehicleTypeRegistration(VehicleTypeProfile request);

	VehicleTypeSearchResponse searchVehicleType(VehicleTypeSearchRequest request, VehicleTypeSearchResponse response);

	boolean saveVehicleManufacturer(VehicleManufacturer request);

	VehicleManufacturerSearchResponse searchVehicleManuf(VehicleManufacturerSearchRequest request,
			VehicleManufacturerSearchResponse response);

	boolean saveVehicleModelRegistration(VehicleModel request);

	VehicleModelSearchResponse searchVehicleModal(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response);

	boolean saveVehicleOnBoarding(VehicleOnboarding request);

	VehicleOnboardingSearchResponse searchVehicleOnboarding(VehicleOnboardingSearchRequest request,
			VehicleOnboardingSearchResponse response);
	
	boolean validateVehicleTypeProfile(VehicleTypeStatusUpdate request);
	
	boolean updateVehicleTypeProfile(VehicleTypeStatusUpdate request);
	
	boolean validateVehicleTypeStatus(VehicleTypeStatusUpdate request);
	
	boolean updateVehicleTypeStatus(VehicleTypeStatusUpdate request);
	
	boolean validateVehicleManufacturerStatus(VehicleManufacturerStatusUpdate request);
	
	boolean updateVehicleManufacturerStatus(VehicleManufacturerStatusUpdate request);
	
	List<VehicleTypeSearchRequest> getVehicleTypeList();

	List<VehicleManufacturerResponse> getManufacturerName();

	List<VehicleModelResponse> getModelName();

	VehicleModelListResponse getVehicleManufacturerList(VehicleModelSearchRequest request);

	boolean validateVehicleModelProfile(VehicleModelProfileUpdate vehicleModelProfileUpdate);

	boolean updateVehicleModelProfile(VehicleModelProfileUpdate vehicleModelProfileUpdate);

	boolean validateVehicleModelProfile(VehicleModelStatusUpdate vehicleModelStatusUpdate);

	VehicleModel updateVehicleModelStatus(VehicleModelStatusUpdate vehicleModelStatusUpdate);

	boolean validateVehicleManufacturer(VehicleManufacturerProfileUpdate request);

	boolean updateVehicleManufacturer(VehicleManufacturerProfileUpdate request);

	VehicleModelListResponse getVehicleModelList(VehicleModelSearchRequest request);

	VehicleOnboarding updateVehicleOnboardStatus(VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate);

	boolean validateVehicleOnboardProfile(VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate);

	boolean updateVehicleOnboardProfile(VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate);

	boolean validateVehicleOnboardProfile(VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate);
	
	boolean validateVehicleManufacturerName(VehicleManufacturerRegistrationRequest request);
	
	boolean validateVehicleModelName(VehicleModelRegistrationRequest request);

	boolean validateEngineNumberInfo(VehicleModelRegistrationRequest request);

	boolean validateChassisNumberInfo(VehicleModelRegistrationRequest request);
	
	VehicleTypeProfile getVehicleTypeId(String vehicleType);
	
	VehicleTypeProfile getVehicleTypeName(int vehicleType);
	
	VehicleManufacturer getVehicleMaunfacturerId(String vehicleManufacturer);	

}
