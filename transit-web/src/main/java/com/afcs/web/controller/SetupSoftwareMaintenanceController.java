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
import com.afcs.web.service.SetupSoftwareMaintenanceService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.SetupSoftwareUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SetupSoftwareMaintenanceController {

	@Autowired
	SetupSoftwareMaintenanceService setupSoftwareMaintenanceService;

	@Autowired
	Environment properties;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(SetupSoftwareMaintenanceController.class);

	private static final String SETUP_SOFTWARE_DATA_SIZE = "setupSoftwareDataSize";
	private static final String SETUP_SOFTWARE_SEARCH = "setup-software-search";
	private static final String SETUP_SOFTWARE_SEARCH_PAGINATION = "software-search-pagination";
	private static final String SETUP_SOFTWARE_VIEW_ACTION = "view-software-action";
	private static final String SETUP_SOFTWARE_EDIT_ACTION = "edit-software-action";
	private static final String SETUP_SOFTWARE_EDIT_REQ = "setupSoftwareEditRequest";
	private static final String SETUP_SOFTWARE_UPDATE = "setup-software-edit";
	private static final String UPDATE_SOFTWARE_STATUS = "update-software-status";
	private static final String SOFTWARE_SEARCH_REQ = "setupSoftwareSearchRequest";
	private static final String SETUP_SOFTWARE_REQUEST = "setupSoftwareRegistrationRequest";
	private static final String PTO_LIST = "ptoList";
	private static final String PTO_LIST_DATA = "ptoListData";
	private static final String SETUP_SOFT_INHERIT_PTO_LIST = "setupSoftwareInheritPtoList";
	private static final String SETUP_SOFTWARE_LIST_DATA = "setupSoftwareListData";
	private static final String DOWNLOAD_SOFTWARE_MAINTENANCE_REPORT = "downloadSoftwareMaintenanceReport";

	@GetMapping(value = "setup-software-registration")
	public ModelAndView setUpSoftwareMaintenanceRegisterGet(Map<String, Object> model, HttpSession session)
			throws AFCSException {
		ModelAndView modelandview = new ModelAndView("setup-software-registration");
		SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest = new SetupSoftwareRegistrationRequest();
		SetupSoftwareListDataResponse response = setupSoftwareMaintenanceService
				.getSoftwareListDataResponse(setupSoftwareRegistrationRequest);
		model.put(SETUP_SOFTWARE_REQUEST, setupSoftwareRegistrationRequest);
		if (!StringUtil.isNull(response)) {
			model.put(SETUP_SOFT_INHERIT_PTO_LIST, response.getListOfInheritAndPto());
			session.setAttribute(SETUP_SOFT_INHERIT_PTO_LIST, response.getListOfInheritAndPto());
		}
		model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		return modelandview;
	}

	@PostMapping(value = "setup-software-registration")
	public ModelAndView setUpSoftwareMaintenanceRegisterPost(Map<String, Object> model,
			SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest, HttpSession session)
			throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_SEARCH);
		SetupSoftwareSearchRequest setupSoftwareSearchRequest = new SetupSoftwareSearchRequest();
		model.put(SOFTWARE_SEARCH_REQ, setupSoftwareSearchRequest);
		SetupSoftwareRegistrationResponse setupSoftwareRegistrationResponse = new SetupSoftwareRegistrationResponse();
		SetupSoftwareRegistrationRequest request = new SetupSoftwareRegistrationRequest();
		try {
			setupSoftwareRegistrationResponse = setupSoftwareMaintenanceService
					.setupSoftwareRegistration(setupSoftwareRegistrationRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(setupSoftwareRegistrationResponse)
				&& setupSoftwareRegistrationResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("software.created.successfully"));
			model.put(SETUP_SOFTWARE_REQUEST, request);
		} else {
			model.put(Constants.ERROR, setupSoftwareRegistrationResponse.getStatusMessage());
		}

		return modelAndView;
	}

	@GetMapping(value = SETUP_SOFTWARE_SEARCH)
	public ModelAndView setUpSoftwareMaintenanceSearch(Map<String, Object> model, HttpSession session)
			throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_SEARCH);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		SetupSoftwareSearchRequest request = new SetupSoftwareSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
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
			request.setPageSize(10);
			request.setPageIndex(Constants.ONE);
			session.setAttribute(SETUP_SOFTWARE_LIST_DATA, request);
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
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

		model.put(SOFTWARE_SEARCH_REQ, request);
		model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		return modelAndView;
	}

	@PostMapping(value = SETUP_SOFTWARE_SEARCH)
	public ModelAndView setUpSoftwareMaintenanceSearchData(Map<String, Object> model,
			SetupSoftwareSearchRequest searchSetupSoftwareRequest, HttpSession session,
			@RequestParam("cancelTypeId") String cancelTypeId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_SEARCH);
		SetupSoftwareSearchResponse response = null;
		model.put(SOFTWARE_SEARCH_REQ, searchSetupSoftwareRequest);
		searchSetupSoftwareRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		if (!StringUtil.isNullEmpty(cancelTypeId) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return setupSoftwareSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		searchSetupSoftwareRequest.setPageIndex(Constants.ONE);
		try {
			if (searchSetupSoftwareRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
				response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(searchSetupSoftwareRequest);
			} else if (searchSetupSoftwareRequest.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
				searchSetupSoftwareRequest
						.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(searchSetupSoftwareRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					searchSetupSoftwareRequest.setPtoName(pto.getPtoName());
				}
				searchSetupSoftwareRequest.setPageIndex(Constants.ONE);
				searchSetupSoftwareRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				searchSetupSoftwareRequest.setPtoId(ptoList.getPtoList().get(0).getPtoMasterId());
				response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(searchSetupSoftwareRequest);
			}
			session.setAttribute(SETUP_SOFTWARE_LIST_DATA, searchSetupSoftwareRequest);

		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(SETUP_SOFTWARE_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(SOFTWARE_SEARCH_REQ, searchSetupSoftwareRequest);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(SETUP_SOFTWARE_LIST_DATA, response.getSetupSoftwareSearchList());
			model.put(SOFTWARE_SEARCH_REQ, searchSetupSoftwareRequest);
			model.put(SETUP_SOFTWARE_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, searchSetupSoftwareRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, SETUP_SOFTWARE_DATA_SIZE);
		}

		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, SetupSoftwareSearchRequest request, HttpSession session,
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

	@PostMapping(value = SETUP_SOFTWARE_SEARCH_PAGINATION)
	public ModelAndView setupSoftwareSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		SetupSoftwareSearchRequest searchSetupSoftwareRequest = (SetupSoftwareSearchRequest) session
				.getAttribute(SOFTWARE_SEARCH_REQ);
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_SEARCH);
		model.put(SOFTWARE_SEARCH_REQ, searchSetupSoftwareRequest);
		SetupSoftwareSearchResponse response = new SetupSoftwareSearchResponse();
		try {
			searchSetupSoftwareRequest.setPageIndex(pageNumber);
			response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(searchSetupSoftwareRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(SETUP_SOFTWARE_DATA_SIZE, response.getTotalRecords());
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(SETUP_SOFTWARE_LIST_DATA, response.getSetupSoftwareSearchList());
			model.put(SOFTWARE_SEARCH_REQ, searchSetupSoftwareRequest);
			session.setAttribute(SETUP_SOFTWARE_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, searchSetupSoftwareRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, SETUP_SOFTWARE_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = SETUP_SOFTWARE_VIEW_ACTION)
	public ModelAndView viewSetupSoftwareData(final HttpSession session, @RequestParam("softwareId") Long softwareId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("setup-software-view");
		SetupSoftwareSearchResponse response = new SetupSoftwareSearchResponse();
		model.put("softwareRegistrationRequest", new SetupSoftwareUpdateRequest());
		SetupSoftwareSearchRequest softwareSearchRequest = new SetupSoftwareSearchRequest();
		softwareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			softwareSearchRequest.setPageIndex(1);
			softwareSearchRequest.setSoftwareId(softwareId);
			if (softwareSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(softwareSearchRequest);

			} else if (softwareSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				softwareSearchRequest.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(softwareSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					softwareSearchRequest.setPtoName(pto.getPtoName());
				}
				softwareSearchRequest.setPageIndex(Constants.ONE);
				softwareSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(softwareSearchRequest);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			SetupSoftwareSearchRequest setupSoftwareSearchRequest = response.getSetupSoftwareSearchList().get(0);
			model.put("softwareRegistrationRequest", setupSoftwareSearchRequest);
		}
		return modelAndView;
	}

	private SetupSoftwareUpdateRequest populateviewRequest(SetupSoftwareSearchResponse response) {
		SetupSoftwareUpdateRequest request = new SetupSoftwareUpdateRequest();
		SetupSoftwareSearchRequest searchSoftwareRequest = response.getSetupSoftwareSearchList().get(0);
		request.setApplyDate(searchSoftwareRequest.getApplyDate());
		request.setDeliveryDate(searchSoftwareRequest.getDeliveryDate());
		request.setDescription(searchSoftwareRequest.getDescription());
		request.setInherit(searchSoftwareRequest.getInherit());
		request.setPtoId(searchSoftwareRequest.getPtoId());
		request.setStatus(searchSoftwareRequest.getStatus());
		request.setFullVersion(searchSoftwareRequest.getFullVersion());
		request.setVersionNumber(searchSoftwareRequest.getVersionNumber());
		request.setSoftwareId(searchSoftwareRequest.getSoftwareId());
		request.setOrganizationId(searchSoftwareRequest.getOrganizationId());
		return request;
	}

	@PostMapping(value = SETUP_SOFTWARE_EDIT_ACTION)
	public ModelAndView editSetupSoftwareData(final HttpSession session, @RequestParam("softwareId") Long softwareId,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_UPDATE);
		SetupSoftwareSearchResponse response = null;
		model.put(SETUP_SOFTWARE_EDIT_REQ, new SetupSoftwareUpdateRequest());
		SetupSoftwareRegistrationRequest setupSoftwareRegistrationRequest = new SetupSoftwareRegistrationRequest();
		SetupSoftwareListDataResponse dataResponse = setupSoftwareMaintenanceService
				.getSoftwareListDataResponse(setupSoftwareRegistrationRequest);
		SetupSoftwareUpdateRequest softwareMaintenanceData = new SetupSoftwareUpdateRequest();
		model.put(SETUP_SOFTWARE_EDIT_REQ, softwareMaintenanceData);
		model.put(SETUP_SOFT_INHERIT_PTO_LIST, dataResponse.getListOfInheritAndPto());
		session.setAttribute(SETUP_SOFT_INHERIT_PTO_LIST, dataResponse.getListOfInheritAndPto());
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = null;
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());

		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}

		model.put(PTO_LIST_DATA, ptoListResponse.getPtoList());
		session.setAttribute(PTO_LIST_DATA, ptoListResponse.getPtoList());

		try {
			SetupSoftwareSearchRequest setupSoftwareSearchRequest = new SetupSoftwareSearchRequest();
			setupSoftwareSearchRequest.setPageIndex(Constants.ONE);
			setupSoftwareSearchRequest.setSoftwareId(softwareId);
			setupSoftwareSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			response = setupSoftwareMaintenanceService.searchSoftwareMaintenance(setupSoftwareSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			softwareMaintenanceData = populateviewRequest(response);
			model.put(SETUP_SOFTWARE_EDIT_REQ, softwareMaintenanceData);
		}
		return modelAndView;
	}

	@PostMapping(value = SETUP_SOFTWARE_UPDATE)
	public ModelAndView updateSoftwareData(HttpSession session, Map<String, Object> model,
			SetupSoftwareUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_UPDATE);
		WebResponse response = null;
		model.put(SETUP_SOFTWARE_EDIT_REQ, request);
		try {
			response = setupSoftwareMaintenanceService.updateSoftwareMaintenance(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("software.maintenance.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(SETUP_SOFTWARE_EDIT_REQ, request);
		}

		return modelAndView;
	}

	@PostMapping(value = UPDATE_SOFTWARE_STATUS)
	public ModelAndView updateSoftwareStatus(final HttpSession session, @RequestParam("softwareId") Long softwareId,
			@RequestParam("status") String status, Map<String, Object> model) {

		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_SEARCH);
		model.put(SOFTWARE_SEARCH_REQ, new UserData());
		SetupSoftwareRegistrationRequest request = new SetupSoftwareRegistrationRequest();
		WebResponse response = null;
		try {
			request.setStatus(status);
			request.setSoftwareId(softwareId);
			response = setupSoftwareMaintenanceService.updateSoftwareStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("software.maintenance.status.suspend.changed"));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("software.maintenance.status.active.changed"));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS,
							properties.getProperty("software.maintenance.status.terminate.changed"));
				}
				return setupSoftwareSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
						model);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@GetMapping(value = "getVersionNumberByPtoName")
	public @ResponseBody String getVersionNumberByPtoName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session, @RequestParam("ptoName") String ptoName,
			SetupSoftwareListDataResponse response) throws JsonProcessingException, AFCSException {
		SetupSoftwareSearchRequest setupSoftwareSearchRequest = new SetupSoftwareSearchRequest();
		setupSoftwareSearchRequest.setPtoName(ptoName);
		SetupSoftwareListDataResponse setupSoftwareListData = null;
		setupSoftwareListData = setupSoftwareMaintenanceService.getVersionNumberByPtoName(setupSoftwareSearchRequest);
		if (!StringUtil.isNull(setupSoftwareListData)
				&& setupSoftwareListData.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			setupSoftwareListData.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(setupSoftwareListData);
		}
		return null;
	}
	@PostMapping(value =  DOWNLOAD_SOFTWARE_MAINTENANCE_REPORT)
	public ModelAndView downloadSetupSoftwareManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: SetupSoftwareManagementController:: downloadSetupSoftwareManagementReport method");
		ModelAndView modelAndView = new ModelAndView(SETUP_SOFTWARE_SEARCH);
		SetupSoftwareSearchResponse setupSoftwareSearchResponse = null;
		try {
			SetupSoftwareSearchRequest searchSetupSoftwareRequest = (SetupSoftwareSearchRequest) session.getAttribute(SETUP_SOFTWARE_LIST_DATA);
			searchSetupSoftwareRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = searchSetupSoftwareRequest.getPageSize();
			searchSetupSoftwareRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				searchSetupSoftwareRequest.setPageIndex(Constants.ONE);
				searchSetupSoftwareRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			setupSoftwareSearchResponse =setupSoftwareMaintenanceService.searchSoftwareMaintenance(searchSetupSoftwareRequest);
			List<SetupSoftwareSearchRequest> reuestResponses = setupSoftwareSearchResponse.getSetupSoftwareSearchList();
			if (!StringUtil.isNull(setupSoftwareSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadUserManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			searchSetupSoftwareRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::SetupSoftwareManagementController:: SetupSoftwareManagementReport method", e);
		}
		logger.info("Exit:: SetupSoftwareManagementController:: downloadSetupSoftwareManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadUserManagementReport(List<SetupSoftwareSearchRequest> searchSetupSoftwareRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("SetupSoftware_");
		exportDetails.setHeaderMessageProperty("chatak.header.setupSoftware.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(searchSetupSoftwareRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("SetupSoftware.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.versionNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.inherit", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.deliveryDate", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.applyDate", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.description", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.fullVersion", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("SetupSoftware.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<SetupSoftwareSearchRequest> searchSetupSoftwareRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (SetupSoftwareSearchRequest softwareData : searchSetupSoftwareRequest) {

			Object[] rowData = { softwareData.getOrganizationName() , softwareData.getPtoName() , softwareData.getVersionNumber() , softwareData.getInherit() ,          
					softwareData.getDeliveryDate() , softwareData.getApplyDate() , softwareData.getDescription() , softwareData.getFullVersion() ,
					softwareData.getStatus()  
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}
}
