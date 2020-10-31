
function validateMobileNumber() {
	var reg = /^[0-9+]*$/;
	var mobileNum = getVal('operatorContactNumber');
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

function validateOrganizationName() {
	var name = getVal('organizationId');
	if (isEmpty(name)) {
		setDiv("organizationIdMsgDiv", message.REQ_ORG_ID);
		return false;
	}
	setDiv("organizationIdMsgDiv", "&nbsp;");
	return true;
}

function validateOperatorName() {
	var name = getVal('operatorName');
	if (isEmpty(name)) {
		setDiv('operatorNameMsgDiv', message.REQ_OPERATOR_NAME);
		return false;
	}
	setDiv('operatorNameMsgDiv', "&nbsp");
	return true;
}

function validateOperatorUserId() {
	var operatorUserId = getVal('operatorUserId');
	if(isEmpty(operatorUserId)) {
		setDiv('operatorIdMsgDiv', "Please Enter Operator User Id");
		return false;
	}
	setDiv('operatorIdMsgDiv', "&nbsp");
	return true;
}

function validateOperatorPassword() {
	var operatorPassword = getVal('operatorPassword');
	if(isEmpty(operatorPassword)) {
		setDiv('operatorPasswordMsgDiv', "Please Enter Operator Password");
		return false;
	}
	setDiv('operatorPasswordMsgDiv', "&nbsp");
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

function validateOperatorSubmit() {
	if (!validateOrganizationName() | !validatePtoId() | !validateOperatorName()
			 | !validateMobileNumber() | !validateOperatorUserId() 
			 | !validateOperatorPassword()) {
		return false;
	} else {
		return true;
	}
}

function validateOperatorEditSubmit() {
	if (!validateOrganizationName() | !validatePtoId() | !validateOperatorName()
			 | !validateMobileNumber() | !validateOperatorUserId()) {
		return false;
	} else {
		return true;
	}
}

function changeOperatorStatus(operatorId,status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Operator Status to " + status + '?');
	$('#operatorDiv').popup('show');
	get('operatorId').value = operatorId;
	get('operatorStatus').value = status;
}
function editOperator(operatorId) {
	get('operatorEdit').value = operatorId;
	document.forms["editOperatorAction"].submit();
}

function viewOperator(operatorId) {
	get('operatorView').value = operatorId;
	document.forms["viewOperatorAction"].submit();
}
function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

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
