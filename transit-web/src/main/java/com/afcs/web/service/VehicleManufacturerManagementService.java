package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelList;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeList;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface VehicleManufacturerManagementService {

	WebResponse saveVehicleManufacturerRegistration(VehicleManufacturerRegistrationRequest request)
			throws AFCSException;

	VehicleTypeList getVehicleType() throws AFCSException;

	VehicleManufacturerSearchResponse vehicleManufSearch(VehicleManufacturerSearchRequest request) throws AFCSException;

	VehicleManufacturerListResponse getVehicleManuf() throws AFCSException;
	
	WebResponse updateVehicleManufactureStatus(VehicleManufacturerStatusUpdate request) throws AFCSException;
	
	WebResponse updateVehicleManufactureProfile(VehicleManufacturerProfileUpdate vehicleManufacturerProfileUpdate) throws AFCSException;

	VehicleOnboardingSearchResponse getOrganizationList(
			VehicleOnboardingSearchRequest vehicleOnboardingSearchRequest) throws AFCSException;

	VehicleModelList getVehicleModel() throws AFCSException;

}
