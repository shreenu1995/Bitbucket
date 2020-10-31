function validateDescription() {
	var name = getVal('description');
	if (isEmpty(name)) {
		setDiv("descriptionMsgDiv", message.REQ_DESCRIPTION);
		return false;
	}
	setDiv("descriptionMsgDiv", "&nbsp;");
	return true
}

function validatePtoOperationList() {
	var organizationList = getVal('ptoOperationList');
	if(isEmpty(organizationList)) {
		setDiv('ptoOperationTypeDiv',message.REQ_PTO_OPE_ID);
		return false;
	}
	setDiv('ptoOperationTypeDiv',"&nbsp");
	return true;
}

function validateStationCode() {
	var name = getVal('stationCode');
	if (isEmpty(name)) {
		setDiv("stationCodeMsgDiv", message.REQ_STATION_NAME);
		return false;
	}
	setDiv("stationCodeMsgDiv", "&nbsp;");
	return true
}

function validateDeviceType() {
	var name = getVal('deviceTypeName');
	if (isEmpty(name)) {
		setDiv("deviceTypeMsgDiv", message.REQ_EQP_TYPE);
		return false;
	}
	setDiv("deviceTypeMsgDiv", "&nbsp;");
	return true
}

function validateDeviceModelNumber() {
	var name = getVal('deviceModelNumber');
	if (isEmpty(name)) {
		setDiv("deviceModelNumberMsgDiv", message.REQ_EQP_MODEL);
		return false;
	}
	setDiv("deviceModelNumberMsgDiv", "&nbsp;");
	return true
}

function validateDeviceSerialNumber() {
	var name = getVal('deviceSerialNumber');
	if (isEmpty(name)) {
		setDiv("deviceSerialNumberMsgDiv", message.REQ_EQP_SERIAL);
		return false;
	}
	setDiv("deviceSerialNumberMsgDiv", "&nbsp;");
	return true
}

function validateAdditionalData() {
	var name = getVal('additionalSpecialData');
	if (isEmpty(name)) {
		setDiv("additionalSpecialDataMsgDiv", message.REQ_ADD_DATA);
		return false;
	}
	setDiv("additionalSpecialDataMsgDiv", "&nbsp;");
	return true
}

function validateDeviceRegistrationSubmit() {
	if (!validateDescription() | !validateStationCode()
			| !validateDeviceType() | !validateDeviceModelNumber()
			| !validateDeviceSerialNumber() | !validateAdditionalData()
			| !validatePtoOperationList()) {
		return false;
	} else {
		return true;
	}
}

function resetDeviceRegister() {
	setDiv("descriptionMsgDiv", "&nbsp;");
	setDiv("stationCodeMsgDiv", "&nbsp;"); 
	setDiv("deviceTypeMsgDiv", "&nbsp;");
	setDiv("deviceModelNumberMsgDiv", "&nbsp;");
	setDiv("deviceSerialNumberMsgDiv", "&nbsp;");
	setDiv("additionalSpecialDataMsgDiv", "&nbsp;");
}

function editDevice(deviceId) {
	get('deviceEdit').value = deviceId;
	document.forms["editDeviceAction"].submit();
}
 
function validateDeviceUpdateSubmit() {
	if (!validateDescription() | !validateStationCode()
			| !validateDeviceType() | !validateDeviceModelNumber()
			| !validateDeviceSerialNumber() | !validateAdditionalData()) {
		return false;
	} else {
		return true;
	}
}


