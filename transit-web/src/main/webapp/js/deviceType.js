function validateDeviceTypeName() {
	var name = getVal('deviceTypeName');
	if (isEmpty(name)) {
		setDiv("deviceTypeNameMsgDiv", "Device type is required");
		return false;
	}
	setDiv("deviceTypeNameMsgDiv", "&nbsp;");
	return true
}

function validateSoftwareName() {
	var name = getVal('softwareName');
	if (isEmpty(name)) {
		setDiv("softwareNameMsgDiv", "Software name is required");
		return false;
	}
	setDiv("softwareNameMsgDiv", "&nbsp;");
	return true
}

function validateDiscription() {
	var name = getVal('description');
	if (isEmpty(name)) {
		setDiv("descriptionMsgDiv", "Description is required");
		return false;
	}
	setDiv("descriptionMsgDiv", "&nbsp;");
	return true
}

function validateDeviceCapabilites() {
	var name = getVal('deviceCapabilities');
	if (isEmpty(name)) {
		setDiv("deviceCapabilitesMsgdiv",
				"Device capabilites is required");
		return false;
	}
	setDiv("deviceCapabilitesMsgdiv", "&nbsp;");
	return true
}

function validateDeviceTypeEditSubmit() {
	if (!validateDeviceTypeName() | !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateDeviceTypeSubmit() {
	if (!validateDeviceTypeName()| !validateDiscription()){
		return false;
	} else {
		return true;
	}
}

function editDeviceType(deviceTypeId) {
	get('deviceTypeEdit').value = deviceTypeId;
	document.forms["editDeviceTypeAction"].submit();
}

function viewDeviceType(deviceTypeId) {
	get('deviceTypeView').value = deviceTypeId;
	document.forms["viewDeviceTypeAction"].submit();
}

function resetDeviceTypeRegister() {
	setDiv("deviceNameMsgDiv", "&nbsp;");
	setDiv("softwareNameMsgDiv", "&nbsp;");
	setDiv("descriptionMsgDiv", "&nbsp;");
	setDiv("deviceModelNumberMsgDiv", "&nbsp;");
	setDiv("deviceCapabilitesMsgdiv", "&nbsp;");
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function changeDeviceTypeStatus(deviceTypeCode,status) {
	get('deviceTypeCode').value = deviceTypeCode;
	get('deviceTypeStatus').value = status;
	document.forms["changeDeviceTypeStatusAction"].submit();
}

