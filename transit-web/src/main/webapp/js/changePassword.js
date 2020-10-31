function validateCurrentPassword() {
	var currentPswd = getVal('currentPassword');
	if (isEmpty(currentPswd)) {
		setDiv("currentPasswordMsgDiv", message.REQ_CURRENT_PSWD);
		return false;
	}
	setDiv("currentPasswordMsgDiv", "&nbsp;");
	return true
}

function validateNewPassword() {
	var newPswd = getVal('newPassword');
	if (isEmpty(newPswd)) {
		setDiv("newPasswordMsgDiv", message.REQ_NEW_PSWD);
		return false;
	}
	setDiv("newPasswordMsgDiv", "&nbsp;");
	return true
}

function validateConfirmNewPassword() {
	var confirmPswd = getVal('confirmNewPassword');
	if (isEmpty(confirmPswd)) {
		setDiv("confirmPasswordMsgDiv", message.REQ_CONFIRM_PSWD);
		return false;
	}
	setDiv("confirmPasswordMsgDiv", "&nbsp;");
	return true
}

function validateChangePasswordSubmit() {
	if (!validateCurrentPassword() | !validateNewPassword()
			| !validateConfirmNewPassword()) {
		return false;
	}
	var confirmPswd = getVal('confirmNewPassword');
	var newPswd = getVal('newPassword');
	if (!(newPswd == confirmPswd)) {
		setDiv("confirmPasswordMsgDiv", message.CONFIRM_PSWD_NEW_PSWD_SAME);
		return false;
	}
	setDiv("confirmPasswordMsgDiv", "&nbsp;");
	return true
}

function validateResetPasswordSubmit() {
	if (!validateNewPassword()
			| !validateConfirmNewPassword()) {
		return false;
	}
	var confirmPswd = getVal('confirmNewPassword');
	var newPswd = getVal('newPassword');
	if (!(newPswd == confirmPswd)) {
		setDiv("confirmPasswordMsgDiv", message.CONFIRM_PSWD_NEW_PSWD_SAME);
		return false;
	}
	setDiv("confirmPasswordMsgDiv", "&nbsp;");
	return true
}

function resetChangePassword() {
	setDiv("confirmPasswordMsgDiv", "&nbsp;");
	setDiv("newPasswordMsgDiv", "&nbsp;");
	setDiv("currentPasswordMsgDiv", "&nbsp;");
}
