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
<link rel="shortcut icon" href="../images/favicon.png">
<title>Software Maintenance Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Tablesorter plugin End-->
<link href="../css/main.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">

<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>
<link href="../css/bootstrap-datepicker.css" rel="stylesheet">

<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<script src="../js/rome.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/setupsoftwaremaintenance.js"></script>
<script src="../js/txnReportDatagenarate.js" type="text/javascript"></script>
<script src="../js/file-download.js"></script>

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
						<span class="breadcrumb-text">Setup</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Software Maintenance</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->

					<div class="tab-header-container marginL40">
						<a href="setup-software-search">Search</a>
					</div>
					<div class="tab-header-container active-background">
						<a href="setup-software-registration">Create</a>
					</div>

					<!-- Tab Buttons End -->
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<!-- Main Page Error Messages End -->

								<!-- Page Form Start -->
								<form:form modelAttribute="setupSoftwareRegistrationRequest"
									action="setup-software-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
                                       <c:choose>
										<c:when
												test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrganizationId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
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
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class ="custom-legend"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePto();">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${ptoListData}" var="ptoListData">
																<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
											</c:when>
										<c:otherwise>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" onblur="validateOrganizationId();"
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
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														class="form-control" onblur="validatePto();">
														<c:forEach items="${ptoListData}" var="ptoListData">
															<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
														</c:forEach>

													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</c:otherwise>
									</c:choose>
									
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = 'custom-legend'><span class="requiredFiled">*</span>Inherit
													</legend>
													<form:select id="inherit" name="inherit" path="inherit"
														class="form-control" onblur="validateInherit();">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${setupSoftwareInheritPtoList}"
															var="setupSoftwareInheritPtoList">
															<form:option
																value="${setupSoftwareInheritPtoList.inherit}">${setupSoftwareInheritPtoList.inherit}</form:option>
														</c:forEach>

													</form:select>
													<div class="discriptionErrorMsg">
														<span id="inheritMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span><span
														class="imp-star">Full Version</span></legend> <select
														id="fullVersion" class="form-control"
														onblur="validateFullVersion();" name="fullVersion">
														<option value="">..:Select:..</option>
														<option>Yes</option>
														<option>No</option>
													</select>
													<div class="discriptionErrorMsg">
														<span id="fullVersionMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> Version
														Number </legend> <input id="versionNumber" name="versionNumber"
														class="form-control" maxlength="50"
														onblur="validateVersionNumber();" autocomplete='off'>

													<div class="discriptionErrorMsg">
														<span id="versionNumberMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="imp-star">Delivery Date</span></legend>
													<div class="input-group generationDate">
														<form:input type="text" id="deliveryDate"
															path="deliveryDate"
															class="form-control generationDateshow"></form:input>
														<label class="input-group-addon btn"
															for="generationDateStart"> <span
															class="fa fa-calendar" onclick="showDate('deliveryDate')"></span>
														</label>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="imp-star">Apply Date</span></legend>
													<div class="input-group generationDate">
														<form:input type="text" id="applyDate" path="applyDate"
															class="form-control generationDateshow"></form:input>
														<label class="input-group-addon btn"
															for="generationDateEnd"> <span
															class="fa fa-calendar"></span>
														</label>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow ">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Description</legend>
													<input id="description" name="description"
														class="form-control" onblur="validateDiscription();"
														type="text" maxlength="250" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="descriptionMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div
													class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" value="Create"
														class="form-control button pull-right"
														onclick="return validateSetupSoftwareSubmit();" /> <a
														href="setup-software-registration"
														class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend> </a>
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
	<script>
		rome(deliveryDate, {
			time : false,
			"inputFormat" : "DD/MM/YYYY"

		});
		rome(applyDate, {
			time : false,
			"inputFormat" : "DD/MM/YYYY"
		});
	</script>
</body>
</html>