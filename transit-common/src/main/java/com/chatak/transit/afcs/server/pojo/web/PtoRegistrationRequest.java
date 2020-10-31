package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class PtoRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 4166413240694137189L;
	
	private String userId;

	private String ptoName;

	private Long orgId;

	private String state;

	private String city;

	private String contactPerson;
	
	@NotEmpty(message = "chatak.afcs.service.mobile.required", groups = NotEmptyAndNull.class)
	@Size(min = 10, max = 13, message = "chatak.afcs.service.mobile.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.REGEX_PHONE_VALIDATION, message = "chatak.afcs.service.mobile.invalid", groups = RegexCheck.class)
	private String ptoMobile;

	@NotEmpty(message = "chatak.afcs.service.email.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.email.invalid")
	private String ptoEmail;

	@NotEmpty(message = "chatak.afcs.service.site.url.required", groups = NotEmptyAndNull.class)
	@Size(max = 32, message = "chatak.afcs.service.site.url.size.invalid", groups = SizeCheck.class)
	private String siteUrl;

	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPtoMobile() {
		return ptoMobile;
	}

	public void setPtoMobile(String ptoMobile) {
		this.ptoMobile = ptoMobile;
	}

	public String getPtoEmail() {
		return ptoEmail;
	}

	public void setPtoEmail(String ptoEmail) {
		this.ptoEmail = ptoEmail;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
}
