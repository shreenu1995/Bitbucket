function ValidateIssuer() {
	if (!validateIssuerName() | !validatePto() | !validateServiceUrl()) {
		return false;
	} else {
		return true;
	}
}

function validateIssuerName() {
	var name = getVal('issuerName');
	if (isEmpty(name)) {
		setDiv("issuerNameMsgDiv", message.REQ_ISSUER_NAME);
		return false;
	}
	setDiv("issuerNameMsgDiv", "&nbsp;");
	return true;
}

function validatePto() {
	var name = getVal('ptoMasterId');
	if (isEmpty(name)) {
		setDiv("ptoMasterIdMsgDiv", message.REQ_PTO_NAME);
		return false;
	}
	setDiv("ptoMasterIdMsgDiv", "&nbsp;");
	return true;
}

function validateServiceUrl() {
	var name = getVal('serviceUrl');
	if (isEmpty(name)) {
		setDiv("serviceUrlMsgDiv", message.REQ_SERVICE_URL_NAME);
		return false;
	}
	setDiv("serviceUrlMsgDiv", "&nbsp;");
	return true;
}

function changeIssuerStatus(issuerId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Fee Status to " + status + '?');
	$('#issuerDiv').popup('show');
	get('issuerId').value = issuerId;
}
