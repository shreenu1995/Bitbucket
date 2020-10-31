function validatePrivilegeName() {
	var name = getVal('privilegeName');
	if (isEmpty(name)) {
		setDiv("privilegeNameMsgdiv", message.REQ_PRIVILEGE_NAME);
		return false;
	}
	setDiv("privilegeNameMsgdiv", "&nbsp;");
	return true
}

function validateDiscription() {
	var name = getVal('description');
	if (isEmpty(name)) {
		setDiv("descriptionMsgDiv", message.REQ_DESCRIPTION);
		return false;
	}
	setDiv("descriptionMsgDiv", "&nbsp;");
	return true
}

function validatePtoOperationList() {
	var ptoList = getVal('ptoOperationList');
	if(isEmpty(ptoList)) {
		setDiv('ptoOperationTypeDiv',message.REQ_PTO_OPE_ID);
		return false;
	}
	
	setDiv('ptoOperationTypeDiv','&nbsp');
	return true;
}

function validatePrivilegeRegistrationSubmit() {
	if (!validatePrivilegeName() | !validateDiscription()
			| !validatePtoOperationList()) {
		return false;
	} else {
		return true;
	}
}

function resetPrivilegeRegistration() {
	setDiv('ptoOperationTypeDiv','&nbsp');
	setDiv('privilegeNameMsgdiv','&nbsp');
	setDiv('descriptionMsgDiv','&nbsp');
}