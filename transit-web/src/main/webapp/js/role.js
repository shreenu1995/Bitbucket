/**
 * Method for role operation
 * 
 * @param id
 * @param status
 * @param statusText
 */
function changeStatus(id, status, statusText,roleType) {
	clearPopupDesc();
	$('#rolePopupDiv').popup('show');
	setDiv("sts", "Do you wish to change Role status to" + statusText + '?');
	get('roleActivateId').value = id;
	get('roleStatus').value = status;
}
/**
 * Method for hide the popup
 * 
 */
function hideRolePopUp() {
	$('#rolePopupDiv').popup('hide');
}

/**
 * Method for Edit the role submit
 * 
 * @param roleId
 */
function editRole(roleId) {
	get('roleId').value = roleId;
	document.forms["editRoleForm"].submit();
}
/**
 * Method for view role
 * 
 * @param roleId
 */
function viewRole(roleId) {
	get('viewRoleId').value = roleId;
	document.forms["viewRoleForm"].submit();
}
/**
 * Method for submit Role data
 * 
 * @returns {Boolean}
 */

function buttonDisabled() {
	
	 document.getElementById('buttonCreate').value='Processing...';
	 
	 document.getElementById('buttonCreate').disabled=true;
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
	if (!validPermission(choices, 'errorDataPermission',message.Atleast_one_permission)
	     	| !checkValidRoles()
			| !validRoleDescription('description', 'roleDescription',webMessages.REQ_DESC,webMessages.Enter_valid_Desc)
            | !validRoleName('roleName', 'roleDiv', webMessages.Enter_role_name,webMessages.Enter_valid_role_name)
            | !searchValidateRoleNamess()
	) {
		return false;
	} else {
		setDiv("errorDataPermission", "&nbsp;");
	}
}
/**
 * Method for Create Role next Page navigation
 * 
 * @returns {Boolean}
 */

function checkValidRoles() {
	var objData = document.getElementsByName("featureTO");
	var roleArray = [];
	for ( var i = 0; i < objData.length; i++) {
		roleArray.push(objData[i].id);
	}

	return true;

}

/**
 * Method for Continue Edit Role
 * 
 */
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
	var objData = document.getElementsByName("featureTO");
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
    setValue("permissions",roleArray);
	if (!validRoleName('roleName', 'roleDiv', webMessages.Enter_role_name, webMessages.Enter_valid_role_name)
			| !validRoleDescription('description', 'roleDescription', webMessages.REQ_DESC, webMessages.Enter_valid_Desc)
			| !validPermission(choices, 'errorDiv', webMessages.Atleast_one_permission)
			| !checkValidRoles()

	) {
		return false;
	}	
	setDiv("errorDataPermission", " ");
	return true;

}

function submitNext() {
	var obj = document.getElementsByClassName("checkboxCustom checkedCustom");
	if (!validRoleDescription('description', 'roleDescription', webMessages.REQ_DESC, webMessages.Enter_valid_Desc)
			| !validPermission(obj.length, 'errorData', webMessages.Atleast_one_permission)
			| !checkValidEditRoles()) {
		return false;
	} else {
		return true;
	}
}

/**
 * Method for Create Role submit
 * 
 */
function continueRole() {
	var roleLevel = getVal("roleLevel");
	if (roleLevel == 'SP') {
		if (checkSp()) {
			createRole('SP');
		}
	} else {
		if (checkSubServiceProvider() && checkSSp()) {
			createRole('SSP');
		}
	}
}


/**
 * Method for Submit ServiceProvider data
 * 
 * @returns {Boolean}
 */
/*function submitDataSp() {
	var obj = document.getElementsByClassName("checkboxCustom checkedCustom");
	if (!validRoleName('roleName', 'roleDiv', webMessages.Enter_role_name,
			webMessages.Enter_valid_role_name)
			| !validRoleDescription('description', 'roleDescription',
					webMessages.REQ_DESC,
					webMessages.Enter_valid_Desc)
			|

			!checkMakerChecker()
			| !validPermission(obj.length, 'errorData',
					webMessages.Atleast_one_permission)
			| !checkRoleLevel() | !checkValidRoles()

	) {
		return false;
	} else {
		return true;
	}
}*/
/**
 * Method for Check the Permission length
 * 
 * @returns {Boolean}
 */

