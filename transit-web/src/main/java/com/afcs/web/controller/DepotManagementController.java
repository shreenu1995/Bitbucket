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
import com.afcs.web.service.DepotManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DepotManagementController {

	private static Logger logger = LoggerFactory.getLogger(DepotManagementController.class);

	@Autowired
	DepotManagementService depotManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;

	@Autowired
	private MessageSource messageSource;

	private static final String DEPOT_MGMT_REGISTRATION = "depot-registration";
	private static final String DEPOT_LIST_DATASIZE = "depotDataSize";
	private static final String DEPOT_SEARCH = "depot-search";
	private static final String DEPOT_SEARCH_PAGINATION = "depot-search-pagination";
	private static final String DEPOT_VIEW_ACTION = "view-depot-action";
	private static final String DEPOT_EDIT_ACTION = "edit-depot-action";
	private static final String DEPOT_SEARCH_REQUEST = "depotSearchRequest";
	private static final String DEPOT_PROFILE_UPDATE_REQUEST = "depotProfileUpdateRequest";
	private static final String DEPOT_NAME = "&&depotName";
	private static final String DEPOT_LIST_DATA = "depotListData";
	private static final String DOWNLOAD_DEPOT_REPORT = "downloadDepotReport";

	@GetMapping(value = DEPOT_MGMT_REGISTRATION)
	public ModelAndView depotManagementRegistrationGet(Map<String, Object> model, HttpSession session)
			throws AFCSException {
		ModelAndView modelandview = new ModelAndView(DEPOT_MGMT_REGISTRATION);
		DepotRegistrationRequest depotRegistrationRequest = new DepotRegistrationRequest();
		model.put("depotRegistrationRequest", depotRegistrationRequest);
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
					"ERROR :: DepotManagementController class :: depotManagementRegistrationGet method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelandview;
		}
		if (!StringUtil.isNull(ptoListResponse)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		return modelandview;
	}

	@PostMapping(value = DEPOT_MGMT_REGISTRATION)
	public ModelAndView depotManagementRegistrationPost(Map<String, Object> model, DepotRegistrationRequest request,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEPOT_SEARCH);
		DepotSearchRequest depotSearchRequest = new DepotSearchRequest();
		model.put(DEPOT_SEARCH_REQUEST, depotSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		DepotRegistrationResponse response = depotManagementService.depotManagementRegistration(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("depotRegistrationRequest", new DepotRegistrationRequest());
			model.put(Constants.SUCCESS, properties.getProperty("depot.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = DEPOT_SEARCH)
	public ModelAndView depotSearch(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEPOT_SEARCH);
		DepotSearchRequest depotSearchRequest = new DepotSearchRequest();
		model.put(DEPOT_SEARCH_REQUEST, depotSearchRequest);
		
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		try {
			depotSearchRequest.setPageSize(10);
			depotSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(DEPOT_LIST_DATA, depotSearchRequest);
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: DepotManagementController class :: depotSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(ptoListResponse)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}

		try {
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationListByOrgId(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					organizationSearchRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(organizationSearchRequest);
				}
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: DepotManagementController class :: depotSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = DEPOT_SEARCH)
	public ModelAndView depotSearch(Map<String, Object> model, DepotSearchRequest depotSearchRequest,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEPOT_SEARCH);
		DepotSearchResponse response = null;
		model.put(DEPOT_SEARCH_REQUEST, depotSearchRequest);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return depotSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			depotSearchRequest.setPageIndex(Constants.ONE);
			depotSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (depotSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = depotManagementService.searchDepot(depotSearchRequest);
			} else if (depotSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				depotSearchRequest.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = depotManagementService.searchDepot(depotSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					depotSearchRequest.setPtoName(pto.getPtoName());
				}
				depotSearchRequest.setPageIndex(Constants.ONE);
				depotSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = depotManagementService.searchDepot(depotSearchRequest);

			}
			session.setAttribute(DEPOT_LIST_DATA, depotSearchRequest);

		} catch (AFCSException e) {
			logger.error("DepotManagementController class :: depotSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(DEPOT_LIST_DATASIZE, response.getNoOfRecords());
			session.setAttribute(DEPOT_SEARCH_REQUEST, depotSearchRequest);
			model.put("searchFlag", "true");
			model.put(DEPOT_LIST_DATA, response.getDepotListResponse());
			model.put(DEPOT_SEARCH_REQUEST, depotSearchRequest);
			model.put(DEPOT_LIST_DATASIZE, response.getNoOfRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, depotSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, DEPOT_LIST_DATASIZE);
		}

		return modelAndView;
	}

	private void getPtoList(Map<String, Object> model, DepotSearchRequest depotSearchRequest, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(depotSearchRequest.getOrganizationId())) {
			ptoListRequest.setOrgId(depotSearchRequest.getOrganizationId());
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

	@PostMapping(value = DEPOT_SEARCH_PAGINATION)
	public ModelAndView depotSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		DepotSearchRequest request = (DepotSearchRequest) session.getAttribute(DEPOT_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(DEPOT_SEARCH);
		model.put(DEPOT_SEARCH_REQUEST, new DepotSearchRequest());
		DepotSearchResponse response = new DepotSearchResponse();
		try {
			request.setPageIndex(pageNumber);
			response = depotManagementService.searchDepot(request);
		} catch (AFCSException e) {
			logger.error("DepotManagementController class :: depotSearchDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put("searchFlag", "true");
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DEPOT_LIST_DATA, response.getDepotListResponse());
			model.put(DEPOT_SEARCH_REQUEST, request);
			session.setAttribute(DEPOT_LIST_DATASIZE, response.getNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, DEPOT_LIST_DATASIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = DEPOT_VIEW_ACTION)
	public ModelAndView viewDepotData(final HttpSession session, @RequestParam("depotId") Long depotId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("depot-view");
		DepotSearchResponse response = new DepotSearchResponse();
		model.put("depotProfileViewRequest", new DepotProfileUpdateRequest());
		PtoListRequest ptoListRequest = new PtoListRequest();
		DepotSearchRequest depotRequest = new DepotSearchRequest();
		try {
			DepotSearchRequest depotSearchRequest = new DepotSearchRequest();
			depotSearchRequest.setPageIndex(1);
			depotSearchRequest.setDepotId(depotId);
			depotSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (depotSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = depotManagementService.searchDepot(depotSearchRequest);
				ptoListRequest
						.setOrgId(response.getDepotListResponse().get(0).getOrganizationId());
				PtoListResponse ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			} else if (depotSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				depotSearchRequest.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = depotManagementService.searchDepot(depotSearchRequest);
				ptoListRequest
						.setOrgId(response.getDepotListResponse().get(0).getOrganizationId());
				PtoListResponse ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			} else {

				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					depotSearchRequest.setPtoName(pto.getPtoName());
				}
				depotRequest.setPageIndex(Constants.ONE);
				depotRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = depotManagementService.searchDepot(depotSearchRequest);
				if (ptoList.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_DATA, ptoList.getPtoList());
				}
			}

			DepotProfileUpdateRequest depotProfileUpdateRequest = populateDepotViewRequest(response);
			model.put("depotProfileViewRequest", depotProfileUpdateRequest);
		} catch (AFCSException e) {
			logger.error("DepotManagementController class :: viewDepotData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	private DepotProfileUpdateRequest populateDepotViewRequest(DepotSearchResponse response) throws AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		DepotProfileUpdateRequest depotProfileUpdateRequest = new DepotProfileUpdateRequest();
		DepotSearchRequest depotSearchRequest = response.getDepotListResponse().get(0);
		depotProfileUpdateRequest.setDepotId(depotSearchRequest.getDepotId());
		depotProfileUpdateRequest.setDepotIncharge(depotSearchRequest.getDepotIncharge());
		depotProfileUpdateRequest.setDepotName(depotSearchRequest.getDepotName());
		depotProfileUpdateRequest.setMobile(depotSearchRequest.getMobile());
		depotProfileUpdateRequest.setPtoName(depotSearchRequest.getPtoName());
		depotProfileUpdateRequest.setOrganizationId(depotSearchRequest.getOrganizationId());
		if (!StringUtil.isNull(depotSearchRequest.getPtoId())) {
			ptoListRequest.setPtoMasterId(depotSearchRequest.getPtoId());
			PtoListResponse ptoList = ptoManagementService.getPtoDataByPtoId(ptoListRequest);
			try {
				if (!StringUtil.isNull(ptoList.getPtoList().get(0).getPtoMasterId())) {
					depotProfileUpdateRequest.setPtoId(ptoList.getPtoList().get(0).getPtoMasterId());
				}
			} catch (IndexOutOfBoundsException e) {
				logger.error("DepotManagementController class :: populateDepotViewRequest method :: exception", e);
			}
		}

		depotProfileUpdateRequest.setDepotShortName(depotSearchRequest.getDepotShortName());
		return depotProfileUpdateRequest;
	}

	@PostMapping(value = DEPOT_EDIT_ACTION)
	public ModelAndView editDepotData(final HttpSession session, @RequestParam("depotId") Long depotId,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView("depot-edit");
		DepotSearchResponse response = new DepotSearchResponse();
		model.put(DEPOT_PROFILE_UPDATE_REQUEST, new DepotProfileUpdateRequest());
		PtoListRequest ptoListRequest = new PtoListRequest();
		DepotSearchRequest depotRequest = new DepotSearchRequest();
		PtoListResponse ptoListResponse = new PtoListResponse();
		try {
			DepotSearchRequest depotSearchRequest = new DepotSearchRequest();
			depotSearchRequest.setPageIndex(1);
			depotSearchRequest.setDepotId(depotId);
			depotSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (depotSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = depotManagementService.searchDepot(depotSearchRequest);
				ptoListRequest
						.setOrgId(response.getDepotListResponse().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			} else if (depotSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				depotSearchRequest.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = depotManagementService.searchDepot(depotSearchRequest);
				ptoListRequest
						.setOrgId(response.getDepotListResponse().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			} else {

				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
					pto.setPtoName(ptoListResponse.getPtoList().get(0).getPtoName());
					depotSearchRequest.setPtoName(pto.getPtoName());
				}
				depotRequest.setPageIndex(Constants.ONE);
				depotRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = depotManagementService.searchDepot(depotSearchRequest);
				if (ptoListResponse.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
				}
			}
			DepotProfileUpdateRequest depotProfileUpdateRequest = populateDepotViewRequest(response);
			model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_DATA, ptoListResponse.getPtoList());
			model.put(DEPOT_PROFILE_UPDATE_REQUEST, depotProfileUpdateRequest);
		} catch (AFCSException e) {
			logger.error("DepotManagementController class :: editDepotData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = "updateDepotProfile")
	public ModelAndView updateDepotData(HttpSession session, Map<String, Object> model,
			DepotProfileUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView("depot-edit");
		WebResponse response = null;
		model.put(DEPOT_PROFILE_UPDATE_REQUEST, new DepotProfileUpdateRequest());
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = depotManagementService.updateDepotProfile(request);
		} catch (AFCSException e) {
			logger.error("DepotManagementController class :: updateDepotData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("depot.update.success"));
			model.put(DEPOT_PROFILE_UPDATE_REQUEST, request);

		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(DEPOT_PROFILE_UPDATE_REQUEST, request);
		}
		return modelAndView;
	}

	@PostMapping(value = "update-depot-status")
	public ModelAndView updateDepotStatus(final HttpSession session, @RequestParam("depotId") Long depotId,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEPOT_SEARCH);
		model.put(DEPOT_SEARCH_REQUEST, new DepotSearchRequest());
		modelAndView.addObject(DEPOT_SEARCH_REQUEST, new DepotSearchRequest());
		DepotStatusUpdateRequest request = new DepotStatusUpdateRequest();
		DepotSearchResponse response = null;
		try {
			request.setStatus(status);
			request.setDepotId(depotId);
			request.setReason(reason);
			response = depotManagementService.updateDepotStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("depot.status.suspend.changed")
							.replace(DEPOT_NAME, response.getDepotName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("depot.status.active.changed")
							.replace(DEPOT_NAME, response.getDepotName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("depot.status.terminate.changed")
							.replace(DEPOT_NAME, response.getDepotName()));
				}

				return depotSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);

			}
		} catch (AFCSException e) {
			logger.error("DepotManagementController class :: updateDepotStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = DOWNLOAD_DEPOT_REPORT)
	public ModelAndView downloadRoleReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RoleController:: downloadRoleReport method");
		ModelAndView modelAndView = new ModelAndView(DEPOT_SEARCH);
		DepotSearchResponse orgResponse = null;
		try {
			DepotSearchRequest depotSearchRequest = (DepotSearchRequest) session.getAttribute(DEPOT_LIST_DATA);
			depotSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = depotSearchRequest.getPageSize();
			depotSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				depotSearchRequest.setPageIndex(Constants.ONE);
				depotSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			orgResponse = depotManagementService.searchDepot(depotSearchRequest);
			List<DepotSearchRequest> reuestResponses = orgResponse.getDepotListResponse();
			if (!StringUtil.isNull(orgResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadRoleReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			depotSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: RoleController:: downloadRoleReport method", e);
		}
		logger.info("Exit:: RoleController:: downloadRoleReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadRoleReport(List<DepotSearchRequest> orgResp,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Depot_");
		exportDetails.setHeaderMessageProperty("chatak.header.dtomanagement.messages");

		exportDetails.setHeaderList(getDepotHeaderList());
		exportDetails.setFileData(getDepotFileData(orgResp));
	}

	private List<String> getDepotHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("organizationList.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ptoManagement.file.exportutil.ptoname", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("depotManagement.file.exportutil.depotName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("depotManagement.file.exportutil.depotShortName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("depotManagement.file.exportutil.depotIncharge", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.mobileNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.Status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDepotFileData(List<DepotSearchRequest> orgResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (DepotSearchRequest roleData : orgResponse) {

			Object[] rowData = { roleData.getOrganizationName(), roleData.getPtoName(), roleData.getDepotName(),
					roleData.getDepotShortName(), roleData.getDepotIncharge(), roleData.getMobile(),
					roleData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}

}
