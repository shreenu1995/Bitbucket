package com.afcs.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.StopManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class StopManagementController {

	private static Logger logger = LoggerFactory.getLogger(StopManagementController.class);

	@Autowired
	StopManagementService stopManagementService;

	@Autowired
	PtoManagementService ptoOperationManagementService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	OrganizationManagementService organizationManagementService;
	
	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String STOP_REGISTRATION = "stop-registration";
	private static final String PTO_OPERATION_LIST = "ptoOperationList";
	private static final String STOP_REGISTRATION_REQUEST = "stopRegistrationRequest";
	private static final String STOP_SEARCH = "stop-search";
	private static final String STOP_SEARCH_PAGINATION = "stop-search-pagination";
	private static final String STOP_DATA_SIZE = "stopDataSize";
	private static final String STOP_SEARCH_REQUEST = "stopSearchRequest";
	private static final String STOP_VIEW = "stop-view";
	private static final String STOP_STATUS_UPDATE = "stop-status-update";
	private static final String STOP_UPDATE = "stop-update";
	private static final String STOP_EDIT_REQ = "stopEditRequest";
	private static final String STOP_EDIT_ACTION = "edit-stop-action";
	private static final String STOP_ID = "stopId";
	private static final String ROUTE_NAME_LIST = "routeNameList";
	private static final String STAGE_NAME_LIST = "stageNameList";
	private static final String STOP_NAME_LIST = "stopNameList";
	private static final String STOP_SEARCH_REQ= "stopsearchrequest";
	private static final String STOP_NAME = "&&stopName";
	private static final String STOP_LIST_DATA = "stopListData";
	private static final String DOWNLOAD_STOP_REPORT = "downloadStopReport";
	private static final String FETCH_STAGE_BY_ROUTE = "fetchStageByRoute";
	
	@GetMapping(value = STOP_REGISTRATION)
	public ModelAndView stopRegisterGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(STOP_REGISTRATION);
		StopRegistrationRequest stopRegistrationRequest = new StopRegistrationRequest();
		List<RouteSearchRequest> listRouteName = stopManagementService.getRouteName().getListOfRouteName();
		session.setAttribute(ROUTE_NAME_LIST, listRouteName);
		model.put(ROUTE_NAME_LIST, listRouteName);
		List<StageSearchRequest> listStageName = stopManagementService.getStageNameList().getListOfStageNames();
		session.setAttribute(STAGE_NAME_LIST, listStageName);
		model.put(STAGE_NAME_LIST, listStageName);
		model.put(STOP_REGISTRATION_REQUEST, stopRegistrationRequest);
		return modelandview;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = STOP_REGISTRATION)
	public ModelAndView stopRegisterPost(Map<String, Object> model, StopRegistrationRequest request,
			HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STOP_SEARCH);
		StopSearchRequest stopSearchRequest = new StopSearchRequest();
		model.put(STOP_SEARCH_REQUEST, stopSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		model.put(ROUTE_NAME_LIST, (List<String>) session.getAttribute(ROUTE_NAME_LIST));
		model.put(STAGE_NAME_LIST, (List<String>) session.getAttribute(STAGE_NAME_LIST));
		StopRegistrationResponse response = new StopRegistrationResponse();
		try {
				response = stopManagementService.stopRegistration(request);
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: stopRegisterPost method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(PTO_OPERATION_LIST, session.getAttribute(PTO_OPERATION_LIST));
		if (!StringUtil.isNull(response)) {
			model.put(STOP_REGISTRATION_REQUEST, new StopRegistrationRequest());
			model.put(STOP_ID, "Stop Id : " + response.getStopId());
			model.put(Constants.SUCCESS, properties.getProperty("stop.created.successfully"));
		} else {
			model.put(STOP_REGISTRATION, request);
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = STOP_SEARCH)
	public ModelAndView stopSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STOP_SEARCH);
		model.put(STOP_SEARCH_REQUEST, new StopSearchRequest());
		
		List<StopSearchRequest> listStopName = stopManagementService.getStopNameList().getListOfStopNames();
		session.setAttribute(STOP_NAME_LIST, listStopName);
		model.put(STOP_NAME_LIST, listStopName);
		
		OrganizationSearchRequest searchPtoRequest = new OrganizationSearchRequest();
		searchPtoRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("ptoSearch", searchPtoRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse = null;
		StopSearchRequest stopSearchRequest = new StopSearchRequest();
		try {
			stopSearchRequest.setPageSize(10);
			stopSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(STOP_LIST_DATA, stopSearchRequest);
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: StopManagementController class :: stopSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
			model.put("ptoListData", ptoListResponse.getPtoList());
			session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		}

		try {
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
					"ERROR :: StopManagementController class :: stopSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}

		return modelAndView;
	}
	
	@PostMapping(value = STOP_SEARCH)
	public ModelAndView stopSearchData(Map<String, Object> model, HttpSession session, StopSearchRequest request,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STOP_SEARCH);
		StopSearchResponse response = null;
		model.put(STOP_SEARCH_REQ, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			model.put(STOP_SEARCH, new StageSearchRequest());
			return stopSearchPagination(model, session, (Integer) session.getAttribute(Constants.PAGE_NUMBER));
		}
		try {
			request.setPageIndex(Constants.ONE);
			model.put(STOP_SEARCH_REQUEST, new StopSearchRequest());
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = stopManagementService.stopSearch(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = stopManagementService.stopSearch(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoOperationService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				request.setPtoId(pto.getPtoMasterId());
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = stopManagementService.stopSearch(request);
			}
			session.setAttribute(STOP_LIST_DATA, request);
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: stopSearchData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(STOP_LIST_DATA, response.getStopSearchList());
			model.put(STOP_DATA_SIZE, response.getNoOfRecords());
			session.setAttribute(STOP_DATA_SIZE, response.getNoOfRecords());
			session.setAttribute(STOP_SEARCH_REQUEST, request);
			model.put(STOP_SEARCH_REQUEST, request);
		}
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		getPtoList(model, request, session, ptoListRequest);
		PaginationUtil.performPagination(modelAndView, session, Constants.ONE, STOP_DATA_SIZE);
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, StopSearchRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrganizationId())) {
			ptoListRequest.setOrgId(request.getOrganizationId());
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

	@PostMapping(value = STOP_SEARCH_PAGINATION)
	public ModelAndView stopSearchPagination(Map<String, Object> model, HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STOP_SEARCH);
		StopSearchRequest request = (StopSearchRequest) session.getAttribute(STOP_SEARCH_REQUEST);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		StopSearchResponse response = null;
		try {
			request.setPageIndex(pageNumber);
			model.put(STOP_SEARCH_REQUEST, request);
			response = stopManagementService.stopSearch(request);
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: stopSearchPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(STOP_DATA_SIZE, response.getNoOfRecords());
			model.put(STOP_LIST_DATA, response.getStopSearchList());
			session.setAttribute(STOP_DATA_SIZE, response.getNoOfRecords());
		}
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		getPtoList(model, request, session, ptoListRequest);
		PaginationUtil.performPagination(modelAndView, session, pageNumber, STOP_DATA_SIZE);
		return modelAndView;
	}

	@PostMapping(value = STOP_VIEW)
	public ModelAndView viewStop(final HttpSession session, Map<String, Object> model,
			@RequestParam("stopId") Long stopId) {
		ModelAndView modelAndView = new ModelAndView(STOP_VIEW);
		StopSearchRequest request = new StopSearchRequest();
		request.setStopId(stopId);
		StopSearchResponse response = new StopSearchResponse();
		model.put(STOP_SEARCH_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setPageIndex(Constants.ONE);
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = stopManagementService.stopSearch(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = stopManagementService.stopSearch(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoOperationService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				request.setPtoId(pto.getPtoMasterId());
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = stopManagementService.stopSearch(request);
			}
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: viewStop method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StopRegistrationRequest stopRegistrationRequest = populateUserUpdateData(response);
			model.put(STOP_SEARCH_REQ, stopRegistrationRequest);
		}
		return modelAndView;
	}

	protected StopRegistrationRequest populateUserUpdateData(StopSearchResponse response) {
		StopRegistrationRequest request = new StopRegistrationRequest();
		StopSearchRequest searchRequest = response.getStopSearchList().get(0);
		request.setStopId(searchRequest.getStopId());
		request.setStopName(searchRequest.getStopName());
		request.setRouteId(searchRequest.getRouteId());
		request.setStageId(searchRequest.getStageId());
		request.setPtoId(searchRequest.getPtoId());
		request.setRouteName(searchRequest.getRouteName());
		request.setStageName(searchRequest.getStageName());
		request.setOrganizationId(searchRequest.getOrganizationId());
		return request;
	}

	@PostMapping(value = STOP_EDIT_ACTION)
	public ModelAndView editStop(final HttpSession session, @RequestParam("stopId") Long routeCode,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(STOP_UPDATE);
		StopSearchRequest request = new StopSearchRequest();
		request.setStopId(routeCode);
		StopSearchResponse response = new StopSearchResponse();
		model.put(STOP_SEARCH_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			List<RouteSearchRequest> listRouteName = stopManagementService.getRouteName().getListOfRouteName();
			session.setAttribute(ROUTE_NAME_LIST, listRouteName);
			List<StageSearchRequest> listStageName = stopManagementService.getStageNameList().getListOfStageNames();
			session.setAttribute(STAGE_NAME_LIST, listStageName);
			request.setPageIndex(Constants.ONE);
			model.put(STOP_SEARCH_REQUEST, new StopSearchRequest());
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = stopManagementService.stopSearch(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = stopManagementService.stopSearch(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoOperationService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				request.setPtoId(pto.getPtoMasterId());
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = stopManagementService.stopSearch(request);
			}
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: editRoute method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StopSearchRequest routeSearchRequest = response.getStopSearchList().get(0);
			model.put(STOP_EDIT_REQ, routeSearchRequest);
		}
		return modelAndView;

	}

	@PostMapping(value = STOP_UPDATE)
	public ModelAndView updateRoute(HttpSession session, Map<String, Object> model,
			StopUpdateRequest stopUpdateRequest) {
		ModelAndView modelAndView = new ModelAndView(STOP_UPDATE);
		WebResponse response = null;
		model.put(STOP_EDIT_REQ, stopUpdateRequest);
		try {
			response = stopManagementService.updateStopProfile(stopUpdateRequest);
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: updateStopData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("stop.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(STOP_EDIT_REQ, stopUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = STOP_STATUS_UPDATE)
	public ModelAndView updateStopStatus(HttpSession session, Map<String, Object> model,
			@RequestParam("stopId") Long stopId, @RequestParam("status") String status,
			@RequestParam("reason") String reason) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STOP_SEARCH);
		StopSearchRequest request = new StopSearchRequest();
		model.put(ROUTE_NAME_LIST, new RouteNameList());
		model.put(STOP_EDIT_REQ, request);
		StopSearchResponse response = null;
		try {
			request.setStopId(stopId);
			request.setStatus(status);
			request.setReason(reason);
			response = stopManagementService.updateStopStatus(request);
		} catch (AFCSException e) {
			logger.error("StopManagementController class :: updateStopStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("stop.status.suspend.changed").replace(STOP_NAME,
						response.getStopName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("stop.status.active.changed").replace(STOP_NAME,
						response.getStopName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("stop.status.terminate.changed")
						.replace(STOP_NAME, response.getStopName()));
			}

			return stopSearchPagination(model, session, (Integer) session.getAttribute(Constants.PAGE_NUMBER));
		}

		return modelAndView;
	}
	@PostMapping(value = DOWNLOAD_STOP_REPORT)
	public ModelAndView downloadStopManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: StopManagementController:: downloadStopManagementReport method");
		ModelAndView modelAndView = new ModelAndView(STOP_SEARCH);
		StopSearchResponse stopSearchResponse = null;
		try {
			StopSearchRequest stopSearchRequest = (StopSearchRequest) session.getAttribute(STOP_LIST_DATA);
			stopSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = stopSearchRequest.getPageSize();
			stopSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				stopSearchRequest.setPageIndex(Constants.ONE);
				stopSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			stopSearchResponse =  stopManagementService.stopSearch(stopSearchRequest);
			List<StopSearchRequest> reuestResponses = stopSearchResponse.getStopSearchList();
			if (!StringUtil.isNull(stopSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadStopManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			stopSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::StopManagementController:: StopManagementReport method", e);
		}
		logger.info("Exit:: StopManagementController:: downloadStopManagementReport method");
		return null;
	}
	
	
	@GetMapping(value = FETCH_STAGE_BY_ROUTE)
	public @ResponseBody String getStageByRoute(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String routeId = request.getParameter("routeId");
		String status = request.getParameter("status");
		StopSearchResponse stopSearchResponse = null;
		StopSearchRequest stopSearchRequest = new StopSearchRequest();
		try {
			stopSearchRequest.setRouteId(Long.valueOf(routeId));
			stopSearchRequest.setStatus(status);
			stopSearchResponse = stopManagementService.getStageByRoute(stopSearchRequest);
			return JsonUtil.convertObjectToJSON(stopSearchResponse);

		} catch (Exception e) {
			logger.error("ERROR:: DashboardController:: getSubServiceProviderForSelectedServiceProvider method", e);
		}
	    return null;
	}

	private void setExportDetailsDataForDownloadStopManagementReport(List<StopSearchRequest> stopRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("StopManagement_");
		exportDetails.setHeaderMessageProperty("chatak.header.stop.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(stopRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("StopManagement.file.exportutil.stopId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StopManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StopManagement.file.exportutil.pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StopManagement.file.exportutil.routeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StopManagement.file.exportutil.stageName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StopManagement.file.exportutil.stopName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StopManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<StopSearchRequest> stopSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (StopSearchRequest stopData : stopSearchRequest) {

			Object[] rowData = { stopData.getStopId() , stopData.getOrganizationId() , stopData.getPtoId() , stopData.getRouteId() , stopData.getStageId(),
					
					 stopData.getStopName() , stopData.getStatus() };

			fileData.add(rowData);
		}

		return fileData;
	}

}
