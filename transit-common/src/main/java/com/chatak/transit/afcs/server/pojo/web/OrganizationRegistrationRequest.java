package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class OrganizationRegistrationRequest implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required", groups = NotEmptyAndNull.class)
	@Size(min = 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid", groups = RegexCheck.class)
	private String userId;

	@NotBlank(message = "chatak.afcs.service.pto.name.required")
	@Size(min = 2, max = 50, message = "chatak.afcs.service.pto.name.invalid")
	private String organizationName;

	@NotEmpty(message = "chatak.afcs.service.contact.person.required", groups = NotEmptyAndNull.class)
	@Size(max = 50, message = "chatak.afcs.service.contact.person.size.invalid", groups = SizeCheck.class)
	private String contactPerson;

	@NotEmpty(message = "chatak.afcs.service.mobile.required", groups = NotEmptyAndNull.class)
	@Size(min = 10, max = 13, message = "chatak.afcs.service.mobile.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.REGEX_PHONE_VALIDATION, message = "chatak.afcs.service.mobile.invalid", groups = RegexCheck.class)
	private String organizationMobile;

	@NotEmpty(message = "chatak.afcs.service.email.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.email.invalid")
	private String organizationEmail;

	@NotEmpty(message = "chatak.afcs.service.site.url.required", groups = NotEmptyAndNull.class)
	@Size(max = 50, message = "chatak.afcs.service.site.url.size.invalid", groups = SizeCheck.class)
	private String siteUrl;

	private String state;

	private String city;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getOrganizationMobile() {
		return organizationMobile;
	}

	public void setOrganizationMobile(String organizationMobile) {
		this.organizationMobile = organizationMobile;
	}

	public String getOrganizationEmail() {
		return organizationEmail;
	}

	public void setOrganizationEmail(String organizationEmail) {
		this.organizationEmail = organizationEmail;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
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

}
