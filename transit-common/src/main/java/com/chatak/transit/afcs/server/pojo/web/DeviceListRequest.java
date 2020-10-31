package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class DeviceListRequest implements Serializable {

	private static final long serialVersionUID = -4790415369247528943L;

	@NotBlank(message = "chatak.afcs.service.user.id.required", groups = NotEmptyAndNull.class)
	@Size(min = 5, max = 50, message = "chatak.afcs.service.user.id.length.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid", groups = RegexCheck.class)
	private String userId;

	@NotBlank(message = "chatak.afcs.service.depot.code.required")
	@Size(min = 4, max = 4, message = "chatak.afcs.service.depot.code.invalid")
	private String depotId;

	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min = 12, max = 12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoOperationId;

	@NotBlank(message = "chatak.afcs.service.station.code.required")
	@Size(max = 4, message = "chatak.afcs.service.station.code.invalid")
	private String stationId;

	@NotBlank(message = "chatak.afcs.service.device.type.required")
	@Size(min = 1, max = 25, message = "chatak.afcs.service.device.type.invalid")
	private String deviceTypeName;

	@NotBlank(message = "chatak.afcs.service.device.model.required")
	@Size(min = 1, max = 25, message = "chatak.afcs.service.device.model.invalid")
	private String deviceModel;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepotId() {
		return depotId;
	}

	public void setDepotId(String depotId) {
		this.depotId = depotId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

}
