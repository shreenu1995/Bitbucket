
package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

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
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.DeviceTypeManagementService;

@RestController
@RequestMapping(value = "/online/device/")
public class DeviceTypeManagementRestController extends ServiceHelper {

	@Autowired
	DeviceTypeManagementService deviceTypeManagementService;

	@PostMapping(value = "deviceTypeRegistration")
	public DeviceTypeRegistrationResponse deviceTypeRegistration(
			@RequestBody @Valid DeviceTypeRegistrationRequest request, BindingResult bindingResult,
			DeviceTypeRegistrationResponse response, HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return deviceTypeManagementService.deviceTypeRegistration(request, response, httpResponse);
	}

	@PostMapping(value = "deviceTypeSearch")
	public DeviceTypeSearchResponse deviceTypeSearch(@RequestBody DeviceTypeSearchRequest request) {
		return deviceTypeManagementService.deviceTypeSearch(request);
	}

	@PostMapping(value = "deviceTypeProfileUpdate")
	public WebResponse deviceTypeProfileUpdate(@RequestBody @Valid DeviceTypeProfileUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return deviceTypeManagementService.deviceTypeProfileUpdate(request, response, httpResponse);
	}
	
	@PostMapping(value = "deviceTypeStatusUpdate")
	public WebResponse deviceTypeStatusuUpdate(@RequestBody @Valid DeviceTypeStatusUpdateRequest request,
			BindingResult bindingResult, WebResponse response, HttpServletResponse httpResponse) {
		if (bindingResult.hasErrors()) {
			response = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(response.getStatusCode());
			response.setStatusMessage(response.getStatusMessage());
			return response;
		}
		return deviceTypeManagementService.deviceTypeStatusuUpdate(request, response, httpResponse);
	}
	
	@PostMapping(value = "deviceTypeListView")
	public DeviceTypeListViewResponse deviceTypeListView(
			@RequestBody @Validated(value = OrderChecks.class) DeviceTypeListViewRequest request,
			BindingResult bindingResult, DeviceTypeListViewResponse response, HttpServletResponse httpResponse)
			throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return deviceTypeManagementService.deviceTypeListView(request, response, httpResponse);
	}
}
