
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


function fetchVersionNumber(ptoName, elementId) {
	$.ajax({
		type : "GET",
		url : "getVersionNumber?ptoName=" + ptoName,
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
					var data = obj.listOfInheritAndPto;

					for (var i = 0; i < data.length; i++) {
						var versionNumber = data[i].versionNumber;

						var newOption = document.createElement("option");
						newOption.value = data[i].versionNumber;
						newOption.innerHTML = versionNumber;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}
function setupSoftwareView(softwareId) {
	get('setupSoftwareView').value = softwareId;
	document.forms["viewSoftwareAction"].submit();
}

function setupSoftwareEdit(softwareId) {
	get('setupSoftwareEdit').value = softwareId;
	document.forms["editSetupSoftwareAction"].submit();
}

function changeSetUpSoftwareStatus(softwareId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Setup Software Status to " + status + '?');
	$('#setupSoftDiv').popup('show');
	get('softwareIdStatus').value = softwareId;
	get('softwareStatus').value = status;
}

function validateVersionNumber() {
	var reg = /^[0-9+]*$/;
	var name = getVal('versionNumber');
	if (isEmpty(name) | !name.match(reg)) {
		setDiv("versionNumberMsgDiv", message.REQ_DIGITS);
		return false;
	}
	setDiv("versionNumberMsgDiv", "&nbsp;");
	return true
}

function validateInherit() {
	var name = getVal('inherit');
	if (isEmpty(name)) {
		setDiv("inheritMsgDiv", "Please Select Inherit Field");
		return false;
	}
	setDiv("inheritMsgDiv", "&nbsp;");
	return true
}

function validatePto() {
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
		setDiv("organizationIdMsgDiv", message.REQ_PTO_ID);
		return false;
	}
	setDiv("organizationIdMsgDiv", "&nbsp;");
	return true;
}


function validateStatus() {
	var name = getVal('status');
	if (isEmpty(name)) {
		setDiv("statusMsgDiv", "Please Enter Status");
		return false;
	}
	setDiv("statusMsgDiv", "&nbsp;");
	return true
}

function validateDescription() {
	var name = getVal('description');
	if (isEmpty(name)) {
		setDiv("descriptionMsgDiv", "Please Enter Description");
		return false;
	}
	setDiv("descriptionMsgDiv", "&nbsp;");
	return true
}

function validateFullVersion() {
	var name = getVal('fullVersion');
	if (isEmpty(name)) {
		setDiv("fullVersionMsgDiv", "Please Select Full Version");
		return false;
	}
	setDiv("fullVersionMsgDiv", "&nbsp;");
	return true
}

function validateSetupSoftwareSubmit() {
	if (!validateOrganizationId() |!validateVersionNumber() |!validateInherit()
			| !validatePto() | !validateDescription() | !validateFullVersion()) {
		return false;
	} else {
		return true;
	}
}

function validateSetupSoftwareEditSubmit() {
	if (!validateOrganizationId() |!validateVersionNumber() |!validateInherit()
			| !validatePto() | !validateDescription() | !validateFullVersion()) {
		return false;
	} else {
		return true;
	}
}

function validateFullVersionCheck() {
	var radios = document.getElementsByName("command");
	var formValid = false;

	var i = 0;
	while (!formValid && i < radios.length) {
		if (radios[i].checked)
			formValid = true;
		i++;
	}

	if (!formValid)
		setDiv("fullVersionDiv", "Please select full version");
	return formValid;
}

function resetErrorSuccessMessage() {
	document.getElementById("success").innerHTML = "";
	document.getElementById("error").innerHTML = "";
}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}