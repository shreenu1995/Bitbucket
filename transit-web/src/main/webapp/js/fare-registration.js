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

function validateOrganizationId() {
	var name = getVal('organizationId');
	if (isEmpty(name)) {
		setDiv("organizationListMsgDiv", message.REQ_PTO_ID);
		return false;
	}
	setDiv("organizationListMsgDiv", "&nbsp;");
	return true;
}

function validatePtoOperationName() {
	var name = getVal('ptoId');
	if(isEmpty(name)){
		setDiv('ptoOperationNameMsgDiv',message.REQ_PTO_NAME);
		return false;
	}
	setDiv('ptoOperationNameMsgDiv','&nbsp');
	return true;
}

function validateFareType() {
	var name = getVal('fareType');
	if(isEmpty(name)){
		setDiv('fareTypeMsgDiv',message.REQ_FARE_TYPE);
		return false;
	}
	setDiv('fareTypeMsgDiv','&nbsp');
	return true;
}


function validateFareName() {
	var name = getVal('fareName');
	if(isEmpty(name)){
		setDiv('fareNameMsgDiv',message.REQ_FARE_NAME);
		return false;
	}
	
	setDiv('fareNameMsgDiv','&nbsp');
	return true;
}

function validateDifference() {
	var difference = getVal('difference');
	var regex = /^[0-9+]*$/;
	if(isEmpty(difference)){
		setDiv('differenceMsgDiv',message.REQ_DIFFERENCE);
		return false;
	} else if(!difference.match(regex)) {
		setDiv('differenceMsgDiv',message.REQ_DIGITS);
		return false;
	}
	setDiv('differenceMsgDiv','&nbsp');
	return true;
}

function validateFareAmount() {
	var reg = /^\d+\.\d{0,2}$/;
	var fareAmount = getVal('fareAmount');
	if(isEmpty(fareAmount)){
		setDiv('fareAmountMsgDiv',message.REQ_DECIMAL_DIGITS);
		return false;
	} else if (!fareAmount.match(reg)) {
		setDiv("fareAmountMsgDiv", message.REQ_DECIMAL_DIGITS);
		return false;
	} else if(fareAmount == 0.0) {
		setDiv("fareAmountMsgDiv", "Please enter fare amount");
		return false;
	}
	setDiv('fareAmountMsgDiv','&nbsp');
	return true;
}

function validateFareSubmit() {
	if (!validateOrganizationId() | !validateFareAmount() | !validatePtoOperationName() | !validateFareType()
			| !validateFareName() | !validateDifference() | !validateFareAmount()) {
		return false;
	} else {
		return true;
	}
}

function viewFare(fareId) {
	get('fareView').value = fareId;
	document.forms["viewFareManagementAction"].submit();
}

function validateFareUpdateRequestEditSubmit() {
	if (!validateFareAmount() | !validatePtoOperationName() | !validateFareType()
			| !validateFareName() | !validateDifference() | !validateOrganizationId()) {
		return false;
	} else {
		return true;
	}
}

function editFare(fareId) {
	get('FareEdit').value = fareId;
	document.forms["editFareAction"].submit();
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function changeFareStatus(fareId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Fare Status to " + status + '?');
	$('#fareDiv').popup('show');
	get('FareIdStatus').value = fareId;
	get('FareStatus').value = status;
}

function validateFile() {
	get('browseFile');
	if (document.getElementById('fileData' == null)) {
		setDiv('dataFile_errDiv', "Please Upload the csv file");
	}
	var fileName = $('input[type=file]').val().split('/').pop().split('\\')
			.pop();
	$('#dataFile').val(fileName);
}

function downloadCsvTemplate() {
	document.forms["downloadbulkfaretemplate"].submit();
}

