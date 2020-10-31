function validateStationAddress() {
	resetErrorSuccessMessage();
	var name = getVal('stationaddress');
	if (isEmpty(name)) {
		setDiv("addressMsgDiv", message.REQ_ADDRESS);
		return false;
	}
	setDiv("addressMsgDiv", "&nbsp;");
	return true
}

function validateCity() {
	resetErrorSuccessMessage();
	var name = getVal('city');
	if (isEmpty(name)) {
		setDiv("cityMsgDiv", message.REQ_CITY);
		return false;
	}
	setDiv("cityMsgDiv", "&nbsp;");
	return true
}

function validateDistrict() {
	resetErrorSuccessMessage();
	var name = getVal('district');
	if (isEmpty(name)) {
		setDiv("districtMsgDiv", message.REQ_DISTRICT);
		return false;
	}
	setDiv("districtMsgDiv", "&nbsp;");
	return true
}

function validateState() {
	resetErrorSuccessMessage();
	var name = getVal('state');
	if (isEmpty(name)) {
		setDiv("stateMsgDiv", message.REQ_STATE);
		return false;
	}
	setDiv("stateMsgDiv", "&nbsp;");
	return true
}

function validateCountry() {
	resetErrorSuccessMessage();
	var name = getVal('country');
	if (isEmpty(name)) {
		setDiv("countryMsgDiv", message.REQ_COUNTRY);
		return false;
	}
	setDiv("countryMsgDiv", "&nbsp;");
	return true
}

function validatePinCode() {
	resetErrorSuccessMessage();
	var name = getVal('pinCode');
	if (isEmpty(name)) {
		setDiv("pinCodeMdgDiv", message.REQ_PINCODE);
		return false;
	}
	setDiv("pinCodeMdgDiv", "&nbsp;");
	return true
}

function validateStopAddress() {
	resetErrorSuccessMessage();
	var name = getVal('stopAddress');
	if (isEmpty(name)) {
		setDiv("addressMsgDiv", message.REQ_ADDRESS);
		return false;
	}
	setDiv("addressMsgDiv", "&nbsp;");
	return true
}
