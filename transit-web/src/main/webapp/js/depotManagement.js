function fetchPtoListByOrganizationId(organizationId, elementId) {
	$.ajax({
		type : "GET",
		url : "getPtoListByOrganizationId?orgId=" + organizationId,
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

function validateEmail() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z]{2,4})+$/;
	var email = getVal('email');
	if (isEmpty(email)) {
		setDiv("depotMailMsgDiv", message.REQ_EMAIL_ID);
		return false;
	} else if (reg.test(email) == false) {
		setDiv("depotMailMsgDiv", message.IN_EMAIL);
		return false;
	}
	setDiv("depotMailMsgDiv", "&nbsp;");
	return true;
}

function validateCity() {
	var city = getVal('city');
	if (isEmpty(city)) {
		setDiv('cityMsgDiv', message.REQ_CITY);
		return false;
	}
	setDiv('cityMsgDiv', "&nbsp");
	return true;
}

function validateDistrict() {
	var district = getVal('district');
	if (isEmpty(district)) {
		setDiv('districtMsgDiv', message.REQ_DISTRICT);
		return false;
	}
	setDiv('districtMsgDiv', "&nbsp");
	return true;
}

function validateState() {
	var state = getVal('state');
	if (isEmpty(state)) {
		setDiv('stateMsgDiv', message.REQ_STATE);
		return false;
	}
	setDiv('stateMsgDiv', "&nbsp");
	return true;
}

function validateCountry() {
	var country = getVal('country');
	if (isEmpty(country)) {
		setDiv('countryMsgDiv', message.REQ_COUNTRY);
		return false;
	}
	setDiv('countryMsgDiv', "&nbsp");
	return true;
}

function validatePinCode() {
	var pincode = getVal('pincode');
	if (isEmpty(pincode)) {
		setDiv('pincodeMsgDiv', message.REQ_PINCODE);
		return false;
	} else if(!isDigit(pincode)) {
		setDiv('pincodeMsgDiv', message.REQ_DIGITS);
		return false;
	} else {
		setDiv('pincodeMsgDiv', "&nbsp");
		return true;
	}
	
}

function validatePhoneNumber() {
	var phoneNum = getVal('phoneNumber');
	if (isEmpty(phoneNum)) {
		setDiv('phonenumberMsgDiv', message.REQ_PHONE_NO);
		return false;
	} else if(!isDigit(phoneNum)) {
		setDiv('phonenumberMsgDiv', message.REQ_DIGITS);
		return false;
	} else if (phoneNum.length < 10 || phoneNum.length > 13) {
		setDiv("phonenumberMsgDiv", message.MIN_PHONE_NO);
		return false;
	} else {
		setDiv('phonenumberMsgDiv', "&nbsp");
		return true;
	}
}

function validateMobileNumber() {
	var reg = /^[0-9+]*$/;
	var mobileNum = getVal('mobile');
	if (isEmpty(mobileNum)) {
		setDiv('mobileMsgDiv', message.REQ_MOBILE_NO);
		return false;
	} else if (!mobileNum.match(reg)) {
		setDiv("mobileMsgDiv", message.REQ_DIGITS);
		return false;
	} else if (mobileNum.length < 10 || mobileNum.length > 13) {
		setDiv("mobileMsgDiv", message.MIN_MOBILE_NO);
		return false;
	} else {
		setDiv('mobileMsgDiv', "&nbsp");
		return true;
	}
}

function validateDepotName() {
	var address = getVal('depotName');
	if (isEmpty(address)) {
		setDiv('depotNameMsgDiv', message.REQ_DEPOT_NAME);
		return false;
	}
	setDiv('depotNameMsgDiv', "&nbsp");
	return true;
}

function validateDepotShortName() {
	var address = getVal('depotShortName');
	if (isEmpty(address)) {
		setDiv('depotShortNameMsgDiv', message.REQ_DEPOT_SHORT_NAME);
		return false;
	}
	setDiv('depotShortNameMsgDiv', "&nbsp");
	return true;
}

function validateDepotIncharge() {
	var address = getVal('depotIncharge');
	if (isEmpty(address)) {
		setDiv('depotInchargeMsgDiv', message.REQ_DEPOT_INCHARGE);
		return false;
	}
	setDiv('depotInchargeMsgDiv', "&nbsp");
	return true;
}

function validatePtoId() {
	var text = getVal("ptoId");
	if (isEmpty(text)) {
		setDiv("ptoNameMsgDiv", message.REQ_PTO_OPERATION_ID);
		return false;
	} else {
		setDiv("ptoNameMsgDiv", "&nbsp;");
		return true;
	}
}

function validateOrganizationId() {
	var name = getVal('organizationId');
	if (isEmpty(name)) {
		setDiv("organizationIdMsgDiv", message.REQ_ORG_ID);
		return false;
	}
	setDiv("organizationIdMsgDiv", "&nbsp;");
	return true;
}

function validateDepotSubmit() {
	if (!validateOrganizationId() | !validatePtoId() | !validateDepotName()
			| !validateDepotShortName() | !validateDepotIncharge() | !validateMobileNumber()) {
		return false;
	} else {
		return true;
	}
}

function viewDepot(depotId) {
	get('depotView').value = depotId;
	document.forms["viewDepotAction"].submit();
}

function changeDepotStatus(depotId,status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Depot Status to " + status + '?');
	$('#depotDiv').popup('show');
	get('depotId').value = depotId;
	get('depotStatus').value = status;
}
function editDepot(depotId) {
	get('depotEdit').value = depotId;
	document.forms["editDepotAction"].submit();
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function fetchPtoByOrganizationIdForPTOSummaryReport(organizationId, elementId) {
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

