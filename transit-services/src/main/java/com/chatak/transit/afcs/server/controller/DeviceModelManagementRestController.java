package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceModelMangementService;

@RestController
@RequestMapping(value = "/online/device/")
public class DeviceModelManagementRestController extends ServiceHelper {

	@Autowired
	DeviceModelMangementService deviceService;


	@PostMapping(value = "deviceModel")
	public DeviceModelResponse deviceModel(@RequestBody DeviceModelRequest request,
			BindingResult bindingResult, DeviceModelResponse response, HttpServletResponse httpResponse)
			throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return deviceService.saveDeviceModel(request, response, httpResponse);
	}

	@PostMapping(value = "searchDeviceModel")
	public DeviceModelListSearchResponse searchDeviceModel(@RequestBody DeviceModelSearchRequest request,
			BindingResult bindingResult, DeviceModelListSearchResponse response, HttpServletResponse httpResponse) {
		
		return deviceService.searchDeviceModel(request, httpResponse);
	}
	
	@PostMapping(value = "deviceModelProfileUpdate")
	public WebResponse deviceModelProfileUpdate(@RequestBody DeviceModelProfileUpdateRequest request,
			BindingResult bindingResult, WebResponse webResponse, HttpServletResponse httpResponse) throws IOException {
		return deviceService.deviceModelProfileUpdate(request, webResponse, httpResponse);
	}

	@PostMapping(value = "deviceModelStatusUpdate")
	public WebResponse deviceModelStatusUpdate(@RequestBody @Valid DeviceModelStatusUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		return deviceService.updateDeviceModelStatus(request, response);
	}
	
	@PostMapping(value = "deviceModelList")
	public DeviceModelListResponse deviceModelList(@RequestBody DeviceModelSearchRequest request) {
		return deviceService.deviceModelList(request);
	}
}
