package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class UserListRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4930099828548615092L;


	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min=12,max=12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoId;
	
	@NotEmpty(message = "chatak.afcs.service.adminuserid.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.adminuserid.size.invalid")
	private String adminUserId;

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getPtoId() {
		return ptoId;
	}

	public void setPtoId(String ptoId) {
		this.ptoId = ptoId;
	}

}
