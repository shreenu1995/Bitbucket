package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.PtoManagementService;

@RestController
@RequestMapping("/online/")
public class PtoManagementRestController extends ServiceHelper {

	Logger logger = LoggerFactory.getLogger(PtoManagementRestController.class);

	@Autowired
	PtoManagementService ptoManagementService;

	@PostMapping(value = "ptoRegistration")
	public PtoRegistrationResponse savePtoRegistration(@RequestBody PtoRegistrationRequest ptoOperationRegRequest,
			BindingResult bindingResult, HttpServletResponse httpResponse, HttpSession session,
			PtoRegistrationResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.savePtoRegistration(ptoOperationRegRequest, httpResponse, session, response);
	}

	@PostMapping(value = "getPtoList")
	public PtoListResponse getPtoList(@Valid @RequestBody PtoListRequest ptoListRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, PtoListResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getPtoList(ptoListRequest, httpResponse, response);
	}

	@PostMapping(value = "getActivePtoList")
	public PtoListResponse getActivePtoList(@Valid @RequestBody PtoListRequest ptoListRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, PtoListResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getActivePtoList(ptoListRequest, httpResponse, response);
	}
	
	@PostMapping(value = "getPtoByOrganizationIdAndUserId")
	public PtoListResponse getPtoByOrganizationIdAndUserId(@Valid @RequestBody PtoListRequest ptoListRequest,
			BindingResult bindingResult, HttpServletResponse httpResponse, PtoListResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getPtoByOrganizationId(ptoListRequest, httpResponse, response);
	}

	@PostMapping(value = "updatePtoStatus")
	public WebResponse updatePtoStatus(@Valid @RequestBody PtoStatusUpdateRequest request, BindingResult bindingResult,
			WebResponse response, HttpServletResponse httpResponse) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return ptoManagementService.updatePtoStatus(request, response);
	}

	@PostMapping(value = "updatePtoMaster")
	public WebResponse updatePtoMaster(@Valid @RequestBody PtoUpdateRequest request, BindingResult bindingResult,
			HttpServletResponse httpResponse, WebResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			return bindingResultErrorDetails(bindingResult);
		}
		return ptoManagementService.updatePtoMaster(request, response, httpResponse);

	}

	@PostMapping(value = "searchPto")
	public PtoSearchResponse searchPto(@RequestBody PtoSearchRequest request) {
			return ptoManagementService.searchPto(request);
	}

	@PostMapping(value = "getPtoListWithStatusNotTerminated")
	public PtoListResponse getPtoListWithStatusNotTerminated(@Valid @RequestBody PtoListRequest ptoListRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, PtoListResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getPtoListWithStatusNotTerminated(ptoListRequest, httpResponse, response);
	}
	
	@PostMapping(value = "getPtoByPtoId")
	public PtoListResponse getPtoByPtoId(@Valid @RequestBody PtoListRequest ptoListRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, PtoListResponse response) throws IOException {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getPtoByPtoId(ptoListRequest, httpResponse, response);
	}
	
	@PostMapping(value = "getPtoListByOrganizationIdAndUserId")
	public PtoListResponse getPtoListByOrganizationIdAndUserId(@Valid @RequestBody PtoListRequest ptoListRequest,
			BindingResult bindingResult, HttpServletResponse httpResponse, PtoListResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getPtoListByOrganizationId(ptoListRequest, httpResponse, response);
	}
	
	@PostMapping(value = "getActivePtoListByOrganizationId")
	public PtoListResponse getActivePtoListByOrganizationId(@Valid @RequestBody PtoListRequest ptoListRequest,
			BindingResult bindingResult, HttpServletResponse httpResponse, PtoListResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest, httpResponse, response);
	}
	
	@PostMapping(value = "validatePtoId")
	public WebResponse validatePtoId(@RequestBody String ptoId) {
		return ptoManagementService.validatePtoId(ptoId);
	}
	
	@PostMapping(value = "getPtoDataByPtoId")
	public PtoListResponse getPtoDataByPtoId(@Valid @RequestBody PtoListRequest ptoListRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, PtoListResponse response) {
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return ptoManagementService.getPtoDataByPtoId(ptoListRequest, httpResponse, response);
	}
	
	@PostMapping(value = "getPtoDataByPtoMasterId")
	public PtoListResponse getPtoByPtoMasterId(@RequestBody Long ptoMasterId) {
		return ptoManagementService.getPtoByPtoMasterId(ptoMasterId);
	}
}
