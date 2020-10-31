function validateIssuerName() {
	var name = getVal('issuerName');
	if (isEmpty(name)) {
		setDiv("issuerNameMsgDiv", message.REQ_ISSUER);
		return false;
	}
	setDiv("issuerNameMsgDiv", "&nbsp;");
	return true;
}

function validateBlacklistedAccountSubmit() {
	if (!validateIssuerName()) {
		return false;
	}
	return true;
}

function downloadBlacklistedAccountCsvTemplate() {
	document.forms["downloadBlacklistedAccountTemplate"].submit();
}

function validateFile() {
	get('browseFile');
	if (document.getElementById('fileData' == null)) {
		setDiv('dataFile_errDiv', "Please Upload the csv file");
	}
	var fileName = $('input[type=file]').val().split('/').pop().split('\\')
			.pop();
	$('#browse').val(fileName);
}
