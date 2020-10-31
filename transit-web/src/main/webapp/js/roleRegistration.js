function validateRoleName() {
	var name = getVal('roleName');
	if (isEmpty(name)) {
		setDiv("roleNameMsgDiv", message.REQ_ROLE_NAME);
		return false;
	}
	setDiv("roleNameMsgDiv", "&nbsp;");
	return true;
}

function validateRoleDescription() {
	var name = getVal('roleDescription');
	if (isEmpty(name)) {
		setDiv("roleDescriptionMsgDiv", message.REQ_DESCRIPTION);
		return false;
	}
	setDiv("roleDescriptionMsgDiv", "&nbsp;");
	return true;
}

function validatePrivilegeList() {
	var privilegeList = getVal('privilegesId');
	if(isEmpty(privilegeList)) {
		setDiv('privilegeListDiv',message.REQ_PRIVILEGE_LIST)
		return false;
	}
	setDiv('privilegeListDiv',"&nbsp;");
	return true;
}

function validateRoleRegistrationSubmit() {
	if (!validateRoleName() | !validateRoleDescription() | !validatePrivilegeList()) {
		return false;
	} else {
		return true;
	}
}

function resetRoleRegistration() {
	setDiv('roleNameMsgDiv','&nbsp');
	setDiv('roleDescriptionMsgDiv','&nbsp');
	setDiv('privilegeListDiv','&nbsp');
}

function setValue(id,newvalue) {
	  var s= document.getElementById(id);
	  s.value = newvalue;
	}  

function validateRoleCategory(roleCategory){
	var roleName = document.getElementById("roleName").value;
	var roleDiscription = document.getElementById("description").value;
	document.getElementById('roleCategory').value = roleCategory;
	setValue("rolesName",roleName);
	setValue("roleDiscription",roleDiscription);
	document.forms["roleCategoryForm"].submit();
	
    
}

function editRole(roleId) {
	get('roleId').value = roleId;
	document.forms["editRoleForm"].submit();
}
function setDiv(divId,message){
var myDiv = document.getElementById(divId);
myDiv.innerHTML = message;
}

function validPermission(lentghData, divId, errorMessage) {
	if (lentghData == 0) {
		setDiv(divId, errorMessage);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;
}

function validRoleName(id, divId, failureMessage1, failureMessage2) {
	var roleName = getVal(id);
	if (isEmpty(roleName)) {
		setDiv(divId, failureMessage1);
		return false;
	} 
	setDiv(divId, "&nbsp;");
	return true;
}

function validRoleDescription(id, divId, failureMessage1, failureMessage2) {
	var description = getVal(id);
	if (isEmpty(description)) {
			setDiv(divId, failureMessage1);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;
}

function submitData() {
	var choices = 0;
	var obj = document.getElementsByName("feature");
	for (var i=0;i<obj.length;i++){
	  if ( obj[i].checked ) {
	    choices= i+1;
	  }
	}
	var objData = document.getElementsByName("feature");
	var roleArray = [];
	for ( var j = 0; j < obj.length; j++) {
		if (objData[j].checked) {
			roleArray.push(objData[j].id);
		}
	}
	setValue("permission",roleArray);
	if (!validPermission(choices, 'errorDataPermission',"Atleast one permission required")
			| !validRoleDescription('description', 'roleDescription',"Description is Required")
            | !validRoleName('roleName', 'roleDiv', "Role name is required")
	     	
	) {
		return false;
	} else {
		setDiv("errorDataPermission", "&nbsp;");
	}
}

function continueEditRole() {
	var flag=true;
	if(!checkValidEditRoles()){
		flag=false;
		return flag;
	} else {
		return flag;
	}
}


function checkValidEditRoles() {
	var choices = 0;
	var objData = document.getElementsByName("feature");
	for (var i=0;i<objData.length;i++){
	  if ( objData[i].checked ) {
	     choices= i+1;
	  }
	}
	var roleArray = [];
	for ( var j = 0; j < objData.length; j++) {
		if ( objData[j].checked ) {
		roleArray.push(objData[j].id);
		}
	}
    setValue("permission",roleArray);
	if (!validRoleName('roleName', 'roleDiv', "Role name is required")
			| !validRoleDescription('description', 'roleDescription', "Description is Required")
			| !validPermission(choices, 'errorDataPermission', "Atleast one permission required")
			

	) {
		return false;
	}	
	setDiv("errorDataPermission", " ");
	return true;

}
function changeRoleStatus(roleId, status) {
	clearPopupDesc();
	setDiv('statusId'," Do You Wish To Change Fee Status to " + status + '?');
	$('#roleDiv').popup('show');
	get('roleI').value = roleId;
	get('roleStatus').value = status;
}

