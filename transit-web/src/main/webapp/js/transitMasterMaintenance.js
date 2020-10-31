
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

$(document).ready(function () {
    var date = new Date();
    var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    var start = new Date();

    $('#generationDateStart').datepicker({
        autoclose: true,
        todayHighlight: true,
        orientation: "bottom", 
        endDate: today,
        format: 'yyyy/mm/dd'
    }).datepicker('update', new Date()).on('changeDate', function () {
        $('#generationDateEnd').datepicker('setStartDate', new Date($(this).val()));
    });

    $('#generationDateEnd').datepicker({
        autoclose: true,
        todayHighlight: true,
        startDate: start,
        orientation: "bottom",
        endDate: today,
        format: 'yyyy/mm/dd'
    }).datepicker('update', new Date()).on('changeDate', function () {
        $('#generationDateStart').datepicker('setEndDate', new Date($(this).val()));
    });

    $("#generationDateStartDate span.input-group-addon").click(function () {
        $("#generationDateStart").datepicker('show');
    });

    $("#generationDateEndDate span.input-group-addon").click(function () {
        $('#generationDateEnd').datepicker('show');
    });

});

function setupTransitMasterMaintenanceView(transitMasterId) {
	get('setupTransitMasterMaintenanceView').value = transitMasterId;
	document.forms["viewTransitMasterMaintenanceAction"].submit();
}

function setupTransitMasterMaintenanceEdit(transitMasterId) {
	get('setupTransitMaster').value = transitMasterId;
	document.forms["editSetupTransitMasterAction"].submit();
}

function changeTransitMasterMaintenanceStatus(id,status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Transit Maintenance Status to " + status + '?');
	$('#transitMasterDiv').popup('show');
	get('transitMasterId').value = id;
	get('transitMasterStatus').value = status;
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
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
		setDiv("inheritMsgDiv", "Please Fill Inherit Field");
		return false;
	}
	setDiv("inheritMsgDiv", "&nbsp;");
	return true
}
function validatePto() {
	var name = getVal('ptoId');
	if (isEmpty(name)) {
		setDiv("ptoMsgDiv", "Please Select PTO Name");
		return false;
	}
	setDiv("ptoMsgDiv", "&nbsp;");
	return true
}
function validateDescription() {
	var name = getVal('Description');
	if (isEmpty(name)) {
		setDiv("descriptionMsgDiv", "Please Enter Status");
		return false;
	}
	setDiv("descriptionMsgDiv", "&nbsp;");
	return true
}

function validatePtolist(){
	var ptoList = getVal('ptoName');
	if(isEmpty(ptoList)) {
		setDiv("ptoNameMsgDiv", "Please Select Pto");
		return false;
	}
	
	setDiv("ptoNameMsgDiv", "&nbsp;");
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

function validateOrgList() {
	var name = getVal('organizationId');
	if (isEmpty(name)) {
		setDiv("orgListMsgDiv", "Please Select Organization");
		return false;
	}
	setDiv("orgListMsgDiv", "&nbsp;");
	return true
}

function validatePtoList() {
	var ptoList = getVal('pto');
	if(isEmpty(ptoList)) {
		setDiv("ptoMsgDiv", "Please Select Pto");
		return false;
	}
	
	setDiv("ptoMsgDiv", "&nbsp;");
	return true
}

function validateTransitMasterSubmit() {
	if (!validateVersionNumber()| !validateInherit()| !validatePto()
			| !validateDescription()| !validateFullVersion() | !validateOrgList()) {
		return false;
	} else {
		return true;
	}
}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");
	resetErrorSuccessMessage();
}

function validateTransitMasterEditSubmit(){
	if(!validateOrgList() | !validateVersionNumber()| !validateInherit()
			| !validateDescription()| !validateFullVersion() 
			| !validatePto()){
		return false;
	}
	else {
		return true;
	}
}