/**
 * Method for check the Maker checker
 * 
 * @returns {Boolean}
 */
function checkMakerChecker() {
	var obj = document.getElementsByClassName("radioboxCustom radioedCustom");
	if (validPermission(obj.length, 'makerCheckerErrorMessage', webMessages.Choose_Maker_Checker)) {
		return true;
	} else {
		return false;
	}

}
/**
 * Method for submit Edit Data
 * 
 * @returns {Boolean}
 */
function submitEditData() {
	var obj = document.getElementsByClassName("checkboxCustom checkedCustom");
	if (!validPermission(obj.length, 'errorData', webMessages.Atleast_one_permission)
			| !validRoleDescription('description', 'roleDescription', webMessages.REQ_DESC, webMessages.Enter_valid_Desc)
			| !validRoleName('roleName', 'roleDiv', webMessages.Enter_role_name, webMessages.Enter_valid_role_name)) {
		return false;
	} else {
		get('roleNameData').value = get('roleName').value;
		get('descriptionData').value = get('description').value;
		get('roleIdData').value = get('roleId').value;
		get('featureData').value = roleArray;
		document.forms['editRoleForm'].submit();
	}
}
/**
 * Method for check the valid Permission
 * 
 * @param lentghData
 * @param divId
 * @param errorMessage
 * @returns {Boolean}
 */
