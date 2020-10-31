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
<title>Chatak Admin Portal</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/bootstrap.js"></script>

<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/userManagement.js"></script>
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
					class="breadcrumb-text">View</span>
			</div>

			<form action="user-search" name="cancelForm" method="post" value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>

			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>


			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="userViewRequest" action="user-view"
							method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row">
									<div class="widgetDescriptionRow">

										<c:choose>
											<c:when test="${userType  eq 'ORG_ADMIN'}">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled"></span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" disabled="true"
														onchange="fetchPtoByOrganizationId(this.value, 'ptoName')"
														Class="form-control">
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${userType  eq 'PTO_ADMIN'}">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend">PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														disabled="true" onblur="validatePto();"
														class="form-control">
														<c:forEach items="${ptoList}" var="ptoList">
															<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoIdMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</c:when>

										</c:choose>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">First Name</span></legend>
											<form:input id="firstName" name="firstName" path="firstName"
												readonly="true" class="form-control" type="text" value=""
												maxlength="25"></form:input>
											<div class="firstNameMsgDiv">
												<span id="firstNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Last Name</span></legend>
											<form:input id="lastName" name="lastName" path="lastName"
												readonly="true" class="form-control" type="text" value=""
												maxlength="25"></form:input>
											<div class="lastNameMsgDiv">
												<span id="lastNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">User Name</span></legend>
											<form:input id="userName" name="userName" path="userName"
												readonly="true" class="form-control" type="text" value=""
												maxlength="25"></form:input>
											<div class="discriptionErrorMsg">
												<span id="userNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">email ID</span></legend>
											<form:input id="email" name="email" path="email"
												class="form-control" type="text" readonly="true" value=""
												maxlength="32"></form:input>
											<div class="discriptionErrorMsg">
												<span id="emailMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">User Type</span></legend>
											<form:input id="userType" name="userType" path="userType"
												readonly="true" class="form-control" type="text" value=""
												maxlength="25"></form:input>
											<div class="userTypeMsgDiv">
												<span id="userTypeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Phone No</span></legend>
											<form:input id="phoneNo" name="phoneNo" path="phoneNumber"
												class="form-control numbersonly" readonly="true" type="text"
												value="" maxlength="13"></form:input>
											<div class="discriptionErrorMsg">
												<span id="phoneNoMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Role Name</span></legend>
											<form:input id="roleName" name="roleName" path="roleName"
												readonly="true" class="form-control"
												onblur="validateRoleName();" type="text" value=""
												maxlength="15"></form:input>
											<div class="discriptionErrorMsg">
												<span id="ptoIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Address</span></legend>
											<form:input id="address" name="address" path="address"
												class="form-control" readonly="true" rows="5"
												maxlength="100" cols="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="addressMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<input type="hidden" value="sundeep.ravipati@girmiti.com"
											name="adminUserId">
									</div>
								</div>
								<!-- Operator section end-->
								<!-- Form Button End -->

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<a href="javascript:cancelButton()"
												class="form-control button pull-right"> <legend class = "custom-legend-button">Cancel</legend> </a>
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
</body>

</html>
