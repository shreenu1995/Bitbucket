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

function validateDiscountType() {
	var name = getVal('discountType');
	if (isEmpty(name)) {
		setDiv("discountTypeMsgDiv", message.REQ_DISCOUNT_TYPE);
		return false;
	}
	setDiv("discountTypeMsgDiv", "&nbsp;");
	return true
}

function validateDiscountName() {
	var name = getVal('discountName');
	if (isEmpty(name)) {
		setDiv("discountNameMsgDiv", message.REQ_DISCOUNT_NAME);
		return false;
	}
	setDiv("discountNameMsgDiv", "&nbsp;");
	return true
}

function validateRouteStageStationDifference() {
	var reg = /^[0-9+]*$/;
	var name = getVal('routeStageStationDifference');
	if (isEmpty(name)) {
		setDiv("routeStageStationDifferenceMsgDiv", message.ROUTE_STAGE_STATION_DIFFERENCE);
		return false;
	} else if (!name.match(reg)) {
		setDiv("routeStageStationDifferenceMsgDiv", message.REQ_DIGITS);
		return false;
	}
	setDiv("routeStageStationDifferenceMsgDiv", "&nbsp;");
	return true
}

function validateDiscount() {
	var reg = /^[0-9+]*$/;
	var name = getVal('discount');
	if (isEmpty(name)) {
		setDiv("discountMsgDiv", message.REQ_DISCOUNT);
		return false;
	} else if (!name.match(reg)) {
		setDiv("discountMsgDiv", message.REQ_DIGITS);
		return false;
	}
	setDiv("discountMsgDiv", "&nbsp;");
	return true;
}

function validateDiscountSubmit() {
	if (!validateOrganizationId() | !validatePto() | !validateDiscountType() | !validateDiscountName()
			| !validateRouteStageStationDifference() | !validateDiscount()) {
		return false;
	} else {
		return true;
	}
}

function validateDiscountEditSubmit(){
	if (!validateOrganizationId() | !validatePto() | !validateDiscountType() | !validateDiscountName()
			| !validateRouteStageStationDifference() | !validateDiscount()) {
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

function editDiscount(discountId) {
	get('discountEdit').value = discountId;
	document.forms["editDiscountAction"].submit();
}

function viewDiscount(discountId) {
	get('discountView').value = discountId;
	document.forms["viewDiscountAction"].submit();
}

function changeDiscountStatus(discountId, status) {
	setDiv('statusId'," Do You Wish To Change Discount Status to " + status + '?');
	$('#discountDiv').popup('show');
	get('discountIdStatus').value = discountId;
	get('discountStatus').value = status;
}

