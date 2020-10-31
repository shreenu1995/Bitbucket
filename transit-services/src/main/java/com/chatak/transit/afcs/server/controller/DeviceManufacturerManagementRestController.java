package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.model.OrderChecks;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceManufacturerManagementService;

@RestController
@RequestMapping(value = "/online/device/")
public class DeviceManufacturerManagementRestController extends ServiceHelper {

	@Autowired
	DeviceManufacturerManagementService deviceManufacturerManagementService;

	@PostMapping(value = "deviceManufacturerRegistration")
	public DeviceManufacturerRegistrationResponse deviceManufacturerRgistration(
			@RequestBody @Validated(value=OrderChecks.class) DeviceManufacturerRegistrationRequest request, BindingResult bindingResult,
			DeviceManufacturerRegistrationResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return deviceManufacturerManagementService.deviceManufacturerRegistration(request, response,
				httpResponse);
	}

	@PostMapping(value = "searchDeviceManufacturer")
	public DeviceManufacturerListSearchResponse searchDeviceManufacturer(
			@RequestBody DeviceManufacturerSearchRequest request){
		return  deviceManufacturerManagementService
				.searchDeviceManufacturer(request);
	}

	@PostMapping(value = "deviceManufacturerProfileUpdate")
	public WebResponse deviceManufacturerProfileUpdate(@RequestBody DeviceManufacturerProfileUpdateRequest request,
			WebResponse response, HttpServletResponse httpResponse) {
		return deviceManufacturerManagementService.deviceManufacturerProfileUpdate(request, response, httpResponse);
	}
	
	@PostMapping(value = "deviceTypeList")
	public DeviceTypeListViewResponse deviceTypeListView(@RequestBody DeviceTypeListViewRequest request) {
		return deviceManufacturerManagementService.deviceTypeList(request);
	}
	 
	@PostMapping(value = "deviceManufacturerList")
	public DeviceManufacturerListSearchResponse deviceManufacturerListView(@RequestBody DeviceManufacturerListViewRequest request) {
		return deviceManufacturerManagementService.deviceManufacturerList(request);
	}
	
	
	@PostMapping(value = "deviceManufacturerStatusUpdate")
	public WebResponse deviceManufacturerStatusUpdate(@RequestBody @Valid DeviceManufacturerStatusUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return deviceManufacturerManagementService.deviceManufacturerStatusuUpdate(request, response, httpResponse);
	}
}
