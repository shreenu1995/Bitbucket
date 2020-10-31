package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class UserProfileUpdateRequest extends AddressRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	@NotEmpty(message = "chatak.afcs.service.adminuserid.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.adminuserid.size.invalid")
	private String adminUserId;

	@NotEmpty(message = "chatak.afcs.service.user.name.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.user.name.invalid",groups=SizeCheck.class)
	private String userName;
	
	@NotEmpty(message = "chatak.afcs.service.user.type.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.user.type.invalid",groups=SizeCheck.class)
	private String userType;
	
	@NotEmpty(message = "chatak.afcs.service.first.name.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.first.name.invalid",groups=SizeCheck.class)
	private String firstName;
	
	@NotEmpty(message = "chatak.afcs.service.last.name.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.last.name.invalid",groups=SizeCheck.class)
	private String lastName;

	private String dateTime;

	@NotBlank(message = "chatak.afcs.service.userrole.required")
	private String userRole;

	private String ptoId;
	
	private String organizationId;
	
	private String roleName;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
