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
	var name = getVal('routeName');
	if (isEmpty(name)) {
		setDiv("routeNameMsgDiv", message.REQ_ROUTE_NAME);
		return false;
	}
	setDiv("routeNameMsgDiv", "&nbsp;");
	return true;
}

function validateFromRoute() {
	var name = getVal('fromRoute');
	if (isEmpty(name)) {
		setDiv("fromRouteMsgDiv", message.REQ_FROM_ROUTE);
		return false;
	}
	setDiv("fromRouteMsgDiv", "&nbsp;");
	return true;
}

function validateToRoute() {
	var name = getVal('toRoute');
	if (isEmpty(name)) {
		setDiv("toRouteMsgDiv", message.REQ_TO_ROUTE);
		return false;
	}
	setDiv("toRouteMsgDiv", "&nbsp;");
	return true;
}

function validateRouteCode() {
	var num = /^[0-9+]*$/;
	var alpha = /^[A-Z]*$/;
	var regex = /^[A-Z0-9]+$/;
	var name = getVal('routeCode');
	if (isEmpty(name) | !name.match(regex) | name.match(num) | name.match(alpha)) {
		setDiv("routeCodeMsgDiv", message.REQ_ROUTE_CODE);
		return false;
	} 
	setDiv("routeCodeMsgDiv", "&nbsp;");
	return true;
}

function validateStageSequenceNumber() {
	var reg = /^[0-9+]*$/;
	var name = getVal('stageSequenceNumber');
	if (isEmpty(name) | !name.match(reg)) {
		setDiv("stageSequenceNumberMsgDiv", message.REQ_DIGITS);
		return false;
	}
	setDiv("stageSequenceNumberMsgDiv", "&nbsp;");
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

function validateStageSequenceNumber1(id) {
	var reg = /^[0-9+]*$/;
	var name = getVal(id);
	if (isEmpty(name) | !name.match(reg)) {
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

function validateRouteRegistrationSubmit() {
	if (!validateOrganizationId () | !validatePtoId() | !validateRouteName()
			| !validateFromRoute() | !validateToRoute() | !validateRouteCode()
			| !validateStageSequenceNumber() | !validateDistance() 
			| !validateStageSequenceNumber1("stageSequenceNumber") | !validateDistance1("distance")) {
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

function validateRouteEditSubmit() {
	if (!validateOrganizationId () | !validatePtoId() | !validateRouteName()
			| !validateFromRoute() | !validateToRoute() | !validateRouteCode()
			| !validateStageSequenceNumber() | !validateDistance() 
			| !validateStageSequenceNumber1("stageSequenceNumber") | !validateDistance1("distance")) {
		return false;
	} else {
		return true;
	}
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

function editRoute(routeId) {
	get('routeEdit').value = routeId;
	document.forms["editRouteAction"].submit();
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}

function changeRouteStatus(routeId, status) {
	clearPopupDesc();
	setDiv('statusId', " Do You Wish To Change Route Status to " + status + '?');
	$('#routeDiv').popup('show');
	get('routeId').value = routeId;
	get('status').value = status;
}

function viewRoute(routeId) {
	get('routeView').value = routeId;
	document.forms["routeStageAction"].submit();
}

function deleteStage(stageId, routeId) {
	get('stageDelete').value = stageId;
	get('routeId').value = routeId;
	document.forms["stageAction"].submit();
}

function fetchPto(organizationName, elementId) {
	$.ajax({
		type : "GET",
		url : "getPtoByOrganizationName?organizationName=" + organizationName,
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

