package com.chatak.transit.afcs.server.service;

import java.util.List;

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

public interface VehicleManagementService {

	WebResponse saveVehicleTypeRegistration(VehicleTypeRegistrationRequest request, WebResponse response);

	VehicleTypeSearchResponse searchVehicleType(VehicleTypeSearchRequest request, VehicleTypeSearchResponse response);

	WebResponse saveVehicleManufacturer(VehicleManufacturerRegistrationRequest request, WebResponse response);

	VehicleManufacturerSearchResponse searchVehicleManuf(VehicleManufacturerSearchRequest request,
			VehicleManufacturerSearchResponse response);

	WebResponse saveVehicleModelRegistration(VehicleModelRegistrationRequest request, WebResponse response);

	VehicleModelSearchResponse searchVehicleModel(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response);

	WebResponse saveVehicleOnBoarding(VehicleOnboardingRequest request, WebResponse response);

	VehicleOnboardingSearchResponse searchVehicleOnboarding(VehicleOnboardingSearchRequest request,
			VehicleOnboardingSearchResponse response);
	
	WebResponse updateProfileVehicleType(VehicleTypeStatusUpdate vehicleTypeStatusUpdate, WebResponse response);
	
	WebResponse updatStatusVehicleType(VehicleTypeStatusUpdate vehicleTypeStatusUpdate, WebResponse response);
	
	WebResponse updateStatusVehicleManufacturer(VehicleManufacturerStatusUpdate manufacturerStatusUpdate, WebResponse response);
	
	List<VehicleTypeSearchRequest> getVehicleType();

	List<VehicleManufacturerResponse> getVehicleManufacturerName();

	List<VehicleModelResponse> getModelName();

	VehicleModelListResponse searchVehicleManufacturerListByVehicleType(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response);

	WebResponse updateVehicleModelProfile(VehicleModelProfileUpdate vehicleModelProfileUpdate, WebResponse response);

	VehicleModelSearchResponse updatStatusVehicleModel(VehicleModelStatusUpdate vehicleModelStatusUpdate, WebResponse response);

	WebResponse updateVehicleMunufacturer(VehicleManufacturerProfileUpdate request, WebResponse response);

	VehicleModelListResponse searchVehicleModelList(VehicleModelSearchRequest request);

	VehicleOnboardingResponse updateVehicleOnboardStatus(VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate, VehicleOnboardingResponse response);

	WebResponse updateVehicleOnboardProfile(VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate,
			WebResponse response);

	VehicleModelListResponse searchVehicleModelByManufacturerName(VehicleModelSearchRequest request,
			VehicleModelSearchResponse response);

	boolean engineNumberExistsCheck(VehicleModelRegistrationRequest request);

	boolean chassisNumberExistsCheck(VehicleModelRegistrationRequest request);

}
