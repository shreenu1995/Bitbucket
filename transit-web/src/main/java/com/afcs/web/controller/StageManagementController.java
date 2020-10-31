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
import com.afcs.web.service.StageManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class StageManagementController {

	private static Logger logger = LoggerFactory.getLogger(StageManagementController.class);

	@Autowired
	StageManagementService stageManagementService;

	@Autowired
	PtoManagementService ptoOperationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String STAGE_REGISTRATION = "stage-registration";
	private static final String STAGE_REGISTRATION_OBJ = "stageRegister";
	private static final String STAGE_SEARCH = "stage-search";
	private static final String STAGE_LIST_SIZE = "stageListSize";
	private static final String STAGE_SEARCH_PAGINATION = "stage-search-pagination";
	private static final String STAGE_VIEW = "stage-view";
	private static final String STAGE_ID = "stageId";
	private static final String STAGE_UPDATE = "stage-edit";
	private static final String STAGE_EDIT_ACTION = "stage-edit-action";
	private static final String STAGE_EDIT_REQ = "stageEditRequest";
	private static final String UPDATE_STAGE_STATUS = "update-status-status";
	private static final String GET_PTO_LIST_BY_ORGANIZATION_NAME = "getPtoListByOrganizationName";
	private static final String ROUTE_NAME_LIST = "routeNameList";
	private static final String STAGE_SEARCH_REQ = "stagesearchrequest";
	private static final String STAGE_NAME = "&&stageName";
	private static final String STAGE_LIST_DATA = "stageListData";
	private static final String DOWNLOAD_STAGE_REPORT ="downloadStageReport";
	private static final String FETCH_ROUTE_BY_ORG_PTO = "fetchRouteByOrgAndPto";

	@GetMapping(value = STAGE_REGISTRATION)
	public ModelAndView showStageRegistration(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(STAGE_REGISTRATION);
		List<RouteSearchRequest> listRouteName = stageManagementService.getRouteName().getListOfRouteName();
		session.setAttribute(ROUTE_NAME_LIST, listRouteName);
		model.put(ROUTE_NAME_LIST, listRouteName);
		StageRegistrationRequest request = new StageRegistrationRequest();
		model.put(STAGE_REGISTRATION_OBJ, request);
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;

		try {
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoManagementService.getActivePtoList(ptoListRequest);
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoManagementService.getActivePtoList(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getActivePtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error(
					"ERROR :: RouteManagementController class :: routeRegistrationGet method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(ptoListResponse)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		return modelAndView;
	}

	@PostMapping(value = STAGE_REGISTRATION)
	public ModelAndView processStageRegistration(Map<String, Object> model, HttpSession session,
			StageRegistrationRequest request) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STAGE_SEARCH);
		StageSearchRequest stageSearchRequest = new StageSearchRequest();
		model.put(STAGE_SEARCH_REQ, stageSearchRequest);
		model.put(STAGE_REGISTRATION_OBJ, request);
		model.put(ROUTE_NAME_LIST, session.getAttribute(ROUTE_NAME_LIST));
		StageRegistrationResponse response = null;
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		try {
			response = stageManagementService.stageRegister(request);
		} catch (AFCSException e) {
			logger.error("StageManagementController class :: processStageRegistration method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(STAGE_ID, "Stage Id : " + response.getStageId());
			model.put(Constants.SUCCESS, properties.getProperty("stage.created.successfully"));
			model.put(STAGE_REGISTRATION_OBJ, request);
		} else {
			model.put(STAGE_REGISTRATION_OBJ, request);
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = STAGE_SEARCH)
	public ModelAndView showStageSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STAGE_SEARCH);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("ptoSearch", organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		StageSearchRequest request = new StageSearchRequest();
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(STAGE_SEARCH_REQ, request);
		PtoListResponse ptoListResponse = null;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoOperationManagementService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoOperationManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoOperationManagementService.getPtoList(ptoListRequest);
		}
		if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
			model.put("ptoListData", ptoListResponse.getPtoList());
			session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		}
		try {
			request.setPageSize(10);
			request.setPageIndex(Constants.ONE);
			session.setAttribute(STAGE_LIST_DATA, request);

			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoOperationManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					organizationSearchRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(organizationSearchRequest);
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

	@PostMapping(value = STAGE_SEARCH)
	public ModelAndView stageSearch(Map<String, Object> model, StageSearchRequest request, HttpSession session, 
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STAGE_SEARCH);
		StageResponse response = null;
		model.put(STAGE_SEARCH_REQ, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			model.put(STAGE_SEARCH, new StageSearchRequest());
			return stageSearchPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			request.setPageIndex(Constants.ONE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = stageManagementService.searchStage(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = stageManagementService.searchStage(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoId(pto.getPtoMasterId());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = stageManagementService.searchStage(request);
			}
			session.setAttribute(STAGE_LIST_DATA, request);
		} catch (Exception e) {
			logger.error("StageManagementController class :: stageSearch method :: exception", e);
			model.put(Constants.ERROR, request);
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			session.setAttribute(STAGE_LIST_SIZE, response.getNoOfRecords());
			model.put("searchFlag", "true");
			model.put(STAGE_SEARCH_REQ, request);
			model.put(STAGE_LIST_DATA, response.getListOfStages());
			model.put(STAGE_LIST_SIZE, response.getNoOfRecords());
			session.setAttribute(STAGE_SEARCH_REQ, request);
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, STAGE_LIST_SIZE);
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, StageSearchRequest request, HttpSession session,
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

	@PostMapping(value = STAGE_SEARCH_PAGINATION)
	public ModelAndView stageSearchPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(STAGE_SEARCH);
		StageSearchRequest request = (StageSearchRequest) session.getAttribute(STAGE_SEARCH_REQ);
		model.put(STAGE_SEARCH_REQ, request);
		StageResponse response = new StageResponse();
		try {
			request.setPageIndex(pageNumber);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());

			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = stageManagementService.searchStage(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = stageManagementService.searchStage(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoId(pto.getPtoMasterId());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = stageManagementService.searchStage(request);
			}
		} catch (AFCSException e) {
			logger.error("StageManagementController class :: stageSearchPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put("searchFlag", "true");
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(STAGE_LIST_DATA, response.getListOfStages());
			model.put(STAGE_SEARCH_REQ, request);
			session.setAttribute(STAGE_LIST_SIZE, response.getNoOfRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, STAGE_LIST_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = STAGE_VIEW)
	public ModelAndView viewStage(final HttpSession session, Map<String, Object> model,
			@RequestParam("stageId") Long stageId) {
		ModelAndView modelAndView = new ModelAndView(STAGE_VIEW);
		StageSearchRequest request = new StageSearchRequest();
		request.setStageId(stageId);
		StageResponse response = new StageResponse();
		model.put(STAGE_SEARCH_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setPageIndex(Constants.ONE);
			response = stageManagementService.viewStopOnStage(request);
		} catch (AFCSException e) {
			logger.error("StageManagementController class :: viewStage method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StageRegistrationRequest stageRequest = populateUserUpdateData(response);
			model.put(STAGE_SEARCH_REQ, stageRequest);
			if (!stageRequest.getDataFieldList().isEmpty()) {
				model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);

			}
			model.put("stopView", stageRequest.getDataFieldList());
		}
		return modelAndView;
	}

	protected StageRegistrationRequest populateUserUpdateData(StageResponse response) {
		StageRegistrationRequest request = new StageRegistrationRequest();
		StageSearchRequest searchRequest = response.getListOfStages().get(0);
		request.setStageId(searchRequest.getStageId());
		request.setOrganizationId(searchRequest.getOrganizationId());
		request.setPtoId(searchRequest.getPtoId());
		request.setStageName(searchRequest.getStageName());
		request.setRouteId(searchRequest.getRouteId());
		request.setDataFieldList(searchRequest.getDataFieldList());
		request.setRouteName(searchRequest.getRouteName());
		return request;

	}

	@PostMapping(value = STAGE_EDIT_ACTION)
	public ModelAndView editStageData(final HttpSession session, @RequestParam("stageId") Long stageId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(STAGE_UPDATE);
		StageSearchRequest request = new StageSearchRequest();
		request.setStageId(stageId);
		StageResponse response = new StageResponse();
		model.put(STAGE_SEARCH_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			List<RouteSearchRequest> listRouteName = stageManagementService.getRouteName().getListOfRouteName();
			session.setAttribute(ROUTE_NAME_LIST, listRouteName);
			request.setPageIndex(Constants.ONE);
			request.setStageId(stageId);
			response = stageManagementService.viewStopOnStage(request);
		} catch (AFCSException e) {
			logger.error("StageManagementController class :: editStageData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StageRegistrationRequest stageRegistrationRequest = populateUserUpdateData(response);
			model.put(STAGE_EDIT_REQ, stageRegistrationRequest);
			model.put(STAGE_SEARCH_REQ, stageRegistrationRequest);
			if (stageRegistrationRequest.getDataFieldList().isEmpty()) {
				    StopRegistrationRequest req = new StopRegistrationRequest();
				    req.setDistance(0);
				    req.setStopName(" ");
				    req.setStopSequenceNumber(0);
				    stageRegistrationRequest.getDataFieldList().add(req);

			}
			model.put("stopView", stageRegistrationRequest.getDataFieldList());
		}
		return modelAndView;

	}

	@PostMapping(value = STAGE_UPDATE)
	public ModelAndView updateStageData(HttpSession session, Map<String, Object> model,
			StageRegistrationRequest request,@RequestParam("stageId") Long stageId) {

		ModelAndView modelAndView = new ModelAndView(STAGE_UPDATE);
		WebResponse response = null;
		model.put(STAGE_SEARCH_REQ, request);
		try {
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			response = stageManagementService.updateStage(request);
		} catch (AFCSException e) {
			logger.error("StageManagementController class :: updateStageData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;

		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("stage.update.success"));
			return editStageData( session, stageId,
					model);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(STAGE_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_STAGE_STATUS)
	public ModelAndView updateStageStatus(final HttpSession session, @RequestParam("stageId") Long stageId,
			@RequestParam("stageStatus") String status, @RequestParam("reason") String reason,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(STAGE_SEARCH);
		model.put(ROUTE_NAME_LIST, new RouteNameList());
		StageStatusUpdateRequest request = new StageStatusUpdateRequest();
		StageResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			request.setStatus(status);
			request.setStageId(stageId);
			request.setReason(reason);
			response = stageManagementService.updateStageStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("stage.status.suspend.changed")
							.replace(STAGE_NAME, response.getStageName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("stage.status.active.changed")
							.replace(STAGE_NAME, response.getStageName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("stage.status.terminate.changed")
							.replace(STAGE_NAME, response.getStageName()));
				}
				return stageSearchPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
			}
		} catch (AFCSException e) {
			logger.error("StageManagementController class :: updateStageStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@GetMapping(value = GET_PTO_LIST_BY_ORGANIZATION_NAME)
	public @ResponseBody String getPtoByOrganizationName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session,
			@RequestParam("organizationName") String organizationName, PtoListResponse response)
			throws JsonProcessingException, AFCSException {
		OrganizationSearchRequest ptoSearchRequest = new OrganizationSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoSearchRequest.setOrganizationName(organizationName);
		OrganizationResponse organizationResponse = new OrganizationResponse();
		organizationResponse = organizationManagementService.getOrganizationIdByOrganizationName(ptoSearchRequest, organizationResponse);
		ptoSearchRequest.setOrgId(organizationResponse.getOrgId());
		ptoListRequest.setOrgId(ptoSearchRequest.getOrgId());
		ptoListRequest.setUserId((String) session.getAttribute(Constants.USER_ID));
		PtoListResponse listPto= null;
		listPto = ptoOperationManagementService.getPtoByOrganizationIdAndUserId(ptoListRequest);
		if (!StringUtil.isNull(listPto) && listPto.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			listPto.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(listPto);
		}
		return null;
	}

	@PostMapping(value = "deleteStop")
	public ModelAndView deleteStopById(HttpSession session, Map<String, Object> model, @RequestParam("stopId") Long stopId,
			@RequestParam("stageId") Long stageId) throws AFCSException {
		StageRegistrationRequest request = new StageRegistrationRequest();
		request.setStopId(stopId);
		stageManagementService.deleteStopById(request);
		return editStageData(session, stageId, model);

	}
	@PostMapping(value = DOWNLOAD_STAGE_REPORT)
	public ModelAndView downloadStageManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: StageManagementController:: downloadStageManagementReport method");
		ModelAndView modelAndView = new ModelAndView(STAGE_SEARCH);
		StageResponse stageResponse = null;
		try {
			StageSearchRequest stageSearchRequest = (StageSearchRequest) session.getAttribute(STAGE_LIST_DATA);
			stageSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = stageSearchRequest.getPageSize();
			stageSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				stageSearchRequest.setPageIndex(Constants.ONE);
				stageSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			stageResponse = stageManagementService.searchStage(stageSearchRequest);
			List<StageSearchRequest> reuestResponses = stageResponse.getListOfStages();
			if (!StringUtil.isNull(stageResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadStageManagementOnboardReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			stageSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::StageManagementController:: StageManagementReport method", e);
		}
		logger.info("Exit:: StageManagementController:: downloadStageManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadStageManagementOnboardReport(List<StageSearchRequest> routeRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("StageManagement_");
		exportDetails.setHeaderMessageProperty("chatak.header.stage.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(routeRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("StageManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StageManagement.file.exportutil.pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StageManagement.file.exportutil.routeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StageManagement.file.exportutil.stageName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("StageManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<StageSearchRequest> stageSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (StageSearchRequest stageData : stageSearchRequest) {

			Object[] rowData = { stageData.getOrganizationId() , stageData.getPtoId() , stageData.getRouteId() , stageData.getStageName(),
					
					stageData.getStatus() };

			fileData.add(rowData);
		}

		return fileData;
	}
	
	@GetMapping(value = FETCH_ROUTE_BY_ORG_PTO)
	public @ResponseBody String getRouteAccordingOrgAndPto(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String ptoId = request.getParameter("ptoId");
		String status = request.getParameter("status");
		RouteSearchResponse routeSearchResponse = null;
		RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
		try {
			if (!StringUtil.isNullEmpty(ptoId)) {
			routeSearchRequest.setPtoId(Long.parseLong(ptoId));
			routeSearchRequest.setStatus(status);
			routeSearchResponse = stageManagementService.getRouteAccordingOrgAndPto(routeSearchRequest);
			return JsonUtil.convertObjectToJSON(routeSearchResponse);
		    }
	    } catch (Exception e) {
		logger.error("ERROR:: DashboardController:: getSubServiceProviderForSelectedServiceProvider method", e);
	    }
	    return null;
	}
}