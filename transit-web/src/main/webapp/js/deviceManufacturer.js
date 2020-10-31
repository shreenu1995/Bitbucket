function validateDeviceType() {
	var name = getVal('deviceTypeId');
	if (isEmpty(name)) {
		setDiv("deviceTypeNameDiv", "Device type name is required");
		return false;
	}
	setDiv("deviceTypeNameDiv", "&nbsp;");
	return true
}

function validateDeviceManufacturer() {
	var name = getVal('deviceManufacturer');
	if (isEmpty(name)) {
		setDiv("deviceManufacturerMsgDiv", "Device Manufacturer name is required");
		return false;
	}
	setDiv("deviceManufacturerMsgDiv", "&nbsp;");
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

function validateDeviceManufacturerSubmit() {
	if (!validateDeviceType() | !validateDeviceManufacturer()
			| !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateDeviceManufacturerEditSubmit() {
	if (!validateDeviceType() | !validateDeviceManufacturer()
			| !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function editDeviceManufacturer(deviceManufacturerId) {
	get('deviceManufacturerEdit').value = deviceManufacturerId;
	document.forms["editDeviceManufacturerAction"].submit();
}

function viewDeviceManufacturer(deviceManufacturerId,deviceTypeName) {
	get('deviceManufacturerView').value = deviceManufacturerId;
	get('deviceTypeManufacturerView').value = deviceTypeName;
	document.forms["viewDeviceManufacturerAction"].submit();
}

function resetDeviceTypeRegister() {
	setDiv("deviceTypeMsgDiv", "&nbsp;");
	setDiv("deviceManufacturerMsgDiv", "&nbsp;");
	setDiv("descriptionMsgDiv", "&nbsp;");
}

function cancelButton() {
	get("cancelManufacturerId").value = "cancel";
	document.forms["cancelForm"].submit();
}

function changeDeviceManufacturerStatus(deviceManufacturerId,status) {
	get('deviceManufacturerId').value = deviceManufacturerId;
	get('deviceManufacturerStatus').value = status;
	document.forms["changeDeviceManufacturerStatusAction"].submit();
}

