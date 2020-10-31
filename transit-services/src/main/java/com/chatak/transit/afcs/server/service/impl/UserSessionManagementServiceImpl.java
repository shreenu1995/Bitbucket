/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.controller.HttValidation;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.UserSessionManagementDao;
import com.chatak.transit.afcs.server.dao.model.AdminTransactionData;
import com.chatak.transit.afcs.server.dao.model.AfcsRole;
import com.chatak.transit.afcs.server.dao.model.LoginSessionDetails;
import com.chatak.transit.afcs.server.dao.model.UserCredentials;
import com.chatak.transit.afcs.server.dao.repository.AfcsRoleRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.EmailEvent;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.enums.UserPasswordStatus;
import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.ForgetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsResponse;
import com.chatak.transit.afcs.server.pojo.web.ResetPasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.SearchUserResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.UserDetail;
import com.chatak.transit.afcs.server.pojo.web.UserInfoCheck;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.UserViewResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.EmailService;
import com.chatak.transit.afcs.server.service.UserSessionManagementService;
import com.chatak.transit.afcs.server.util.PasswordHandler;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class UserSessionManagementServiceImpl extends HttValidation implements UserSessionManagementService {
	Logger logger = LoggerFactory.getLogger(UserSessionManagementServiceImpl.class);

	@Autowired
	UserSessionManagementDao userSessionManagementDao;

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	Environment properties;

	@Autowired
	EmailService emailService;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	AfcsRoleRepository afcsRoleRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	private static final int PSWD_MAX_LENGTH = 8;
	public static final String DELIMIT_PASS = "@/@";
	private static final int PREVIOUS_DB_PASS_COUNT = 2;
	private static final String EXCEPTION = "Exception";
	private static final long LOGIN_MODE = 1;

	@Override
	public WebResponse checkUserInfo(UserInfoCheck request, HttpServletResponse httpResponse, WebResponse response)
			throws IOException {
		if (userSessionManagementDao.validateUserInfo(request)
				&& userSessionManagementDao.validateExistingUserInfo(request)) {
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		} else {
			infoCheckErrors(request, response);
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
		}
		return response;
	}

	@Override
	public void infoCheckErrors(UserInfoCheck request, WebResponse response) {
		if (!(dataValidation.isValidUser(request.getAdminUserId()))) {
			response.setStatusCode(CustomErrorCodes.INVALID_ADMIN.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_ADMIN.getCustomErrorMsg());
		} else if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse userRegistration(UserRegistrationRequest request, HttpServletResponse httpResponse,
			WebResponse response) throws IOException, ParseException {
		if (userSessionManagementDao.validateUserRegistrationRequest(request)) {
			UserCredentials userCredentials = new UserCredentials();
			LocalDateTime now = LocalDateTime.now();
			String randomPassword = PasswordHandler.getSystemGeneratedPassword(PSWD_MAX_LENGTH);
			String userId = String.valueOf(System.currentTimeMillis());
			userCredentials.setUserID(userId);
			userCredentials.setCreatedDateTime(Timestamp.valueOf(now));
			userCredentials.setUpdatedDateTime(Timestamp.valueOf(now));
			userCredentials.setCurrentLoginStatus(false);
			userCredentials.setOrgId(request.getOrgId());
			userCredentials.setEmail(request.getEmail());
			userCredentials.setPtoMasterId(request.getPtoMasterId());
			userCredentials.setUserType(request.getUserType());
			userCredentials.setUserRole(request.getUserRole());
			userCredentials.setUserName(request.getUserName());
			userCredentials.setPassWord(randomPassword);
			userCredentials.setUserStatus(Status.ACTIVE.getValue());
			userCredentials.setAdminUserId(request.getAdminUserId());
			checkUserRoleAndType(request, userCredentials);
			userCredentials.setAddress(request.getAddress());
			userCredentials.setFirstName(request.getFirstName());
			userCredentials.setLastName(request.getLastName());
			userCredentials.setPhone(request.getPhoneNumber());
			userCredentials.setLoginMode(Long.valueOf(UserPasswordStatus.FIRSTTIMELOGIN.ordinal()));
			if (!StringUtil.isNullEmpty(request.getUserRole()) && !StringUtil.isNullEmpty(request.getUserType())) {
				try {
					List<AfcsRole> roleList = afcsRoleRepository.findByUserTypeAndId(request.getUserType(),
							Long.valueOf(request.getUserRole()));
					userCredentials.setRoleName(roleList.get(0).getName());
				} catch (Exception e) {
					logger.error(
							"ERROR :: UserSessionManagementServiceImpl class :: userRegistration method :: exception",
							e);
				}
			}
			if (userSessionManagementDao.saveUserDetails(userCredentials)) {
				String emailToken = StringUtil.getEmailToken(request.getEmail());
				request.setPassword(userCredentials.getPassWord());
				userSessionManagementDao.updateUserProfileEmailToken(emailToken, request.getEmail(), userId);
				String url = properties.getProperty("afcs.login.redirection.url");
				String finalURL = url += "?t=" + emailToken;
				sendEmailToUser(request, EmailEvent.USER_REGISTER.toString(), finalURL);
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			}
		} else {
			registrationErrors(request, response);
		}

		return response;
	}

	private void checkUserRoleAndType(UserRegistrationRequest request, UserCredentials userCredentials) {
		if (!StringUtil.isNullEmpty(request.getUserRole()) && !StringUtil.isNullEmpty(request.getUserType())) {
			try {
				List<AfcsRole> roleList = afcsRoleRepository.findByUserTypeAndId(request.getUserType(),
						Long.valueOf(request.getUserRole()));
				userCredentials.setRoleName(roleList.get(0).getName());
			} catch (Exception e) {
				logger.error("ERROR :: UserSessionManagementServiceImpl class :: userRegistration method :: exception",
						e);
			}
		}
	}

	private void sendEmailToUser(UserRegistrationRequest request, String eventType, String finalUrl) {
		String subject = properties.getProperty("afcs.email.user.create.subject");
		String messageBody = properties.getProperty("afcs.email.user.create.body");
		messageBody = MessageFormat.format(messageBody, request.getUserName(), request.getEmail(),
				request.getPassword(), finalUrl);
		emailService.saveMail(request.getEmail(), subject, messageBody, eventType);
	}

	@Override
	public void registrationErrors(UserRegistrationRequest request, WebResponse response) {
		if (!dataValidation.isValidAdminUser(request.getAdminUserId())) {
			response.setStatusCode(CustomErrorCodes.INVALID_ADMIN.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_ADMIN.getCustomErrorMsg());
		} else if (dataValidation.isValidUserName(request.getUserName())) {
			response.setStatusCode(CustomErrorCodes.INVALID_USERNAME.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_USERNAME.getCustomErrorMsg());
		}
	}

	public void updateUserStatusErrors(UserStatusUpdateRequest request, UserStatusUpdateResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.isValidAdminUser(request.getAdminUserId())) {
			response.setStatusCode(CustomErrorCodes.INVALID_ADMIN.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_ADMIN.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse changePassword(ChangePasswordRequest request, HttpServletResponse httpResponse,
			WebResponse response) {
		logger.debug("Enter into changePassword");

		if (request.getNewPassword().equals(request.getCurrentPassword())) {
			response.setStatusCode(CustomErrorCodes.AFCS_ADMIN_USER_CHANGEPASSWORD_OLDNEW_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.AFCS_ADMIN_USER_CHANGEPASSWORD_OLDNEW_ERROR.getCustomErrorMsg());
			return response;
		}
		if (userSessionManagementDao.validateChangePasswordRequest(request)) {
			return changeUserCredentials(request, response);
		} else {
			changePasswordErrors(request, response);
			return response;
		}
	}

	private WebResponse changeUserCredentials(ChangePasswordRequest request, WebResponse response) {
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setEmail(request.getUserId());
		UserCredentials userDetails = userSessionManagementDao.getUserDetail(userCredentials);
		userCredentials.setUserID(userDetails.getUserID());
		userCredentials.setPassWord(request.getCurrentPassword());
		userCredentials.setUserID(userDetails.getUserID());
		userCredentials.setPassWord(request.getCurrentPassword());
		userCredentials.setLoginMode(Long.valueOf(UserPasswordStatus.ACTIVE.ordinal()));
		UserCredentials user = userSessionManagementDao.getUserDetail(userCredentials);
		if (!StringUtil.isNullEmpty(user.getPreviousPasswords())) {
			String previousPass = user.getPreviousPasswords();
			String[] dbPasswords = previousPass.split(DELIMIT_PASS);

			if (Arrays.asList(dbPasswords).contains(request.getNewPassword())
					|| user.getPassWord().equals(request.getNewPassword())) {
				response.setStatusCode(CustomErrorCodes.PREVIOUS_PSWD_INVALID.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.PREVIOUS_PSWD_INVALID.getCustomErrorMsg());
				return response;
			}
			setNewPreviousPass(userCredentials, user, previousPass, dbPasswords);
		} else {
			userCredentials.setPreviousPasswords(user.getPassWord() + DELIMIT_PASS);
		}
		if (userSessionManagementDao.changePassword(userCredentials, request.getNewPassword())) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		return response;
	}

	@Override
	public void changePasswordErrors(ChangePasswordRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.passwordValidation(request.getCurrentPassword())) {
			response.setStatusCode(CustomErrorCodes.WRONG_PASSWORD.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.WRONG_PASSWORD.getCustomErrorMsg());
		}
	}

	@Override
	public TransactionResponse saveAdminTransactionData(TransactionRequest request, TransactionResponse response,
			HttpServletResponse httpResponse) throws IOException {
		logger.debug("Enter into saveAdminTransactionService");
		if (userCredentialsRepository.existsByEmailAndUserStatus(request.getUserId(), Status.SUSPENDED.getValue())) {
			response.setStatusCode(CustomErrorCodes.USER_STATUS_SUSPENDED.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_STATUS_SUSPENDED.getCustomErrorMsg());
			return response;
		}
		UserCredentials userCredentials = userCredentialsRepository.findByEmailAndUserStatusNotLike(request.getUserId(),
				Status.TERMINATED.getValue());

		if (userSessionManagementDao.validateAdminTransactionDataRequest(request)
				&& !StringUtil.isNull(userCredentials)) {
			String userID = userCredentials.getUserID();
			UserCredentials userCredential = userCredentialsRepository.findByUserID(userID);

			if (StringUtil.isNull(userCredential)) {
				response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
				return response;
			}

			if (userCredential.getLoginMode().toString()
					.equals(Integer.toString(UserPasswordStatus.FIRSTTIMELOGIN.ordinal()))) {
				response.setStatusCode(CustomErrorCodes.FIRST_TIME_LOGIN.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.FIRST_TIME_LOGIN.getCustomErrorMsg());
				return response;
			} else {
				AdminTransactionData adminTransactionData = new AdminTransactionData();
				adminTransactionData.setUserId(request.getUserId());
				adminTransactionData.setPassword(request.getPassword());
				adminTransactionData.setTransactionId(request.getTransactionId());
				adminTransactionData.setDateTime(Timestamp.valueOf(request.getDateTime()));
				userCredential.setLoginMode(Long.valueOf(UserPasswordStatus.ACTIVE.ordinal()));
				TransactionResponse adminTxnresponse = userSessionManagementDao
						.saveAdminTransactionData(adminTransactionData, response);
				if (adminTxnresponse != null) {
					adminTxnresponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
					adminTxnresponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
					adminTxnresponse.setUserName(adminTxnresponse.getUserName());
					logger.debug("Inside If statement");
					return adminTxnresponse;
				}
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				logger.debug("inside saveAdminTransactionData");
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		} else {
			checkAdminTransactionErrors(request, response);
			return response;
		}
	}

	@Override
	public void checkAdminTransactionErrors(TransactionRequest request, TransactionResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.passwordValidation(request.getPassword())) {
			response.setStatusCode(CustomErrorCodes.WRONG_PASSWORD.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.WRONG_PASSWORD.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.USER_ID_PASSWORD.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_ID_PASSWORD.getCustomErrorMsg());
		}
	}

	@Override
	public UserViewResponse getUserDetail(UserDetail request, UserViewResponse response,
			HttpServletResponse httpResponse) {
		logger.debug("Enter into getUserDetail");
		UserCredentials userCredentials = new UserCredentials();
		if (userSessionManagementDao.validateUserDetail(request, response)) {
			userCredentials.setUserID(request.getUserId());
			UserCredentials userProfileDetail = userSessionManagementDao.getUserDetail(userCredentials);
			UserCredentials userCredentialsDetail = userSessionManagementDao.getUserStatus(userCredentials);
			if (userProfileDetail != null) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setId(userProfileDetail.getId());
				response.setUserName(userProfileDetail.getUserName());
				response.setUserId(userProfileDetail.getUserID());
				response.setPassWord(userProfileDetail.getPassWord());
				response.setAddress(userProfileDetail.getAddress());
				response.setUserType(userProfileDetail.getUserType());
				response.setFirstName(userProfileDetail.getFirstName());
				response.setLastName(userProfileDetail.getLastName());
				response.setPhoneNumber(userProfileDetail.getPhone());
				response.setEmail(userProfileDetail.getEmail());
				response.setCreatedDateTime(userProfileDetail.getCreatedDateTime());
				response.setUpdatedDateTime(userProfileDetail.getUpdatedDateTime());
				response.setUserRole(userProfileDetail.getUserRole());
				response.setStatus(userCredentialsDetail.getUserStatus());
				response.setCurrentLoginStatus(userCredentialsDetail.isCurrentLoginStatus());
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;
			}
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			try {
				logger.debug("Inside try block");
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
			return response;
		} else {
			checkUserDetailErrors(request, response);
			try {
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
			return response;
		}
	}

	@Override
	public void checkUserDetailErrors(UserDetail request, UserViewResponse response) {
		if (!dataValidation.isValidAdminUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse updateUserProfile(UserProfileUpdateRequest request, HttpServletResponse httpResponse,
			WebResponse response) throws IOException {
		if (userSessionManagementDao.validateUserProfileRequest(request)) {
			if (userSessionManagementDao.updateUserProfile(request)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				return response;
			}
		} else {
			errorsUserUpdateProfile(request, response);
			return response;
		}
	}

	@Override
	public void errorsUserUpdateProfile(UserProfileUpdateRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getAdminUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public WebResponse forgetPassword(ForgetPasswordRequest request, HttpServletResponse httpResponse)
			throws IOException {
		WebResponse response = new WebResponse();
		UserInfoCheck userParameter = new UserInfoCheck();
		userParameter.setUserId(request.getUserId());
		boolean isUserActive = dataValidation.isUserActive(request.getUserId());
		if (userSessionManagementDao.validateExistingUserForPassword(userParameter)	&& isUserActive) {
			UserCredentials user = new UserCredentials();
			user.setUserID(request.getUserId());
			UserCredentials userCredentials = userSessionManagementDao.getUserDetailByUserId(user);
			String emailToken = StringUtil.getEmailToken(userCredentials.getEmail());
			userSessionManagementDao.updateUserProfileEmailToken(emailToken, userCredentials.getEmail(),
					request.getUserId());
			String url = properties.getProperty("afcs.forget.password.redirection.url");
			String finalUrl = url += "?t=" + emailToken;

			sendForgetPswdEmail(userCredentials.getEmail(), finalUrl);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		}
		checkForgetPasswordErrors(request, response);
		return response;
	}

	private void checkForgetPasswordErrors(ForgetPasswordRequest request, WebResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.EMAIL_NOT_EXISTS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.EMAIL_NOT_EXISTS.getCustomErrorMsg());
		} else if (!dataValidation.isUserActive(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_INACTIVE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_INACTIVE.getCustomErrorMsg());
		}
	}

	private void sendForgetPswdEmail(String email, String url) {
		String subject = properties.getProperty("afcs.email.reset.password.subject");
		String messageBody = properties.getProperty("afcs.email.reset.password.email.body");
		messageBody = MessageFormat.format(messageBody, email, url);
		emailService.saveMail(email, subject, messageBody, EmailEvent.RESET_PASSWORD.name());
	}

	@Override
	public WebResponse resetPassword(ResetPasswordRequest request, HttpServletResponse httpResponse)
			throws IOException {
		WebResponse response = new WebResponse();
		String[] paramsArray = null;
		if (!StringUtil.isNull(request) && !StringUtil.isNullEmpty(request.getEmailToken())) {
			String tokenExpireTime = properties.getProperty("afcs.user.email.token.expiry.time");
			response.setStatusCode(CustomErrorCodes.RESET_PSWD_LINK_EXPIRED.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.RESET_PSWD_LINK_EXPIRED.getCustomErrorMsg());
			try {
				paramsArray = StringUtil.parseEmailToken(request.getEmailToken(), tokenExpireTime);
				if (paramsArray != null) {
					String userId = paramsArray[0];
					boolean isValid = userSessionManagementDao.validateEmailToken(request.getEmailToken(), userId);
					if (isValid) {
						UserCredentials userCredentials = new UserCredentials();
						UserCredentials userProfile = new UserCredentials();
						userCredentials.setEmail(userId);
						UserCredentials userData = userCredentialsRepository.findByEmailAndUserStatusNotLike(userId,
								Status.TERMINATED.getValue());
						userCredentials.setUserID(userData.getUserID());
						userProfile.setEmail(userId);
						UserCredentials user = userSessionManagementDao.getUserDetail(userProfile);
						if (!StringUtil.isNullEmpty(user.getPreviousPasswords())) {
							String previousPass = user.getPreviousPasswords();
							String[] dbPasswords = previousPass.split(DELIMIT_PASS);

							if (Arrays.asList(dbPasswords).contains(request.getNewPassword())
									|| user.getPassWord().equals(request.getNewPassword())) {
								response.setStatusCode(CustomErrorCodes.PREVIOUS_PSWD_INVALID.getCustomErrorCode());
								response.setStatusMessage(CustomErrorCodes.PREVIOUS_PSWD_INVALID.getCustomErrorMsg());
								return response;

							}
							setNewPreviousPass(userCredentials, user, previousPass, dbPasswords);
						} else {
							userCredentials.setPreviousPasswords(user.getPassWord() + DELIMIT_PASS);
						}
						userCredentials.setLoginMode(LOGIN_MODE);
						if (userSessionManagementDao.changePassword(userCredentials, request.getNewPassword())) {
							response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
							response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
							return response;
						}
					}
				}
			} catch (IllegalArgumentException e) {
				logger.error("UserSessionManagementServiceImpl class :: resetPassword method :: exception", e);
			}
		}
		return response;
	}

	private void setNewPreviousPass(UserCredentials userCredentials, UserCredentials user, String previousPass,
			String[] dbPasswords) {
		String newPreviousPass = (dbPasswords.length >= PREVIOUS_DB_PASS_COUNT)
				? (dbPasswords[1] + DELIMIT_PASS + user.getPassWord())
				: previousPass + user.getPassWord();
				userCredentials.setPreviousPasswords(newPreviousPass);
	}

	@Override
	public Response validateEmailToken(ResetPasswordRequest request) {
		Response response = new Response();
		boolean isValid = false;
		if (!StringUtil.isNullEmpty(request.getEmailToken())) {
			String tokenExpireTime = properties.getProperty("afcs.user.email.token.expiry.time");
			String[] paramsArray = null;
			paramsArray = StringUtil.parseEmailToken(request.getEmailToken(), tokenExpireTime);
			if (paramsArray != null) {
				String userId = paramsArray[0];
				isValid = userSessionManagementDao.validateEmailToken(request.getEmailToken(), userId);
			}
		}
		if (isValid) {
			response.setResult(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		} else {
			response.setResult(CustomErrorCodes.FAILURE.getCustomErrorCode());
		}
		return response;
	}

	@Override
	public boolean emailExistsCheck(UserInfoCheck userInfoCheck) {
		return userSessionManagementDao.validateExistingUserInfo(userInfoCheck);
	}

	@Override
	public SearchUserResponse searchUser(UserData request) {
		SearchUserResponse response = userSessionManagementDao.getUserList(request);
		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public WebResponse saveLoginSessionDetails(LoginSessionDetailsRequest request) {
		WebResponse response = new WebResponse();
		LoginSessionDetails loginSessionDetails = new LoginSessionDetails();
		loginSessionDetails.setId(request.getId());
		loginSessionDetails.setUserName(request.getUserName());
		loginSessionDetails.setSessionId(request.getSessionId());
		loginSessionDetails.setLoginTime(request.getLoginTime());
		loginSessionDetails.setLogoutTime(request.getLogoutTime());
		loginSessionDetails.setLastActivityTime(request.getLastActivityTime());
		loginSessionDetails.setBrowserType(request.getBrowserName());
		loginSessionDetails.setIpAddress(request.getIpAddress());
		loginSessionDetails.setLoginStatus(request.getLoginStatus());
		if (userSessionManagementDao.saveOrUpdateSessionDetails(loginSessionDetails)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public LoginSessionDetailsResponse getSessionDetails(LoginSessionDetailsRequest loginSessionDetailsRequest) {
		return userSessionManagementDao.searchLoginSessionDetails(loginSessionDetailsRequest);
	}

	@Override
	public WebResponse updateLoginSessionDetails(LoginSessionDetailsRequest request) {
		WebResponse response = new WebResponse();
		LoginSessionDetails loginSessionDetails = new LoginSessionDetails();
		loginSessionDetails.setLogoutTime(request.getLogoutTime());
		loginSessionDetails.setLastActivityTime(request.getLastActivityTime());
		loginSessionDetails.setLoginStatus(request.getLoginStatus());
		if (userSessionManagementDao.saveOrUpdateSessionDetails(loginSessionDetails)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

	@Override
	public UserStatusUpdateResponse updateUserStatus(UserStatusUpdateRequest request,
			UserStatusUpdateResponse response) {
		UserStatusUpdateResponse statusUpdateResponse = new UserStatusUpdateResponse();

		if (!StringUtil.isNull(request)) {
			UserCredentials userCredentials = userSessionManagementDao.updateUserStatus(request);
			statusUpdateResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			statusUpdateResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			statusUpdateResponse.setUserName(userCredentials.getUserName());
			return statusUpdateResponse;
		} else {
			statusUpdateResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			statusUpdateResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			return statusUpdateResponse;
		}

	}

}