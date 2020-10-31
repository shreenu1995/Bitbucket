function isEmpty(id) {
	if (!id || id == "" || id.length == 0)
		return true;
	else
		return false;
}

function isValidAlpha(val) {
	var regex = /^[a-zA-Z]*$/;
	var name = getVal(val);
	return regex.test(name);
}

function getVal(id) {
	return get(id).value;
}

function get(id) {
	return document.getElementById(id);
}

function setDiv(id, val) {
	if (get(id)) {
		get(id).innerHTML = val;
	}
}

function isDigit(val) {
	var regex = /^\d+$/;
	return regex.test(val);
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

function validateReason() {
	var reason = get('reason').value;
	if(isEmpty(reason)) {
		setDiv('reasonMsgDiv','please enter reason');
		return false;
	}
	
	setDiv('reasonMsgDiv','&nbsp');
	return true;
}

function clearPopupDesc() {
	get("reason").value = "";
	setDiv("reasonMsgDiv", "");
}

function downloadSubMerchantReport(curPageNumber, type) {
get('downloadPageNumberId').value = curPageNumber;
get('downloadTypeId').value = type;
document.forms["downloadReport"].submit();
}

function downloadReport(curPageNumber, type, totalRecords) {
	get('downloadPageNumberId').value = curPageNumber;
	get('downloadTypeId').value = type;
	get('totalRecords').value = totalRecords;
	if($('#totalRecordsDownload').prop('checked')== true){
		//setValue('downloadAllRecords', true );
		get('downloadAllRecords').value = true;
		if (type == 'PDF') {
			get('totalRecords').value = downloadLimit;
			document.getElementById("sucessDiv").innerHTML = webMessages.maximumownloadLimit;
		}
	} else {
		//setValue('downloadAllRecords', false );
		get('downloadAllRecords').value = false;
	}
	document.forms["downloadReport"].submit();
}