function validPermission(lentghData, divId, errorMessage) {
	if (lentghData == 0) {
		setDiv(divId, errorMessage);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;
}
/**
 * Method for validation Role activtion
 * 
 * @param value
 * @returns {Boolean}
 */
function validRoleDescriptionData(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 .,!?@$%&_-'";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}
/**
 * Method for Valid Role Name
 * 
 * @param id
 * @param divId
 * @param failureMessage1
 * @param failureMessage2
 * @returns {Boolean}
 */
function validRoleName(id, divId, failureMessage1, failureMessage2) {
	var roleName = getVal(id);
	if (isEmpty(roleName)) {
		setDiv(divId, failureMessage1);
		return false;
	} 
	setDiv(divId, "&nbsp;");
	return true;
}

/**
 * Validating search role name
 * 
 * @param id
 * @param divId
 * @param failureMessage2
 * @returns {Boolean}
 */
function validSearchRoleName(id, divId, failureMessage2) {
	var roleName = getVal(id);
	if (isEmpty(roleName)) {
		errorFieldFocus(id);
		setDiv(divId, "&nbsp;");
		return true;
	} else if (!validRoleNameData(roleName)) {
		errorFieldFocus(id);
		setDiv(divId, failureMessage2);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;
}
/**
 * Validating Role name
 * 
 * @param value
 * @returns {Boolean}
 */
function validRoleNameData(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 _-'";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}

function hasDoubleSpace(val) {
	return (val.indexOf('  ') != -1);
}
/**
 * Validating Role Data
 * 
 * @returns {Boolean}
 */
function validRoleData() {
	if (!validRoleName('roleName', 'roleDiv', webMessages.Enter_role_name,	webMessages.Enter_valid_role_name)	
			| !validRoleDescription('description', 'roleDescription',webMessages.REQ_DESC,webMessages.Enter_valid_Desc))
		return false;
	else
		return true;
}
/**
 * Validation Role Description
 * 
 * @param id
 * @param divId
 * @param failureMessage1
 * @param failureMessage2
 * @returns {Boolean}
 */
function validRoleDescription(id, divId, failureMessage1, failureMessage2) {
	var description = getVal(id);
	if (isEmpty(description)) {
			setDiv(divId, failureMessage1);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;
}
/**
 * Validation load feature
 * 
 * @param loadData
 */
function loadFeatre(loadData) {
	if (!isEmpty(loadData)) {
		var loadArry = loadData.split(",");
		for ( var i = 0; i < loadArry.length; i++) {
			if (document.getElementById(loadArry[i]) != null)
				document.getElementById(loadArry[i]).className = "checkboxCustom checkedCustom";
		}
	}
}

/**
 * Check the Role Level
 * 
 * @returns {Boolean}
 */
function checkRoleLevel() {

	var roleLevel = getVal("roleLevel");
	if (isEmpty(roleLevel)) {

		setDiv('roleLevelErrorMessage', webMessages.Role_level);
		return false;
	} else {
		setDiv('roleLevelErrorMessage', '');
		return true;
	}
}
/**
 * Method for get the featureLevel
 * 
 * @returns {Boolean}
 */
function getFeatureOnRole() {
	invokeREST(HTTP_METHOD.POST, APP_SERVICE_BASE_URL
			+ "/admin/getFeatureOnRole", 'roleLevel=CPA',
			getFeatureOnRoleSuccess, getFeatureOnRoleFailure);
	setDiv("errorDataPermission", "");
}

function getFeatureOnRoleSuccess(data) {

	if (data.status) {
		setDiv("roleData", data.roleData);

		$(".checkboxCustom")
				.click(
						function() {
							// alert("erffror");
							$(this).toggleClass('checkedCustom');
							var obj = document
									.getElementsByClassName("checkboxCustom checkedCustom");
							var len = obj.length;
							if (len < 1) {
								setDiv('errorData',
										webMessages.Atleast_one_permission);
								return false;
							}
							setDiv('errorData', "&nbsp;");
							if (!checkValidRoles()) {
								return false;
							}
							return true;
						});

	} else {
		setDiv("roleData", "No Permission Found");
	}
}

function getFeatureOnRoleFailure(data) {
	setDiv('errorData', webMessages.Atleast_one_permission);
}

/**
 * Method for check Service Provider
 * 
 * @returns {Boolean}
 */
function checkSp() {
	var val = document.getElementById('secondSelectms2side__dx').options;
	if (val.length == 0) {
		setDiv("spError", webMessages.Service_provider);
		return false;
	}
	setDiv("spError", "");
	return true;
}

function checkSSp() {
	var val = document.getElementById('secondSelectms2side__dx').options;
	if (val.length == 0) {
		setDiv("spError", webMessages.Sub_service_provider);
		return false;
	}
	setDiv("spError", "");
	return true;
}

/**
 * Method for redirect the search role
 * 
 * @param roleName
 */

function redirectInActivePage() {
	document.forms['inActiveRoleForm'].submit();
}

/**
 * Method for change the button id
 * 
 * @param roleLevel
 */
function changeButton(roleLevel) {
	if (roleLevel == 'MW') {
		show('buttonCreate');
		hide('buttonNext');
		$("#roleStep2heading").hide();

	} else {
		hide('buttonCreate');
		showInline('buttonNext');
		$("#roleStep2heading").show();
	}
}

/**
 * Method for edit Role Level
 * 
 */
function changeEditRoleLevel() {
	setDiv("spError", "");
	var roleLevel = getVal("roleLevelEdit");
	setDiv("errorDiv", "");
	setDiv("roleData", "");
	setDiv("SpForEdit", "");
	setDiv("errorDataPermission", "");
	setDiv("spDataNavigation", "");
	invokeREST(HTTP_METHOD.POST, APP_SERVICE_BASE_URL
			+ "/admin/prepaidAdminEditRole", ('roleLevel=' + roleLevel),
			prepaidAdminEditRoleSuccess, prepaidAdminEditRoleFailure);
}

function prepaidAdminEditRoleSuccess(data) {
	if (data.status) {
		setDiv("roleData", data.roleData);
		show('buttonCreate');
		hide('buttonNext');
	} else {
		setDiv("errorDiv", data.message);
	}

	$(".checkboxCustom")
			.click(
					function() {

						$(this).toggleClass('checkedCustom');
						var obj = document
								.getElementsByClassName("checkboxCustom checkedCustom");
						var len = obj.length;
						if (len < 1) {
							setDiv('errorData',
									webMessages.Atleast_one_permission);
							return false;
						}
						setDiv('errorData', "&nbsp;");
						if (!checkValidEditRoles()) {
							return false;
						}
						return true;
					});

}

function prepaidAdminEditRoleFailure(data) {
	setDiv("errorDiv", webMessages.Unable_process);
}

/**
 * Method for display the search role overlay
 * 
 * @param spsspnames
 */
function openOverlay(spsspnames) {

	$('#my_popup').popup('show');
	// show("my_popup");
	var spLayout = "<table class='table table-striped table-bordered table-responsive table-condensed' style='margin-top:20px;'>";
	spLayout += "<tr><td colspan='7' class='searchRsltTblHeading'></td></tr>";
	spLayout += "<tr><th style='width:250px; background-color: gray'>Role Availability </th></tr>";
	var sp = spsspnames.split(",");

	for ( var i = 0; i < sp.length; i++) {
		spLayout += "<tr><td>" + sp[i] + "</td></tr>";
	}

	spLayout += "</table>";

	setDiv("spLayout", spLayout);

}

function showRoleDetail(roleId) {
	show("roleDetailDiv");
	setDiv("roleDetail", "");
	fetchSubServiceProviderDetails(roleId);
}

function hideRoleDetailsPopUp() {
	hide("roleDetailDiv");
}

/**
 * Method to check the role data
 * 
 * @param ele
 */
function checkRoleData(ele) {
	var permissionDiv = get('dynamicFeatureId' + ele.id);
	var permission = permissionDiv.innerHTML;
	if (!isEmpty(permission)) {
		try {
			var permissionArray = permission.split("|");
			if (permissionArray.length > 1) {
				for ( var i = 0; i < permissionArray.length; i++) {
					if ($("#" + ele.id).hasClass('checkedCustom')) {
						$("#" + permissionArray[i])
								.removeClass('checkedCustom');
					} else {
						$("#" + permissionArray[i]).addClass('checkedCustom');
					}
				}
			}
		} catch (e) {
			// alert(e);
		}
	}
}

/**
 * Method to check the permission
 * 
 * @param ele
 */
function checkPermission(ele) {

	var subPermissionDiv = get('dynamicSubFeatue' + ele.id);
	var subPermission = subPermissionDiv.innerHTML;
	var permissionDiv = get('dynamicFeatureId' + subPermission);
	var permission = permissionDiv.innerHTML;
	var permissionArray = permission.split("|");
	var incrCount = 0;
	for ( var i = 0; i < permissionArray.length; i++) {
		if ($("#" + permissionArray[i]).hasClass('checkedCustom')) {
			incrCount++;
		}
	}

	if ($("#" + ele.id).hasClass('checkedCustom')) {
		incrCount--;
	} else {
		incrCount++;
	}
	if (incrCount == permissionArray.length) {
		$("#" + subPermission).addClass('checkedCustom');
	} else {
		$("#" + subPermission).removeClass('checkedCustom');
	}

}

function viewUpdateRole(roleId, status) {
	// alert(roleId);
	get('viewRolId').value = roleId;
	get('viewRoleStatus').value = status;
	document.forms["viewUpdateRoleForm"].submit();

}

function editrole(userRoleId) {
	get('roleId').value = roleId;
	document.forms["editRoleForm"].submit();
}

function validateRoleCategory(roleCategory){
	var roleName = document.getElementById("roleName").value;
	var roleDiscription = document.getElementById("description").value;
	document.getElementById('roleCategory').value = roleCategory;
	setValue("rolesName",roleName);
	setValue("roleDiscription",roleDiscription);
	document.forms["roleCategoryForm"].submit();
	
    
}

function searchValidateRoleName(){
	assigValidationType('SUBMIT');
	if(!clientValidation('roleName','middle_name','rolenameerror')){
		clearValidationType();
		return false;
	}
	return true;
}

function searchValidateRoleNamess(){	
	assigValidationType('SUBMIT');	
	if(!clientValidation('roleName','last_name','rolenameerror')			
		|!clientValidation('description','last_name','roleDescription')){	
		clearValidationType();	
		return false;
		}
	return true;
	}
