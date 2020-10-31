function fetchActivePtoListByOrganizationId(organizationId, elementId) {
	$.ajax({
		type : "GET",
		url : "getActivePtoListByOrganizationId?orgId=" + organizationId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.statusCode === '0') {
				// remove the previous option from element
				document.getElementById(elementId).options.length = 0;
				// create select option
				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$(("#" + elementId)).append(selectOption);

				if (obj.statusCode === '0') {
					var data = obj.ptoList;

					for (var i = 0; i < data.length; i++) {
						var ptoName = data[i].ptoName;

						var newOption = document.createElement("option");
						newOption.value = data[i].ptoMasterId;
						newOption.innerHTML = ptoName;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}

function doAjaxFetchDeviceMAnufacturer(deviceTypeId) {
	var status = 'Terminated'
	if (deviceTypeId == '') {
		document.getElementById('deviceManufacturerId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#deviceManufacturerCode").append(selectOption);
	} else {
		$
				.ajax({
					type : "GET",
					async : false,
					url : "fetchManufacturerForDeviceType?selectedDeviceTypeId="
							+ deviceTypeId + "&status=" + status,
					success : function(response) {
						var obj = JSON.parse(response);
						if (document.getElementById('deviceManufacturerCode').options == 'undefined') {
							document.getElementById('deviceManufacturerCode').options.length = 0;
						}
						document.getElementById('deviceManufacturerCode').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#deviceManufacturerCode").append(selectOption);

						var data = obj.listDeviceManuFacturer;
						data.sort(function(a, b) {
							return (a.name > b.name) ? 1
									: ((b.name > a.name) ? -1 : 0);
						});

						for (var i = 0; i < data.length; i++) {
							var name = data[i];
							{
								var newOption = document.createElement("option");
								var deviceOnboardName = name.deviceManufacturer;
								newOption.value = name.deviceManufacturerId;
								newOption.innerHTML = deviceOnboardName;
								$("#deviceManufacturerCode").append(newOption);
							}
						}
					},
					error : function(e) {
					}
				});
	}
}

function doAjaxFetchDeviceModel(deviceManufacturerCode) {
	var status = 'Terminated'
	if (deviceManufacturerCode == '') {
		document.getElementById('deviceModelId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#deviceModelId").append(selectOption);
	} else {
		$
				.ajax({
					type : "GET",
					async : false,
					url : "fetchModelForManufacturerName?selectedManufacturerId="
							+ deviceManufacturerCode + "&status=" + status,
					success : function(response) {
						var obj = JSON.parse(response);
						if (document.getElementById('deviceModelId').options == 'undefined') {
							document.getElementById('deviceModelId').options.length = 0;
						}
						document.getElementById('deviceModelId').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#deviceModelId").append(selectOption);
						var data = obj.listDeviceModel;
						data.sort(function(a, b) {
							return (a.name > b.name) ? 1
									: ((b.name > a.name) ? -1 : 0);
						});

						for (var i = 0; i < data.length; i++) {
							var name = data[i];
							{
								var newOption = document.createElement("option");
								var deviceModelname = name.deviceModel;
								newOption.value = name.deviceId;
								newOption.innerHTML = deviceModelname;
								$("#deviceModelId").append(newOption);
							}
						}
					},
					error : function(e) {
					}
				});
	}
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

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function viewDeviceOnboarding(deviceOnboardId) {
	get('deviceOnboardingView').value = deviceOnboardId;
	document.forms["viewDeviceOnboardingAction"].submit();
}

function validatePtoOperationList() {
	var organizationList = getVal('ptoId');
	if (isEmpty(organizationList)) {
		setDiv('ptoOperationMsgDiv', message.REQ_PTO_OPE_ID);
		return false;
	} else {
		setDiv('ptoOperationMsgDiv', '&nbsp');
		return true;
	}
}

function validateOrganizationName() {
	var name = getVal('organizationId');
	if (isEmpty(name)) {
		setDiv("organizationNameMsgDiv", message.REQ_ORG_NAME);
		return false;
	}
	setDiv("organizationNameMsgDiv", "&nbsp;");
	return true;
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
		setDiv("deviceManufacturerMsgDiv", message.REQ_EQP_TYPE);
		return false;
	}
	setDiv("deviceManufacturerMsgDiv", "&nbsp;");
	return true
}

function validateDeviceModel() {
	var name = getVal('deviceModelId');
	if (isEmpty(name)) {
		setDiv("deviceModelMsgDiv", message.REQ_EQP_MODEL_NAME);
		return false;
	}
	setDiv("deviceModelMsgDiv", "&nbsp;");
	return true
}

function validateDeviceOnboardingSubmit() {
	if (!validateOrganizationName() |!validatePtoOperationList() | !validateDeviceTypeName()
			| !validateDeviceManufacturer() | !validateDeviceModel()) {
		return false;
	} else {
		return true;
	}
}

function validateDeviceOnboardEditSubmit() {
	if (!validateOrganizationName() |!validateDeviceTypeName() | !validateDeviceManufacturer() |!validateDeviceModel()
			| !validatePtoOperationList()) {
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

function editDeviceOnboarding(deviceOnboardlId) {
	get('deviceOnboardEdit').value = deviceOnboardlId;
	document.forms["editDeviceOnboardAction"].submit();
}

function changeDeviceOnboardStatus(deviceOnboardId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Device OnBoarding Status to " + status + '?');
	$('#deviceOnboardDiv').popup('show');
	get('deviceOnboardIdStatus').value = deviceOnboardId;
	get('deviceOnboardStatus').value = status;
}
