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
import com.afcs.web.service.RegionManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class OrganizationManagementController {

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	RegionManagementService regionManagementService;

	@Autowired
	Environment properties;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationManagementController.class);

	private static final String ORGANIZATION_REGISTRATION = "organization-registration";
	private static final String ORGANIZATION_DATA_SIZE = "totalRecords";
	private static final String ORGANIZATION_SEARCH = "organization-search";
	private static final String ORGANIZATION_SEARCH_PAGINATION = "organization-search-pagination";
	private static final String ORGANIZATION_EDIT_REQ = "organizationEditRequest";
	private static final String ORGANIZATION_EDIT_ACTION = "edit-organization-action";
	private static final String ORGANIZATION_VIEW_ACTION = "view-organization-action";
	private static final String ORGANIZATION_UPDATE = "update-organization";
	private static final String UPDATE_ORGANIZATION_STATUS = "update-organization-status";
	private static final String ORGANIZATION_SEARCH_REQ = "organizationSearch";
	private static final String ORGANIZATION_LIST = "organizationList";
	private static final String ORGANIZATION_NAME = "&&organizationName";
	private static final String ERROR_ORG_SEARCH = "ERROR :: OrganizationManagementController class :: organizationSearch method :: exception";
	private static final String DOWNLOAD_ROLE_REPORT = "downloadRoleport";
	private static final String ORGANIZATION_LIST_DATA = "organizationListData";

	@GetMapping(value = ORGANIZATION_REGISTRATION)
	public ModelAndView createOrganization(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(ORGANIZATION_REGISTRATION);
		OrganizationRegistrationRequest organizationRegistrationRequest = new OrganizationRegistrationRequest();
		organizationRegistrationRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		model.put("organizationRegistrationRequest", organizationRegistrationRequest);
		getAllStates(model, session);
		return modelandview;
	}

	private void getAllStates(Map<String, Object> model, HttpSession session) {
		StateListResponse stateResponse = new StateListResponse();
		stateResponse = regionManagementService.getAllStates(stateResponse);
		if (!StringUtil.isNull(stateResponse) && stateResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("stateList", stateResponse.getStateList());
			session.setAttribute("stateList", stateResponse.getStateList());
		}
	}

	@PostMapping(value = ORGANIZATION_REGISTRATION)
	public ModelAndView createOrganization(Map<String, Object> model, OrganizationRegistrationRequest request,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(ORGANIZATION_SEARCH);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		OrganizationRegistrationResponse response = null;
		model.put(ORGANIZATION_SEARCH_REQ, organizationSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		try {
			response = organizationManagementService.createOrganization(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: OrganizationManagementController class :: createOrganization method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			modelAndView = organizationSearch(model, session);
			model.put(Constants.SUCCESS, properties.getProperty("organization.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = ORGANIZATION_SEARCH)
	public ModelAndView organizationSearch(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(ORGANIZATION_SEARCH);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(ORGANIZATION_SEARCH_REQ, organizationSearchRequest);
		OrganizationSearchResponse response = null;

		try {
			organizationSearchRequest.setPageSize(10);
			organizationSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(ORGANIZATION_LIST_DATA, organizationSearchRequest);
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService
						.getOrganizationListWithStatusNotTerminated(organizationSearchRequest);
			} else {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService
						.getOrganizationListWithStatusNotTerminated(organizationSearchRequest);
			}

		} catch (AFCSException e) {
			logger.error(ERROR_ORG_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("organizationNotTerminatedList", response.getOrganizationList());
			session.setAttribute("organizationNotTerminatedList", response.getOrganizationList());
		}
		try {
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			}

		} catch (AFCSException e) {
			logger.error(ERROR_ORG_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(ORGANIZATION_LIST, response.getOrganizationList());
			session.setAttribute(ORGANIZATION_LIST, response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = ORGANIZATION_SEARCH)
	public ModelAndView organizationSearch(Map<String, Object> model,
			OrganizationSearchRequest organizationSearchRequest, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) {
		ModelAndView modelAndView = new ModelAndView(ORGANIZATION_SEARCH);
		OrganizationSearchResponse response = null;
		model.put(ORGANIZATION_SEARCH_REQ, new OrganizationSearchRequest());
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return organizationSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		organizationSearchRequest.setPageIndex(Constants.ONE);
		try {
			response = organizationManagementService.searchOrganization(organizationSearchRequest);
			session.setAttribute(ORGANIZATION_LIST_DATA, organizationSearchRequest);
		} catch (AFCSException e) {
			logger.error(ERROR_ORG_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(ORGANIZATION_DATA_SIZE, response.getTotalRecords());
			session.setAttribute("searchOrganizationRequest", organizationSearchRequest);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(ORGANIZATION_SEARCH_REQ, organizationSearchRequest);
			model.put(ORGANIZATION_LIST, session.getAttribute(ORGANIZATION_LIST));
			model.put(ORGANIZATION_DATA_SIZE, response.getTotalRecords());
			model.put(ORGANIZATION_LIST_DATA, response.getOrganizationList());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, ORGANIZATION_DATA_SIZE);
		}

		return modelAndView;
	}

	@PostMapping(value = ORGANIZATION_SEARCH_PAGINATION)
	public ModelAndView organizationSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		OrganizationSearchRequest organizationSearchRequest = (OrganizationSearchRequest) session
				.getAttribute("searchOrganizationRequest");
		ModelAndView modelAndView = new ModelAndView(ORGANIZATION_SEARCH);
		model.put(ORGANIZATION_SEARCH_REQ, organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		organizationSearchRequest.setPageIndex(pageNumber);
		try {
			response = organizationManagementService.searchOrganization(organizationSearchRequest);
		} catch (AFCSException e) {
			logger.error(
					"ERROR :: OrganizationManagementController class :: organizationSearchDataPagination method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(ORGANIZATION_LIST_DATA, response.getOrganizationList());
			model.put(ORGANIZATION_SEARCH_REQ, organizationSearchRequest);
			session.setAttribute(ORGANIZATION_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, ORGANIZATION_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = ORGANIZATION_EDIT_ACTION)
	public ModelAndView showOrganizationUpdatePage(final HttpSession session,
			@RequestParam("orgId") Long orgId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("organization-edit");
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		model.put(ORGANIZATION_EDIT_REQ, new OrganizationUpdateRequest());
		try {
			getAllStates(model, session);
			OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
			organizationSearchRequest.setPageIndex(1);
			organizationSearchRequest.setOrgId(orgId);
			response = organizationManagementService.searchOrganization(organizationSearchRequest);

		} catch (AFCSException e) {
			logger.error(
					"ERROR :: OrganizationManagementController class :: showOrganizationUpdatePage method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StateRequest stateRequest = new StateRequest();
			CityResponse cityResponse = new CityResponse();
			if (!StringUtil.isNullEmpty(response.getOrganizationList().get(0).getState())) {
				stateRequest.setStateName(response.getOrganizationList().get(0).getState());
				int stateId = regionManagementService.getStateId(stateRequest);
				stateRequest.setStateId(stateId);
				cityResponse = regionManagementService.getCities(stateRequest, cityResponse);
				model.put("cityList", cityResponse.getCityList());
			}
			OrganizationUpdateRequest organizationUpdateRequest = populateOrganizationUpdateData(response);
			model.put(ORGANIZATION_EDIT_REQ, organizationUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = ORGANIZATION_UPDATE)
	public ModelAndView updateOrganization(HttpSession session, Map<String, Object> model,
			OrganizationUpdateRequest request, @RequestParam("cancelTypeId") String cancelType) {
		ModelAndView modelAndView = new ModelAndView("organization-edit");
		WebResponse response = null;
		model.put(ORGANIZATION_EDIT_REQ, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return organizationSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = organizationManagementService.updateOrganization(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: OrganizationManagementController class :: updateOrganization method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StateRequest stateRequest = new StateRequest();
			CityResponse cityResponse = new CityResponse();
			model.put(Constants.SUCCESS, properties.getProperty("organization.update.success"));
			stateRequest.setStateName(request.getState());
			int stateId = regionManagementService.getStateId(stateRequest);
			stateRequest.setStateId(stateId);
			cityResponse = regionManagementService.getCities(stateRequest, cityResponse);
			model.put("cityList", cityResponse.getCityList());
			model.put(ORGANIZATION_EDIT_REQ, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(ORGANIZATION_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_ORGANIZATION_STATUS)
	public ModelAndView updateOrganizationStatus(final HttpSession session,
			@RequestParam("orgId") Long orgId, @RequestParam("status") String status,
			@RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(ORGANIZATION_SEARCH);
		model.put(ORGANIZATION_SEARCH_REQ, new OrganizationSearchRequest());
		OrganizationStatusUpdateRequest request = new OrganizationStatusUpdateRequest();
		OrganizationSearchResponse response = null;
		try {
			request.setStatus(status);
			request.setUserId(session.getAttribute("userId").toString());
			request.setOrgId(orgId);
			request.setReason(reason);
			response = organizationManagementService.updateOrganizationStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("organization.status.suspend.changed")
							.replace(ORGANIZATION_NAME, response.getOrganizationName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("organization.status.active.changed")
							.replace(ORGANIZATION_NAME, response.getOrganizationName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("organization.status.terminate.changed")
							.replace(ORGANIZATION_NAME, response.getOrganizationName()));
				}

				return organizationSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
						model);
			}
		} catch (AFCSException e) {
			logger.error(
					"ERROR :: OrganizationManagementController class :: updateOrganizationStatus method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = ORGANIZATION_VIEW_ACTION)
	public ModelAndView viewOrganizationData(final HttpSession session,
			@RequestParam("orgId") Long orgId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("organization-view");
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		model.put(ORGANIZATION_EDIT_REQ, new OrganizationUpdateRequest());
		try {
			OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
			organizationSearchRequest.setPageIndex(1);
			organizationSearchRequest.setOrgId(orgId);
			response = organizationManagementService.searchOrganization(organizationSearchRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: PtoOperationManagementController class :: viewOrganizationData method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			OrganizationUpdateRequest organizationUpdateRequest = populateOrganizationUpdateData(response);
			model.put(ORGANIZATION_EDIT_REQ, organizationUpdateRequest);
		}
		return modelAndView;
	}

	protected OrganizationUpdateRequest populateOrganizationUpdateData(OrganizationSearchResponse response) {
		OrganizationUpdateRequest request = new OrganizationUpdateRequest();
		OrganizationSearchRequest organizationSearchRequest = response.getOrganizationList().get(0);
		request.setContactPerson(organizationSearchRequest.getContactPerson());
		request.setOrganizationEmail(organizationSearchRequest.getOrganizationEmail());
		request.setOrgId(organizationSearchRequest.getOrgId());
		request.setOrganizationMobile(organizationSearchRequest.getOrganizationMobile());
		request.setOrganizationName(organizationSearchRequest.getOrganizationName());
		request.setState(organizationSearchRequest.getState());
		request.setCity(organizationSearchRequest.getCity());
		request.setSiteUrl(organizationSearchRequest.getSiteUrl());
		return request;
	}

	@PostMapping(value = DOWNLOAD_ROLE_REPORT)
	public ModelAndView downloadRoleReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RoleController:: downloadRoleReport method");
		ModelAndView modelAndView = new ModelAndView(ORGANIZATION_SEARCH);
		OrganizationSearchResponse orgResponse = null;
		try {
			OrganizationSearchRequest organizationSearchRequest = (OrganizationSearchRequest) session.getAttribute(ORGANIZATION_LIST_DATA);
			organizationSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = organizationSearchRequest.getPageSize();
			if (downloadAllRecords) {
				organizationSearchRequest.setPageIndex(Constants.ONE);
				organizationSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			orgResponse = organizationManagementService.searchOrganization(organizationSearchRequest);
			List<OrganizationSearchRequest> reuestResponses = orgResponse.getOrganizationList();
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
			organizationSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: RoleController:: downloadRoleReport method", e);
		}
		logger.info("Exit:: RoleController:: downloadRoleReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadRoleReport(List<OrganizationSearchRequest> orgResp,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Organization_");
		exportDetails.setHeaderMessageProperty("chatak.header.organization.messages");

		exportDetails.setHeaderList(getOrganizationHeaderList());
		exportDetails.setFileData(getOrganizationFileData(orgResp));
	}

	private List<String> getOrganizationHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("organizationList.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.contactPerson", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.mobileNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.emailId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.state", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.City", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.Site_URL", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("organizationList.file.exportutil.Status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getOrganizationFileData(List<OrganizationSearchRequest> orgResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (OrganizationSearchRequest roleData : orgResponse) {

			Object[] rowData = { roleData.getOrganizationName(), roleData.getContactPerson(),
					roleData.getOrganizationMobile(), roleData.getOrganizationEmail(), roleData.getState(),
					roleData.getCity(), roleData.getSiteUrl(), roleData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
}
