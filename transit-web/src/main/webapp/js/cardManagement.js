
function viewCard(applicationId) {
	get('cardView').value = applicationId;
	document.forms["viewCardAction"].submit();
}

function validatePassType() {
	resetErrorSuccessMessage();
	var name = getVal('passType');
	if (isEmpty(name)) {
		setDiv("passTypeDiv", "Please enter passType");
		return false;
	}
	setDiv("passTypeDiv", "&nbsp;");
	return true
}

function validatePanNumber() {
	resetErrorSuccessMessage();
	var name = getVal('panNumber');
	if (isEmpty(name)) {
		setDiv("panNumberDiv","Please enter panNumber");
		return false;
	}
	setDiv("panNumberDiv", "&nbsp;");
	return true
}

function validateName() {
	resetErrorSuccessMessage();
	var name = getVal('name');
	if (isEmpty(name)) {
		setDiv("nameMsgDiv", "please enter name");
		return false;
	}
	setDiv("nameMsgDiv", "&nbsp;");
	return true
}

function validateMobileNumber() {
	var reg = /^[0-9+]*$/;
	var mobNumber = getVal('mobileNumber');
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

function validateCardSubmit() {
	if (!validatePassType() | !validatePanNumber()
			| !validateName() | !validateMobileNumber()) {
		return false;
	} else {
		return true;
	}
}

function resetErrorSuccessMessage() {
	document.getElementById("sucessDiv").innerHTML = "";
	document.getElementById("errorDiv").innerHTML = "";
}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");
	resetErrorSuccessMessage();
}