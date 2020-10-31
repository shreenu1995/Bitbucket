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

function validateProductType() {
	var name = getVal('productType');
	if (isEmpty(name)) {
		setDiv("productTypeMsgDiv", message.REQ_PRODUCT_TYPE);
		return false;
	}
	setDiv("productTypeMsgDiv", "&nbsp;");
	return true;
}

function validateProductName() {
	var name = getVal('productName');
	if (isEmpty(name)) {
		setDiv("productNameMsgDiv", message.REQ_PRODUCT_NAME);
		return false;
	}
	setDiv("productNameMsgDiv", "&nbsp;");
	return true;
}

function validateTicketAndPassType() {
	var name = getVal('ticketAndPassType');
	if (isEmpty(name)) {
		setDiv("ticketAndPassTypeMsgDiv", message.REQ_TICKET_AND_PASSTYPE);
		return false;
	}
	setDiv("ticketAndPassTypeMsgDiv", "&nbsp;");
	return true;
}

function validateAmount() {
	var reg = /^[0-9+]*$/;
	var name = getVal('amount');
	if (isEmpty(name)) {
		setDiv("amountMsgDiv", message.REQ_AMOUNT);
		return false;
	} else if (!name.match(reg)) {
		setDiv("amountMsgDiv", message.REQ_DIGITS);
		return false;
	}
	setDiv("amountMsgDiv", "&nbsp;");
	return true;
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

function validateOrgId() {
	var text = getVal("organizationId");
	if (isEmpty(text)) {
		setDiv("orgNameMsgDiv", message.REQ_ORG_ID);
		return false;
	} else {
		setDiv("orgNameMsgDiv", "&nbsp;");
		return true;
	}
}

function validateProductSubmit() {
	if (!validateOrgId() | !validatePtoId() | !validateProductType() | !validateProductName()
			| !validateTicketAndPassType() | !validateAmount()
			| !validateDiscount()) {
		return false;
	} else {
		return true;
	}
}

function validateProductEditSubmit() {
	if (!validateOrgId() | !validatePtoId() | !validateProductType() | !validateProductName()
			| !validateTicketAndPassType() | !validateAmount()
			| !validateDiscount()) {
		return false;
	} else {
		return true;
	}
}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");
	resetErrorSuccessMessage();
}

function editProduct(id) {
	get('productEdit').value = id;
	document.forms["editProductAction"].submit();
}

function viewProduct(id) {
	get('productView').value = id;
	document.forms["viewProductAction"].submit();
}

function changeProductStatus(id, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Product Status to " + status + '?');
	$('#productDiv').popup('show');
	get('productIdStatus').value = id;
	get('productStatus').value = status;
}
