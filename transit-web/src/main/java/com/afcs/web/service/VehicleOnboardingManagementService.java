package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface VehicleOnboardingManagementService {

	WebResponse saveVehicleOnboardRegistration(VehicleOnboardingRequest request) throws AFCSException;

	VehicleOnboardingSearchResponse vehicleOnboardSearch(VehicleOnboardingSearchRequest request) throws AFCSException;

	VehicleModelListResponse getVehicleModelByManufacturerName(
			VehicleModelSearchRequest vehicleModelSearchRequest) throws AFCSException;

	WebResponse updateVehicleOnboard(VehicleOnboardProfileUpdate request) throws AFCSException;

	VehicleOnboardingResponse updateVehicleOnboardStatus(VehicleOnboardStatusUpdate request) throws AFCSException;

}
