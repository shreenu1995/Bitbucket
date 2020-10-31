package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class DeviceModelExistCheckRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9100337262670315784L;

	@NotBlank(message = "chatak.afcs.service.user.id.required",groups=NotEmptyAndNull.class)
	@Size(min=8, max = 32, message = "chatak.afcs.service.user.id.length.invalid",groups=SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid",groups=RegexCheck.class)
	private String userId;

	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min=12,max = 12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoOperationId;

	@NotBlank(message="chatak.afcs.service.device.type.name.required")
	@Size(min=1, max = 35, message = "chatak.afcs.service.device.type.name.invalid")
	private String deviceTypeName;

	@NotBlank(message="chatak.afcs.service.device.model.required")
	@Size(min=1, max = 25, message = "chatak.afcs.service.device.model.invalid")
	private String deviceModel;

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" DeviceModelExistCheckRequest  [  ");
		builder.append(" userId = ");
		builder.append(getUserId());
		builder.append(" ,  ptoOperationId = ");
		builder.append(getPtoOperationId());
		builder.append(" ,  deviceTypeName = ");
		builder.append(getDeviceTypeName());
		builder.append(" ,  deviceModel = ");
		builder.append(getDeviceModel());
		builder.append("   ]");
		return builder.toString();
	}
}
