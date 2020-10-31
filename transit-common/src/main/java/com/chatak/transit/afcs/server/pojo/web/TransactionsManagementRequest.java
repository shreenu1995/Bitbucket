package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class TransactionsManagementRequest implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required",groups=NotEmptyAndNull.class)
	@Size(min=8, max = 32, message = "chatak.afcs.service.user.id.length.invalid",groups=SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP,message = "chatak.afcs.service.user.id.invalid", groups=RegexCheck.class)
	private String userID;
	private String deviceType;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}
