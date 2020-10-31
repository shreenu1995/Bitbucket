package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface VehicleTypeManagementService {

	WebResponse saveVehicleTypeRegistration(VehicleTypeRegistrationRequest request) throws AFCSException;

	VehicleTypeSearchResponse vehicleTypeSearch(VehicleTypeSearchRequest request) throws AFCSException;
	
	WebResponse updateVehicleTypeProfile(VehicleTypeStatusUpdate request) throws AFCSException;
	
	WebResponse updateVehicleTypeStatus(VehicleTypeStatusUpdate request) throws AFCSException;
	
   	}
