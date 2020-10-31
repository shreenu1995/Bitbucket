package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class DeviceRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 5019392181009131694L;

	@NotBlank(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(max=35, message = "chatak.afcs.service.userid.size.invalid")
	private String userId;

	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min=12,max=12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoOperationId;

	private String description;
	
	@NotBlank(message = "chatak.afcs.service.station.code.required")
	@Size(max=4, message = "chatak.afcs.service.station.code.size.invalid")
	private String stationCode;

	@NotBlank(message="chatak.afcs.service.device.type.id.required")
	@Size(max = 4, message = "chatak.afcs.service.device.type.id.size.invalid")
	private String deviceTypeName;

	@NotBlank(message = "chatak.afcs.service.device.modelno.required")
	private String deviceModel;

	@NotBlank(message = "chatak.afcs.service.device.serialno.required")
	private String deviceSerialNumber;

	private String additionalSpecialData;
	
	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getAdditionalSpecialData() {
		return additionalSpecialData;
	}

	public void setAdditionalSpecialData(String additionalSpecialData) {
		this.additionalSpecialData = additionalSpecialData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceRegistrationRequest [userId=");
		builder.append(userId);
		builder.append(", ptoOperationId=");
		builder.append(ptoOperationId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", stationCode=");
		builder.append(stationCode);
		builder.append(", deviceTypeName=");
		builder.append(deviceTypeName);
		builder.append(", deviceModel=");
		builder.append(deviceModel);
		builder.append(", deviceSerialNumber=");
		builder.append(deviceSerialNumber);
		builder.append(", additionalSpecialData=");
		builder.append(additionalSpecialData);
		builder.append("]");
		return builder.toString();
	}

}
