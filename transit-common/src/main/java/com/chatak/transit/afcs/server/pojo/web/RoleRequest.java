package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class RoleRequest extends LoginResponseParameters implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String roleName;
	private String roleCategory;
	private Long roleId;
	private String description;
	private String[] feature;
	private String[] featurData;
	private String status;
	private String roleType;
	private String reason;
	private String userType;
	private Long entityId;
	private String updateCheckFlage;
	private List<String> systemRoles;
	private Boolean isAuditable;
	private String dataChange;
	private String createdBy;
	private int pageSize;
	private int pageIndex;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(String roleCategory) {
		this.roleCategory = roleCategory;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDataChange() {
		return dataChange;
	}

	public void setDataChange(String dataChange) {
		this.dataChange = dataChange;
	}

	public Boolean getIsAuditable() {
		return isAuditable;
	}

	public void setIsAuditable(Boolean isAuditable) {
		this.isAuditable = isAuditable;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getFeature() {
		return feature;
	}

	public void setFeature(String[] feature) {
		this.feature = feature;
	}

	public String[] getFeaturData() {
		return featurData;
	}

	public void setFeaturData(String[] featurData) {
		this.featurData = featurData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String getUserType() {
		return userType;
	}

	@Override
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getUpdateCheckFlage() {
		return updateCheckFlage;
	}

	public void setUpdateCheckFlage(String updateCheckFlage) {
		this.updateCheckFlage = updateCheckFlage;
	}

	public List<String> getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(List<String> systemRoles) {
		this.systemRoles = systemRoles;
	}

}
