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

function fetchPto(organizationName, elementId) {
	$.ajax({
		type : "GET",
		url : "getPtoListByOrganizationName?organizationName=" + organizationName,
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
function viewStage(ptoId) {
	get('stageView').value = ptoId;
	document.forms["viewStageAction"].submit();
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
		setDiv("organizationIdMsgDiv", message.REQ_ORG_ID);
		return false;
	}
	setDiv("organizationIdMsgDiv", "&nbsp;");
	return true;
}

function validateRouteName() {
	var name = getVal('routeId');
	if (isEmpty(name)) {
		setDiv("routeNameMsgDiv", message.REQ_ROUTE_NAME);
		return false;
	}
	setDiv("routeNameMsgDiv", "&nbsp;");
	return true;
}

function validateStageName() {
	var name = getVal('stageName');
	if (isEmpty(name)) {
		setDiv("stageNameMsgDiv", message.REQ_STAGE_NAME);
		return false;
	}
	setDiv("stageNameMsgDiv", "&nbsp;");
	return true;
}

function validateStopSequenceNumber() {
	var reg = /^[0-9+]*$/;
	var name = getVal('stopSequenceNumber');
	if (isEmpty(name) | !name.match(reg)) {
		setDiv("stopSequenceNumberMsgDiv", message.REQ_DIGITS);
		return false;
	} 
	setDiv("stopSequenceNumberMsgDiv", "&nbsp;");
	return true;
}

function validateDistance() {
	var reg = /^\d+\.\d{0,2}$/;
	var name = getVal('distance');
	if (isEmpty(name) | !name.match(reg)) {
		setDiv("distanceMsgDiv", message.REQ_DECIMAL_DIGITS);
		return false;
	} 
	setDiv("distanceMsgDiv", "&nbsp;");
	return true;
}

function validateStopSequenceNumber1(id) {
	var reg = /^[0-9+]*$/;
	var name = getVal(id);
	if (isEmpty(name)) {
		setDiv("MsgDiv" + id, message.REQ_STOP_SEQUENCE_NUMBER);
		return false;
	} else if (!name.match(reg)) {
		setDiv("MsgDiv" + id, message.REQ_DIGITS);
		return false;
	}
	setDiv("MsgDiv" + id, "&nbsp;");
	return true;
}

function validateDistance1(id) {
	var reg = /^\d+\.\d{0,2}$/;
	var name = getVal(id);
	if (isEmpty(name) | !name.match(reg)) {
		setDiv("MsgDiv" + id, message.REQ_DECIMAL_DIGITS);
		return false;
	} 
	setDiv("MsgDiv" + id, "&nbsp;");
	return true;
}

function validateStageSubmit() {
	if (!validateOrganizationId() | !validatePto() | !validateRouteName() 
		| !validateStageName() | !validateStopSequenceNumber() 
		| !validateDistance() | !validateStopSequenceNumber1("stopSequenceNumber")
		| !validateDistance1("distance")) {
		return false;
	} else {
		return true;
	}
}

function validateStageEditSubmit() {
	if (!validateOrganizationId() | !validatePto() 
		| !validateRouteName() | !validateStageName() 
		) {
		return false;
	} else {
		return true;
	}
}

function editStage(stageId) {
	get('stageEdit').value = stageId;
	document.forms["editStageAction"].submit();
}

function cancelButton() {
	get("cancelTypeId").value = "cancel";
	document.forms["cancelForm"].submit();
}

function changeStageStatus(stageId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Stage Status to " + status + '?');
	$('#stageDiv').popup('show');
	get('stageId').value = stageId;
	get('stageStatus').value = status;
}

function deleteStop(stopId, stageId) {
	get('stopDelete').value = stopId;
	get('stageId').value = stageId;
	document.forms["stopAction"].submit();
}


function doAjaxFetchRouteByPto(ptoId) {
	var status = 'Terminated'
	if (ptoId == '') {
		document.getElementById('routeId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#routeId").append(selectOption);
	} else {
		$.ajax({
					type : "GET",
					async : false,
					url : "fetchRouteByOrgAndPto?ptoId=" + ptoId + "&status=" + status,
					success : function(response) {
						// we have the response
						var obj = JSON.parse(response);
						// Remove previous options from the dropdown
						if (document.getElementById('routeId').options == 'undefined') {
							document.getElementById('routeId').options.length = 0;
						}
						document.getElementById('routeId').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#routeId").append(selectOption);

						var data = obj.listORoutes;
							data.sort(function(a,b) {return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);} ); 

							for (var i = 0; i < data.length; i++) {
								var name = data[i];
								{
									var newOption = document.createElement("option");
									var routeName = name.routeName;
									newOption.value = name.routeId;
									newOption.innerHTML = routeName;
									$("#routeId").append(newOption);
								}
							}
					},
					error : function(e) {
					}
				});
	}
}


