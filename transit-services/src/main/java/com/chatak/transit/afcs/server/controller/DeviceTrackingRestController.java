package com.chatak.transit.afcs.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;
import com.chatak.transit.afcs.server.service.DeviceTrackMgmtService;

@RestController
@RequestMapping("/deviceTracking/")
public class DeviceTrackingRestController {

	@Autowired
	DeviceTrackMgmtService deviceTrackMgmtService;
	
	@PostMapping(value = "searchDeviceTracking")
	public DeviceTrackingResponse searchDeviceTracking(@RequestBody DeviceTrackingRequest request) {
			return deviceTrackMgmtService.searchDeviceTracking(request);
	}
}
