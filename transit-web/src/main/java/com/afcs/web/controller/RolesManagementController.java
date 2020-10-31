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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.RolesManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.EditRoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class RolesManagementController {

	private static Logger logger = LoggerFactory.getLogger(RolesManagementController.class);

	@Autowired
	Environment properties;

	@Autowired
	RolesManagementService rolesManagementService;
	
	@Autowired
	private MessageSource messageSource;

	public static final String EDIT_ROLE_RESPONSE_DATA = "transit-admin-edit-role";
	public static final String SHOW_TRANSIT_ROLE_EDIT = "role-edit-action";
	public static final String UPDATE_TRANSIT_ROLE = "transitUpdateRole";
	public static final String TRANSIT_ADMIN_ROLE_CATEGORY = "getRoleCategory";
	public static final String TRANSIT_ROLE_CREATE_PAGE = "afcs-admin-role-create";
	public static final String TRANSIT_ADMIN_ADD_ROLE = "transitAdminAddRole";
	public static final String SHOW_TRANSIT_ROLE_CREATE = "showCreateRole";
	private static final String ROLE_SEARCH = "role-search";
	private static final String ROLE_SARCH = "roleSearch";
	private static final String ROLE_SEARCH_PAGINATION = "role-search-pagination";
	private static final String ROLE_SEARCH_REQUEST = "roleSearchRequest";
	private static final String ROLE_DATA = "roleData";
	private static final String ROLE_DATA_SIZE = "roleDataSize";
	private static final String USER_ROLE_REQ = "userRoleRequest";
	private static final String EXISTING_FEATURES = "existingFeatures";
	private static final String FEATURE_LIST = "featureList";
	public static final String TRANSIT_ROLE_REQUEST = "transitRoleRequest";
	public static final String DOWNLOAD_ROLE_REPORT = "downloadRoleReport";
	public static final String ROLE_NAME = "&&roleName";
	
	@GetMapping(value = ROLE_SEARCH)
	public ModelAndView roleSearchGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(ROLE_SEARCH);
		RoleRequest roleRequest = new RoleRequest();
		roleRequest.setPageSize(10);
		roleRequest.setPageIndex(Constants.ONE);
		session.setAttribute(ROLE_DATA, roleRequest);
		model.put(ROLE_SARCH, roleRequest);
		return modelAndView;
	}

	@PostMapping(value = ROLE_SEARCH)
	public ModelAndView searchRolePost(Map<String, Object> model, RoleRequest request, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(ROLE_SEARCH);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			model.put(ROLE_SARCH, new RoleRequest());
			return roleDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		request.setPageSize(Constants.ONE);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		request.setCreatedBy(session.getAttribute(Constants.USER_ID).toString());
		model.put(ROLE_SARCH, new RoleRequest());
		session.setAttribute(ROLE_SEARCH_REQUEST, request);
		RolesSearchResponse response = rolesManagementService.searchRole(request);
		session.setAttribute(ROLE_DATA, request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(ROLE_DATA, response.getListOfAfcsRole());
			session.setAttribute(ROLE_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(ROLE_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(ROLE_SARCH, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		PaginationUtil.performPagination(modelAndView, session, Constants.ONE, ROLE_DATA_SIZE);
		return modelAndView;
	}

	@PostMapping(value = ROLE_SEARCH_PAGINATION)
	public ModelAndView roleDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		RoleRequest request = (RoleRequest) session.getAttribute(ROLE_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(ROLE_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		RolesSearchResponse response = new RolesSearchResponse();
		try {
			request.setPageSize(pageNumber);
			response = rolesManagementService.searchRole(request);
		} catch (AFCSException e) {
			logger.error("RolesManagementController class :: roleDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(ROLE_DATA, response.getListOfAfcsRole());
			model.put(ROLE_SARCH, request);
			session.setAttribute(ROLE_DATA_SIZE, response.getTotalNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, ROLE_DATA_SIZE);
		}
		return modelAndView;
	}

	@GetMapping(value = SHOW_TRANSIT_ROLE_CREATE)
	public ModelAndView showCreateRole(HttpServletRequest request, HttpServletResponse response,
			RoleRequest roleRequest, Map<String, Object> model, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView(TRANSIT_ROLE_CREATE_PAGE);

		try {
			RoleRequest userRoleRequest = new RoleRequest();
			List<RoleLevel> roleLeveList = new ArrayList<>();
			List<RoleLevel> getAllTypeRoleLogin = getRoleListForRoles(session, roleLeveList);
			model.put("roleLevelList", getAllTypeRoleLogin);
			model.put(USER_ROLE_REQ, userRoleRequest);
			TransitFeatureRequest prepaidFeatureRequest = new TransitFeatureRequest();
			List<TransitFeatureRequest> featureList = null;

			if (roleRequest.getRoleCategory() == null || roleRequest.getRoleCategory().isEmpty()) {
				prepaidFeatureRequest.setRoleType(RoleLevel.SUPER_ADMIN.getValue());
				featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
			} else {

				if (roleRequest.getRoleCategory().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
					prepaidFeatureRequest.setRoleType(RoleLevel.SUPER_ADMIN.getValue());
					featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
				} else if (roleRequest.getRoleCategory().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
					prepaidFeatureRequest.setRoleType(RoleLevel.ORG_ADMIN.getValue());
					featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
				} else if (roleRequest.getRoleCategory().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
					prepaidFeatureRequest.setRoleType(RoleLevel.PTO_ADMIN.getValue());
					featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
				}
			}

			model.put(USER_ROLE_REQ, userRoleRequest);
			List<TransitFeatureRequest> featureList2 = getAssignedFeatureList(featureList, session);
			modelAndView.addObject(FEATURE_LIST, featureList2);

		}

		catch (Exception e) {
			logger.error("RolesManagementController class :: showCreateRole method :: exception", e);
		}
		return modelAndView;
	}

	private List<TransitFeatureRequest> getAssignedFeatureList(List<TransitFeatureRequest> prepaidFeatureRequestList,
			HttpSession session) {
		List<TransitFeatureRequest> featureList2 = new ArrayList<>();
		if (StringUtil.isListNotNullNEmpty(prepaidFeatureRequestList)) {
			String featureId = (String) session.getAttribute(EXISTING_FEATURES);
			for (TransitFeatureRequest featureIds : prepaidFeatureRequestList) {
				if (featureId.contains(featureIds.getFeatureId().toString())) {
					TransitFeatureRequest prepaidFeatureRequest2 = new TransitFeatureRequest();
					prepaidFeatureRequest2.setFeatureId(featureIds.getFeatureId());
					prepaidFeatureRequest2.setName(featureIds.getName());
					prepaidFeatureRequest2.setCreatedDate(featureIds.getCreatedDate());
					prepaidFeatureRequest2.setFeatureLevel(featureIds.getFeatureLevel());
					prepaidFeatureRequest2.setRefFeatureId(featureIds.getRefFeatureId());
					prepaidFeatureRequest2.setStatus(featureIds.getStatus());
					featureList2.add(prepaidFeatureRequest2);
				}
			}
		}
		return featureList2;
	}

	public List<RoleLevel> getRoleListForRoles(HttpSession session, List<RoleLevel> roleLevels) {
		TransactionResponse loginResponse = (TransactionResponse) session.getAttribute(Constants.LOGIN_RESPONSE);

		if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.name())) {
			roleLevels = Arrays.asList(RoleLevel.SUPER_ADMIN, RoleLevel.ORG_ADMIN, RoleLevel.PTO_ADMIN);
		}

		if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.name())) {
			roleLevels = Arrays.asList(RoleLevel.ORG_ADMIN, RoleLevel.PTO_ADMIN);
		}

		if (loginResponse.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.name())) {
			roleLevels = Arrays.asList(RoleLevel.PTO_ADMIN);
		}
		return roleLevels;
	}

	@PostMapping(value = TRANSIT_ADMIN_ADD_ROLE)
	public ModelAndView processCreateRole(HttpServletRequest request, Map<String, Object> model, HttpSession session,
			RoleRequest userRoleRequest, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(ROLE_SEARCH);
		model.put(ROLE_SARCH, new RoleRequest());
		userRoleRequest.setCreatedBy(session.getAttribute(Constants.USER_ID).toString());
		WebResponse webResponse = rolesManagementService.addOrUpdateRole(userRoleRequest);
		if (!StringUtil.isNull(webResponse) && webResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("role.profile.create.success"));
			modelAndView.addObject(Constants.SUCCESS, properties.getProperty("role.profile.create.success"));
		} else {
			model.put(Constants.ERROR, webResponse.getStatusMessage());
		}

		return modelAndView;
	}

	@PostMapping(value = TRANSIT_ADMIN_ROLE_CATEGORY)
	public ModelAndView roleCategoryByType(HttpServletRequest request, HttpServletResponse response,
			RoleRequest roleRequest, @RequestParam("roleCategory") final String roleCategory, Map model,
			HttpSession session) {
		roleRequest.setRoleCategory(roleCategory);

		ModelAndView modelAndView = new ModelAndView(TRANSIT_ROLE_CREATE_PAGE);

		try {
			List<RoleLevel> roleLeveList = new ArrayList<>();
			List<RoleLevel> getAllTypeRoleLogin = getRoleListForRoles(session, roleLeveList);
			model.put("roleLevelList", getAllTypeRoleLogin);
			model.put(USER_ROLE_REQ, roleRequest);
			model.put("roleCategory", roleCategory);
			TransitFeatureRequest prepaidFeatureRequest = new TransitFeatureRequest();
			List<TransitFeatureRequest> featureList = null;

			if (roleRequest.getRoleCategory() == null || roleRequest.getRoleCategory().isEmpty()) {
				prepaidFeatureRequest.setRoleType(RoleLevel.SUPER_ADMIN.getValue());
				featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
			} else {

				if (roleRequest.getRoleCategory().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
					prepaidFeatureRequest.setRoleType(RoleLevel.SUPER_ADMIN.getValue());
					featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
				} else if (roleRequest.getRoleCategory().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
					prepaidFeatureRequest.setRoleType(RoleLevel.ORG_ADMIN.getValue());
					featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
				} else if (roleRequest.getRoleCategory().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
					prepaidFeatureRequest.setRoleType(RoleLevel.PTO_ADMIN.getValue());
					featureList = rolesManagementService.getFeatueMapOnUserLevel(prepaidFeatureRequest);
				}
			}

			model.put(USER_ROLE_REQ, roleRequest);
			List<TransitFeatureRequest> featureList2 = getAssignedFeatureList(featureList, session);
			modelAndView.addObject(FEATURE_LIST, featureList2);

		}

		catch (Exception e) {
			logger.error("RolesManagementController class :: roleCategoryByType method :: exception", e);
		}
		return modelAndView;

	}

	@PostMapping(value = SHOW_TRANSIT_ROLE_EDIT)
	public ModelAndView showEditRole(HttpSession session, RoleRequest transitRoleRequest, BindingResult bindingResult,
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(EDIT_ROLE_RESPONSE_DATA);
		try {
			EditRoleResponse showEditRoleResponseData = rolesManagementService.getRoleDetail(transitRoleRequest);
			session.setAttribute("showEditRoleResponseData", showEditRoleResponseData);
			model.put("SAME_ROLE_FLAG", "false");

			TransitFeatureRequest transitFeatureRequest = new TransitFeatureRequest();

			model.put("roleId", showEditRoleResponseData.getRoleRequest().getRoleId().toString());
			transitRoleRequest.setRoleName(showEditRoleResponseData.getRoleRequest().getRoleName());
			transitRoleRequest.setDescription(showEditRoleResponseData.getRoleRequest().getDescription());
			transitRoleRequest.setRoleCategory(showEditRoleResponseData.getRoleRequest().getRoleCategory());
			transitFeatureRequest.setRoleType(transitRoleRequest.getRoleCategory());
			model.put(TRANSIT_ROLE_REQUEST, transitRoleRequest);
			List<TransitFeatureRequest> featureList = rolesManagementService
					.getFeatueMapOnUserLevel(transitFeatureRequest);
			model.put(TRANSIT_ROLE_REQUEST, transitRoleRequest);

			List<TransitFeatureRequest> featureList2 = getAssignedFeatureList(featureList, session);
			model.put(FEATURE_LIST, featureList2);

			for (TransitFeatureRequest feature : featureList2) {
				feature.setRoleFeatureId("|" + feature.getFeatureId().toString() + "|");
			}
			StringBuilder roleExistingFeature = new StringBuilder();
			for (Long feature : showEditRoleResponseData.getExistingFeatures()) {
				roleExistingFeature.append("|" + feature);
			}
			roleExistingFeature.append("|");
			String roleExistingFeatures = roleExistingFeature.toString();
			session.setAttribute("roleExistingFeatures", roleExistingFeatures);
			model.put(TRANSIT_ROLE_REQUEST, transitRoleRequest);
		} catch (Exception e) {
			logger.error("RolesManagementController class :: showEditRole method :: exception", e);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_TRANSIT_ROLE)
	public ModelAndView updateAgentRole(HttpServletRequest request, Map<String, Object> model, RoleRequest createRole,
			BindingResult bindingResult, HttpSession session, HttpServletResponse response,
			@RequestParam("cancelTypeId") String cancelType) {

		ModelAndView modelAndView = new ModelAndView(EDIT_ROLE_RESPONSE_DATA);
		model.put("agentRoleRequest", createRole);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return roleDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		WebResponse updateResponse = rolesManagementService.addOrUpdateRole(createRole);
		if (!StringUtil.isNull(updateResponse) && updateResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			showEditRole(session, createRole, bindingResult, model, request, response);
			model.put(Constants.SUCCESS, properties.getProperty("role.profile.update.success"));
		}
		return modelAndView;

	}

	@PostMapping(value = "updateRoleStatus")
	public ModelAndView updateRoleStatus(HttpSession session, Map<String, Object> model,
			@RequestParam("roleId") String roleId, @RequestParam("roleStatus") String status,
			@RequestParam("reason") String reason) {
		ModelAndView modelAndView = new ModelAndView(ROLE_SEARCH);

		RoleRequest roleRequest = new RoleRequest();
		RolesSearchResponse response = new RolesSearchResponse();
		try {
			roleRequest.setRoleId((Long.valueOf(roleId)));
			roleRequest.setStatus(status);
			roleRequest.setReason(reason);
			response = rolesManagementService.updateRoleStatus(roleRequest);
		} catch (AFCSException e) {
			logger.error("RolesManagementController class :: updateRoleStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("role.status.suspend.changed")
						.replaceAll(ROLE_NAME, response.getListOfAfcsRole().get(0).getRoleName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("role.status.active.changed")
						.replaceAll(ROLE_NAME, response.getListOfAfcsRole().get(0).getRoleName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("role.status.terminate.changed")
						.replaceAll(ROLE_NAME, response.getListOfAfcsRole().get(0).getRoleName()));
			}
			return roleDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		return modelAndView;
	}
   
	@PostMapping(value =  DOWNLOAD_ROLE_REPORT)
	public ModelAndView downloadRoleManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RoleManagementController:: downloadRoleManagementReport method");
		ModelAndView modelAndView = new ModelAndView(ROLE_SEARCH);
		RolesSearchResponse rolesSearchResponse = null;
		try {
			RoleRequest roleRequest = (RoleRequest) session.getAttribute(ROLE_DATA);
			roleRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = roleRequest.getPageSize();
			roleRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				roleRequest.setPageIndex(Constants.ONE);
				roleRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			rolesSearchResponse = rolesManagementService.searchRole(roleRequest);
			List<RoleRequest> reuestResponses = rolesSearchResponse.getListOfAfcsRole();
			if (!StringUtil.isNull(rolesSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadRoleManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			roleRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::RoleManagementController:: RoleManagementReport method", e);
		}
		logger.info("Exit:: RoleManagementController:: downloadRoleManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadRoleManagementReport(List<RoleRequest> roleRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Roles_");
		exportDetails.setHeaderMessageProperty("chatak.header.roles.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(roleRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("Role.file.exportutil.roleName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Role.file.exportutil.discription", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Role.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<RoleRequest> roleRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (RoleRequest roleData : roleRequest) {

			Object[] rowData = { roleData.getRoleName() , roleData.getDescription() , roleData.getStatus() 
			};

			fileData.add(rowData);
		}

		return fileData;
	}
}
