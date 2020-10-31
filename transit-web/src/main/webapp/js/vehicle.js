function doAjaxFetchVehicleManufacturer(vehicleTypeId) {
	var status = "Terminated"
	if (vehicleTypeId == '') {
		document.getElelmentByID('vehicleManufacturerId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#vehicleManufacturerId").append(selectOption);
	} else {
		$
				.ajax({
					type : "GET",
					async : false,
					url : "fetchVehicleManufacturerForVehicleType?selectedVehicleTypeId="
							+ vehicleTypeId + "&status=" + status,
					success : function(response) {
						var obj = JSON.parse(response);
						if (document.getElementById('vehicleManufacturerId').options == 'undefined') {
							document.getElementById('vehicleManufacturerId').options.length = 0;
						}
						document.getElementById('vehicleManufacturerId').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#vehicleManufacturerId").append(selectOption);

						var data = obj.listofVehicleManufacturer;
						data.sort(function(a, b) {
							return (a.name > b.name) ? 1
									: ((b.name > a.name) ? -1 : 0);
						});

						for (var i = 0; i < data.length; i++) {
							var name = data[i];
							{
								var newOption = document
										.createElement("option");
								var vehicleManufacturerName = name.vehicleManufacturerName;
								newOption.value = name.vehicleManufacturerId;
								newOption.innerHTML = vehicleManufacturerName;
								$("#vehicleManufacturerId").append(newOption);
							}
						}
					},
					error : function(e) {
					}
				});
	}
}

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

function doAjaxFetchVehicleModel(vehicleManufacturerId) {
	var status = 'Terminated'
	if (vehicleManufacturerId == '') {
		document.getElementById('vehicleModelId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#vehicleModelId").append(selectOption);
	} else {
		$
				.ajax({
					type : "GET",
					async : false,
					url : "fetchVehicleModelForManufacturerName?selectedManufacturerId="
							+ vehicleManufacturerId + "&status=" + status,
					success : function(response) {
						var obj = JSON.parse(response);
						if (document.getElementById('vehicleModelId').options == 'undefined') {
							document.getElementById('vehicleModelId').options.length = 0;
						}
						document.getElementById('vehicleModelId').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#vehicleModelId").append(selectOption);
						var data = obj.listofModel;
						data.sort(function(a, b) {
							return (a.name > b.name) ? 1
									: ((b.name > a.name) ? -1 : 0);
						});

						for (var i = 0; i < data.length; i++) {
							var name = data[i];
							{
								var newOption = document
										.createElement("option");
								newOption.innerHTML = name;
								var vehicleModelName = data[i].vehicleModelName;
								newOption.value = data[i].vehicleModelId;
								newOption.innerHTML = vehicleModelName;
								$("#vehicleModelId").append(newOption);
							}
						}
					},
					error : function(e) {
					}
				});
	}
}

function validateVehicleTypeName() {
	var name = getVal('vehicleTypeId');
	if (isEmpty(name)) {
		setDiv("vehicleTypeMsgDiv", "Vehicle type name is required");
		return false;
	}
	setDiv("vehicleTypeMsgDiv", "&nbsp;");
	return true
}

function validatVehicleTypeName() {
	var name = getVal('vehicleTypeName');
	if (isEmpty(name)) {
		setDiv("vehicleTypeMsgDiv", "Vehicle type name is required");
		return false;
	}
	setDiv("vehicleTypeMsgDiv", "&nbsp;");
	return true
}


function validateVehicleManufacturerName() {
	var name = getVal('vehicleManufacturerId');
	if (isEmpty(name)) {
		setDiv("vehicleManufacturerNameMsgDiv", "Manufacturer name is required");
		return false;
	}
	setDiv("vehicleManufacturerNameMsgDiv", "&nbsp;");
	return true
}

function validateVehicleManufactureName() {
	var name = getVal('vehicleManufacturerName');
	if (isEmpty(name)) {
		setDiv("vehicleManufacturerNameMsgDiv", "Manufacturer name is required");
		return false;
	}
	setDiv("vehicleManufacturerNameMsgDiv", "&nbsp;");
	return true
}
function validateVehicleModelName() {
	var name = getVal('vehicleModelId');
	if (isEmpty(name)) {
		setDiv("vehicleModelnameMsgDiv", "Model name is required");
		return false;
	}
	setDiv("vehicleModelnameMsgDiv", "&nbsp;");
	return true
}

function validateVehicleModeName() {
	var name = getVal('vehicleModelName');
	if (isEmpty(name)) {
		setDiv("vehicleModelnameMsgDiv", "Model name is required");
		return false;
	}
	setDiv("vehicleModelnameMsgDiv", "&nbsp;");
	return true
}

function validateVehicleRegistrationNumber() {
	var name = getVal('vehicleRegistrationNumber');
	if (isEmpty(name)) {
		setDiv("vehicleRegistrationNumberMsgDiv",
				"Registration Number is required");
		return false;
	}
	setDiv("vehicleRegistrationNumberMsgDiv", "&nbsp;");
	return true
}

function validateVehicleEngineNumber() {
	var name = getVal('vehicleEngineNumber');
	if (isEmpty(name)) {
		setDiv("vehicleEngineNumberMsgDiv", "Engine Number is required");
		return false;
	}
	
	fetchUniqueVehicleEngineNumber(name);
	if (unique == true) {
		setDiv("vehicleEngineNumberMsgDiv", "&nbsp;");
		return true;
	} else {
		setDiv("vehicleEngineNumberMsgDiv", "Engine Number is already Exists");
		return false;
	}
	
}

function fetchUniqueVehicleEngineNumber(name) {
	$.ajax({
		type : 'GET',
		url : 'engineNumberExistsCheck?name=' + name,
		async : false,
		success : function(data) {
			if (data == "Success") {
				unique = false;
			} else {
				unique = true;
			}
		},
		error : function() {
			setDiv("vehicleEngineNumberMsgDiv", "&nbsp;");
		},
	});

}

function validateVehicleChassisNumber() {
	var name = getVal('vehicleChassisNumber');
	if (isEmpty(name)) {
		setDiv("vehicleChassisNumberMsgDiv", "Chassis Number is required");
		return false;
	}
	
	fetchUniqueVehicleChassisNumber(name);
	if (unique == true) {
		setDiv("vehicleChassisNumberMsgDiv", "&nbsp;");
		return true;
	} else {
		setDiv("vehicleChassisNumberMsgDiv", "Chassis Number is already Exists");
		return false;
	}
}

function fetchUniqueVehicleChassisNumber(name) {
	$.ajax({
		type : 'GET',
		url : 'chassisNumberExistsCheck?name=' + name,
		async : false,
		success : function(data) {
			if (data == "Success") {
				unique = false;
			} else {
				unique = true;
			}
		},
		error : function() {
			setDiv("vehicleChassisNumberMsgDiv", "&nbsp;");
		},
	});

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

function validateOrganizationName() {
	var name = getVal('organizationId');
	if (isEmpty(name)) {
		setDiv("organizationNameMsgDiv", message.REQ_ORG_NAME);
		return false;
	}
	setDiv("organizationNameMsgDiv", "&nbsp;");
	return true;
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

function validateOrgId() {
	var name = getVal('orgId');
	if (isEmpty(name)) {
		setDiv("orgIdMsgDiv", message.REQ_ORG_ID);
		return false;
	}
	setDiv("orgIdMsgDiv", "&nbsp;");
	return true;
}

function validateVehicleTypeSubmit() {
	if (!validateVehicleTypeName() | !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleTypeEditSubmit() {
	if (!validatVehicleTypeName() | !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleManufacturerSubmit() {
	if (!validateVehicleTypeName() | !validateVehicleManufacturerName()
			| !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleManufacturerEditSubmit() {
	if (!validateVehicleTypeName() | !validateVehicleManufactureName()
			| !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleModelSubmit() {
	if (!validateVehicleTypeName() | !validateVehicleManufacturerName()
			| !validateVehicleModeName()
			| !validateVehicleRegistrationNumber()
			| !validateVehicleEngineNumber() | !validateVehicleChassisNumber()
			| !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleModelEditSubmit() {
	if (!validateVehicleTypeName() | !validateVehicleManufacturerName()
			| !validateVehicleModeName()
			| !validateVehicleRegistrationNumber()
			| !validateDiscription()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleOnboardSubmit() {
	if (!validateVehicleTypeName() | !validateVehicleManufacturerName()
			| !validateVehicleModelName() | !validateOrgId()| !validatePtoOperationList()) {
		return false;
	} else {
		return true;
	}
}

function validateVehicleOnboardEditSubmit() {
	if (!validateVehicleTypeName() | !validateVehicleManufacturerName()
			| !validateVehicleModelName() | !validateOrganizationName()| !validatePtoOperationList()) {
		return false;
	} else {
		return true;
	}
}

function viewProduct(productId) {
	get('productView').value = productId;
	document.forms["viewProductAction"].submit();
}

function resetVehicleTypeRegister() {
	setDiv("vehicleNameMsgDiv", "&nbsp;");
	setDiv("descriptionMsgDiv", "&nbsp;");
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function resetErrorSuccessMessage() {
	document.getElementById("vehicleTypeMsgDiv").innerHTML = "";
	document.getElementById("vehicleManufacturerNameMsgDiv").innerHTML = "";
	document.getElementById("vehicleModelnameMsgDiv").innerHTML = "";
	document.getElementById("vehicleRegistrationNumberMsgDiv").innerHTML = "";
	document.getElementById("vehicleEngineNumberMsgDiv").innerHTML = "";
	document.getElementById("vehicleChassisNumberMsgDiv").innerHTML = "";
	document.getElementById("descriptionMsgDiv").innerHTML = "";
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

function viewVehicleType(vehicleTypeId) {
	get('vehicleTypeView').value = vehicleTypeId;
	document.forms["viewVehicleTypeAction"].submit();
}

function editVehicleType(vehicleTypeId) {
	get('vehicleTypeEdit').value = vehicleTypeId;
	document.forms["editVehicleTypeAction"].submit();
}

function changeVehicleTypeStatus(vehicleTypeId, status) {
	get('vehicleTypeId').value = vehicleTypeId;
	get('vehicleTypeStatus').value = status;
	document.forms["vehicleTypeStatus"].submit();
}

function viewVehicleManuf(vehicleManufacturerId) {
	get('vehicleManufView').value = vehicleManufacturerId;
	document.forms["viewVehicleManufAction"].submit();
}

function editVehicleManufacturer(vehicleManufId) {
	get('vehicleManufEdit').value = vehicleManufId;
	document.forms["editVehicleManufAction"].submit();
}

function changeVehicleManufacturerStatus(vehicleManufId, status) {
	get('vehicleManufId').value = vehicleManufId;
	get('vehicleManufStatus').value = status;
	document.forms["vehicleManufacturerStatus"].submit();
}

function viewVehicleModel(vehicleModelId) {
	get('vehicleModelView').value = vehicleModelId;
	document.forms["viewVehicleModelAction"].submit();
}

function editVehicleModel(vehicleModelId) {
	get('vehicleModelEdit').value = vehicleModelId;
	document.forms["editVehicleModelAction"].submit();
}

function changeVehicleModelStatus(vehicleModelId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Vehicle Model Status to " + status + '?');
	$('#vehicleModelDiv').popup('show');
	get('vehicleModelId').value = vehicleModelId;
	get('vehicleModelStatus').value = status;
}

function viewVehicleOnboard(vehicleOnboardingId) {
	get('vehicleOnboardView').value = vehicleOnboardingId;
	document.forms["viewVehicleOnboardAction"].submit();
}

function editVehicleOnboarding(vehicleOnboardingId) {
	get('vehicleOnboardEdit').value = vehicleOnboardingId;
	document.forms["editVehicleOnboardAction"].submit();
}
function changeVehicleOnboardingStatus(vehicleOnboardingId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Vehicle OnBoarding Status to " + status + '?');
	$('#vehicleOnboardDiv').popup('show');
	get('vehicleOnboardingId').value = vehicleOnboardingId;
	get('vehicleOnboardStatus').value = status;
}