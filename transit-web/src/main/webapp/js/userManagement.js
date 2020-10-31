function validateFirstName() {
	var name = getVal('firstName');
	if (isEmpty(name)) {
		setDiv("firstNameMsgDiv", message.REQ_FIRST_NAME);
		return false;
	}
	setDiv("firstNameMsgDiv", "&nbsp;");
	return true
}

function validateLastName() {
	var name = getVal('lastName');
	if (isEmpty(name)) {
		setDiv("lastNameMsgDiv", message.REQ_LAST_NAME);
		return false;
	}
	setDiv("lastNameMsgDiv", "&nbsp;");
	return true
}

function validateUserName() {
	var name = getVal('userName');
	if (isEmpty(name)) {
		setDiv("userNameMsgDiv", message.REQ_USER_NAME);
		return false;
	}
	setDiv("userNameMsgDiv", "&nbsp;");
	return true
}

function validateUserType() {
	var name = getVal('userType');
	if (isEmpty(name)) {
		setDiv("userTypeMsgDiv", message.REQ_USER_TYPE);
		return false;
	}
	setDiv("userTypeMsgDiv", "&nbsp;");
	return true
}

function validateEmail() {
	var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z]{2,4})+$/;
	var emailAddress = getVal('email');
	if (isEmpty(emailAddress)) {
		setDiv("emailMsgDiv", message.REQ_EMAIL_ID);
		return false;
	} else if (!emailAddress.match(emailReg) || emailAddress.length > 50) {
		setDiv("emailMsgDiv", message.IN_EMAIL);
		return false;
	}
	setDiv("emailMsgDiv", "&nbsp;");
	return true
}

function validatePhoneNo() {
	var val = getVal('phoneNo');
	if (isEmpty(val)) {
		setDiv("phoneNoMsgDiv", message.REQ_MOBILE_NO);
		return false;
	} else if (!isDigit(val) || val.length < 8 || val.length > 15 || val == 0) {
		setDiv("phoneNoMsgDiv", message.INV_MOBILE);
		return false;
	} else {
		setDiv("phoneNoMsgDiv", "&nbsp;");
		return true
	}
}

function validateRoleName() {
	var roleName = getVal('userRole');
	if (isEmpty(roleName)) {
		setDiv('roleNameDiv', message.REQ_ROLE);
		return false;
	}
	setDiv('roleNameDiv', '&nbsp');
	return true
}
function validateRole() {
	var roleName = getVal('roleName');
	if (isEmpty(roleName)) {
		setDiv('roleNameDiv', message.REQ_ROLE);
		return false;
	}
	setDiv('roleNameDiv', '&nbsp');
	return true
}
function validateAddress() {
	var name = getVal('address');
	if (isEmpty(name)) {
		setDiv("addressMsgDiv", message.REQ_ADDRESS);
		return false;
	}
	setDiv("addressMsgDiv", "&nbsp;");
	return true
}
function validateOrganization() {
	var name = getVal('orgId');
	if (isEmpty(name)) {
		setDiv("organizationIdMsgDiv", message.REQ_ORG_ID);
		return false;
	}
	setDiv("organizationIdMsgDiv", "&nbsp;");
	return true;
}
function validatePto() {
	var name = getVal('ptoMasterId');
	if (isEmpty(name)) {
		setDiv("ptoIdMsgDiv", message.REQ_PTO_OPERATION_ID);
		return false;
	}
	setDiv("ptoIdMsgDiv", "&nbsp;");
	return true;
}
function validateUserRegistrationSubmit() {
	var userType = document.getElementById("userType").value;
	if (userType == 'SUPER_ADMIN') {
		if (!validateRoleName()
				| !validateFirstName()
				| !validateLastName()
				| !validateUserName()
				| !validateEmailExistsCheck('email')
				| !validatePhoneNo('phoneNo', message.REQ_PHONE_NO,
						message.INV_PHONE) | !validateAddress()) {
			return false;
		} else {
			return true;
		}
	}
	if (userType == 'ORG_ADMIN') {
		if (!validateRoleName()
				| !validateOrganization()
				| !validateFirstName()
				| !validateLastName()
				| !validateUserName()
				| !validateEmailExistsCheck('email')
				| !validatePhoneNo('phoneNo', message.REQ_PHONE_NO,
						message.INV_PHONE) | !validateAddress()) {
			return false;
		} else {
			return true;
		}
	}
	if (userType == 'PTO_ADMIN') {
		if (!validateUserName()
				| !validateRoleName()
				| !validatePto()
				| !validateUserType()
				| !validateFirstName()
				| !validateLastName()
				| !validateEmailExistsCheck('email')
				| !validatePhoneNo('phoneNo', message.REQ_PHONE_NO,
						message.INV_PHONE) | !validateAddress()) {
			return false;
		} else {
			return true;
		}
	}

}

function validateUserEditSubmit() {
	if (!validateFirstName() | !validateLastName() | !validateUserName()
			| !validateUserType() | !validateEmail() | !validatePhoneNo()
			| !validateRole() | !validateAddress()) {
		return false;
	} else {
		return true;
	}
}

function validateEmailExistsCheck(id) {
	var email = getVal(id);
	var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z]{2,4})+$/;
	if (isEmpty(email)) {
		setDiv("emailMsgDiv", message.REQ_EMAIL_ID);
		return false;
	} else if (!email.match(emailReg) || email.length > 50) {
		setDiv("emailMsgDiv", message.IN_EMAIL);
		return false;
	}
	fetchUniqueUserId(email);
	if (unique == true) {
		setDiv("emailMsgDiv", "&nbsp;");
		return true;
	} else {
		setDiv("emailMsgDiv", message.EMAIL_ID_EXISTS);
		return false;
	}
}

function fetchUniqueUserId(email) {
	$.ajax({
		type : 'GET',
		url : 'userExistsCheck?email=' + email,
		async : false,
		success : function(data) {
			if (data == "Success") {
				unique = false;
			} else {
				unique = true;
			}
		},
		error : function() {
			setDiv("emailMsgDiv", "&nbsp;");
		},
	});

}

function editUser(userId) {
	get('userEdit').value = userId;
	document.forms["editUserAction"].submit();
}

function changeUserStatus(userId, status) {
	clearPopupDesc();
	setDiv('statusId', " Do You Wish To Change User Status to " + status + '?')
	$('#userDiv').popup('show');
	get('userIdStatus').value = userId;
	get('userStatus').value = status;
}

function viewUser(userId) {
	get('userView').value = userId;
	document.forms["viewUserManagementAction"].submit();
}

function showSpecific() {
	var text = getVal("userType");
	if (text == 'SUPER_ADMIN') {
		$("#orgId").attr("disabled", "disabled");
		$('#ptoMasterId').attr("disabled", "disabled");
	} else if (text == 'ORG_ADMIN') {
		$('#ptoMasterId').attr("disabled", "disabled");
		$("#orgId").removeAttr("disabled", "disabled");

	} else {
		$("#orgId").attr("disabled", "disabled");
		$('#ptoMasterId').removeAttr("disabled", "disabled");
	}
}

function fetchRoleNameByUserType(userType, elementId) {
	$.ajax({
		type : "GET",
		url : "getRoleNameByUserType?userType=" + userType,
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
					var data = obj.roleList;

					for (var i = 0; i < data.length; i++) {
						data[i].roleId;

						var newOption = document.createElement("option");
						newOption.value = data[i].roleId;
						newOption.innerHTML = data[i].roleName;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}
