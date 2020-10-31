function fetchPtoByOrganizationId(organizationId, elementId) {
	$.ajax({
		type : "GET",
		url : "getPtoByOrganizationId?orgId=" + organizationId,
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
						newOption.value = data[i].ptoName;
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

function fetchcity(stateName, elementId) {
	$.ajax({
		type : "GET",
		url : "getCityByStateId?stateName=" + stateName,
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
					var data = obj.cityList;

					for (var i = 0; i < data.length; i++) {
						var city = data[i].cityName;

						var newOption = document.createElement("option");
						newOption.value = data[i].cityName;
						newOption.innerHTML = city;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}

function fetchstate(countryId, elementId) {
	$.ajax({
		type : "GET",
		url : "getStateByCountryId?countryId=" + countryId,
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
					var data = obj.stateList;

					for (var i = 0; i < data.length; i++) {
						var state = data[i].stateName;

						var newOption = document.createElement("option");
						newOption.value = data[i].stateName;
						newOption.innerHTML = state;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
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

function validateVehicleType() {
	var name = getVal('vehicleType');
	if (isEmpty(name)) {
		setDiv("vehicleTypeMsgDiv", message.REQ_VEHICLE_TYPE);
		return false;
	}
	setDiv("vehicleTypeMsgDiv", "&nbsp;");
	return true;
}
function validateDeviceType() {
	var name = getVal('deviceType');
	if (isEmpty(name)) {
		setDiv("deviceTypeMsgDiv", message.REQ_DEVICE_TYPE);
		return false;
	}
	setDiv("deviceTypeMsgDiv", "&nbsp;");
	return true;
}
function validateFareType() {
	var name = getVal('fareType');
	if (isEmpty(name)) {
		setDiv("fareTypeMsgDiv", message.REQ_FARE_TYPE);
		return false;
	}
	setDiv("fareTypeMsgDiv", "&nbsp;");
	return true;
}

function validatePto() {
	var name = getVal('ptoName');
	if (isEmpty(name)) {
		setDiv("ptoNameMsgDiv", message.REQ_PTO_OPERATION_NAME);
		return false;
	}
	setDiv("ptoNameMsgDiv", "&nbsp;");
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

function validateCity() {
	var name = getVal('city');
	if (isEmpty(name)) {
		setDiv("citydiv", message.REQ_CITY);
		return false;
	}
	setDiv("citydiv", "&nbsp;");
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

function validateMobile() {
	var reg = /^[0-9+]*$/;
	var mobNumber = getVal('ptoMobile');
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
function validateEmail() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z]{2,4})+$/;
	var emailAddress = getVal('ptoEmail');
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

function validatePtoSubmit() {
	if (!validateOrgId() | !validatePto() | !validateState()
			| !validateCity() | !validateContactPerson() | !validateMobile()
			| !validateEmail() | !validateSiteURL()) {

		return false;
	} else {
		return true;
	}
}

function validateEditPtoSubmit() {
	if (!validateOrgId() | !validatePto() | !validateState()
			| !validateCity() | !validateContactPerson() | !validateMobile()
			| !validateEmail() | !validateSiteURL()) {

		return false;
	} else {
		return true;
	}
}

function resetPtoRegistration() {
	setDiv('ptoOperationIdDiv', '&nbsp');
	setDiv('ptoOperationNameDiv', '&nbsp');
	setDiv('citydiv', '&nbsp');
	setDiv('ptoOperationAddressdiv', '&nbsp');
	setDiv('districtdiv', '&nbsp');
	setDiv('statediv', '&nbsp');
	setDiv('countrydiv', '&nbsp');
	setDiv('pinCodediv', '&nbsp');
	setDiv('ptoOperPhoneNumberdiv', '&nbsp');
	setDiv('ptoOperMobilediv', '&nbsp');
	setDiv('ptoOperEmaildiv', '&nbsp');
}

function viewPto(ptoMasterId) {
	get('ptoView').value = ptoMasterId;
	document.forms["viewPtoAction"].submit();
}

function editPto(ptoMasterId) {
	get('ptoEdit').value = ptoMasterId;
	document.forms["editPtoAction"].submit();
}

function changePtoStatus(ptoMasterId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change PTO Status to " + status + '?');
	$('#ptoDiv').popup('show');
	get('ptoMasterId').value = ptoMasterId;
	get('ptoStatus').value = status;
}

function fetchPtoByOrgId(organizationId, elementId) {
	$.ajax({
		type : "GET",
		url : "getPtoByOrganizationId?organizationId=" + organizationId,
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
						newOption.value = data[i].ptoId;
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

function fetchOperatorByPtoName(pto, elementId) {
	var ptoName = pto.options[pto.selectedIndex].text;
	$.ajax({
		type : "GET",
		url : "getOperatorByPtoName?ptoName=" + ptoName,
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
					var data = obj.listOfOperators;

					for (var i = 0; i < data.length; i++) {
						var operatorName = data[i].operatorName;

						var newOption = document.createElement("option");
						newOption.value = data[i].operatorId;
						newOption.innerHTML = operatorName;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}
