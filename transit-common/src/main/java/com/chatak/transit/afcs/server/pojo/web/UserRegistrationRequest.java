package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class UserRegistrationRequest implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private String userId;

	@NotEmpty(message = "chatak.afcs.service.adminuserid.required",groups=NotEmptyAndNull.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.adminuserid.size.invalid", groups=RegexCheck.class)
	private String adminUserId;

	@NotEmpty(message = "chatak.afcs.service.user.name.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.user.name.invalid",groups=SizeCheck.class)
	private String userName;

	@NotEmpty(message = "chatak.afcs.service.password.required",groups=NotEmptyAndNull.class)
	@Size(min=6 , max = 15, message = "chatak.afcs.service.password.size.invalid",groups=SizeCheck.class)
	private String password;

	@NotBlank(message = "chatak.afcs.service.userrole.required")
	private String userRole;

	private String organizationId;
	
	@Size(max = 100, message = "chatak.afcs.service.address.size.invalid")
	private String address;
	
	@Pattern(regexp = Constants.REGEX_PHONE_VALIDATION, message = "chatak.afcs.service.phone.invalid")
	private String phoneNumber;
	
	@NotEmpty(message = "chatak.afcs.service.email.required")
	@Size(min=8, max = 32, message = "chatak.afcs.service.email.id.length.invalid",groups=SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.email.invalid")
	private String email;
	
	@NotEmpty(message = "chatak.afcs.service.user.type.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.user.type.invalid",groups=SizeCheck.class)
	private String userType;
	
	@NotEmpty(message = "chatak.afcs.service.first.name.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.first.name.invalid",groups=SizeCheck.class)
	private String firstName;
	
	@NotEmpty(message = "chatak.afcs.service.last.name.required",groups=NotEmptyAndNull.class)
	@Size(min=2, max = 30, message = "chatak.afcs.service.last.name.invalid",groups=SizeCheck.class)
	private String lastName;
	
	private String ptoId;
	
	private Long orgId;
	
	private Long ptoMasterId;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}
	
}
