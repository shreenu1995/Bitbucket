<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="../images/favicon.png">
<title>User Registration</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<link href="../css/font-new-awesome.css" rel="stylesheet">
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/userManagement.js"></script>
</head>

<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
		<article>
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Setup</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">User</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Create</span>
			</div>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<!-- Tab Buttons Start -->
			<div class="tab-header-container  marginL40">
				<a href="user-search">Search</a>
			</div>
			<div class="tab-header-container active-background">
				<a href="user-registration">Create</a>
			</div>

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="userRegister"
							action="user-registration" method="post">
							<div class="col-sm-12 ">
								<!-- Overview section start -->
								<div class="row">
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">User Type</span></legend>
											<form:select id="userType" class="form-control"
												name="userType" path="userType"
												onchange="showSpecific();fetchRoleNameByUserType(this.value, 'userRole');"
												onblur="validateUserType();">
												<c:forEach items="${roleLevelList}" var="roleLevelList">
													<form:option value="${roleLevelList.name()}">
														<spring:message text="${roleLevelList.value}"
															code="${roleLevelList.value}" />
													</form:option>
												</c:forEach>
											</form:select>
											<div class="userTypeMsgDiv">
												<span id="userTypeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span>Role Name</legend>
											<form:select id="userRole" name="userRole" path="userRole"
												onblur="validateRoleName();" class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${roleList}" var="roleList">
													<form:option value="${roleList.roleId}">${roleList.roleName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="roleNameDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span>
												Organization Name</legend>
											<form:select id="orgId" name="orgId"
												path="orgId" onblur="validateOrganization();"
												Class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${organizationList}"
													var="organizationList">
													<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span>PTO Name</legend>
											<form:select id="ptoMasterId" name="ptoMasterId" path="ptoMasterId"
												onblur="validatePto();" class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoList}" var="ptoList">
													<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">First Name</span></legend>
											<form:input id="firstName" name="firstName" path="firstName"
												onblur="validateFirstName();" class="form-control"
												type="text" value="" maxlength="50"></form:input>
											<div class="firstNameMsgDiv">
												<span id="firstNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">Last Name</span></legend>
											<form:input id="lastName" name="lastName" path="lastName"
												onblur="validateLastName();" class="form-control"
												type="text" value="" maxlength="50"></form:input>
											<div class="lastNameMsgDiv">
												<span id="lastNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 clear form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">User Name</span></legend>
											<form:input id="userName" name="userName" path="userName"
												onblur="this.value=this.value.trim();return validateUserName();" class="form-control"
												type="text" value="" maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="userNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">email ID</span></legend>
											<form:input id="email" name="email" path="email"
												class="form-control" type="text"
												onblur="this.value=this.value.trim();return validateEmailExistsCheck('email');" value=""
												maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="emailMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">Phone No</span></legend>
											<form:input id="phoneNo" name="phoneNo" path="phoneNumber"
												class="form-control numbersonly"
												onblur="validatePhoneNo('phoneNo','Please enter Phone number','Invalid phone no.');"
												type="text" value="" maxlength="13"></form:input>
											<div class="discriptionErrorMsg">
												<span id="phoneNoMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view-user"><span class="requiredFiled">*</span><span
												class="imp-star">Address</span></legend>
											<textarea id="address" name="address" class="form-control"
												onblur="validateAddress();" rows="5" maxlength="250"
												cols="50"></textarea>
											<div class="discriptionErrorMsg">
												<span id="addressMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<input type="hidden" value="sundeep.ravipati@girmiti.com"
											name="adminUserId">
										<!-- Operator section end-->
										<!-- Form Button End -->
									</div>
								</div>
								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">

										<div class="col-sm-6 widgetSearchButton no-search-criteria padding0" style="float:right">

											<input type="submit" id="user-search" value="Create"
												onclick="return validateUserRegistrationSubmit();"
												class="form-control button pull-right"> <a
												href="user-registration"
												class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend></a>
										</div>
									</fieldset>
								</div>
								<!-- Form Button End -->

							</div>
						</form:form>
						<!-- Page Form End -->
					</div>
				</div>

			</div>
			<!-- Content Block End -->
			<div id="noofRecordFetched"
				style="margin-left: 50px; padding-bottom: 10px; font-size: 14px; font-weight: bold"></div>
			<div id="table_wrapper"
				style="margin: 20px 19px; border: 1px solid #c0bfbf; display: none">
				<table id="myTable" class="display">

				</table>
			</div>

		</div>

		</article>
		<!--Article Block Start-->
		<jsp:include page="footer.jsp" />
		<div id="footer"></div>
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->
<script>
	$(document).ready(function() {
		var text = document.getElementById("userType").value;
		if (text == 'SUPER_ADMIN') {
			$("#orgId").attr("disabled", "disabled");
			$('#ptoMasterId').attr("disabled", "disabled");
		} else if (text == 'ORG_ADMIN') {
			$('#ptoMasterId').attr("disabled", "disabled");
			$("#orgId").removeAttr("disabled", "disabled");

		} else {
			$("#orgId").attr("disabled", "disabled");
			$('#ptoMasterId').removeAttr("disabled", "disabled");
		}
	});
</script>

</body>

</html>