/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.AdminTransactionData;
import com.chatak.transit.afcs.server.dao.model.LoginSessionDetails;
import com.chatak.transit.afcs.server.dao.model.UserCredentials;
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

public interface UserSessionManagementDao {

	boolean saveUserDetails(UserCredentials userManagementMaster);

	boolean validateUserInfo(UserInfoCheck request);

	boolean validateExistingUserInfo(UserInfoCheck request);

	boolean validateExistingUserForRegistration(UserRegistrationRequest request);

	UserCredentials updateUserStatus(UserStatusUpdateRequest request);

	boolean validateUserStatusUpdate(UserStatusUpdateRequest request);

	boolean changePassword(UserCredentials usercredentials, String newPassword);

	boolean validateChangePasswordRequest(ChangePasswordRequest request);

	TransactionResponse saveAdminTransactionData(AdminTransactionData adminTransactionData,
			TransactionResponse adminTxnResponse);

	boolean validateAdminTransactionDataRequest(TransactionRequest request);

	boolean validateUserList(UserListRequest request);

	UserCredentials getUserDetail(UserCredentials userCredentials);

	boolean validateUserDetail(UserDetail request, UserViewResponse response);

	UserCredentials getUserStatus(UserCredentials userCredential);

	boolean updateUserProfile(UserProfileUpdateRequest request);

	boolean validateUserProfileRequest(UserProfileUpdateRequest request);

	boolean updateUserProfileEmailToken(String emailToken, String email, String userId);

	boolean validateEmailToken(String emailToken, String userId);

	SearchUserResponse getUserList(UserData request);
	
	boolean saveOrUpdateSessionDetails(LoginSessionDetails loginSessionDetails);
	
	LoginSessionDetailsResponse searchLoginSessionDetails(LoginSessionDetailsRequest loginSessionDetails);
	
	boolean validateExistingUserForPassword(UserInfoCheck request);

	boolean validateUserRegistrationRequest(UserRegistrationRequest request);

	UserCredentials getUserDetailByUserId(UserCredentials userCredentials);

}
