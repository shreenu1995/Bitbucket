/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.RolesManagementDao;
import com.chatak.transit.afcs.server.dao.UserSessionManagementDao;
import com.chatak.transit.afcs.server.dao.model.AdminTransactionData;
import com.chatak.transit.afcs.server.dao.model.AfcsRole;
import com.chatak.transit.afcs.server.dao.model.LoginSessionDetails;
import com.chatak.transit.afcs.server.dao.model.QLoginSessionDetails;
import com.chatak.transit.afcs.server.dao.model.QUserCredentials;
import com.chatak.transit.afcs.server.dao.model.UserCredentials;
import com.chatak.transit.afcs.server.dao.repository.AdminTransactionDataRepository;
import com.chatak.transit.afcs.server.dao.repository.AfcsRoleRepository;
import com.chatak.transit.afcs.server.dao.repository.LoginSessionDetailsRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.ChangePasswordRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsRequest;
import com.chatak.transit.afcs.server.pojo.web.LoginSessionDetailsResponse;
import com.chatak.transit.afcs.server.pojo.web.SearchUserResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionResponse;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.UserDetail;
import com.chatak.transit.afcs.server.pojo.web.UserInfoCheck;
import com.chatak.transit.afcs.server.pojo.web.UserListRequest;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserViewResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class UserSessionManagementDaoImpl implements UserSessionManagementDao {

	Logger logger = LoggerFactory.getLogger(UserSessionManagementDaoImpl.class);
	
	public static final String ERROR_LOG = "ERROR :: UserSessionManagementDaoImpl class :: getUserList method :: exception";

	@Autowired
	AdminTransactionDataRepository adminTransactionRepository;

	@Autowired
	RolesManagementDao rolesManagementDao;

	@Autowired
	PtoMasterRepository ptoOperationMasterRepository;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	LoginSessionDetailsRepository loginSessionDetailsRepository;

	@Autowired
	AfcsRoleRepository afcsRoleRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean saveUserDetails(UserCredentials userCredentials) {
		return (null != userCredentialsRepository.save(userCredentials));
	}

	@Override
	public boolean changePassword(UserCredentials usercredentials, String newPassword) {
		return (ServerConstants.COUNT == userCredentialsRepository.updatePassword(newPassword,
				usercredentials.getUserID(), usercredentials.getPreviousPasswords(), usercredentials.getLoginMode()));
	}

	@Override
	public TransactionResponse saveAdminTransactionData(AdminTransactionData adminTransactionData,
			TransactionResponse adminTransactionResponse) {
		logger.debug("Enter into saveAdminTransactionDataOfDaoImpl");
		TransactionResponse adminTransactionResp = new TransactionResponse();
		AdminTransactionData adminTransaction = adminTransactionRepository.save(adminTransactionData);
			Long orgId = userCredentialsRepository
					.findByEmailAndUserStatusNotLike(adminTransactionData.getUserId(), Status.TERMINATED.getValue())
					.getOrgId();
			Long ptoMasterId = userCredentialsRepository
					.findByEmailAndUserStatusNotLike(adminTransactionData.getUserId(), Status.TERMINATED.getValue())
					.getPtoMasterId();
			adminTransactionResp.setRollId(String.valueOf(userCredentialsRepository
					.findByEmailAndUserStatusNotLike(adminTransactionData.getUserId(), Status.TERMINATED.getValue())
					.getUserRole()));
			adminTransactionResp.setUserName(adminTransaction.getUserId());
			adminTransactionResp.setOrgId(orgId);
			adminTransactionResp.setPtoMasterId(ptoMasterId);
			adminTransactionResp.setListOfExistingFeature(
					rolesManagementDao.getFeatureDataOnRoleIdData(Long.valueOf(adminTransactionResp.getRollId())));
			adminTransactionResp.setUserType(userCredentialsRepository
					.findByEmailAndUserStatusNotLike(adminTransactionData.getUserId(), Status.TERMINATED.getValue())
					.getUserType());
		return adminTransactionResp;
	}

	@Override
	public boolean validateUserInfo(UserInfoCheck request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials)
				.where(QUserCredentials.userCredentials.userID.eq(request.getAdminUserId()),
						QUserCredentials.userCredentials.userStatus.eq(Status.ACTIVE.getValue()))
				.exists();
	}

	@Override
	public boolean validateExistingUserInfo(UserInfoCheck request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials)
				.where(QUserCredentials.userCredentials.email.eq(request.getEmail()),
						isStatusNotTerminated(Status.TERMINATED.getValue()))
				.exists();

	}

	@Override
	public boolean validateExistingUserForRegistration(UserRegistrationRequest request) {
		JPAQuery query = new JPAQuery(em);
		return !query.from(QUserCredentials.userCredentials)
				.where(QUserCredentials.userCredentials.userID.eq(request.getUserId())).exists();
	}

	@Override
	public boolean validateUserStatusUpdate(UserStatusUpdateRequest request) {
		return userCredentialsRepository.existsByUserID(request.getUserId())
				&& userCredentialsRepository.existsByAdminUserId(request.getAdminUserId());
	}

	@Override
	public boolean validateChangePasswordRequest(ChangePasswordRequest request) {
		return userCredentialsRepository.existsByEmailAndUserStatusNotLike(request.getUserId(),
				Status.TERMINATED.getValue())
				&& userCredentialsRepository.existsByPassWordAndEmail(request.getCurrentPassword(), request.getUserId());

	}

	@Override
	public boolean validateAdminTransactionDataRequest(TransactionRequest request) {

		logger.debug("Enter into validateAdminTransactionDataRequestService");

		return userCredentialsRepository.existsByPassWordAndEmail(request.getPassword(), request.getUserId());

	}

	@Override
	public boolean validateUserList(UserListRequest request) {
		logger.debug("Enter into validateUserList");
		return userCredentialsRepository.existsByEmail(request.getAdminUserId());

	}

	@Override
	public UserCredentials getUserDetail(UserCredentials userProfile) {
		return userCredentialsRepository.findByEmailAndUserStatusNotLike(userProfile.getEmail(),
				Status.TERMINATED.getValue());

	}

	@Override
	public UserCredentials getUserStatus(UserCredentials userCredential) {
		return userCredentialsRepository.findByEmailAndUserStatusNotLike(userCredential.getUserID(),
				Status.TERMINATED.getValue());
	}

	@Override
	public boolean validateUserDetail(UserDetail request, UserViewResponse response) {
		return userCredentialsRepository.existsByEmailAndUserStatusNotLike(request.getUserId(),
				Status.TERMINATED.getValue());
	}

	@Override
	public boolean validateUserProfileRequest(UserProfileUpdateRequest request) {
		return userCredentialsRepository.existsByUserID(request.getUserId());
	}

	@Transactional
	@Override
	public boolean updateUserProfile(UserProfileUpdateRequest request) {
		QUserCredentials user = QUserCredentials.userCredentials;
		long noOfRowUserCrd = new JPAUpdateClause(em, user).where(user.userID.eq(request.getUserId()))
				.set(user.userName, request.getUserName()).set(user.address, request.getAddress())
				.set(user.userType, request.getUserType()).set(user.firstName, request.getFirstName())
				.set(user.lastName, request.getLastName()).set(user.phone, request.getPhoneNumber())
				.set(user.email, request.getEmail()).set(user.roleName, request.getRoleName())
				.set(user.updatedDateTime, Timestamp.from(Instant.now())).execute();
		return noOfRowUserCrd == 1l;
	}

	@Override
	public boolean validateEmailToken(String emailToken, String userId) {
		UserCredentials userCredentials = userCredentialsRepository.findByEmailAndUserStatusLike(userId, Status.ACTIVE.getValue());
		return (!StringUtil.isNull(userCredentials) && userCredentials.getEmailToken().equals(emailToken));
	}

	@Override
	public SearchUserResponse getUserList(UserData request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<Tuple> userList = null;
		if (request.getLoginUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.toString())) {
			userList = query.from(QUserCredentials.userCredentials)
					.where(QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
							isUserName(request.getUserName()), isUserId(request.getUserId()),
							isUserType(request.getUserType()), isFirstName(request.getFirstName()),
							isLastName(request.getLastName()), isPhoneNumber(request.getPhoneNumber()),
							isEmailId(request.getEmail()), isRole(request.getUserRole()), isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QUserCredentials.userCredentials.id.desc())
					.list(QUserCredentials.userCredentials.address, QUserCredentials.userCredentials.userType,
							QUserCredentials.userCredentials.firstName, QUserCredentials.userCredentials.email,
							QUserCredentials.userCredentials.phone, QUserCredentials.userCredentials.lastName,
							QUserCredentials.userCredentials.userID, QUserCredentials.userCredentials.userName,
							QUserCredentials.userCredentials.userRole, QUserCredentials.userCredentials.userStatus,
							QUserCredentials.userCredentials.userStatus);
		} else if (request.getLoginUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.toString())) {
			userList = query.from(QUserCredentials.userCredentials)
					.where(QUserCredentials.userCredentials.userID.eq(QUserCredentials.userCredentials.userID),
							QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
							QUserCredentials.userCredentials.userType.ne(RoleLevel.SUPER_ADMIN.toString()),
							QUserCredentials.userCredentials.adminUserId.eq(request.getAdminUserId()),
							isUserName(request.getUserName()), isUserId(request.getUserId()),
							isUserType(request.getUserType()), isFirstName(request.getFirstName()),
							isLastName(request.getLastName()), isPhoneNumber(request.getPhoneNumber()),
							isEmailId(request.getEmail()), isRole(request.getUserRole()), isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QUserCredentials.userCredentials.id.desc())
					.list(QUserCredentials.userCredentials.address, QUserCredentials.userCredentials.userType,
							QUserCredentials.userCredentials.firstName, QUserCredentials.userCredentials.email,
							QUserCredentials.userCredentials.phone, QUserCredentials.userCredentials.lastName,
							QUserCredentials.userCredentials.userID, QUserCredentials.userCredentials.userName,
							QUserCredentials.userCredentials.userRole, QUserCredentials.userCredentials.userStatus,
							QUserCredentials.userCredentials.userStatus);
		} else {
			userList = query.from(QUserCredentials.userCredentials)
					.where(QUserCredentials.userCredentials.userID.eq(QUserCredentials.userCredentials.userID),
							QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
							QUserCredentials.userCredentials.userType.eq(RoleLevel.PTO_ADMIN.toString()),
							QUserCredentials.userCredentials.adminUserId.eq(request.getAdminUserId()),
							isUserName(request.getUserName()), isUserId(request.getUserId()),
							isUserType(request.getUserType()), isFirstName(request.getFirstName()),
							isLastName(request.getLastName()), isPhoneNumber(request.getPhoneNumber()),
							isEmailId(request.getEmail()), isRole(request.getUserRole()), isStatus(request.getStatus()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QUserCredentials.userCredentials.id.desc())
					.list(QUserCredentials.userCredentials.address, QUserCredentials.userCredentials.userType,
							QUserCredentials.userCredentials.firstName, QUserCredentials.userCredentials.email,
							QUserCredentials.userCredentials.phone, QUserCredentials.userCredentials.lastName,
							QUserCredentials.userCredentials.userID, QUserCredentials.userCredentials.userName,
							QUserCredentials.userCredentials.userRole, QUserCredentials.userCredentials.userStatus,
							QUserCredentials.userCredentials.userStatus);
		}
		List<UserData> userDataList = new ArrayList<>();
		for (Tuple tuple : userList) {
			getUserDataList(userDataList, tuple);
		}

		SearchUserResponse response = new SearchUserResponse();
		response.setListUser(userDataList);
		response.setTotalRecords(totalUserRecords(request));
		return response;
	}

	private void getUserDataList(List<UserData> userDataList, Tuple tuple) {
		UserData userData = new UserData();
		userData.setStatus(tuple.get(QUserCredentials.userCredentials.userStatus));
		userData.setAddress(tuple.get(QUserCredentials.userCredentials.address));
		userData.setUserType(tuple.get(QUserCredentials.userCredentials.userType));
		userData.setFirstName(tuple.get(QUserCredentials.userCredentials.firstName));
		userData.setEmail(tuple.get(QUserCredentials.userCredentials.email));
		userData.setPhoneNumber(tuple.get(QUserCredentials.userCredentials.phone));
		userData.setLastName(tuple.get(QUserCredentials.userCredentials.lastName));
		userData.setUserId(tuple.get(QUserCredentials.userCredentials.userID));
		userData.setUserName(tuple.get(QUserCredentials.userCredentials.userName));
		userData.setUserRole(tuple.get(QUserCredentials.userCredentials.userRole));
		if (!StringUtil.isNullEmpty(tuple.get(QUserCredentials.userCredentials.userType)) && tuple
				.get(QUserCredentials.userCredentials.userType).equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			try {
				UserCredentials userCredentials = userCredentialsRepository
						.findByUserID(tuple.get(QUserCredentials.userCredentials.userID));
				userData.setOrganizationId(String.valueOf(userCredentials.getOrgId()));
			} catch (Exception e) {
				logger.error(ERROR_LOG, e);
			}
		}
		if (!StringUtil.isNullEmpty(tuple.get(QUserCredentials.userCredentials.userType)) && tuple
				.get(QUserCredentials.userCredentials.userType).equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			try {
				UserCredentials userCredentials = userCredentialsRepository
						.findByUserID(tuple.get(QUserCredentials.userCredentials.userID));
				userData.setPtoId(String.valueOf(userCredentials.getPtoMasterId()));
			} catch (Exception e) {
				logger.error(ERROR_LOG, e);
			}
		}
		if (!StringUtil.isNullEmpty(tuple.get(QUserCredentials.userCredentials.userRole))
				&& !StringUtil.isNullEmpty(tuple.get(QUserCredentials.userCredentials.userType))) {
			try {
				List<AfcsRole> roleList = afcsRoleRepository.findByUserTypeAndId(userData.getUserType(),
						Long.valueOf(tuple.get(QUserCredentials.userCredentials.userRole)));
				userData.setRoleName(roleList.get(0).getName());
			} catch (Exception e) {
				logger.error(ERROR_LOG, e);
			}
		}
		userData.setStatus(tuple.get(QUserCredentials.userCredentials.userStatus));
		userDataList.add(userData);
	}

	private int totalUserRecords(UserData request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getLoginUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.toString())) {
			count = query.from(QUserCredentials.userCredentials)
					.where(QUserCredentials.userCredentials.userID.eq(QUserCredentials.userCredentials.userID),
							QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
							isUserName(request.getUserName()), isUserId(request.getUserId()),
							isUserType(request.getUserType()), isFirstName(request.getFirstName()),
							isLastName(request.getLastName()), isPhoneNumber(request.getPhoneNumber()),
							isEmailId(request.getEmail()), isRole(request.getUserRole()), isStatus(request.getStatus()))
					.orderBy(QUserCredentials.userCredentials.id.desc()).count();
		} else if (request.getLoginUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.toString())) {
			count = query.from(QUserCredentials.userCredentials)
					.where(QUserCredentials.userCredentials.userID.eq(QUserCredentials.userCredentials.userID),
							QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
							QUserCredentials.userCredentials.userType.ne(RoleLevel.SUPER_ADMIN.toString()),
							QUserCredentials.userCredentials.adminUserId.eq(request.getAdminUserId()),
							isUserName(request.getUserName()), isUserId(request.getUserId()),
							isUserType(request.getUserType()), isFirstName(request.getFirstName()),
							isLastName(request.getLastName()), isPhoneNumber(request.getPhoneNumber()),
							isEmailId(request.getEmail()), isRole(request.getUserRole()), isStatus(request.getStatus()))
					.orderBy(QUserCredentials.userCredentials.id.desc()).count();
		} else {
			count = query.from(QUserCredentials.userCredentials)
					.where(QUserCredentials.userCredentials.userID.eq(QUserCredentials.userCredentials.userID),
							QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
							QUserCredentials.userCredentials.userType.eq(RoleLevel.PTO_ADMIN.toString()),
							QUserCredentials.userCredentials.adminUserId.eq(request.getAdminUserId()),
							isUserName(request.getUserName()), isUserId(request.getUserId()),
							isUserType(request.getUserType()), isFirstName(request.getFirstName()),
							isLastName(request.getLastName()), isPhoneNumber(request.getPhoneNumber()),
							isEmailId(request.getEmail()), isRole(request.getUserRole()), isStatus(request.getStatus()))
					.orderBy(QUserCredentials.userCredentials.id.desc()).count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isUserName(String userName) {
		return !StringUtil.isNullEmpty(userName) ? QUserCredentials.userCredentials.userName.toUpperCase()
				.like("%" + userName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isUserId(String userId) {
		return !StringUtil.isNullEmpty(userId) ? QUserCredentials.userCredentials.userID.toUpperCase()
				.like("%" + userId.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isUserType(String userType) {
		return !StringUtil.isNullEmpty(userType) ? QUserCredentials.userCredentials.userType.toUpperCase()
				.like("%" + userType.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isFirstName(String firstName) {
		return !StringUtil.isNullEmpty(firstName) ? QUserCredentials.userCredentials.firstName.toUpperCase()
				.like("%" + firstName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isPhoneNumber(String phoneNumber) {
		return !StringUtil.isNullEmpty(phoneNumber) ? QUserCredentials.userCredentials.phone.like(phoneNumber) : null;
	}

	private BooleanExpression isEmailId(String email) {
		return !StringUtil.isNullEmpty(email) ? QUserCredentials.userCredentials.email.toUpperCase()
				.like("%" + email.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isLastName(String lastName) {
		return !StringUtil.isNullEmpty(lastName) ? QUserCredentials.userCredentials.lastName.toUpperCase()
				.like("%" + lastName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isRole(String role) {
		return !StringUtil.isNullEmpty(role) ? QUserCredentials.userCredentials.userRole.toUpperCase()
				.like("%" + role.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QUserCredentials.userCredentials.userStatus.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isStatusNotTerminated(String status) {
		return QUserCredentials.userCredentials.userStatus.toUpperCase()
				.notLike("%" + status.toUpperCase().replace("*", "") + "%");
	}

	@Override
	public boolean saveOrUpdateSessionDetails(LoginSessionDetails loginSessionDetails) {
		return (null != loginSessionDetailsRepository.save(loginSessionDetails));
	}

	@Override
	public LoginSessionDetailsResponse searchLoginSessionDetails(LoginSessionDetailsRequest loginSessionDetails) {
		JPAQuery query = new JPAQuery(em);
		List<LoginSessionDetailsRequest> userList = new ArrayList<>();
		LoginSessionDetailsResponse loginSessionDetailsResponse = new LoginSessionDetailsResponse();
		List<LoginSessionDetails> tupleList = query.from(QLoginSessionDetails.loginSessionDetails)
				.where(isLoginStatus(loginSessionDetails.getLoginStatus()),
						isSessionId(loginSessionDetails.getSessionId()),
						isUserSessionId(loginSessionDetails.getUserId()))

				.orderBy(orderByLoginTimeDesc()).list(QLoginSessionDetails.loginSessionDetails);

		for (LoginSessionDetails sessionDetails : tupleList) {
			LoginSessionDetailsRequest response = new LoginSessionDetailsRequest();
			response.setBrowserName(sessionDetails.getBrowserType());
			response.setIpAddress(sessionDetails.getIpAddress());
			response.setLoginStatus(sessionDetails.getLoginStatus());
			response.setLoginTime(sessionDetails.getLoginTime());
			response.setUserName(sessionDetails.getUserName());
			userList.add(response);
		}

		loginSessionDetailsResponse.setLoginSessionDetailsRequest(userList);
		return loginSessionDetailsResponse;

	}

	private BooleanExpression isSessionId(String sessionId) {
		return sessionId != null ? QLoginSessionDetails.loginSessionDetails.sessionId.eq(sessionId) : null;
	}

	private BooleanExpression isLoginStatus(String loginStatus) {
		return loginStatus != null ? QLoginSessionDetails.loginSessionDetails.loginStatus.eq(loginStatus) : null;
	}

	private OrderSpecifier<Timestamp> orderByLoginTimeDesc() {
		return QLoginSessionDetails.loginSessionDetails.loginTime.desc();
	}

	private BooleanExpression isUserSessionId(String userId) {
		return userId != null ? QLoginSessionDetails.loginSessionDetails.userName.eq(userId) : null;
	}

	@Override
	public boolean updateUserProfileEmailToken(String emailToken, String email, String userId) {
		UserCredentials userCredentials = userCredentialsRepository.findByEmailAndUserStatusLike(userId,
				Status.ACTIVE.getValue());
		if (!StringUtil.isNull(userCredentials)) {
			userCredentials.setEmailToken(emailToken);
			userCredentials.setUpdatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
			userCredentialsRepository.save(userCredentials);
			return true;
		}
		return false;
	}

	@Override
	public UserCredentials getUserDetailByUserId(UserCredentials userCredentials) {
		return userCredentialsRepository.findByEmailAndUserStatusLike(userCredentials.getUserID(),
				Status.ACTIVE.getValue());
	}

	@Override
	public boolean validateExistingUserForPassword(UserInfoCheck request) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials)
				.where(QUserCredentials.userCredentials.email.eq(request.getUserId())).exists();
	}

	@Override
	public boolean validateUserRegistrationRequest(UserRegistrationRequest request) {
		return !userCredentialsRepository.existsByUserNameAndUserStatusNotLike(request.getUserName(),
				Status.TERMINATED.getValue());
	}

	@Override
	public UserCredentials updateUserStatus(UserStatusUpdateRequest request) {
		UserCredentials userCredentials = userCredentialsRepository.findByUserID(request.getUserId());
		userCredentials.setUserStatus(request.getStatus());
		userCredentials.setReason(request.getReason());
		return userCredentialsRepository.save(userCredentials);
	}

}