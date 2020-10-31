function validateLoginUserName() {
	var userName = getVal('loginUser');
	if (isEmpty(userName)) {
		setDiv('error-response', 'Please enter email');
		return false;
	}

	setDiv('error-response', '&nbsp');
	return true;
}

function validateLoginPassword() {
	var password = getVal('loginPass');
	if (isEmpty(password)) {
		setDiv('error-response', 'Please enter password');
		return false;
	}

	setDiv('error-response', '&nbsp');
	return true;
}

function validateLogin() {
	if (!validateLoginUserName() && !validateLoginPassword()) {
		setDiv('error-response', 'Please enter your email/Password');
		return false;
	} else if (!validateLoginUserName()) {
		setDiv('error-response', 'Please enter email');
		return false;
	} else if (!validateLoginPassword()) {
		setDiv('error-response', 'Please enter password');
		return false;
	} else {
		return true;
	}
}
