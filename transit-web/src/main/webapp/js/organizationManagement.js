function validateOrganizationName() {
	var name = getVal('organizationName');
	if (isEmpty(name)) {
		setDiv("organizationNameMsgDiv", message.REQ_ORG_NAME);
		return false;
	}
	setDiv("organizationNameMsgDiv", "&nbsp;");
	return true;
}

function validateContactPerson() {
	var name = getVal('contactPerson');
	if (isEmpty(name)) {
		setDiv("contactPersonMsgDiv", message.REQ_CONTACT_PERSON);
		return false;
	}
	setDiv("contactPersonMsgDiv", "&nbsp;");
	return true;
}

function validateSiteURL() {
	var text = getVal('siteUrl');
	var regex = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/;
	if (isEmpty(text)) {
		setDiv("siteUrlMsgDiv", message.REQ_SITE_URL);
		return false;
	} else if (!regex.test(text)) {
		setDiv("siteUrlMsgDiv", message.INVALID_SITE_URL);
		return false;
	}
	setDiv("siteUrlMsgDiv", "&nbsp;");
	return true;
}

function validateOrganizationMobile() {
	var reg = /^[0-9+]*$/;
	var mobNumber = getVal('organizationMobile');
	if (isEmpty(mobNumber)) {
		setDiv("mobileMsgDiv", message.REQ_MOBILE_NO);
		return false;
	} else if (!mobNumber.match(reg)) {
		setDiv("mobileMsgDiv", message.REQ_DIGITS);
		return false;
	} else if (mobNumber.length < 10 || mobNumber.length > 13) {
		setDiv("mobileMsgDiv", message.MIN_MOBILE_NO);
		return false;
	} else {
		setDiv("mobileMsgDiv", "&nbsp;");
		return true;
	}
}
function validateOrganizationEmail() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z]{2,4})+$/;
	var emailAddress = getVal('organizationEmail');
	if (isEmpty(emailAddress)) {
		setDiv("emailMsgDiv", message.REQ_EMAIL_ID);
		return false;
	} else if (reg.test(emailAddress) == false) {
		setDiv("emailMsgDiv", message.IN_EMAIL);
		return false;
	}
	setDiv("emailMsgDiv", "&nbsp;");
	return true;
}
function validateState() {
	var name = getVal('state');
	if (isEmpty(name)) {
		setDiv("stateMsgDiv", message.REQ_STATE);
		return false;
	}
	setDiv("stateMsgDiv", "&nbsp;");
	return true;
}

function validateCityName() {
	var name = getVal('city');
	if (isEmpty(name)) {
		setDiv("citydiv", message.REQ_CITY);
		return false;
	}
	setDiv("citydiv", "&nbsp;");
	return true;
}

function validateOrganizationSubmit() {
	if (!validateOrganizationName() | !validateState() | !validateCityName()
			| !validateContactPerson() | !validateOrganizationMobile() | !validateOrganizationEmail()
			| !validateSiteURL()) {
		return false;
	} else {
		return true;
	}
}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");
	resetErrorSuccessMessage();
}

function editOrganization(orgId) {
	get('organizationEdit').value = orgId;
	document.forms["editOrganizationAction"].submit();
}

function viewOrganization(orgId) {
	get('organizationView').value = orgId;
	document.forms["viewOrganizationAction"].submit();
}

function changeOrganizationStatus(organizationId, status) {
	clearPopupDesc();
	setDiv('statusId', " Do You Wish To Change Organisation Status to "
			+ status + '?');
	$('#orgDiv').popup('show');
	get('organizationIdStatus').value = organizationId;
	get('organizationStatus').value = status;
}
