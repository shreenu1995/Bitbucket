package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.SearchUserResponse;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserStatusUpdateResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface UserManagementService {

	WebResponse userRegister(UserRegistrationRequest request) throws AFCSException;

	String emailExistsCheck(String userId);

	SearchUserResponse searchUser(UserData request) throws AFCSException;

	WebResponse updateUser(UserProfileUpdateRequest request) throws AFCSException;

	UserStatusUpdateResponse updateUserStatus(UserStatusUpdateRequest request) throws AFCSException;

}
