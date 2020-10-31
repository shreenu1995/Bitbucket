

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

function doAjaxFetchStageByRoute(routeId) {
	var status = 'Terminated'
	if (routeId == '') {
		document.getElementById('stageId').innerHTML = "";
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#routeId").append(selectOption);
	} else {
		$.ajax({
					type : "GET",
					async : false,
					url : "fetchStageByRoute?routeId=" + routeId + "&status=" + status,
					success : function(response) {
						// we have the response
						var obj = JSON.parse(response);
						// Remove previous options from the dropdown
						if (document.getElementById('stageId').options == 'undefined') {
							document.getElementById('stageId').options.length = 0;
						}
						document.getElementById('stageId').innerHTML = "";
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#stageId").append(selectOption);

						var data = obj.stopSearchList;
							data.sort(function(a,b) {return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);} ); 

							for (var i = 0; i < data.length; i++) {
								var name = data[i];
								{
									var newOption = document.createElement("option");
									var stageName = name.stageName;
									newOption.value = name.stageId;
									newOption.innerHTML = stageName;
									$("#stageId").append(newOption);
								}
							}
					},
					error : function(e) {
					}
				});
	}
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

function validatePtoId() {
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
	return true
}

function validateStageName() {
	var name = getVal('stageId');
	if (isEmpty(name)) {
		setDiv("stageNameMsgDiv", message.REQ_STAGE_NAME);
		return false;
	}
	setDiv("stageNameMsgDiv", "&nbsp;");
	return true;
}

function validateStopName() {
	var name = getVal('stopName');
	if (isEmpty(name)) {
		setDiv("stopNameMsgDiv", message.REQ_STOP_NAME);
		return false;
	}
	setDiv("stopNameMsgDiv", "&nbsp;");
	return true;
}

function validateStopSubmit() {
	if (!validateOrganizationId() | !validatePtoId() | !validateStopName()
			| !validateRouteName() | !validateStageName()) {
		return false;
	} else {
		return true;
	}
}

function validateStopEditSubmit() {
	if (!validateOrganizationId() | !validatePtoId() | !validateStopName()
			| !validateRouteName() | !validateStageName()) {
		return false;
	} else {
		return true;
	}
}

function resetErrorSuccessMessage() {
	document.getElementById("sucessDiv").innerHTML = "";
	document.getElementById("errorDiv").innerHTML = "";
}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");
	resetErrorSuccessMessage();
}

function viewStop(stopId) {
	get('stopView').value = stopId;
	document.forms["viewStopAction"].submit();
}

function editStop(stopId) {
	get('stopEdit').value = stopId;
	document.forms["editStopAction"].submit();
}
function changeStopStatus(stopId, status, ptoOperationId) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Stop Status to " + status + '?');
	$('#stopDiv').popup('show');
	get('stopId').value = stopId;
	get('status').value = status;
	get('ptoOperationId').value = ptoOperationId;
}

function cancelButton() {
	get("cancelTypeId").value = "cancel";
	document.forms["cancelForm"].submit();
}