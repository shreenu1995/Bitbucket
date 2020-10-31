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
import com.afcs.web.service.RolesManagementService;
import com.afcs.web.service.UserManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleResponse;
import com.chatak.transit.afcs.server.pojo.web.SearchUserResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class UserManagementController {

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	Environment properties;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	RolesManagementService rolesManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;
	
	@Autowired
	private MessageSource messageSource;

	private static Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	private static final String USER_REGISTER = "user-registration";
	private static final String USER_REGISTER_OBJ = "userRegister";
	private static final String USER_EXISTS_CHECK = "userExistsCheck";
	private static final String USER_DATA_SIZE = "userDataSize";
	private static final String USER_SEARCH = "user-search";
	private static final String USER_SEARCH_PAGINATION = "user-search-pagination";
	private static final String USER_EDIT_REQ = "userEditRequest";
	private static final String USER_EDIT_ACTION = "edit-user-action";
	private static final String USER_UPDATE = "update-user";
	private static final String UPDATE_USER_STATUS = "update-user-status";
	private static final String USER_SEARCH_DATA = "userSearch";
	private static final String USER_VIEW_ACTION = "view-user-action";
	private static final String USER_VIEW_REQ = "userViewRequest";
	private static final String USER_NAME = "&&userName";
	private static final String ERROR_USER_SEARCH = "ERROR :: UserManagementController class :: userSearch method :: exception";
	private static final String DOWNLOAD_USER_REPORT = "downloadUserReport";
  	private static final String USER_LIST_DATA = "userListData";
	
   @GetMapping(value = USER_REGISTER)
	public ModelAndView userRegister(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(USER_REGISTER);
		UserRegistrationRequest userRegister = new UserRegistrationRequest();
		model.put(USER_REGISTER_OBJ, userRegister);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		try {
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoManagementService.getActivePtoList(ptoListRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoManagementService.getActivePtoList(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getActivePtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error(ERROR_USER_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.PTO_LIST, ptoListResponse.getPtoList());
		return modelAndView;
	}

	@PostMapping(value = USER_REGISTER)
	public ModelAndView userRegisterData(Map<String, Object> model, HttpSession session,
			UserRegistrationRequest request) {
		ModelAndView modelAndView = new ModelAndView(USER_SEARCH);
		UserData userData = new UserData();
		model.put(USER_SEARCH_DATA, userData);
		WebResponse response = null;
		request.setAdminUserId(session.getAttribute(Constants.USER_ID).toString());
		try {
			response = userManagementService.userRegister(request);
		} catch (AFCSException e) {
			model.put(USER_REGISTER_OBJ, request);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("user.created.successfully"));
		} else {
			model.put(USER_REGISTER_OBJ, request);
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = USER_EXISTS_CHECK)
	public @ResponseBody String userExistsCheck(@RequestParam("email") final String email) {
		return userManagementService.emailExistsCheck(email);
	}

	@GetMapping(value = USER_SEARCH)
	public ModelAndView userSearch(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(USER_SEARCH);
		UserData userData = new UserData();
		model.put(USER_SEARCH_DATA, userData);
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		RoleRequest roleRequest = new RoleRequest();
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("loginUserType", session.getAttribute(Constants.USER_TYPE).toString());
		session.setAttribute("loginUserType", session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		RoleResponse roleResponse = new RoleResponse();
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		try {
			userData.setPageSize(10);
			userData.setPageIndex(Constants.ONE);
			session.setAttribute(USER_LIST_DATA, userData);
			if(session.getAttribute(Constants.USER_TYPE).toString().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				roleRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				roleRequest.setRoleCategory(RoleLevel.SUPER_ADMIN.getValue());
				roleResponse = rolesManagementService.getRoleListByUserType(roleRequest);
			} else if(session.getAttribute(Constants.USER_TYPE).toString().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				roleRequest.setRoleCategory(RoleLevel.ORG_ADMIN.getValue());
				roleRequest.setCreatedBy(session.getAttribute(Constants.USER_ID).toString());
				roleRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				roleResponse = rolesManagementService.getRoleListByUserType(roleRequest);
			} else {
				roleRequest.setRoleCategory((String) session.getAttribute(Constants.USER_TYPE));
				roleRequest.setUserType((String) session.getAttribute(Constants.USER_TYPE));
				roleRequest.setCreatedBy((String) session.getAttribute(Constants.USER_ID));
				roleResponse = rolesManagementService.getRoleListByUserType(roleRequest);
			}
			
		} catch (AFCSException e) {
			logger.error(ERROR_USER_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.ROLE_LIST, roleResponse.getRoleList());
		session.setAttribute(Constants.ROLE_LIST, roleResponse.getRoleList());
		RoleRequest userRoleRequest = new RoleRequest();
		List<RoleLevel> roleLeveList = new ArrayList<>();
		List<RoleLevel> getAllTypeRoleLogin = getRoleListForRoles(session, roleLeveList);
		model.put("roleLevelList", getAllTypeRoleLogin);
		session.setAttribute("roleLevelList", getAllTypeRoleLogin);
		model.put("userRoleRequest", userRoleRequest);
		try {
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListRequest.setOrgId(null);
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {

				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error(ERROR_USER_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.PTO_LIST, ptoListResponse.getPtoList());
		session.setAttribute(Constants.PTO_LIST, ptoListResponse.getPtoList());
		try {
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				// do Nothing
			}
		} catch (AFCSException e) {
			logger.error(ERROR_USER_SEARCH, e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
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

	@PostMapping(value = USER_SEARCH)
	public ModelAndView userSearchData(Map<String, Object> model, UserData request, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) {
		ModelAndView modelAndView = new ModelAndView(USER_SEARCH);
		SearchUserResponse response = null;
		model.put(USER_SEARCH_DATA, new UserData());
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return userSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			String loginUserType = session.getAttribute(Constants.USER_TYPE).toString();
			request.setPageIndex(Constants.ONE);
			request.setLoginUserType(loginUserType);
			request.setAdminUserId(session.getAttribute(Constants.USER_ID).toString());
			response = userManagementService.searchUser(request);
			session.setAttribute(USER_LIST_DATA, request);


		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(USER_DATA_SIZE, response.getTotalRecords());
			session.setAttribute("userSearchRequest", request);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(USER_LIST_DATA, response.getListUser());
			model.put(USER_SEARCH_DATA, request);
			model.put(USER_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, USER_DATA_SIZE);
		}

		return modelAndView;
	}

	@PostMapping(value = USER_SEARCH_PAGINATION)
	public ModelAndView userSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		UserData request = (UserData) session.getAttribute("userSearchRequest");
		ModelAndView modelAndView = new ModelAndView(USER_SEARCH);
		model.put(USER_SEARCH_DATA, new UserData());
		SearchUserResponse response = new SearchUserResponse();
		try {
			String loginUserType = session.getAttribute(Constants.USER_TYPE).toString();
			request.setPageIndex(pageNumber);
			request.setLoginUserType(loginUserType);
			response = userManagementService.searchUser(request);

		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(USER_LIST_DATA, response.getListUser());
			model.put(USER_SEARCH_DATA, request);
			session.setAttribute(USER_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, USER_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = USER_EDIT_ACTION)
	public ModelAndView editUserData(final HttpSession session, @RequestParam("userId") String userId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("user-edit");
		SearchUserResponse response = new SearchUserResponse();
		model.put(USER_EDIT_REQ, new UserProfileUpdateRequest());
		try {
			String loginUserType = session.getAttribute(Constants.USER_TYPE).toString();
			UserData searchUserRequest = new UserData();
			searchUserRequest.setPageIndex(1);
			searchUserRequest.setUserId(userId);
			searchUserRequest.setLoginUserType(loginUserType);
			searchUserRequest.setAdminUserId(session.getAttribute(Constants.USER_ID).toString());
			response = userManagementService.searchUser(searchUserRequest);

		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			UserProfileUpdateRequest userData = populateUserUpdateData(response);
			model.put(USER_EDIT_REQ, userData);
			model.put(Constants.USER_TYPE, userData.getUserType());
		}
		return modelAndView;
	}

	@PostMapping(value = USER_UPDATE)
	public ModelAndView updateUserData(HttpSession session, Map<String, Object> model, UserProfileUpdateRequest request,
			@RequestParam("userId") String userId) {
		ModelAndView modelAndView = new ModelAndView("user-edit");
		WebResponse response = null;
		model.put(USER_EDIT_REQ, request);
		try {
			request.setUserId(userId);
			response = userManagementService.updateUser(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("user.profile.update.success"));
			model.put(Constants.USER_TYPE, request.getUserType());
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(USER_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_USER_STATUS)
	public ModelAndView updateUserStatus(final HttpSession session, @RequestParam("userId") String userId,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(USER_SEARCH);
		model.put(USER_SEARCH_DATA, new UserData());
		UserStatusUpdateRequest request = new UserStatusUpdateRequest();
		UserStatusUpdateResponse response = null;
		try {
			request.setAdminUserId(session.getAttribute(Constants.USER_ID).toString());
			request.setStatus(status);
			request.setUserId(userId);
			request.setReason(reason);
			response = userManagementService.updateUserStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("user.status.suspend.changed")
							.replace(USER_NAME, response.getUserName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("user.status.active.changed").replace(USER_NAME,
							response.getUserName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("user.status.terminate.changed")
							.replace(USER_NAME, response.getUserName()));
				}

				return userSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = USER_VIEW_ACTION)
	public ModelAndView viewUserDate(final HttpSession session, @RequestParam("userId") String userId,
			Map<String, Object> model) {

		ModelAndView modelAndView = new ModelAndView("user-view");
		SearchUserResponse response = new SearchUserResponse();
		model.put("userUpdateRequest", new UserProfileUpdateRequest());
		try {
			UserData userData = new UserData();
			userData.setPageIndex(Constants.ONE);
			userData.setUserId(userId);
			userData.setLoginUserType(session.getAttribute(Constants.USER_TYPE).toString());
			userData.setAdminUserId(session.getAttribute(Constants.USER_ID).toString());
			response = userManagementService.searchUser(userData);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			UserData viewRequest = populateViewUser(response);
			model.put(USER_VIEW_REQ, viewRequest);
			model.put(Constants.USER_TYPE, viewRequest.getUserType());
		}
		return modelAndView;
	}

	protected UserData populateViewUser(SearchUserResponse response) {
		return response.getListUser().get(0);
	}

	@GetMapping(value = "getRoleNameByUserType")
	public @ResponseBody String getRoleNameByUserType(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session, @RequestParam("userType") String userType,
			RoleResponse response) throws JsonProcessingException, AFCSException {
		RoleRequest roleRequest = new RoleRequest();
		roleRequest.setRoleCategory(userType);
		roleRequest.setUserType((String) session.getAttribute(Constants.USER_TYPE));
		roleRequest.setUserId((String) session.getAttribute(Constants.USER_ID));
		if(session.getAttribute(Constants.USER_TYPE).toString().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			response = rolesManagementService.getRoleListByUserType(roleRequest);
		} else if (session.getAttribute(Constants.USER_TYPE).toString().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())
				|| session.getAttribute(Constants.USER_TYPE).toString()
						.equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			roleRequest.setCreatedBy((String) session.getAttribute(Constants.USER_ID));
			response = rolesManagementService.getRoleListByUserType(roleRequest);
		} 
		
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(response);
		}
		return null;
	}

	protected UserProfileUpdateRequest populateUserUpdateData(SearchUserResponse response) {
		UserProfileUpdateRequest request = new UserProfileUpdateRequest();
		UserData user = response.getListUser().get(0);
		request.setAddress(user.getAddress());
		request.setEmail(user.getEmail());
		request.setPhoneNumber(user.getPhoneNumber());
		request.setUserId(user.getUserId());
		request.setUserName(user.getUserName());
		request.setUserRole(user.getUserRole());
		request.setUserType(user.getUserType());
		request.setFirstName(user.getFirstName());
		request.setLastName(user.getLastName());
		request.setRoleName(user.getRoleName());
		if (user.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			request.setOrganizationId(user.getOrganizationId());
		} else if (user.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			request.setPtoId(user.getPtoId());
		}
		return request;
	}
	@PostMapping(value =  DOWNLOAD_USER_REPORT)
	public ModelAndView downloadUserManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: UserManagementController:: downloadUserManagementReport method");
		ModelAndView modelAndView = new ModelAndView(USER_SEARCH);
		SearchUserResponse searchUserResponse = null;
		try {
			UserData userDataRequest = (UserData) session.getAttribute(USER_LIST_DATA);
			userDataRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = userDataRequest.getPageSize();

			String loginUserType = session.getAttribute(Constants.USER_TYPE).toString();
			userDataRequest.setLoginUserType(loginUserType);

			if (downloadAllRecords) {
				userDataRequest.setPageIndex(Constants.ONE);
				userDataRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			searchUserResponse = userManagementService.searchUser(userDataRequest);
			List<UserData> reuestResponses = searchUserResponse.getListUser();
			if (!StringUtil.isNull(searchUserResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadUserManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			userDataRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::UserManagementController:: UserManagementReport method", e);
		}
		logger.info("Exit:: UserManagementController:: downloadUserManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadUserManagementReport(List<UserData> userRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("User_");
		exportDetails.setHeaderMessageProperty("chatak.header.user.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(userRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("User.file.exportutil.userType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.userName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.role", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.firstName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.lastName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.emailId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.phoneNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("User.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<UserData> userSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (UserData userData : userSearchRequest) {

			Object[] rowData = { userData.getUserType() , userData.getUserName() , userData.getRoleName(), userData.getFirstName() , 
					             userData.getLastName() , userData.getEmail() , userData.getPhoneNumber() , userData.getStatus() 
			};

			fileData.add(rowData);
		}

		return fileData;
	}
}
