package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.DevcieTypeList;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelList;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceOnboardingService;

@RestController
@RequestMapping(value = "/online/device/")
public class DeviceOnboardingRestController {

	@Autowired
	DeviceOnboardingService deviceService;

	@PostMapping(value = "deviceOnboardingRegistration")
	public WebResponse saveDeviceOnboarding(@RequestBody DeviceOnboardingRequest request, WebResponse response) {
		return deviceService.saveDeviceOnboarding(request, response);
	}

	@PostMapping(value = "searchDeviceOnboard")
	public DeviceOnboardListSearchResponse searchDeviceOnboarding(
			@RequestBody DeviceOnboardSearchRequest request, BindingResult bindingResult,
			DeviceOnboardListSearchResponse response, HttpServletResponse httpResponse) {
		return deviceService.searchDeviceOnboarding(request, httpResponse);
	}

	@PostMapping(value = "deviceOnboardProfileUpdate")
	public WebResponse updatDeviceOnboardProfile(@RequestBody DeviceOnboardProfileUpdateRequest request,
			BindingResult bindingResult, WebResponse webResponse, HttpServletResponse httpResponse) {
		return deviceService.updatDeviceOnboardProfile(request, webResponse);
	}

	@PostMapping(value = "deviceOnboardStatusUpdate")
	public DeviceOnboardSearchResponse updateDeviceOnboardStatus(@RequestBody DeviceOnboardProfileUpdateRequest request, WebResponse response) {
		return deviceService.updateDeviceOnBoardStatus(request);
	}
	
	@PostMapping(value = "getDeviceTypeName")
	public DevcieTypeList getDeviceTypeName() {
		DevcieTypeList devcieTypeList = new DevcieTypeList();
		devcieTypeList.setListOfDeviceType(deviceService.getDeviceTypeName());
		return devcieTypeList;
	}
	
	@PostMapping(value = "getDeviceManuName")
	public DeviceManufacturerListResponse getDeviceManuName() {
		DeviceManufacturerListResponse devcieManuList = new DeviceManufacturerListResponse();
		devcieManuList.setListOfManufacturer(deviceService.getDeviceManuName());
		return devcieManuList;
	}
	
	@PostMapping(value = "getDeviceModelName")
	public DeviceModelList getDeviceModelName() {
		DeviceModelList deviceModelList = new DeviceModelList();
		deviceModelList.setListOfDeviceModel(deviceService.getDeviceModelName());
		return deviceModelList;
	}
}
