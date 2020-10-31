package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class VehicleOnboardStatusUpdate extends BaseRequest{

	private static final long serialVersionUID = -760758171670622289L;

	@NotBlank(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid")
	private String userId;

	private Long vehicleOnboardingId;

	private String status;

	private Integer pageIndex;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getVehicleOnboardingId() {
		return vehicleOnboardingId;
	}

	public void setVehicleOnboardingId(Long vehicleOnboardingId) {
		this.vehicleOnboardingId = vehicleOnboardingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

}
