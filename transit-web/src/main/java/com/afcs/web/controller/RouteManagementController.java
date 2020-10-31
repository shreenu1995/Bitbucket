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
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.RouteRegistrationManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.AddStages;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class RouteManagementController {

	private static Logger logger = LoggerFactory.getLogger(RouteManagementController.class);

	@Autowired
	RouteRegistrationManagementService routeRegistrationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;
	
	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;

	@Autowired
	private MessageSource messageSource;

	private static final String ROUTE_REGISTRATION = "route-registration";
	private static final String ROUTE_REGISTRATION_OBJ = "routeCreate";
	private static final String SEARCH_ROUTE = "route-search";
	private static final String ROUTE_LIST_SIZE = "routelistsize";
	private static final String SEARCH_ROUTE_PAGINATION = "route-search-pagination";
	private static final String VIEW_ROUTE = "route-view";
	private static final String ROUTE_STATUS_UPDATE = "route-status-update";
	private static final String ROUTE_UPDATE = "route-update";
	private static final String ROUTE_EDIT_REQ = "routeEditRequest";
	private static final String ROUTE_EDIT_ACTION = "edit-route-action";
	private static final String ROUTE_SEARCH_REQ = "routesearchrequest";
	private static final String PTO_LIST_DATA = "ptoListData";
	private static final String ROUTE_NAME = "&&routeName";
	private static final String ROUTE_LIST_DATA = "routeListData";
	private static final String DOWNLOAD_ROUTE_REPORT = "downloadRouteReport";
	private static final String PTO_LIST = "ptoList";

	@GetMapping(value = ROUTE_REGISTRATION)
	public ModelAndView routeRegistrationGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(ROUTE_REGISTRATION);
		RouteRegistrationRequest routeRegistrationRequest = new RouteRegistrationRequest();
		model.put("routeRegistrationRequest", routeRegistrationRequest);
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
			return modelandview;
		}
		if (!StringUtil.isNull(ptoListResponse)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		return modelandview;
	}

	@PostMapping(value = ROUTE_REGISTRATION)
	public ModelAndView routeRegistrationPost(Map<String, Object> model, RouteRegistrationRequest request,
			HttpSession session) throws AFCSException {
		ModelAndView modelandview = new ModelAndView(SEARCH_ROUTE);
		RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
		model.put(ROUTE_SEARCH_REQ, routeSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		WebResponse response = null;
		try {
		response = routeRegistrationManagementService.routeRegistration(request);
		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: routeRegistrationPost method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelandview;
		}

		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(ROUTE_REGISTRATION_OBJ, new RouteRegistrationRequest());
			model.put(Constants.SUCCESS, properties.getProperty("route.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelandview;
	}

	@GetMapping(value = SEARCH_ROUTE)
	public ModelAndView showSearchRoute(Map<String, Object> model, HttpSession session) throws AFCSException {
		
		ModelAndView modelAndView = new ModelAndView(SEARCH_ROUTE);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchRequest searchPtoRequest = new OrganizationSearchRequest();
		searchPtoRequest.setPageIndex(Constants.ONE);
		searchPtoRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse = null;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}
		if (!StringUtil.isNull(ptoListResponse)
				&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		
		try {
			routeSearchRequest.setPageSize(10);
			routeSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(ROUTE_LIST_DATA, routeSearchRequest);
			if (searchPtoRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(searchPtoRequest);
			} else if (searchPtoRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				searchPtoRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(searchPtoRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					searchPtoRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(searchPtoRequest);
				}
			}

		} catch (AFCSException e) {
			logger.error("ERROR :: RouteManagementController class :: showSearchRoute method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		model.put(ROUTE_SEARCH_REQ, routeSearchRequest);
		model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		return modelAndView;
	}

	@PostMapping(value = SEARCH_ROUTE)
	public ModelAndView processSearchRoute(Map<String, Object> model, RouteSearchRequest request, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(SEARCH_ROUTE);
		RouteSearchResponse response = null;
		model.put(ROUTE_SEARCH_REQ, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return routeSearchPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		request.setPageIndex(Constants.ONE);

		try {
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = routeRegistrationManagementService.searchRoute(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = routeRegistrationManagementService.searchRoute(request);
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
				response = routeRegistrationManagementService.searchRoute(request);
			}
			session.setAttribute(ROUTE_LIST_DATA, request);
			session.setAttribute(ROUTE_LIST_DATA, response.getListORoutes());
		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: processSearchRoute method :: exception", e);
			model.put(Constants.ERROR, request);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			session.setAttribute(ROUTE_LIST_SIZE, response.getTotalRecords());
			session.setAttribute(ROUTE_SEARCH_REQ, request);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(ROUTE_LIST_DATA, response.getListORoutes());
			model.put(ROUTE_SEARCH_REQ, request);
			model.put(ROUTE_LIST_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, ROUTE_LIST_SIZE);
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, RouteSearchRequest request, HttpSession session,
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
	
	@PostMapping(value = SEARCH_ROUTE_PAGINATION)
	public ModelAndView routeSearchPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {

		ModelAndView modelAndView = new ModelAndView(SEARCH_ROUTE);
		RouteSearchRequest routeSearchRequest = (RouteSearchRequest) session.getAttribute(ROUTE_SEARCH_REQ);
		RouteSearchResponse response = new RouteSearchResponse();
		model.put(ROUTE_SEARCH_REQ, routeSearchRequest);
		try {
			routeSearchRequest.setPageIndex(pageNumber);
			routeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());

			if (routeSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = routeRegistrationManagementService.searchRoute(routeSearchRequest);
			} else if (routeSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				routeSearchRequest.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = routeRegistrationManagementService.searchRoute(routeSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					routeSearchRequest.setPtoId(pto.getPtoMasterId());
				}
				routeSearchRequest.setPageIndex(Constants.ONE);
				routeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = routeRegistrationManagementService.searchRoute(routeSearchRequest);
			}

		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: routeSearchPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put("searchFlag", "true");
		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(ROUTE_LIST_DATA, response.getListORoutes());
			model.put(ROUTE_SEARCH_REQ, routeSearchRequest);
			session.setAttribute(ROUTE_LIST_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, routeSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, ROUTE_LIST_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = VIEW_ROUTE)
	public ModelAndView viewRoute(final HttpSession session, Map<String, Object> model,
			@RequestParam("routeId") Long routeId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VIEW_ROUTE);
		RouteSearchRequest request = new RouteSearchRequest();
		request.setRouteId(routeId);
		RouteSearchResponse response = new RouteSearchResponse();
		model.put(ROUTE_SEARCH_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setPageIndex(Constants.ONE);
			response = routeRegistrationManagementService.viewRoute(request);
		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: viewRoute method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			RouteRegistrationRequest routeRegistrationRequest = populateRouteData(response);
			model.put(ROUTE_SEARCH_REQ, routeRegistrationRequest);
			model.put("stageView", routeRegistrationRequest.getDataFieldList());
		}
		return modelAndView;
	}

	protected RouteRegistrationRequest populateRouteData(RouteSearchResponse response) throws AFCSException {
		
		RouteRegistrationRequest request = new RouteRegistrationRequest();
		RouteSearchRequest searchRequest = response.getListORoutes().get(0);
		AddStages addStage= new AddStages();
		List<AddStages> addStageList= new ArrayList<>();
		request.setRouteId(searchRequest.getRouteId());
		request.setOrganizationId(searchRequest.getOrganizationId());
		request.setPtoId(searchRequest.getPtoId());
		request.setFromRoute(searchRequest.getFromRoute());
		request.setToRoute(searchRequest.getToRoute());
		request.setRouteName(searchRequest.getRouteName());
		addStage.setId(searchRequest.getId());
		addStage.setDistance(searchRequest.getDistance());
		addStage.setStageName(searchRequest.getStageName());
		addStage.setStageSequenceNumber(searchRequest.getStageSequenceNumber());
		addStageList.add(addStage);
		request.setDataFieldList(addStageList);
		request.setRouteCode(searchRequest.getRouteCode());
		PtoListRequest ptoListRequest = new PtoListRequest();
		if (!StringUtil.isNull(searchRequest.getPtoId())) {
			ptoListRequest.setPtoMasterId(searchRequest.getPtoId());
			PtoListResponse ptoList = ptoManagementService.getPtoDataByPtoId(ptoListRequest);
			try {
				if (!StringUtil.isNull(ptoList.getPtoList().get(0).getPtoMasterId())) {
					request.setPtoId(ptoList.getPtoList().get(0).getPtoMasterId());
				}
			} catch (IndexOutOfBoundsException e) {
				logger.error("DepotManagementController class :: populateDepotViewRequest method :: exception", e);
			}
		}
		return request;
	}

	@PostMapping(value = ROUTE_EDIT_ACTION)
	public ModelAndView editRouteData(final HttpSession session, @RequestParam("routeId") Long routeId,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(ROUTE_UPDATE);
		RouteSearchRequest request = new RouteSearchRequest();
		request.setRouteId(routeId);
		RouteSearchResponse response = new RouteSearchResponse();
		model.put(ROUTE_EDIT_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setPageIndex(Constants.ONE);
			response = routeRegistrationManagementService.viewRoute(request);
		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: viewRoute method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			RouteRegistrationRequest routeRegistrationRequest = populateRouteData(response);
			if (routeRegistrationRequest.getDataFieldList().isEmpty()) {
				AddStages req = new AddStages();
				req.setDistance(0);
				req.setStageName(" ");
				req.setStageSequenceNumber(0);
				routeRegistrationRequest.getDataFieldList().add(req);

			}
			model.put(ROUTE_EDIT_REQ, routeRegistrationRequest);
			model.put("stageView", routeRegistrationRequest.getDataFieldList());
			return modelAndView;
		}
		return modelAndView;

	}

	@PostMapping(value = ROUTE_UPDATE)
	public ModelAndView updateRoute(HttpSession session, Map<String, Object> model,
			RouteRegistrationRequest routeStatusUpdate, @RequestParam("routeId") Long routeId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(ROUTE_UPDATE);
		WebResponse response = null;
		model.put(ROUTE_EDIT_REQ, routeStatusUpdate);
		try {
			routeStatusUpdate.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			response = routeRegistrationManagementService.updateRouteProfile(routeStatusUpdate);
		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: updateRouteData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("route.update.success"));
			return editRouteData(session, routeId, model);

		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(ROUTE_EDIT_REQ, routeStatusUpdate);
		}
		return modelAndView;
	}

	@PostMapping(value = ROUTE_STATUS_UPDATE)
	public ModelAndView updateRouteStatus(HttpSession session, Map<String, Object> model,
			@RequestParam("routeId") Long routeId, @RequestParam("status") String status) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(SEARCH_ROUTE);
		RouteStatusUpdateRequest request = new RouteStatusUpdateRequest();
		model.put(ROUTE_EDIT_REQ, request);
		RouteSearchResponse response = null;
		try {
			request.setRouteId(routeId);
			request.setStatus(status);
			response = routeRegistrationManagementService.updateRouteStatus(request);
		} catch (AFCSException e) {
			logger.error("RouteManagementController class :: updateRouteStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("route.status.suspend.changed").replace(ROUTE_NAME,
						response.getRouteName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("route.status.active.changed").replace(ROUTE_NAME,
						response.getRouteName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("route.status.terminate.changed")
						.replace(ROUTE_NAME, response.getRouteName()));
			}

			return routeSearchPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}

		return modelAndView;
	}

	@PostMapping(value = "delete")
	public ModelAndView deleteStageById(HttpSession session, Map<String, Object> model, @RequestParam("id") String id,
			@RequestParam("routeId") Long routeId) throws AFCSException {
		RouteRegistrationRequest request = new RouteRegistrationRequest();
		request.setId(Long.valueOf(id));
		routeRegistrationManagementService.deleteStageById(request);
		return editRouteData(session, routeId, model);
	}

	@PostMapping(value = DOWNLOAD_ROUTE_REPORT)
	public ModelAndView downloadRouteManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RouteManagementController:: downloadRouteManagementReport method");
		ModelAndView modelAndView = new ModelAndView(SEARCH_ROUTE);
		RouteSearchResponse discountListResponse = null;
		try {
			RouteSearchRequest routeSearchRequest = (RouteSearchRequest) session.getAttribute(ROUTE_LIST_DATA);
			routeSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = routeSearchRequest.getPageSize();
			routeSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				routeSearchRequest.setPageIndex(Constants.ONE);
				routeSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			discountListResponse = routeRegistrationManagementService.searchRoute(routeSearchRequest);
			List<RouteSearchRequest> reuestResponses = discountListResponse.getListORoutes();
			if (!StringUtil.isNull(discountListResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadRouteManagementOnboardReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			routeSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: RouteManagementController:: RouteManagementReport method", e);
		}
		logger.info("Exit:: RouteManagementController:: downloadRouteManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadRouteManagementOnboardReport(List<RouteSearchRequest> routeRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("RouteManagement_");
		exportDetails.setHeaderMessageProperty("chatak.header.route.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(routeRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("RouteManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("RouteManagement.file.exportutil.pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("RouteManagement.file.exportutil.routeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("RouteManagement.file.exportutil.fromRoute", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("RouteManagement.file.exportutil.toRoute", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("RouteManagement.file.exportutil.routeCode", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("RouteManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<RouteSearchRequest> routeSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (RouteSearchRequest routeData : routeSearchRequest) {

			Object[] rowData = { routeData.getOrganizationId() , routeData.getPtoId() , routeData.getRouteName() , routeData.getFromRoute(), 
					
				routeData.getToRoute() , routeData.getRouteCode() , routeData.getStatus() };

			fileData.add(rowData);
		}

		return fileData;
	}

}