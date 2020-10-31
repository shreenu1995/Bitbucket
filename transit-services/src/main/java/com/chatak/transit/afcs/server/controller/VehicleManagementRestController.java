package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelList;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeList;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.VehicleManagementService;

@RestController
@RequestMapping("/online/")
public class VehicleManagementRestController extends ServiceHelper {

	@Autowired
	VehicleManagementService vehicleManagementService;

	@PostMapping("saveVehicleType")
	public WebResponse saveVehicleTypeRegistration(@RequestBody VehicleTypeRegistrationRequest request,
			BindingResult bindingResult, WebResponse response) {

		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return vehicleManagementService.saveVehicleTypeRegistration(request, response);
	}

	@PostMapping(value = "searchVehicleType")
	public VehicleTypeSearchResponse searchVehicleType(@RequestBody VehicleTypeSearchRequest request,
			BindingResult bindingResult, VehicleTypeSearchResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
		}
		return vehicleManagementService.searchVehicleType(request, response);
	}

	@PostMapping(value = "vehicleTypeProfileUpdate")
	public WebResponse vehicleTypeProfileUpdate(@RequestBody VehicleTypeStatusUpdate vehicleTypeStatusUpdate,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		
		return vehicleManagementService.updateProfileVehicleType(vehicleTypeStatusUpdate, response);
	}
	
   @PostMapping(value = "vehicleTypeStatusUpdate")	
   public WebResponse updateVehicleTypestatus(@RequestBody VehicleTypeStatusUpdate vehicleTypeStatusUpdate,
		   BindingResult bindingResult, WebResponse response) {
	   
	   if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
	   
	   return vehicleManagementService.updatStatusVehicleType(vehicleTypeStatusUpdate, response);
   }
   
  
  @PostMapping(value = "vehicleManufactureStatusUpdate")
  public WebResponse updateVehicleManufacturerStatus(@RequestBody VehicleManufacturerStatusUpdate vehicleManufacturerStatusUpdate,
		   BindingResult bindingResult, WebResponse response) {
	   
	   if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
	   
	   return vehicleManagementService.updateStatusVehicleManufacturer(vehicleManufacturerStatusUpdate, response);
  }
  
	// Vehicle Manufacturer

	@PostMapping(value = "saveVehicleManufacturer")
	public WebResponse saveVehicleManufacturerRegistration(
			@RequestBody @Valid VehicleManufacturerRegistrationRequest request, BindingResult bindingResult,
			WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return vehicleManagementService.saveVehicleManufacturer(request, response);
	}

	@PostMapping(value = "searchVehicleManufacturer")
	public VehicleManufacturerSearchResponse searchVehicleManuf(@RequestBody VehicleManufacturerSearchRequest request,
			BindingResult bindingResult, VehicleManufacturerSearchResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
		}
		return vehicleManagementService.searchVehicleManuf(request, response);
	}

	// Vehicle Model

	@PostMapping(value = "saveVehicleModel")
	public WebResponse saveVehicleModelRegistration(@RequestBody @Valid VehicleModelRegistrationRequest request,
			BindingResult bindingResult, WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return vehicleManagementService.saveVehicleModelRegistration(request, response);
	}

	@PostMapping(value = "searchVehicleModel")
	public VehicleModelSearchResponse searchVehicleModel(@RequestBody VehicleModelSearchRequest request,
			BindingResult bindingResult, VehicleModelSearchResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
		}
		return vehicleManagementService.searchVehicleModel(request, response);
	}

	@PostMapping(value = "vehicleModelProfileUpdate")
	public WebResponse vehicleModelProfileUpdate(@RequestBody VehicleModelProfileUpdate vehicleModelProfileUpdate,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		
		return vehicleManagementService.updateVehicleModelProfile(vehicleModelProfileUpdate, response);
	}
	
	@PostMapping(value = "vehicleModelStatusUpdate")
	   public WebResponse updateVehicleModelStatus(@RequestBody VehicleModelStatusUpdate vehicleModelStatusUpdate,
			   BindingResult bindingResult, WebResponse response) {
		   
		   if (bindingResult.hasErrors()) {
				response = bindingResultErrorDetails(bindingResult);
				response.setStatusCode(response.getStatusCode());
				response.setStatusMessage(response.getStatusMessage());
				return response;
			}
		   
		   return vehicleManagementService.updatStatusVehicleModel(vehicleModelStatusUpdate, response);
	   }
	
	@PostMapping(value = "vehicleManufacturerListByVehicleType")
	public VehicleModelListResponse searchVehicleManufacturerListByVehicleType(@RequestBody VehicleModelSearchRequest request,
			BindingResult bindingResult, VehicleModelSearchResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
		}
		return vehicleManagementService.searchVehicleManufacturerListByVehicleType(request, response);
	}
	
	@PostMapping(value = "getVehicleModelByManufacturerName")
	public VehicleModelListResponse searchVehicleModelByManufacturerName(@RequestBody VehicleModelSearchRequest request,
			BindingResult bindingResult, VehicleModelSearchResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
		}
		return vehicleManagementService.searchVehicleModelByManufacturerName(request, response);
	}
	
	
	@PostMapping(value = "saveVehicleOnBoarding")
	public WebResponse saveVehicleOnBoarding(@RequestBody @Valid VehicleOnboardingRequest request,
			BindingResult bindingResult, WebResponse response) {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return vehicleManagementService.saveVehicleOnBoarding(request, response);
	}

	@PostMapping(value = "vehicleModelList")
	public VehicleModelListResponse vehicleModelList(@RequestBody VehicleModelSearchRequest request) {
		return vehicleManagementService.searchVehicleModelList(request);
	}
	
	@PostMapping(value = "searchVehicleOnboarding")
	public VehicleOnboardingSearchResponse searchVehicleOnboarding(@RequestBody VehicleOnboardingSearchRequest request,
			BindingResult bindingResult, VehicleOnboardingSearchResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
		}
		return vehicleManagementService.searchVehicleOnboarding(request, response);
	}
	
	@PostMapping(value = "vehicleOnboardProfileUpdate")
	public WebResponse updateVehicleOnboardProfile(@RequestBody VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		
		return vehicleManagementService.updateVehicleOnboardProfile(vehicleOnboardProfileUpdate, response);
	}
	
	@PostMapping(value = "vehicleOnboardStatusUpdate")
	public VehicleOnboardingResponse updateVehicleOnboardStatus(@RequestBody VehicleOnboardStatusUpdate vehicleOnboardStatusUpdate,
			BindingResult bindingResult, VehicleOnboardingResponse response) {
		if (bindingResult.hasErrors()) {
			response = (VehicleOnboardingResponse) bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return vehicleManagementService.updateVehicleOnboardStatus(vehicleOnboardStatusUpdate, response);
	}

	@PostMapping(value = "getVehicleType")
	public VehicleTypeList getAllVehicleType() {
		VehicleTypeList vehicleList = new VehicleTypeList();
		vehicleList.setListOfVehicleType(vehicleManagementService.getVehicleType());
		return vehicleList;
	}

	// Find All Vehicle Manufacturer Name

	@PostMapping(value = "getVehicleManufName")
	public VehicleManufacturerListResponse getAllManufacturerName() {

		VehicleManufacturerListResponse vehicleManufList = new VehicleManufacturerListResponse();
		vehicleManufList.setListOfManufacturer(vehicleManagementService.getVehicleManufacturerName());
		return vehicleManufList;
	}

	// Find All Vehicle Model name

	@PostMapping(value = "getVehicleModel")
	public VehicleModelList getAllModelname() {

		VehicleModelList vehicleModel = new VehicleModelList();
		vehicleModel.setListOfVehicleModel(vehicleManagementService.getModelName());
		return vehicleModel;
	}
	
   @PostMapping(value = "vehicleManufacturerProfileUpdate")
   public WebResponse updateVehicleManufacturerProfile(@RequestBody VehicleManufacturerProfileUpdate request,
		   BindingResult bindingResult, WebResponse response) {
	   
	   if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
	   return vehicleManagementService.updateVehicleMunufacturer(request, response);
   }
   
   @PostMapping(value = "engineNumberExistsCheck", consumes = MediaType.APPLICATION_JSON_VALUE,
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public Response engineNumberExists(@RequestBody VehicleModelRegistrationRequest request) {
		boolean isEngineNumberExists = vehicleManagementService.engineNumberExistsCheck(request);
		Response response = new Response();
		if(isEngineNumberExists) {
			response.setResult(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setResult(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}
   
   @PostMapping(value = "chassisNumberExistsCheck", consumes = MediaType.APPLICATION_JSON_VALUE,
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public Response chassisNumberExistsCheck(@RequestBody VehicleModelRegistrationRequest request) {
		boolean isChassisNumberExists = vehicleManagementService.chassisNumberExistsCheck(request);
		Response response = new Response();
		if(isChassisNumberExists) {
			response.setResult(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setResult(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}
   
}