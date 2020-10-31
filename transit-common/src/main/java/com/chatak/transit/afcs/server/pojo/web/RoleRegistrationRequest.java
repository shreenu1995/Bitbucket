package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;

public class RoleRegistrationRequest implements Serializable {

	private static final long serialVersionUID = -1532467575254079460L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Size(min= 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	@NotBlank(message = "chatak.afcs.service.role.name.required")
	@Size(min = 2, max = 30, message = "chatak.afcs.service.role.name.invalid")
	private String roleName;

	@NotBlank(message = "chatak.afcs.service.pto.id.required")
	@Size(min = 6, max = 6, message = "chatak.afcs.service.pto.id.invalid")
	private String ptoId;

	@NotEmpty(message = "chatak.afcs.service.privilege.id.list")
	private List<Integer> privilegesId;

	@Size(max = 100, message = "chatak.afcs.service.description.invalid")
	private String roleDescription;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

	public List<Integer> getPrivilegesId() {
		return privilegesId;
	}

	public void setPrivilegesId(List<Integer> privilegesId) {
		this.privilegesId = privilegesId;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

}
