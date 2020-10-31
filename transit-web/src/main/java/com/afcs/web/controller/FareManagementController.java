package com.afcs.web.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.FareManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.RouteRegistrationManagementService;
import com.afcs.web.service.StopManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class FareManagementController {

	@Autowired
	FareManagementService fareManagementService;

	@Autowired
	Environment properties;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	RouteRegistrationManagementService routeRegistrationManagementService;
	
	@Autowired
	StopManagementService stopManagementService; 
	
	@Autowired
	PtoManagementService ptoManagementService; 

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(FareManagementController.class);
	private static final String FARE_REGISTRATION = "fare-registration";
	private static final String FARE_DATA_SIZE = "fareDataSize";
	private static final String FARE_SEARCH = "fare-search";
	private static final String FARE_SEARCH_PAGINATION = "fare-search-pagination";
	private static final String FARE_VIEW_ACTION = "view-fare-action";
	private static final String FARE_MANAGEMENT_EDIT_ACTION = "edit-Fare-action";
	private static final String FARE_MANAGEMENT_EDIT_REQ = "fareeditrequest";
	private static final String FARE_MANAGEMENT_UPDATE = "fare-edit";
	private static final String UPDATE_FARE_STATUS = "update-Fare-status";
	private static final String PROCESS_BULK_FARE_CREATION = "process-bulk-fare";
	private static final String SHOW_BULK_FARE_CREATION = "fare-bulk-upload";
	private static final String DOWNLOAD_BULK_FARE_TEMPLATE = "downloadbulkfaretemplate";
	private static final String FARE_SEARCH_REQUEST = "fareSearchRequest";
	private static final String FARE_NAME = "&&fareName";
	private static final String FARE_LIST = "fareList";
	private static final String DOWNLOAD_FARE_REPORT = "downloadFareReport";

	@GetMapping(value = FARE_REGISTRATION)
	public ModelAndView fareRegistrationGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(FARE_REGISTRATION);
		FareRegistrationRequest fareRegistrationRequest = new FareRegistrationRequest();
		model.put("fareRegistrationRequest", fareRegistrationRequest);
		model.put(Constants.PTO_LIST_DATA, session.getAttribute(Constants.PTO_LIST_DATA));
		return modelAndView;
	}

	@PostMapping(value = FARE_REGISTRATION)
	public ModelAndView fareRegistrationPost(Map<String, Object> model, FareRegistrationRequest fareRegistrationRequest,
			HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FARE_SEARCH);
		FareSearchRequest fareSearchRequest = new FareSearchRequest();
		model.put(FARE_SEARCH_REQUEST, fareSearchRequest);
		FareRegistrationResponse fareRegistrationResponse = null;
		try {
			fareRegistrationResponse = fareManagementService.fareRegistration(fareRegistrationRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(fareRegistrationResponse)
				&& fareRegistrationResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("fare.created.successfully"));
			model.put("fareRegistrationRequest", new FareRegistrationRequest());
		} else {
			model.put(Constants.ERROR, fareRegistrationResponse.getStatusMessage());
		}

		return modelAndView;
	}

	@GetMapping(value = FARE_SEARCH)
	public ModelAndView fareSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FARE_SEARCH);
		FareSearchRequest fareSearchRequest = new FareSearchRequest();
		fareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchRequest searchPtoRequest = new OrganizationSearchRequest();
		searchPtoRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		model.put(FARE_SEARCH_REQUEST, fareSearchRequest);
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		ptoListRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		PtoListResponse ptoListResponse = null;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
		}
		if (!StringUtil.isNull(ptoListResponse)
				&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put("ptoListData", ptoListResponse.getPtoList());
			session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		}

		try {
			fareSearchRequest.setPageSize(10);
			fareSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(FARE_LIST, fareSearchRequest);
			if (searchPtoRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(searchPtoRequest);
			} else if (searchPtoRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				searchPtoRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(searchPtoRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoOperationService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					searchPtoRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(searchPtoRequest);
				}
			}
		} catch (AFCSException e) {
			logger.error(
					"ERROR :: PtoOperationManagementController class :: showPtoOperationSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}

		return modelAndView;
	}

	@PostMapping(value = FARE_SEARCH)
	public ModelAndView fareSearchData(Map<String, Object> model, FareSearchRequest fareSearchRequest,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FARE_SEARCH);
		FareSearchResponse response = null;
		model.put(FARE_SEARCH_REQUEST, fareSearchRequest);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return fareSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		fareSearchRequest.setPageIndex(Constants.ONE);
		try {
			fareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (fareSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = fareManagementService.searchFare(fareSearchRequest);
			} else if (fareSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				fareSearchRequest.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = fareManagementService.searchFare(fareSearchRequest);
			} else {
				fareSearchRequest.setPtoId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				fareSearchRequest.setPageIndex(Constants.ONE);
				fareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = fareManagementService.searchFare(fareSearchRequest);
			}
			session.setAttribute(FARE_LIST, fareSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(FARE_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(FARE_SEARCH_REQUEST, fareSearchRequest);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(FARE_LIST, response.getFareList());
			model.put(FARE_SEARCH_REQUEST, fareSearchRequest);
			model.put(FARE_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, fareSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, FARE_DATA_SIZE);
		}

		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, FareSearchRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrgId())) {
			ptoListRequest.setOrgId(request.getOrgId());
			if (!StringUtil.isNullEmpty(session.getAttribute(Constants.USER_TYPE).toString())
					&& !session.getAttribute(Constants.USER_TYPE).toString()
							.equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
				PtoListResponse ptoListResponse = ptoManagementService
						.getPtoListByOrganizationIdAndUserId(ptoListRequest);
				if (!StringUtil.isNull(ptoListResponse)) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
			}
		}
	}

	@PostMapping(value = FARE_SEARCH_PAGINATION)
	public ModelAndView fareSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		FareSearchRequest fareSearchRequest = (FareSearchRequest) session.getAttribute(FARE_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(FARE_SEARCH);
		model.put(FARE_SEARCH_REQUEST, fareSearchRequest);
		FareSearchResponse response = new FareSearchResponse();
		fareSearchRequest.setPageIndex(pageNumber);
		try {
			response = fareManagementService.searchFare(fareSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(FARE_LIST, response.getFareList());
			model.put(FARE_SEARCH_REQUEST, fareSearchRequest);
			session.setAttribute(FARE_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, fareSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, FARE_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = FARE_VIEW_ACTION)
	public ModelAndView viewFareData(final HttpSession session, @RequestParam("fareId") String fareId,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView("fare-view");
		FareSearchResponse response = new FareSearchResponse();
		model.put("fareUpdateRequest", new FareUpdateRequest());
		FareSearchRequest fareSearchRequest = new FareSearchRequest();
		fareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			fareSearchRequest.setPageIndex(1);
			fareSearchRequest.setFareId(fareId);
			if (fareSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = fareManagementService.searchFare(fareSearchRequest);
			} else if (fareSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				fareSearchRequest.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = fareManagementService.searchFare(fareSearchRequest);
			} else {
				fareSearchRequest.setPtoId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				response = fareManagementService.searchFare(fareSearchRequest);
			}
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setOrgId(response.getFareList().get(0).getOrganizationId());
			PtoListResponse ptoListResponse = ptoOperationService.getActivePtoListByOrganizationId(ptoListRequest);
			model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_DATA, ptoListResponse.getPtoList());
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: FareManagementController:: viewFareData method :: Exception", e);
			return modelAndView;
		}
		FareUpdateRequest fareUpdateRequest = populateviewRequest(response);
		model.put("fareUpdateRequest", fareUpdateRequest);
		return modelAndView;
	}

	private FareUpdateRequest populateviewRequest(FareSearchResponse response) throws AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		FareUpdateRequest fareUpdateRequest = new FareUpdateRequest();
		FareSearchRequest fareSearchRequest = response.getFareList().get(0);
		fareUpdateRequest.setFareId(fareSearchRequest.getFareId());
		fareUpdateRequest.setPtoId(fareSearchRequest.getPtoId());
		fareUpdateRequest.setOrganizationId(fareSearchRequest.getOrganizationId());
		fareUpdateRequest.setFareName(fareSearchRequest.getFareName());
		fareUpdateRequest.setFareType(fareSearchRequest.getFareType());
		fareUpdateRequest.setDifference(fareSearchRequest.getDifference());
		fareUpdateRequest.setFareAmount(fareSearchRequest.getFareAmount());
		fareUpdateRequest.setStatus(fareSearchRequest.getStatus());
		if (!StringUtil.isNull(fareSearchRequest.getPtoId())) {
			ptoListRequest.setPtoMasterId(fareSearchRequest.getPtoId());
			PtoListResponse ptoList = ptoOperationService.getPtoDataByPtoId(ptoListRequest);
			try {
				if (!StringUtil.isNull(ptoList.getPtoList().get(0).getPtoMasterId())) {
					fareUpdateRequest.setPtoId(ptoList.getPtoList().get(0).getPtoMasterId());
				}
			} catch (IndexOutOfBoundsException e) {
				logger.error("DepotManagementController class :: populateDepotViewRequest method :: exception", e);
			}
		}

		return fareUpdateRequest;
	}

	@PostMapping(value = FARE_MANAGEMENT_EDIT_ACTION)
	public ModelAndView editFareManagementData(final HttpSession session, @RequestParam("fareId") String fareId,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FARE_MANAGEMENT_UPDATE);
		FareSearchResponse response = new FareSearchResponse();
		FareSearchRequest fareSearchRequest = new FareSearchRequest();
		fareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {

			fareSearchRequest.setPageIndex(Constants.ONE);
			fareSearchRequest.setFareId(fareId);
			if (fareSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = fareManagementService.searchFare(fareSearchRequest);
			} else if (fareSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				fareSearchRequest
						.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = fareManagementService.searchFare(fareSearchRequest);
			} else {

				fareSearchRequest.setPtoId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				response = fareManagementService.searchFare(fareSearchRequest);
			}
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setOrgId(response.getFareList().get(0).getOrganizationId());
			PtoListResponse ptoListResponse = ptoOperationService.getActivePtoListByOrganizationId(ptoListRequest);
			model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_DATA, ptoListResponse.getPtoList());
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: FareManagementController:: viewFareData method :: Exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			FareUpdateRequest fareUpdateRequest = populateviewRequest(response);
			model.put(FARE_MANAGEMENT_EDIT_REQ, fareUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = FARE_MANAGEMENT_UPDATE)
	public ModelAndView updateFareManagementData(HttpSession session, Map<String, Object> model,
			FareUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(FARE_MANAGEMENT_UPDATE);
		WebResponse response = null;
		model.put(FARE_MANAGEMENT_EDIT_REQ, request);
		try {
			response = fareManagementService.updateFareManagement(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("fare.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(FARE_MANAGEMENT_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_FARE_STATUS)
	public ModelAndView updateFareStatus(final HttpSession session, @RequestParam("fareId") String fareId,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model,
			FareUpdateRequest request) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FARE_SEARCH);
		FareSearchResponse response = null;
		model.put(FARE_MANAGEMENT_EDIT_REQ, request);
		try {
			request.setStatus(status);
			request.setFareId(fareId);
			request.setReason(reason);
			response = fareManagementService.updateFareManagementStatus(request);
		} catch (AFCSException e) {
			logger.error("ERROR:: FareManagementController :: updateFareManagementStatus method: Exception ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("fare.status.suspend.changed").replace(FARE_NAME,
						response.getFareName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("fare.status.active.changed").replace(FARE_NAME,
						response.getFareName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("fare.status.terminate.changed").replace(FARE_NAME,
						response.getFareName()));
			}

			return fareSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		} else {

			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(FARE_MANAGEMENT_EDIT_REQ, request);
		}

		return modelAndView;
	}

	@GetMapping(value = SHOW_BULK_FARE_CREATION)
	public ModelAndView showBulkUpload(HttpServletRequest request, BulkUploadRequest bulkUploadRequest,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(SHOW_BULK_FARE_CREATION);
		model.put("bulkUploadRequest", bulkUploadRequest);
		return modelAndView;
	}

	@PostMapping(value = PROCESS_BULK_FARE_CREATION)
	public ModelAndView processBulkUploadFare(HttpServletRequest request, BulkUploadRequest bulkUploadRequest,
			HttpServletResponse httpResponse, HttpSession session, Map<String, Object> model,
			@RequestParam("dataFile") MultipartFile file) throws IOException, AFCSException {
		ModelAndView modelAndView = new ModelAndView(SHOW_BULK_FARE_CREATION);
		List<BulkUploadRequest> fareList = getBulkFareList(file.getBytes());
		WebResponse response = null;
		StopSearchResponse stopSearchResponse = null;
		RouteSearchResponse routeSearchResponse = null;
		
		
		for (int i = 0, size = fareList.size(); i < size; i++) {

			if (!StringUtil.isNull(fareList.get(i).getBulkErrorCause())) {
				model.put(Constants.ERROR, properties.getProperty("fare.bulk.upload.failure.alphanumerics"));
				return modelAndView;
			}
			
			if (!StringUtil.isNull(fareList.get(i).getRouteCode())) {
				
				response = ptoOperationService.validatePtoId(fareList.get(i).getPtoId());
				if (!response.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
					model.put(Constants.ERROR, response.getStatusMessage());
					return modelAndView;
				}
				
				bulkUploadRequest.setPtoId(fareList.get(i).getPtoId());
				bulkUploadRequest.setRouteCode(fareList.get(i).getRouteCode());
				routeSearchResponse = routeRegistrationManagementService.validateRouteCode(bulkUploadRequest);
				if (!routeSearchResponse.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
					model.put(Constants.ERROR, properties.getProperty("fare.bulk.upload.failure.pto.route.id"));
					return modelAndView;
				}
				
				stopSearchResponse = stopManagementService.validateStopCode(fareList.get(i).getStartStopCode());
				if (!stopSearchResponse.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
					model.put(Constants.ERROR, stopSearchResponse.getStatusMessage());
					return modelAndView;
				}
				
				if (!stopSearchResponse.getPtoId().equalsIgnoreCase(fareList.get(i).getPtoId())) {
					model.put(Constants.ERROR, properties.getProperty("fare.bulk.upload.failure.pto.start.stop.id"));
					return modelAndView;
				}
				
				stopSearchResponse = stopManagementService.validateStopCode(fareList.get(i).getEndStopCode());
				if (!stopSearchResponse.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
					model.put(Constants.ERROR, stopSearchResponse.getStatusMessage());
					return modelAndView;
				}
				
				
				if (!stopSearchResponse.getPtoId().equalsIgnoreCase(fareList.get(i).getPtoId())) {
					model.put(Constants.ERROR, properties.getProperty("fare.bulk.upload.failure.pto.end.stop.id"));
					return modelAndView;
				}
			}
		}

		FareSearchResponse fareResponse = new FareSearchResponse();
		try {
			fareResponse = fareManagementService.bulkFareCreate(fareList);
		} catch (AFCSException e) {
			logger.error("ERROR:: FareManagementController :: processBulkUploadFare method: Exception ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(fareResponse) && fareResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("fare.bulk.upload.success"));
		} else {
			model.put(Constants.ERROR, properties.getProperty("fare.bulk.upload.failure"));
			model.put("bulkUploadRequest", bulkUploadRequest);
		}

		return modelAndView;
	}

	@PostMapping(value = DOWNLOAD_BULK_FARE_TEMPLATE)
	public void downloadFareBulkUploadTemplate(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fileName = "BULK_FARE_UPLOAD_TEMPLATE.csv";
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		StringBuilder builder = new StringBuilder();
		builder.append("Pto Id");
		builder.append(',');
		builder.append("Route Code");
		builder.append(',');
		builder.append("Starting Stop Code");
		builder.append(',');
		builder.append("Ending Stop Code");
		builder.append(',');
		builder.append("Fare Amount");
		builder.append('\n');
		response.getWriter().print(builder);
		response.flushBuffer();
	}

	public static List<BulkUploadRequest> getBulkFareList(byte[] fareFile) {
		List<BulkUploadRequest> fareList = new ArrayList<>();
		try {
			String fileData = new String(fareFile, StandardCharsets.UTF_8);
			String[] lines = fileData.split("\n");
			int length = lines.length;
			String[] columns = null;
			for (int i = 0; i < length; i++) {
				columns = lines[i].split(",");
				
				// Validate the file header
				if ((i == 0) && !(columns[Constants.ZERO].equalsIgnoreCase("Pto Id")
						&& columns[Constants.ONE].equalsIgnoreCase("Route Code")
						&& columns[Constants.TWO].equalsIgnoreCase("Starting Stop Code")
						&& columns[Constants.THREE].equalsIgnoreCase("Ending Stop Code")
						&& columns[Constants.FOUR].equalsIgnoreCase("Fare Amount"))) {
					logger.info("Validate the file header");
				} else if (i == 0) {
					continue;
				}

				// empty line
				if (columns.length == 0) {
					continue;
				}

				StringBuilder errorMessage = new StringBuilder();
				// Remove carriage return("\r") if present at the end of the
				// each row
				if (columns[columns.length - 1].contains("\r")) {
					columns[columns.length - 1] = columns[columns.length - 1].replaceAll("\r", "");
				}

				// Getting the Columns Values from Row
				BulkUploadRequest bulkUploadRequest = new BulkUploadRequest();

				setPtoId(columns, errorMessage, bulkUploadRequest);

				setRouteCode(columns, errorMessage, bulkUploadRequest);

				setStartStopCode(columns, errorMessage, bulkUploadRequest);

				setEndStopCode(columns, errorMessage, bulkUploadRequest);

				setFareAmount(columns, errorMessage, bulkUploadRequest);

				fareList.add(bulkUploadRequest);
			}

		} catch (Exception e) {
			logger.error("ERROR:: FareManagementController :: getBulkFareList method: Exception ", e);
		}

		return fareList;
	}

	private static void setFareAmount(String[] columns, StringBuilder errorMessage,
			BulkUploadRequest bulkUploadRequest) {
		String fareAmount = StringUtil.isNullEmpty(columns[Constants.FOUR]) ? "" : columns[Constants.FOUR];
		if (StringUtil.isNullEmpty(fareAmount) || !isValidAlfaNumeric(fareAmount) || !isValidNumeric(fareAmount)) {
			errorMessage.append("Please check Fare Amount Field");
			bulkUploadRequest.setBulkErrorCause(errorMessage.toString());
		} else {
			bulkUploadRequest.setFareAmount(fareAmount);
		}
	}

	private static void setEndStopCode(String[] columns, StringBuilder errorMessage,
			BulkUploadRequest bulkUploadRequest) {
		String endStopCode = StringUtil.isNullEmpty(columns[Constants.THREE]) ? "" : columns[Constants.THREE];
		if (StringUtil.isNullEmpty(endStopCode) || !isValidAlfaNumeric(endStopCode) || !isValidNumeric(endStopCode)) {
			errorMessage.append("Please check End Stop  Code Field");
			bulkUploadRequest.setBulkErrorCause(errorMessage.toString());
		} else {
			bulkUploadRequest.setEndStopCode(endStopCode);
		}
	}

	private static void setStartStopCode(String[] columns, StringBuilder errorMessage,
			BulkUploadRequest bulkUploadRequest) {
		String startStopCode = StringUtil.isNullEmpty(columns[Constants.TWO]) ? "" : columns[Constants.TWO];
		if (StringUtil.isNullEmpty(startStopCode) || !isValidAlfaNumeric(startStopCode) || !isValidNumeric(startStopCode)) {
			errorMessage.append("Please check Start Stop Code Field");
			bulkUploadRequest.setBulkErrorCause(errorMessage.toString());
		} else {
			bulkUploadRequest.setStartStopCode(startStopCode);
		}
	}

	private static void setRouteCode(String[] columns, StringBuilder errorMessage,
			BulkUploadRequest bulkUploadRequest) {
		String routeCode = StringUtil.isNullEmpty(columns[Constants.ONE]) ? "" : columns[Constants.ONE];
		if (StringUtil.isNullEmpty(routeCode) || !isValidAlfaNumeric(routeCode)) {
			errorMessage.append("Please check Route Code Field");
			bulkUploadRequest.setBulkErrorCause(errorMessage.toString());
		} else {
			bulkUploadRequest.setRouteCode(routeCode);
		}
	}

	private static void setPtoId(String[] columns, StringBuilder errorMessage, BulkUploadRequest bulkUploadRequest) {
		String ptoId = StringUtil.isNullEmpty(columns[Constants.ZERO]) ? "" : columns[Constants.ZERO];
		if (StringUtil.isNullEmpty(ptoId) || !isValidAlfaNumeric(ptoId)) {
			errorMessage.append("Please check Pto Id Field");
			bulkUploadRequest.setBulkErrorCause(errorMessage.toString());
		} else {
			bulkUploadRequest.setPtoId(ptoId);
		}
	}

	private static boolean isValidAlfaNumeric(String data) {
		String pattern = "^[a-zA-Z0-9-\\\\_\\\\s ]*$";
		return data.matches(pattern);
	}
	
	private static boolean isValidNumeric(String data) {
		String pattern = "[0-9]+";
		return data.matches(pattern);
	}

	@PostMapping(value = DOWNLOAD_FARE_REPORT)
	public ModelAndView downloadFareManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: FareManagementController:: downloadFareManagementReport method");
		ModelAndView modelAndView = new ModelAndView(FARE_SEARCH);
		FareSearchResponse fareSearchResponse = null;
		try {
			FareSearchRequest fareSearchRequest = (FareSearchRequest) session.getAttribute(FARE_LIST);
			fareSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = fareSearchRequest.getPageSize();
			fareSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				fareSearchRequest.setPageIndex(Constants.ONE);
				fareSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			fareSearchResponse = fareManagementService.searchFare(fareSearchRequest);
			List<FareSearchRequest> reuestResponses = fareSearchResponse.getFareList();
			if (!StringUtil.isNull(fareSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadFareManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			fareSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::FareManagementController:: FareManagementReport method", e);
		}
		logger.info("Exit:: FareManagementController:: downloadFareManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadFareManagementReport(List<FareSearchRequest> fareRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("FareManagement_");
		exportDetails.setHeaderMessageProperty("chatak.header.fare.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(fareRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("FareManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FareManagement.file.exportutil.pto", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("FareManagement.file.exportutil.fareName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FareManagement.file.exportutil.fareType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FareManagement.file.exportutil.difference", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FareManagement.file.exportutil.fareAmount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FareManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<FareSearchRequest> fareSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (FareSearchRequest fareData : fareSearchRequest) {

			Object[] rowData = { fareData.getOrganizationId(), fareData.getPtoId(), fareData.getFareName(),
					fareData.getFareType(),

					fareData.getDifference(), fareData.getFareAmount(), fareData.getStatus() };

			fileData.add(rowData);
		}

		return fileData;
	}

}
