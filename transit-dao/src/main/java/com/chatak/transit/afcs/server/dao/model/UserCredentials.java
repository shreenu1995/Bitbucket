/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_credentials")
public class UserCredentials implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "seq_user_credentials", sequenceName = "seq_user_credentials")
	@GeneratedValue(generator = "seq_user_credentials")

	@Column(name = "id")
	private long id;

	@Column(name = "user_id")
	private String userID;
	
	@Column(name = "user_name")
	private String userName;

	@Column(name = "pass_word")
	private String passWord;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String userStatus;

	@Column(name = "organization_id")
	private String organizationId;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "created_date_time")
	private Timestamp createdDateTime;

	@Column(name = "updated_date_time")
	private Timestamp updatedDateTime;

	@Column(name = "current_login_status")
	private boolean currentLoginStatus;

	@Column(name = "reason")
	private String reason;
	
	@Column(name = "pto_id")
	private String ptoId;
	
	@Column(name = "user_role")
	private String userRole;
	
	@Column(name = "admin_user_id")
	private String adminUserId;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "transaction_id")
	private String transactionID;

	@Column(name = "address")
	private String address;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "login_mode")
	private Long loginMode;
	
	@Column(name = "email_token")
	private String emailToken;

	@Column(name = "previous_passwords")
	private String previousPasswords;
	
	@Column(name = "org_id")
	private Long orgId;
	
	@Column(name = "pto_master_id")
	private Long ptoMasterId;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public long getId() {
		return id;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public boolean isCurrentLoginStatus() {
		return currentLoginStatus;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setCurrentLoginStatus(boolean currentLoginStatus) {
		this.currentLoginStatus = currentLoginStatus;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(Long loginMode) {
		this.loginMode = loginMode;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	public String getPreviousPasswords() {
		return previousPasswords;
	}

	public void setPreviousPasswords(String previousPasswords) {
		this.previousPasswords = previousPasswords;
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