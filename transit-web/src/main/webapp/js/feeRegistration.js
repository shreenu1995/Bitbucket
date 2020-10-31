function fetchPtoListByOrganizationId(orgId, elementId) {
	$.ajax({
		type : "GET",
		url : "getPtoListByOrganizationId?orgId=" + orgId,
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
	var ptoname = get('ptoId').value;
	if(isEmpty(ptoname)) {
		setDiv('ptoNameMsgDiv','please enter pto name');
		return false;
	}
	
	setDiv('ptoNameMsgDiv','&nbsp');
	return true;
}

function validateFeeName() {
	var feeName = get('feeName').value;
	if(isEmpty(feeName)) {
		setDiv('feeNameMsgDiv','please enter fee name');
		return false;
	}
	
	setDiv('feeNameMsgDiv','&nbsp');
	return true;
}


function validateOrgName() {
	var orgName = get('organizationId').value;
	if(isEmpty(orgName)) {
		setDiv('orgMsgDiv','please enter organisation name');
		return false;
	}
	
	setDiv('orgMsgDiv','&nbsp');
	return true;
}

function validatePtoshareType() {
	var ptoShareType = get('ptoshare').value;
	if(isEmpty(ptoShareType)) {
		setDiv('ptoshareMsgDiv','please select pto share type');
		return false;
	}
	
	setDiv('ptoshareMsgDiv','&nbsp');
	return true;
}

function validateFeeType() {
	var feeType = get('ptoFeeType').value;
	if(isEmpty(feeType)) {
		setDiv('ptoFeeTypeMsgDiv','please select pto fee type');
		return false;
	}
	
	setDiv('ptoFeeTypeMsgDiv','&nbsp');
	return true;
}

function validateFeeValue() {
	var feeValue = get('ptoFeeValue').value;
	if(isEmpty(feeValue)) {
		setDiv('ptoFeeValueMsgDiv','please enter pto fee value');
		return false;
	}
	
	setDiv('ptoFeeValueMsgDiv','&nbsp');
	return true;
	
}

function validateOrgShareType() {
	var orgShareType = get('orgshare').value;
	if(isEmpty(orgShareType)) {
		setDiv('orgShareTypeMsgDiv','please select organisation share type');
		return false;
	}
	
	setDiv('orgShareTypeMsgDiv','&nbsp');
	return true;
}


function validateOrgFeeType() {
	var orgFeeType = get('orgFeeType').value;
	if(isEmpty(orgFeeType)) {
		setDiv('orgFeeTypeMsgDiv','please select organisation fee type');
		return false;
	}
	
	setDiv('orgFeeTypeMsgDiv','&nbsp');
	return true;
}

function validateOrgFeeValue() {
	var orgFeeVal = get('orgshareFeeVal').value;
	if(isEmpty(orgFeeVal)) {
		setDiv('orgfeevalueMsgDiv','please enter organisation fee value');
		return false;
	}
	
	setDiv('orgfeevalueMsgDiv','&nbsp');
	return true;
}

function validateFeeCreate() {
	if(!validatePto() |!validateFeeName() |!validateOrgName()
			|!validatePtoshareType() |!validateFeeType() 
			|!validateFeeValue() |!validateOrgFeeValue() | !validateOrgFeeType()
		    |!validateOrgShareType()) {
		return false;
	} else {
		return true;
	}
}

function viewFee(feeId) {
	get('feeView').value = feeId;
	document.forms["viewFeeManagementAction"].submit();
}

function editFee(feeId) {
	get('feeEditId').value = feeId;
	document.forms["editFeeAction"].submit();
}

function changeFeeStatus(feeStatusId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Fee Status to " + status + '?');
	$('#feeDiv').popup('show');
	get('feeId').value = feeStatusId;
	get('feeStatus').value = status;
}

function cancelButton() {
	get('cancelTypeId').value = "cancel";
	document.forms["cancelForm"].submit();
}