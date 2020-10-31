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


function viewOpsmanifest(opsManifestId) {
	get('opsmanifestView').value = opsManifestId;
	document.forms["viewOpsManagementAction"].submit();
}

function editOpsManifest(opsManifestId) {
	get('opsManifestEdit').value = opsManifestId;
	document.forms["editOpsManifestAction"].submit();
}

function changeOpsManifestStatus(opsManifestId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Ops Manifest Status to " + status + '?');
	$('#opsManifestDiv').popup('show');
	get('opsManifestId').value = opsManifestId;
	get('opsManifestStatus').value = status;
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

function validatePtoOperationName() {
	var name = getVal('ptoId');
	if (isEmpty(name)) {
		setDiv("ptoNameMsgDiv", message.REQ_PTO_OPERATION_ID);
		return false;
	}
	setDiv("ptoNameMsgDiv", "&nbsp;");
	return true;
}

function validateOperator() {
	var name = getVal('operatorId');
	if (isEmpty(name)) {
		setDiv("operatorMsgDiv", message.REQ_OPERATOR);
		return false;
	}
	setDiv("operatorMsgDiv", "&nbsp;");
	return true;
}

function validateDeviceNumber() {
	var deviceNo = getVal('deviceNo');
	if (isEmpty(deviceNo)) {
		setDiv("deviceNoMsgDiv", message.REQ_DEVICENO);
		return false;
	}
	fetchUniqueDeviceNumber(deviceNo);
	if (isDeviceExist == true) {
		setDiv("deviceNoMsgDiv", message.DEVICE_EXISTS);
		return false;
	} else {
		setDiv("deviceNoMsgDiv", "&nbsp;");
		return true;
	}
}

function validateDevice() {
	var deviceNo = getVal('deviceNo');
	if (isEmpty(deviceNo)) {
		setDiv("deviceNoMsgDiv", message.REQ_DEVICENO);
		return false;
	} else {
		setDiv("deviceNoMsgDiv", "&nbsp;");
		return true;
	}
}

var isDeviceExist;
function fetchUniqueDeviceNumber(deviceNo) {
	$.ajax({
		type : 'GET',
		url : 'deviceNoExistsCheck?deviceNo=' + deviceNo,
		async : false,
		success : function(data) {
			if (data == "Success") {
				setDiv("deviceNoMsgDiv", message.DEVICE_EXISTS);
				isDeviceExist = true;
				return false;
			} else {
				setDiv("deviceNoMsgDiv", "&nbsp;");
				isDeviceExist = false;
				return true;
			}
		},
		error : function() {
			setDiv("deviceNoMsgDiv", "&nbsp;");
		},
	});
}

function validateDepotName() {
	var name = getVal('depotId');
	if (isEmpty(name)) {
		setDiv("depotNameMsgDiv", message.REQ_DEPOTNAME);
		return false;
	}
	setDiv("depotNameMsgDiv", "&nbsp;");
	return true;
}
function validateDate() {
	var name = getVal('date');
	if (isEmpty(name)) {
		setDiv("dateMsgDiv", message.REQ_DATE);
		return false;
	}
	setDiv("dateMsgDiv", "&nbsp;");
	return true;
}


function validateOpsManifestSubmit() {
	if (!validateOrganizationId() | !validatePtoOperationName()
			| !validateOperator() | !validateDeviceNumber()
			| !validateDepotName()) {
		return false;
	} else {
		return true;
	}
}

function validateOpsManifestEditSubmit() {
	if (!validateOrganizationId() | !validatePtoOperationName()
			| !validateOperator() | !validateDeviceNumber()
			| !validateDepotName()) {
		return false;
	} else {
		return true;
	}
}

$(document).ready(function () {
    var date = new Date();
    var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    var start = new Date();
    var end = new Date(new Date().setYear(start.getFullYear() + 1));

    $('#date').datepicker({
        autoclose: true,
        todayHighlight: true,
        startDate: start,
        orientation: "bottom",
        endDate: today,
        format: 'yyyy/mm/dd'
    });
    
});

