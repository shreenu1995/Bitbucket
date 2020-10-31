package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface VehicleModelManagementService {

	WebResponse saveVehicleModelRegistration(VehicleModelRegistrationRequest request) throws AFCSException;

	VehicleManufacturerListResponse getVehicleManufacturer() throws AFCSException;

	VehicleModelSearchResponse vehicleModelSearch(VehicleModelSearchRequest request) throws AFCSException;

	VehicleModelListResponse getVehicleModelForManufacturerListForVehicleType(
			VehicleManufacturerSearchRequest vehicleManufacturerSearchRequest) throws AFCSException;

	WebResponse updateVehicleModel(VehicleModelProfileUpdate request) throws AFCSException;

	VehicleModelSearchResponse updateVehicleModelStatus(VehicleModelStatusUpdate request) throws AFCSException;

	String engineNumberExistsCheck(String name);

	String chassisNumberExistsCheck(String name);
}
