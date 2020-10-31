package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class AddressRequest extends WebResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(max = 100, message = "chatak.afcs.service.address.size.invalid")
	private String address;

	@Size(max = 20, message = "chatak.afcs.service.city.size.invalid")
	private String city;

	@Size(max = 20, message = "chatak.afcs.service.district.size.invalid")
	private String district;

	@Size(max = 20, message = "chatak.afcs.service.state.size.invalid")
	private String state;

	@Size(max = 20, message = "chatak.afcs.service.country.size.invalid")
	private String country;

	@Size(max = 7, message = "chatak.afcs.service.pincode.invalid")
	private String pincode;

	@Pattern(regexp = Constants.REGEX_PHONE_VALIDATION, message = "chatak.afcs.service.phone.invalid")
	private String phoneNumber;

	@NotEmpty(message = "chatak.afcs.service.mobile.required", groups = NotEmptyAndNull.class)
	@Pattern(regexp = Constants.REGEX_PHONE_VALIDATION, message = "chatak.afcs.service.mobile.invalid", groups = RegexCheck.class)
	private String mobile;

	@NotEmpty(message = "chatak.afcs.service.email.required")
	@Size(min = 8, max = 32, message = "chatak.afcs.service.email.id.length.invalid", groups = SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.email.invalid")
	private String email;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
