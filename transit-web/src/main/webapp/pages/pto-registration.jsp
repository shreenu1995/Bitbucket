<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>PTO Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script src="../js/ptoManagement.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
</head>
<body>
	<!-- Main Body Wrapper Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid cw-admin-search-widget">

			<!-- Navigation Block Starts -->
			<jsp:include page="header.jsp" />

			<!-- Navigation Block Ends -->

			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Manage</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">PTO</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>

					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container  marginL40 ">
						<a href="pto-search">Search</a>
					</div>

					<div class="tab-header-container active-background">
						<a href="pto-registration">Create</a>
					</div>

					<!-- Tab Buttons End -->

					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<span class="success-msg help-block align-center" id="success">${success}</span>
								<span class="success-msg help-block align-center"
									class="has-error help-block align-center" id="error">${error}</span>
								<!-- Main Page Error Messages End -->

								<!-- Page Form Start -->
								<form:form modelAttribute="ptoRequest" action="pto-registration"
									method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="orgId" name="orgId"
														path="orgId" onblur="validateOrgId();"
														Class="form-control">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="orgIdMsgDiv">
														<span id="orgIdMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
													<input id="ptoName" name="ptoName" path="ptoName"
														class="form-control" onblur="this.value=this.value.trim();return validatePto();" type="text"
														value="" maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> State</legend>
													<form:select id="state" name="state" path="state"
														onblur="validateState();" Class="form-control"
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
														value="" autocomplete="off">
														<form:option value="">..:Select:..</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="citydiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Contact
														Person</legend> <input id="contactPerson" name="contactPerson"
														class="form-control" onblur="validateContactPerson();"
														type="text" value="" maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="contactPersonMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Mobile</legend> <input
														id="ptoMobile" name="ptoMobile" class="form-control"
														onblur="validateMobile();" type="text" value=""
														minlength="10" maxlength="13" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="mobileMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Email</legend> <input
														id="ptoEmail" name="ptoEmail" class="form-control"
														onblur="validateEmail();" type="text" value=""
														maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="emailMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Site URL</legend>
													<input id="siteUrl" name="siteUrl" class="form-control"
														onblur="this.value=this.value.trim();return validateSiteURL();"
														type="text" value="" maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="siteUrlMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div
													class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" id="user-search" value="Create"
														onclick="return validatePtoSubmit();"
														class="form-control button pull-right" onclick="">
													<a href="pto-registration"
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
				</div>
			</article>

			<jsp:include page="footer.jsp" />

		</div>
		<!--Container block End -->
	</div>

	<!-- Main Body Wrapper End -->

</body>
</html>