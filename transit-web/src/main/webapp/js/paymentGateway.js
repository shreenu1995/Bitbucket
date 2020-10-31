function ValidatePg() {
	if (!validatePgName() | !validatePto() | !validateServiceUrl() | !validateCurrency() | !validateMID()) {
		return false;
	} else {
		return true;
	}
}

function validateMID() {
	var name = getVal('mid');
	if (isEmpty(name)) {
		setDiv("midMsgDiv", message.REQ_MID);
		return false;
	}
	setDiv("midMsgDiv", "&nbsp;");
	return true;
}

function validateCurrency() {
	var name = getVal('currency');
	if (isEmpty(name)) {
		setDiv("currencyMsgDiv", message.REQ_CURRENCY);
		return false;
	}
	setDiv("currencyMsgDiv", "&nbsp;");
	return true;
}

function validatePgName() {
	var name = getVal('pgName');
	if (isEmpty(name)) {
		setDiv("pgNameMsgDiv", message.REQ_PG_NAME);
		return false;
	}
	setDiv("pgNameMsgDiv", "&nbsp;");
	return true;
}

function validatePto() {
	var name = getVal('ptoMasterId');
	if (isEmpty(name)) {
		setDiv("ptoMasterIdMsgDiv", message.REQ_PTO_NAME);
		return false;
	}
	setDiv("ptoMasterIdMsgDiv", "&nbsp;");
	return true;
}

function validateServiceUrl() {
	var name = getVal('serviceUrl');
	if (isEmpty(name)) {
		setDiv("serviceUrlMsgDiv", message.REQ_SERVICE_URL_NAME);
		return false;
	}
	setDiv("serviceUrlMsgDiv", "&nbsp;");
	return true;
}

function changePgStatus(pgId, status) {
	clearPopupDesc();
	setDiv('statusId', " Do You Wish To Change Fee Status to " + status + '?');
	$('#pgDiv').popup('show');
	get('pgId').value = pgId;
}

function fetchPM(currency, elementId) {
	$.ajax({
		type : "GET",
		url : "getPmBycurrency?currency=" + currency,
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
					var data = obj.programManager;

					for (var i = 0; i < data.length; i++) {
						var pgName = data[i].programManagerName;

						var newOption = document.createElement("option");
						newOption.value = data[i].programManagerName;
						newOption.innerHTML = pgName;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}