function doAjaxFetchDeviceMAnufacturer(deviceTypeId) {
	var status = 'Terminated'
	if (deviceTypeId == '') {
		document.getElementById('deviceManufacturerId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#deviceManufacturerCode").append(selectOption);
	} else {
		$.ajax({
					type : "GET",
					async : false,
					url : "fetchManufacturerForDeviceType?selectedDeviceTypeId=" + deviceTypeId + "&status=" + status,
					success : function(response) {
						// we have the response
						var obj = JSON.parse(response);
						// Remove previous options from the dropdown
						if (document.getElementById('deviceManufacturerCode').options == 'undefined') {
							document.getElementById('deviceManufacturerCode').options.length = 0;
						}
						document.getElementById('deviceManufacturerCode').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#deviceManufacturerCode").append(selectOption);

						var data = obj.listDeviceManuFacturer;
							data.sort(function(a,b) {return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);} ); 

							for (var i = 0; i < data.length; i++) {
								var name = data[i];
								{
									var newOption = document.createElement("option");
									var deviceModelName = name.deviceManufacturer;
									newOption.value = name.deviceManufacturerId;
									newOption.innerHTML = deviceModelName;
									$("#deviceManufacturerCode").append(newOption);
								}
							}
					},
					error : function(e) {
					}
				});
	}
}


function validateDeviceTypeName() {
	var name = getVal('deviceTypeId');
	if (isEmpty(name)) {
		setDiv("deviceTypeNameMsgDiv", message.REQ_EQP_TYPE);
		return false;
	}
	setDiv("deviceTypeNameMsgDiv", "&nbsp;");
	return true
}

function validateDeviceManufacturer() {
	var name = getVal('deviceManufacturerCode');
	if (isEmpty(name)) {
		setDiv("deviceManufacturerMsgDiv", message.REQ_DEV_MANU);
		return false;
	}
	setDiv("deviceManufacturerMsgDiv", "&nbsp;");
	return true
}

function validateDeviceModel() {
	var name = getVal('deviceModel');
	if (isEmpty(name)) {
		setDiv("deviceModelMsgDiv", message.REQ_EQP_MODEL_NAME);
		return false;
	}
	setDiv("deviceModelMsgDiv", "&nbsp;");
	return true
}

function validateDeviceIMEINumber() {
	var name = getVal('deviceIMEINumber');
	if (isEmpty(name)) {
		setDiv("deviceIMEINumberMsgDiv", message.REQ_IMEI_NUMBER);
		return false;
	}
	setDiv("deviceIMEINumberMsgDiv", "&nbsp;");
	return true
}

function validateDescription() {
	var name = getVal('description');
	if (isEmpty(name)) {
		setDiv("descriptionMsgDiv", message.REQ_DESCRIPTION);
		return false;
	}
	setDiv("descriptionMsgDiv", "&nbsp;");
	return true
}

function validateDeviceModelSubmit() {
	if (!validateDeviceTypeName() | !validateDeviceManufacturer() |!validateDeviceModel()
			|!validateDeviceIMEINumber() | !validateDescription()) {
		return false;
	} else {
		return true;
	}
}

function validateDeviceModelEditSubmit() {
	if (!validateDeviceTypeName() | !validateDeviceManufacturer() |!validateDeviceModel()
			|!validateDeviceIMEINumber() | !validateDescription()) {
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

function getPortalOnPage(pageNumber) {
	get('pageNumberId').value = pageNumber;
	document.forms["paginationForm"].submit();

}
function getPortalPrevPage(curPageNumber) {
	getPortalOnPage(parseInt(curPageNumber) - 1);
}

function getPortalNextPage(curPageNumber) {
	getPortalOnPage(parseInt(curPageNumber) + 1);
}

function editDeviceModel(deviceId) {
	get('deviceModelEdit').value = deviceId;
	document.forms["editDeviceModelAction"].submit();
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function viewDeviceModel(deviceId) {
	get('deviceModelView').value = deviceId;
	document.forms["viewDeviceModelAction"].submit();
}

function changeDeviceModelStatus(deviceId,status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Device Model Status to " + status + '?');
	$('#deviceModelDiv').popup('show');
	get('deviceModelIDStatus').value = deviceId;
	get('deviceModelStatus').value = status;
}
