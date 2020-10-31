package com.afcs.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.RolesManagementService;
import com.afcs.web.service.UserLoginService;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.ForgetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.ResetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class UserLoginController {

	@Autowired
	UserLoginService loginService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	Environment properties;

	@Autowired
	RolesManagementService rolesManagementService;

	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	private static final String USER_LOGIN = "user-login";
	private static final String ADMIN_DASHBOARD = "dashboard";
	private static final String FORGOT_PSWD = "forgot-password";
	private static final String RESET_PSWD = "reset-password";
	private static final String EMAIL_TOKEN = "emailToken";
	private static final String INVALID_LINK = "invalid-reset-password-link";
	private static final String CHANGE_PSWD = "change-password";
	private static final String CHNAGE_PSWD_REQ = "changePassword";
	private static final String AFCS_LOG_OUT = "logout";
	private static final String CHANGE_PSWD_FIRST_TIME = "changePasswordFirstTime";
	private static final String PSWD_RESET = "password-reset";
	private static final String EXISTING_FEATURES = "existingFeatures";
	private static final String PSWD_CHNAGED_SUCCESS = "pswd.changed.success";
	
	@GetMapping(value = USER_LOGIN)
	public ModelAndView userLoginGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(USER_LOGIN);
		TransactionRequest loginRequest = new TransactionRequest();
		model.put("userLogin", loginRequest);
		return modelAndView;
	}

	@PostMapping(value = USER_LOGIN)
	public ModelAndView userLoginPost(Map<String, Object> model, HttpSession session, TransactionRequest request,
			HttpServletRequest httprequest, HttpServletResponse httpResponse,
			LoginSessionDetailsRequest loginSessionDetailsRequest) throws AFCSException{
		ModelAndView modelAndView = new ModelAndView(USER_LOGIN);
		String userAgent = httprequest.getHeader("user-agent");
		TransactionResponse response = null;
		String browserName = null;
		try {

			if (null != userAgent) {
				userAgent = userAgent.replaceAll("\\ ", "");
			}

			response = loginService.userLoginRequest(request);
			if(response.getStatusCode().equals(CustomErrorCodes.FIRST_TIME_LOGIN.getCustomErrorCode())) {
				session.setAttribute(Constants.USER_ID, request.getUserId());
				modelAndView = validatePasswordConfiguartion(model, response, session);
				return modelAndView;
			}
			
			session.setAttribute(Constants.LOGIN_RESPONSE, response);
			String existingFeatures = "";
			logger.info("Entering:: existingFeatureList method");
			existingFeatures = existingFeatureList(response, existingFeatures);
			existingFeatures += "|";
			session.setAttribute(Constants.EXISTING_FEATURES, existingFeatures);
			session.setAttribute(Constants.EXISTING_FEATURE_DATA, response.getListOfExistingFeature());

		} catch (AFCSException e) {
			logger.error("ERROR :: UserLoginController class :: userLoginPost method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		String ipAddr = httprequest.getRemoteAddr();
		session.setAttribute(Constants.CURRENT_LOGIN_IP_ADDRESS, ipAddr);

		// Getting the browser names
		if (userAgent != null) {
			getBrowserName(session, httpResponse, userAgent, response, browserName, ipAddr);
		}
		
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(Constants.USER_ID, request.getUserId());
			session.setAttribute(Constants.USER_TYPE, response.getUserType());
			session.setAttribute(Constants.ROLE_ID, response.getRollId());
			getListOfPto(session, response);
			RoleRequest roleRequest = new RoleRequest();
			roleRequest.setRoleCategory(response.getUserType());
			List<RoleLevel> roleLeveList = new ArrayList<>();
			List<RoleLevel> getAllTypeRoleLogin = getRoleListForRoles(session, roleLeveList);
			model.put("roleLevelList", getAllTypeRoleLogin);
			model.put("userRoleRequest", roleRequest);

			TransitFeatureRequest prepaidFeatureRequest = new TransitFeatureRequest();
			List<TransitFeatureRequest> featureList = null;

			featureList = getFeatureMapBasedOnUserLevel(roleRequest, prepaidFeatureRequest, featureList);

			model.put("userRoleRequest", roleRequest);
			List<TransitFeatureRequest> featureList2 = getAssignedFeatureList(featureList, session);
			modelAndView.addObject("featureList", featureList2);
			model.put(Constants.SUCCESS, response.getStatusMessage());
			modelAndView.setViewName(ADMIN_DASHBOARD);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	private List<TransitFeatureRequest> getFeatureMapBasedOnUserLevel(RoleRequest roleRequest,
			TransitFeatureRequest prepaidFeatureRequest, List<TransitFeatureRequest> featureList) throws AFCSException{
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
		return featureList;
	}

	private void getListOfPto(HttpSession session, TransactionResponse response) throws AFCSException {
		PtoListResponse ptoListResponse;
		if (!StringUtil.isNullEmpty(response.getOrganizationId()) || !StringUtil.isNull(response.getOrgId())) {
			session.setAttribute(Constants.ORGANIZATION_ID, response.getOrganizationId());
			session.setAttribute(Constants.ORG_ID, response.getOrgId());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
			} else {
				ptoListResponse = ptoOperationService.getPtoList(ptoListRequest);
			}

			session.setAttribute(Constants.PTO_LIST, ptoListResponse.getPtoList());
		} else {
			session.setAttribute(Constants.PTO_ID, response.getPtoId());
			session.setAttribute(Constants.PTO_MASTER_ID, response.getPtoMasterId());
		}
	}

	private void getBrowserName(HttpSession session, HttpServletResponse httpResponse, String userAgent,
			TransactionResponse response, String browserName, String ipAddr) throws AFCSException {
		LoginSessionDetailsRequest loginSessionDetailsRequest;
		if (!StringUtil.isNull(response)) {
			String userAgentValue = userAgent;
			String agent = userAgentValue.toLowerCase();
			if (agent.contains("msie")) {
				String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
				browserName = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[0];
			} else if (agent.contains("safari") && agent.contains("version")) {
				browserName = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (agent.contains("opr") || agent.contains("opera")) {
				browserName = getOperaBrowser(userAgent, browserName, agent);
			} else if (agent.contains("chrome")) {
				browserName = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
			} else if ((agent.indexOf("mozilla/7.0") > -1) || (agent.indexOf("netscape6") != -1)
					|| (agent.indexOf("mozilla/4.7") != -1) || (agent.indexOf("mozilla/4.78") != -1)
					|| (agent.indexOf("mozilla/4.08") != -1) || (agent.indexOf("mozilla/3") != -1)) {
				browserName = "Netscape-?";
			} else if (agent.contains("firefox")) {
				browserName = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
			} else if (agent.contains("rv")) {
				browserName = "IE";
			} else {
				browserName = "UnKnown, More-Info: " + userAgent;
			}

			loginSessionDetailsRequest = new LoginSessionDetailsRequest();
			loginSessionDetailsRequest.setLoginTime(new Timestamp(System.currentTimeMillis()));
			loginSessionDetailsRequest.setLastActivityTime(new Timestamp(System.currentTimeMillis()));
			loginSessionDetailsRequest.setSessionId(session.getId());
			loginSessionDetailsRequest.setIpAddress(ipAddr);
			loginSessionDetailsRequest.setLoginStatus(Constants.YES);
			loginSessionDetailsRequest.setBrowserName(browserName);
			loginSessionDetailsRequest.setUserName(response.getUserName());
			loginService.saveLoginSessionDetails(loginSessionDetailsRequest);

			if (!StringUtil.isNull(loginSessionDetailsRequest.getUserName())) {
				String encUName = encrypt(loginSessionDetailsRequest.getUserName());
				Cookie myCookie = new Cookie(Constants.CHATAK_ID, encUName + session.getId());
				myCookie.setMaxAge(20 * 60);
				httpResponse.addCookie(myCookie);
				loginSessionDetailsRequest.setjSession(userAgent + encUName + session.getId());
				myCookie.setHttpOnly(true);
			}
		}
	}

	private String getOperaBrowser(String userAgent, String browserName, String agent) {
		if (agent.contains("opera"))
			browserName = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		else if (agent.contains("opr"))
			browserName = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
					.replace("OPR", "Opera");
		return browserName;
	}
	
	public ModelAndView validatePasswordConfiguartion(Map<String, Object> model, TransactionResponse response, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(USER_LOGIN);
		
		if(response.getStatusCode().equals(CustomErrorCodes.FIRST_TIME_LOGIN.getCustomErrorCode())) {
			modelAndView = changePasswordFirstTime(model, session);
			return modelAndView;
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

	public static String encrypt(String str) {
		StringBuilder encryptData = new StringBuilder();
		encryptData.append(Constants.PREFIX);
		for (int k = 0; k < str.length(); k++) {
			encryptData.append(Character.toString(Constants.ALPHA_CHARS.charAt(k)) + Character.toString(str.charAt(k)));
		}
		encryptData.append(Constants.SUFFIX);
		return encryptData.toString();
	}

	public String existingFeatureList(TransactionResponse loginResponse, String existingFeatures) {
		logger.info("Entering:: LoginController:: existingFeatureList method");
		StringBuilder existingFeature = new StringBuilder();
		existingFeature.append(existingFeatures);
		if (!StringUtil.isNull(loginResponse.getListOfExistingFeature())) {
			for (Long feature : loginResponse.getListOfExistingFeature()) {
				existingFeature.append("|" + feature);
			}
		}
		logger.info("Exiting:: LoginController:: existingFeatureList method");
		return existingFeature.toString();
	}

	@GetMapping(value = FORGOT_PSWD)
	public ModelAndView forgotPassword(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(FORGOT_PSWD);
		model.put("forgotPassword", new ForgetPasswordRequest());
		return modelAndView;
	}

	@PostMapping(value = FORGOT_PSWD)
	public ModelAndView forgotPasswordData(Map<String, Object> model, HttpSession session,
			ForgetPasswordRequest request) {
		ModelAndView modelAndView = new ModelAndView(FORGOT_PSWD);
		WebResponse response = null;
		model.put("forgotPassword", new ForgetPasswordRequest());
		try {
			response = loginService.forgotPassword(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: UserLoginController class :: forgotPasswordData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("forget.pswd.email.sent"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = RESET_PSWD)
	public ModelAndView resetPassword(Map<String, Object> model, HttpSession session,
			@RequestParam("t") String emailToken) {
		ResetPasswordRequest resetPswdRequest = new ResetPasswordRequest();
		resetPswdRequest.setEmailToken(emailToken);
		ModelAndView modelAndView = new ModelAndView(RESET_PSWD);
		boolean isValidEmailToken;
		try {
			isValidEmailToken = loginService.validateEmailToken(resetPswdRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: UserLoginController class :: resetPassword method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!isValidEmailToken) {
			modelAndView = new ModelAndView(INVALID_LINK);
			return modelAndView;
		}
		model.put("resetPassword", new ResetPasswordRequest());
		session.setAttribute(EMAIL_TOKEN, emailToken);
		return modelAndView;
	}

	@PostMapping(value = RESET_PSWD)
	public ModelAndView resetPasswordData(Map<String, Object> model, HttpSession session,
			ResetPasswordRequest request) {
		ModelAndView modelAndView = new ModelAndView(RESET_PSWD);
		model.put("resetPassword", new ResetPasswordRequest());
		WebResponse response = null;
		try {
			request.setEmailToken(session.getAttribute(EMAIL_TOKEN).toString());
			response = loginService.resetPassword(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: UserLoginController class :: resetPasswordData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty(PSWD_CHNAGED_SUCCESS));
		} else if (!StringUtil.isNull(response)) {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = CHANGE_PSWD)
	public ModelAndView changePassword(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(CHANGE_PSWD);
		model.put(CHNAGE_PSWD_REQ, new ChangePasswordRequest());
		return modelAndView;
	}

	@PostMapping(value = CHANGE_PSWD)
	public ModelAndView changePasswordData(Map<String, Object> model, HttpSession session,
			ChangePasswordRequest request) {
		ModelAndView modelAndView = new ModelAndView(CHANGE_PSWD);
		model.put(CHNAGE_PSWD_REQ, new ChangePasswordRequest());
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = loginService.changePassword(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: UserLoginController class :: changePasswordData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty(PSWD_CHNAGED_SUCCESS));
		} else {
			model.put(CHNAGE_PSWD_REQ, request);
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}
	
	@GetMapping(value = CHANGE_PSWD_FIRST_TIME)
	public ModelAndView changePasswordFirstTime(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(PSWD_RESET);
		model.put(CHNAGE_PSWD_REQ, new ChangePasswordRequest());
		return modelAndView;
	}
	
	@PostMapping(value = CHANGE_PSWD_FIRST_TIME)
	public ModelAndView changePasswordFirstTimeSubmit(Map<String, Object> model, HttpSession session, ChangePasswordRequest request) {
		ModelAndView modelAndView = new ModelAndView(USER_LOGIN);
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = loginService.changePassword(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: UserLoginController class :: changePasswordData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty(PSWD_CHNAGED_SUCCESS));
		} else {
			modelAndView = new ModelAndView(PSWD_RESET);
			model.put(CHNAGE_PSWD_REQ, new ChangePasswordRequest());
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		
		return modelAndView;
	}

	@GetMapping(value = AFCS_LOG_OUT)
	public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response, Map model,
			HttpSession session) {
		try {
			Cookie myCookie = new Cookie(Constants.CHATAK_ID, null);
			myCookie.setMaxAge(0);
			response.addCookie(myCookie);
			myCookie.setHttpOnly(true);
		} catch (Exception e) {
			logger.error("ERROR:: LoginController:: logOut method", e);
		}
		session.invalidate();
		return new ModelAndView("user-logout");

	}
}