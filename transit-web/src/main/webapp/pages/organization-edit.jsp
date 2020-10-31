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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Edit Organization</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/bootstrap.js"></script>
<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/organizationManagement.js"></script>
<script src="../js/ptoManagement.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>

</head>

<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!-- Navigation Bar Start --->
		<div id="header"></div>
		<!-- Navigation Bar End -->
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
		<article>
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Manage</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Organization</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<form action="organization-search" name="cancelForm" method="post"
				value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<span class="success-msg help-block align-center" id="sucessDiv"></span>
						<span class="has-error help-block align-center" id="errorDiv"></span>
						<form:form modelAttribute="organizationEditRequest"
							action="update-organization" method="post">
							<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Organization ID</span></legend>
											<form:input id="orgId" name="orgId"
												path="orgId" class="form-control" onblur=""
												readonly="true" type="text" value="" maxlength="12"></form:input>
											<div class="userIdMsgDiv">
												<span id="userIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Organization Name</span></legend>
											<form:input id="organizationName" name="organizationName"
												readonly="true" path="organizationName"
												onblur="this.value=this.value.trim();return validateOrganizationName();"
												class="form-control" type="text" value="" minlength="2"
												maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="organizationNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span> State</legend>
												<form:select id="state" name="state" path="state"
													onblur="validateState();" Class="form-control" maxlength="50"
													onchange="fetchcity(this.value, 'city')">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${stateList}" var="stateList">
														<form:option value="${stateList.stateName}">${stateList.stateName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="stateMsgDiv" style="color: red;">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>City</legend>
												<form:select id="city" name="city" path="city"
													class="form-control" onblur="validateCity();" type="text"
													value="" maxlength="50" autocomplete="off">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${cityList}" var="cityList">
														<form:option value="${cityList.cityName}">${cityList.cityName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="citydiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Contact Person</span></legend>
											<form:input id="contactPerson" path="contactPerson"
												name="contactPerson" class="form-control"
												onblur="validateContactPerson();" maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="contactPersonMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Mobile No</span></legend>
											<form:input id="organizationMobile" name="organizationMobile"
												path="organizationMobile" class="form-control numbersonly"
												onblur="validateMobile();" minlength="10" maxlength="13"
												type="text" value=""></form:input>
											<div class="discriptionErrorMsg">
												<span id="mobileMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">email ID</span></legend>
											<form:input id="organizationEmail" name="organizationEmail"
												path="organizationEmail" class="form-control" type="text"
												onblur="validateEmail();" value="" maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="emailMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Site URL</span></legend>
											<form:input id="siteUrl" name="siteUrl" path="siteUrl"
												class="form-control" onblur="validateSiteURL();" type="text"
												value="" maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="siteUrlMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>


									</div>
								</div>
								<!-- Operator section end-->
								<!-- Form Button End -->

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="organizationUpdate" value="Update"
												onclick="return validateOrganizationSubmit();"
												class="form-control button pull-right"> <a
												href="javascript:cancelButton()"
												class="form-control button pull-right"><legend class = "custom-legend-button"> Cancel</legend> </a>
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
</div>
</div>
</div>
</div>
<!-- Popup container End -->
</div>
<!--Container block End -->
<!-- Main Body Wrapper End -->
</body>
</html>